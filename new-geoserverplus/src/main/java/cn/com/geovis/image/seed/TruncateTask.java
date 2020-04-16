package cn.com.geovis.image.seed;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.geowebcache.GeoWebCacheException;
import org.geowebcache.filter.request.RequestFilter;
import org.geowebcache.layer.TileLayer;
import org.geowebcache.seed.GWCTask;
import org.geowebcache.storage.StorageBroker;
import org.geowebcache.storage.TileRange;

import java.util.Iterator;
import java.util.List;

class TruncateTask extends GWCTask {
    private static Log log = LogFactory.getLog(TruncateTask.class);

    private final TileRange tr;

    private final TileLayer tl;

    private final boolean doFilterUpdate;

    private final StorageBroker storageBroker;

    public TruncateTask(StorageBroker sb, TileRange tr, TileLayer tl, boolean doFilterUpdate) {
        this.storageBroker = sb;
        this.tr = tr;
        this.tl = tl;
        this.doFilterUpdate = doFilterUpdate;

        super.parsedType = TYPE.TRUNCATE;
        super.layerName = tl.getName();
    }

    @Override
    protected void doActionInternal() throws GeoWebCacheException, InterruptedException {
        super.state = STATE.RUNNING;
        checkInterrupted();
        try {

        	log.info("开始执行【" + tr.getLayerName()+"】图层的删除任务");
            storageBroker.delete(tr);
            log.info("【" + tr.getLayerName()+"】图层的删除任务执行完毕");

        } catch (Exception e) {
        	super.state = STATE.DEAD;
            log.error("【" + tr.getLayerName()+"】图层的删除任务执行出现异常，异常原因:"+e.getMessage());
            log.error("During truncate request", e);
        }

        checkInterrupted();
        if (doFilterUpdate) {
            runFilterUpdates();
        }

        if (super.state != STATE.DEAD) {
            super.state = STATE.DONE;
            log.debug("Completed truncate request.");
        }
    }

    /**
     * Updates any request filters
     */
    private void runFilterUpdates() {
        // We will assume that all filters that can be updated should be updated
        List<RequestFilter> reqFilters = tl.getRequestFilters();
        if (reqFilters != null && !reqFilters.isEmpty()) {
            Iterator<RequestFilter> iter = reqFilters.iterator();
            while (iter.hasNext()) {
                RequestFilter reqFilter = iter.next();
                if (reqFilter.update(tl, tr.getGridSetId())) {
                    log.debug("Updated request filter " + reqFilter.getName());
                } else {
                    log.debug("Request filter " + reqFilter.getName()
                            + " returned false on update.");
                }
            }
        }
    }

    @Override
    protected void dispose() {
        // do nothing
    }

}

