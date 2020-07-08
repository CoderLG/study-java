package lg.task;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

/**
 * author: LG
 * date: 2020-04-26 15:26
 * desc:
 */
@Slf4j
public class StudyTask implements  Runnable{

    private Integer current;
    private Integer target;
    private CountDownLatch countDownLatch;
    public  StudyTask(Integer current, Integer target,CountDownLatch countDownLatch ){
        this.current = current;
        this.target = target;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public void run() {
        log.info(Thread.currentThread().getName() + " -->" +countDownLatch.getCount() );

        try {
            Thread.sleep(1000 * 10);
            log.info(Thread.currentThread().getName() + " -->" + "任务执行结束");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(current == target)
            log.info(Thread.currentThread().getName() + " -->" + "current = target");

        countDownLatch.countDown();

    }
}
