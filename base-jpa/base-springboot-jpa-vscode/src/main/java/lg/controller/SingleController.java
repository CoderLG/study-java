package lg.controller;

import io.swagger.annotations.Api;
import lg.study.SingleDemo2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * author: LG
 * date: 2019-10-15 10:45
 * desc:
 *
 * 双检锁
 * 共用同一个单例 线程同步
 */

@Api(tags = "线程池中的单例模式",description = "线程池和单例模式")
@RestController
public class SingleController {

    @GetMapping("testSingle")
    public String testSingle(){
        System.out.println("testSingle");

        Executor executor = Executors.newFixedThreadPool(5);
        try {
            long start = System.currentTimeMillis();
            int i = 1;
            boolean flag = true;
            while (flag) {

                int finalI = i;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("查询当前I的值是："+ finalI);
                        SingleDemo2.getSingle();
                    }
                });
                if(i % 3==0){

                    flag=false;

                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "testSingle";
    }
}
