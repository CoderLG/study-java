package lg.controller;

import cn.hutool.core.thread.ThreadUtil;
import io.swagger.annotations.Api;

import lg.task.StudyTask;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ThreadUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Target;
import java.util.concurrent.CountDownLatch;

/**
 * author: LG
 * date: 2020-04-26 15:26
 * desc:
 */
@RestController
@Api(tags = "多线程任务")
@Slf4j
public class TaskController {

    @GetMapping("/runTask")
    public void runTask()  {
        log.info("主线程开始执行");
        CountDownLatch countDownLatch = ThreadUtil.newCountDownLatch(3);

        for (int i = 0; i < 3; i++) {
            StudyTask studyTask = new StudyTask(i, 2, countDownLatch);
            ThreadUtil.execute(()->{
                studyTask.run();
            });
        }
        log.info("主线程开始等待");
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("主线程开始执行结束");
    }


}
