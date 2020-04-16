package cn.com.geovis.image.seed;


import cn.com.geovis.image.seed.listener.ISeedTaskListener;
import cn.com.geovis.image.seed.listener.SeedTaskListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geowebcache.GeoWebCacheException;
import org.geowebcache.layer.TileLayer;
import org.geowebcache.seed.GWCTask;
import org.geowebcache.seed.GWCTask.TYPE;
import org.geowebcache.seed.SeedRequest;
import org.geowebcache.seed.TileBreeder;
import org.geowebcache.storage.TileRange;
import org.geowebcache.storage.TileRangeIterator;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class ImageTileBreeder extends TileBreeder{

	private static Log log = LogFactory.getLog(ImageTileBreeder.class);
	
	/**
     * How many retries per failed tile. 0 = don't retry, 1 = retry once if failed, etc
     */
    private int tileFailureRetryCount = 3;

    /**
     * How much (in milliseconds) to wait before trying again a failed tile
     */
    private long tileFailureRetryWaitTime = 100;

    /**
     * How many failures to tolerate before aborting the seed task. Value is shared between all the
     * threads of the same run.
     */
    private long totalFailuresBeforeAborting = 1000;
	
	 /**
     * Create tasks to manipulate the cache (Seed, truncate, etc).  They will still need to be dispatched.
     * 
     * @param tr The range of tiles to work on.
     * @param tl The layer to work on.  Overrides any layer specified on tr.
     * @param type The type of task(s) to create
     * @param threadCount The number of threads to use, forced to 1 if type is TRUNCATE
     * @return Array of tasks.  Will have length threadCount or 1.
     * @throws GeoWebCacheException
     */
    public GWCTask[] createTasks(TileRange tr, TileLayer tl, GWCTask.TYPE type, int threadCount,
            boolean filterUpdate,String imageServiceUrl){

        if (threadCount < 1) {
            log.trace("Forcing thread count to 1");
            threadCount = 1;
        }

        TileRangeIterator trIter = new TileRangeIterator(tr, tl.getMetaTilingFactors());

        GWCTask[] tasks = new GWCTask[threadCount];

        AtomicLong failureCounter = new AtomicLong();
        AtomicInteger sharedThreadCount = new AtomicInteger();
        
        ISeedTaskListener seedTaskListener = new SeedTaskListener(threadCount,imageServiceUrl);
        
        for (int i = 0; i < threadCount; i++) {
            if (type != TYPE.TRUNCATE) {
            	 SeedTask task = (SeedTask) createSeedTask2(type, trIter, tl, filterUpdate);
                 task.setSeedTaskListener(seedTaskListener);
                 task.setFailurePolicy(tileFailureRetryCount, tileFailureRetryWaitTime,
                         totalFailuresBeforeAborting, failureCounter);
                 tasks[i] = task;
            }else{
                tasks[i] = createTruncateTask2(trIter, tl, filterUpdate);
            }
            tasks[i].setThreadInfo(sharedThreadCount, i);
        }

        return tasks;
    }
    
    private GWCTask createTruncateTask2(TileRangeIterator trIter, TileLayer tl,
            boolean doFilterUpdate) {
        return new TruncateTask(getStorageBroker(), trIter.getTileRange(), tl, doFilterUpdate);
    }
    
    /**
     * Create a Seed/Reseed task.
     * @param type the type, SEED or RESEED
     * @param trIter a collection of tile ranges
     * @param tl the layer
     * @param doFilterUpdate 
     * @return
     * @throws IllegalArgumentException
     */
    private GWCTask createSeedTask2(TYPE type, TileRangeIterator trIter, TileLayer tl,
            boolean doFilterUpdate) {

        switch (type) {
        case SEED:
            return new SeedTask(getStorageBroker(), trIter, tl, false, doFilterUpdate);
        case RESEED:
            return new SeedTask(getStorageBroker(), trIter, tl, true, doFilterUpdate);
        default:
            throw new IllegalArgumentException("Unknown request type " + type);
        }
    }
    

	public void seed(String layerName, SeedRequest sr, String imageServiceUrl) throws GeoWebCacheException {
		
		 TileLayer tl = findTileLayer(layerName);

	     TileRange tr = createTileRange(sr, tl);

	     GWCTask[] tasks = createTasks(tr, tl, sr.getType(), sr.getThreadCount(),  sr.getFilterUpdate(),imageServiceUrl);

	     dispatchTasks(tasks);
		
	}
    
}
