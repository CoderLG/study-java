package cn.com.geovis.image.seed;


import cn.com.geovis.image.seed.listener.ISeedTaskListener;
import com.google.common.annotations.VisibleForTesting;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geowebcache.GeoWebCacheException;
import org.geowebcache.conveyor.ConveyorTile;
import org.geowebcache.filter.request.RequestFilter;
import org.geowebcache.layer.TileLayer;
import org.geowebcache.layer.wms.WMSLayer;
import org.geowebcache.seed.GWCTask;
import org.geowebcache.storage.StorageBroker;
import org.geowebcache.storage.TileRange;
import org.geowebcache.storage.TileRangeIterator;
import org.geowebcache.util.Sleeper;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class SeedTask extends GWCTask {
	
	private static Log log = LogFactory.getLog(SeedTask.class);

    private final TileRangeIterator trIter;

    private final TileLayer tl;

    private boolean reseed;

    private boolean doFilterUpdate;

    private StorageBroker storageBroker;

    private int tileFailureRetryCount = 5;

    private long tileFailureRetryWaitTime;

    private long totalFailuresBeforeAborting;

    private AtomicLong sharedFailureCounter;
    
    private ISeedTaskListener seedTaskListener;
    
    @VisibleForTesting
    Sleeper sleeper = Thread::sleep;
    
    /**
     * Constructs a SeedTask
     * @param sb
     * @param trIter
     * @param tl
     * @param reseed
     * @param doFilterUpdate
     */
    public SeedTask(StorageBroker sb, TileRangeIterator trIter, TileLayer tl, boolean reseed,
                    boolean doFilterUpdate) {
        this.storageBroker = sb;
        this.trIter = trIter;
        this.tl = tl;
        this.reseed = reseed;
        this.doFilterUpdate = doFilterUpdate;

        tileFailureRetryCount = 0;
        tileFailureRetryWaitTime = 100;
        totalFailuresBeforeAborting = 10000;
        sharedFailureCounter = new AtomicLong();

        if (reseed) {
            super.parsedType = TYPE.RESEED;
        } else {
            super.parsedType = TYPE.SEED;
        }
        super.layerName = tl.getName();

        super.state = STATE.READY;
    }

    @Override
    protected void doActionInternal() throws GeoWebCacheException, InterruptedException {
        super.state = STATE.RUNNING;

        // Lower the priority of the thread
        reprioritize();

        checkInterrupted();

        // approximate thread creation time
        final long START_TIME = System.currentTimeMillis();

        final String layerName = tl.getName();
        log.info(getThreadName() + " begins seeding layer : " + layerName);

        TileRange tr = trIter.getTileRange();

        checkInterrupted();

        super.tilesTotal = tileCount(tr);

        final int metaTilingFactorX = tl.getMetaTilingFactors()[0];
        final int metaTilingFactorY = tl.getMetaTilingFactors()[1];

        final boolean tryCache = !reseed;

        checkInterrupted();
        long[] gridLoc = trIter.nextMetaGridLocation(new long[3]);

        long seedCalls = 0;
        while (gridLoc != null && !this.terminate) {

            checkInterrupted();
            Map<String, String> fullParameters = tr.getParameters();

            ConveyorTile tile = new ConveyorTile(storageBroker, layerName, tr.getGridSetId(), gridLoc,
                    tr.getMimeType(), fullParameters, null, null);

            seedOneTile(tile, tryCache);

            if (log.isTraceEnabled()) {
                log.trace(getThreadName() + " seeded " + Arrays.toString(gridLoc));
            }

            // note: computing the # of tiles processed by this thread instead of by the whole group
            // also reduces thread contention as the trIter methods are synchronized and profiler
            // shows 16 threads block on synchronization about 40% the time
            final long tilesCompletedByThisThread = seedCalls * metaTilingFactorX
                    * metaTilingFactorY;

            updateStatusInfo(tilesCompletedByThisThread, START_TIME);

            checkInterrupted();
            seedCalls++;
            gridLoc = trIter.nextMetaGridLocation(gridLoc);
        }

        if (this.terminate) {
        	if(seedTaskListener != null)
        		seedTaskListener.taskBeStop(layerName, tilesTotal, tilesDone);
            log.info("Job on " + getThreadName() + " was terminated after "
                    + this.tilesDone + " tiles");
        } else {
        	if(seedTaskListener != null)
        		seedTaskListener.taskSuccess(layerName, tilesTotal, tilesDone);
            log.info(getThreadName() + " completed (re)seeding layer " + layerName
                    + " after " + this.tilesDone + " tiles and " + this.timeSpent + " seconds.");
        }

        checkInterrupted();
        if (threadOffset == 0 && doFilterUpdate) {
            runFilterUpdates(tr.getGridSetId());
        }

        super.state = STATE.DONE;
    }


    private void seedOneTile(ConveyorTile tile,boolean tryCache) throws GeoWebCacheException, InterruptedException {

    	 for (int fetchAttempt = 0; fetchAttempt <= tileFailureRetryCount; fetchAttempt++) {
             try {
                 checkInterrupted();
                 tl.seedTile(tile, tryCache);
                 break;// success, let it go
             } catch (Exception e) {
            	 handleSeedException(tile, fetchAttempt, e);
             }
         }
    }

    private void handleSeedException(ConveyorTile tile,int fetchAttempt,Exception e) throws GeoWebCacheException, InterruptedException {

        // if GWC_SEED_RETRY_COUNT was not set then none of the settings have effect, in
        // order to keep backwards compatibility with the old behaviour
        if (tileFailureRetryCount == 0) {
        	throw new GeoWebCacheException(e);
        }

        String message = e.getMessage();
        //出现这个错误说明已经无法切片，抛出错误后停止切片任务
        if(message.equals("Problem communicating with GeoServer")) {
        	log.error("Problem communicating with GeoServer");
        	throw new GeoWebCacheException(e);
        }

        long sharedFailureCount = sharedFailureCounter.incrementAndGet();
        if (sharedFailureCount >= totalFailuresBeforeAborting) {
            log.info("Aborting seed thread " + getThreadName()
                    + ". Error count reached configured maximum of "
                    + totalFailuresBeforeAborting);
            super.state = STATE.DEAD;
            return;
        }
        String logMsg = "Seed failed at " + tile.toString() + " after "
                + (fetchAttempt + 1) + " of " + (tileFailureRetryCount + 1)
                + " attempts.";
        if (fetchAttempt < tileFailureRetryCount) {
            log.debug(logMsg);
            if (tileFailureRetryWaitTime > 0) {
                log.trace("Waiting " + tileFailureRetryWaitTime
                        + " before trying again");
                waitToRetry();
            }
        } else {
            log.info(logMsg
                    + " Skipping and continuing with next tile. Original error: "
                    + e.getMessage());
        }

    }

    private void reprioritize() {
        Thread.currentThread().setPriority(
                (Thread.NORM_PRIORITY + Thread.MIN_PRIORITY) / 2);
    }

    private void waitToRetry() throws InterruptedException {
        sleeper.sleep(tileFailureRetryWaitTime);
    }

    private String getThreadName() {
        return Thread.currentThread().getName();
    }

    /**
     * helper for counting the number of tiles
     * 
     * @param tr
     * @return -1 if too many
     */
    private long tileCount(TileRange tr) {

        final int startZoom = tr.getZoomStart();
        final int stopZoom = tr.getZoomStop();

        long count = 0;

        for (int z = startZoom; z <= stopZoom; z++) {
            long[] gridBounds = tr.rangeBounds(z);

            final long minx = gridBounds[0];
            final long maxx = gridBounds[2];
            final long miny = gridBounds[1];
            final long maxy = gridBounds[3];

            long thisLevel = (1 + maxx - minx) * (1 + maxy - miny);

            if (thisLevel > (Long.MAX_VALUE / 4) && z != stopZoom) {
                return -1;
            } else {
                count += thisLevel;
            }
        }

        return count;
    }

    /**
     * Helper method to update the members tracking thread progress.
     * 
     * @param layer
     * @param tilesCount
     * @param start_time
     */
    private void updateStatusInfo(long tilesCount, long startTime) {

    	tilesCount = tilesCount == 0 ? 1 : tilesCount;
        // working on tile
        this.tilesDone = tilesCount;

        // estimated time of completion in seconds, use a moving average over the last
        this.timeSpent = (int) (System.currentTimeMillis() - startTime) / 1000;

        int threadCount = sharedThreadCount.get();
        long timeTotal = Math.round((double) timeSpent
                * (((double) tilesTotal / threadCount) / (double) tilesCount));

        this.timeRemaining = (int) (timeTotal - timeSpent);
    }

    /**
     * Updates any request filters
     */
    private void runFilterUpdates(String gridSetId) {
        // We will assume that all filters that can be updated should be updated
        List<RequestFilter> reqFilters = tl.getRequestFilters();
        if (reqFilters != null && !reqFilters.isEmpty()) {
            Iterator<RequestFilter> iter = reqFilters.iterator();
            while (iter.hasNext()) {
                RequestFilter reqFilter = iter.next();
                if (reqFilter.update(tl, gridSetId)) {
                    log.info("Updated request filter " + reqFilter.getName());
                } else {
                    log.debug("Request filter " + reqFilter.getName()
                            + " returned false on update.");
                }
            }
        }
    }

    public void setFailurePolicy(int tileFailureRetryCount, long tileFailureRetryWaitTime,
            long totalFailuresBeforeAborting, AtomicLong sharedFailureCounter) {
        this.tileFailureRetryCount = tileFailureRetryCount;
        this.tileFailureRetryWaitTime = tileFailureRetryWaitTime;
        this.totalFailuresBeforeAborting = totalFailuresBeforeAborting;
        this.sharedFailureCounter = sharedFailureCounter;
    }

    @Override
    protected void dispose() {
        if (tl instanceof WMSLayer) {
            ((WMSLayer) tl).cleanUpThreadLocals();
        }
    }

	public ISeedTaskListener getSeedTaskListener() {
		return seedTaskListener;
	}

	public void setSeedTaskListener(ISeedTaskListener seedTaskListener) {
		this.seedTaskListener = seedTaskListener;
	}
    
    
    

}
