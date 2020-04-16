package lg.common.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * author: LG
 * date: 2019-08-18 14:22
 * desc:
 *
 * Async
 * 异步方法调用
 *
 */

@Service
public class AsyncServiceImpl {

    @Async
    public String getAsync(){
        System.out.println("2");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("3");
        return "Async res";
    }
}
