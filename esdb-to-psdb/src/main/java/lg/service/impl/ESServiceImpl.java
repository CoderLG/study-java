package lg.service.impl;

import lg.dao.es.LGDMSJDao;
import lg.dao.es.ToponymDataDao;
import lg.domain.GFGX_Y_DMK_DMSJ_DAO;
import lg.domain.ToponymData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

;

/**
 * dmsj表的相关操作
 * @author Morris
 * @date 10:29 2019/9/10
 */
@Service
@Slf4j
public class ESServiceImpl{

    private int threadCount = 1;
    @Autowired
    private ToponymDataDao toponymDataDao;

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    @Async
    public void savetoponymDatas(List<ToponymData> arr) {
        threadCount++;
        long saveStart = System.currentTimeMillis();
        toponymDataDao.saveAll(arr);
        long saveEnd = System.currentTimeMillis();
        threadCount --;
        log.info("当前时间--> "+System.currentTimeMillis()+",有条线程保存完成，用时-->" + (saveEnd - saveStart));
    }





    @Autowired
    private LGDMSJDao lgdmsjDao;
    private int saveListSize = 0;
    public int getSaveListSize() {
        return saveListSize;
    }

    public void setSaveListSize(int saveListSize) {
        this.saveListSize = saveListSize;
    }

    @Async
    public void hand_save_GFGX_Y_DMK_DMSJ_Arr(List<GFGX_Y_DMK_DMSJ_DAO> arr) {
        long saveStart = System.currentTimeMillis();
        lgdmsjDao.saveAll(arr);
        long saveEnd = System.currentTimeMillis();
        log.info("保存用时--> " + (saveEnd - saveStart));
    }

    @Async
    public void auto_save_GFGX_Y_DMK_DMSJ_Arr(List<GFGX_Y_DMK_DMSJ_DAO> arr) {
        int me = ++saveListSize;
        long saveStart = System.currentTimeMillis();
        lgdmsjDao.saveAll(arr);
        long saveEnd = System.currentTimeMillis();
        saveListSize --;
        log.info(me+"保存第"+100000+"条用时--> " + (saveEnd - saveStart));
    }



    public Long get_GFGX_Y_DMK_DMSJ_count() {
        return lgdmsjDao.count();
    }

    public void save_GFGX_Y_DMK_DMSJ(GFGX_Y_DMK_DMSJ_DAO arr) {
        long saveStart = System.currentTimeMillis();
        lgdmsjDao.save(arr);
        long saveEnd = System.currentTimeMillis();
        log.info("保存用时--> " + (saveEnd - saveStart));
    }

    public GFGX_Y_DMK_DMSJ_DAO get_GFGX_Y_DMK_DMSJ_by_id(String id) {
        return lgdmsjDao.findById(id).get();
    }

}
