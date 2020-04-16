package lg.controller;

import io.swagger.annotations.Api;
import lg.domain.GFGX_Y_DMK_DMSJ_DAO;
import lg.domain.ToponymData;
import lg.service.impl.ESServiceImpl;
import lg.service.impl.PlaceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author: LG
 * date: 2019-09-11 16:37
 * desc:
 */

@Api(tags = "从pg向es导入数据")
@RestController
@Slf4j
public class Transport {

    @Autowired
    private PlaceServiceImpl placeService;

    @Autowired
    private ESServiceImpl esService;






    @GetMapping("autoSaveToponymDatas")
    public void autoSaveToponymDatas(@RequestParam int threadTotal, @RequestParam int oneCount,@RequestParam int start,@RequestParam int end) throws InterruptedException {
        //一次保存15960000条
       // int threadCount = 2;
       // int selectCount = 1000;
        long saveStart = System.currentTimeMillis();
        log.info( "查询任务开始，当前时间--> " + saveStart);
        for (int i = start; i < end;) {
            long findStart = System.currentTimeMillis();
            List<ToponymData> some = placeService.findToponymData(oneCount,i);
            long findEnd = System.currentTimeMillis();
            int endN = i + oneCount;
            log.info( "查询第"+i+"条到"+endN+"用时--> " + (findEnd - findStart));

            esService.savetoponymDatas(some);


            //睡一秒等待getSaveListSize 的同步
            Thread.sleep(1000);

            log.info( "目前第"+esService.getThreadCount()+"个线程正在等到执行任务。");
            while ((esService.getThreadCount() % (threadTotal+1)) == 0){
                log.info( "我是查询，我正在等待保存! 目前第"+esService.getThreadCount()+"个线程正在等到执行任务,我是不允许的！！");
                Thread.sleep(5000);
        }
            i = i+oneCount;
        }
        long saveEnd= System.currentTimeMillis();
        //log.info( "查询任务全部执行完成用时" + (saveEnd - saveStart));
    }












    @GetMapping("hand_save_GFGX_Y_DMK_DMSJ_Arr")
    public void save_GFGX_Y_DMK_DMSJ_Arr(@RequestParam int limit, @RequestParam int offset){
        long findStart = System.currentTimeMillis();
        List<GFGX_Y_DMK_DMSJ_DAO> some = placeService.find_GFGX_Y_DMK_DMSJ_Arr(limit,offset);
        long findEnd = System.currentTimeMillis();
        log.info(limit + "条查询用时--> " + (findEnd - findStart));
        esService.hand_save_GFGX_Y_DMK_DMSJ_Arr(some);
    }

    @GetMapping("auto_save_GFGX_Y_DMK_DMSJ_Arr")
    public void auto_save_GFGX_Y_DMK_DMSJ_Arr() throws InterruptedException {
        //一次保存15960000条

        int threadCount = 2;
        int selectCount = 10000;
        for (int i = 0,j=0; i < 15960001;) {
            long findStart = System.currentTimeMillis();
            List<GFGX_Y_DMK_DMSJ_DAO> some = placeService.find_GFGX_Y_DMK_DMSJ_Arr(selectCount,i);
            long findEnd = System.currentTimeMillis();
            int end = i + selectCount;
            log.info( "查询第"+i+"条到"+end+"用时--> " + (findEnd - findStart));
            esService.auto_save_GFGX_Y_DMK_DMSJ_Arr(some);
            //睡一秒等待getSaveListSize 的同步
            Thread.sleep(1000);
            log.info( "threadCount-->"+esService.getSaveListSize());
            while ((esService.getSaveListSize() % threadCount) == 0){
                log.info( "我是查询，我正在等待保存，threadCount-->"+esService.getSaveListSize());
                Thread.sleep(5000);
            }
            i = i+selectCount;

        }
    }

    @GetMapping("auto_save_GFGX_Y_DMK_DMSJ_Arr_demo")
    public void auto_save_GFGX_Y_DMK_DMSJ_Arr_demo() throws InterruptedException {
        //一次保存15960000条

        for (int i = 0; i < 280001;) {
            long findStart = System.currentTimeMillis();
            List<GFGX_Y_DMK_DMSJ_DAO> some = placeService.find_GFGX_Y_DMK_DMSJ_Arr(140000,i);
            long findEnd = System.currentTimeMillis();
            int start = i;
            int end = start + 140000;
            i = i+140000;
            log.info( "查询第"+start+"条到"+end+"用时--> " + (findEnd - findStart));
            esService.auto_save_GFGX_Y_DMK_DMSJ_Arr(some);
        }
    }











}
