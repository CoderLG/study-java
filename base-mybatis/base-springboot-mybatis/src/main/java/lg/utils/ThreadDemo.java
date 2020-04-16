package lg.utils;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;


@Slf4j
@Component
public class ThreadDemo {


    private AtomicInteger count = new AtomicInteger(0);

    public void doIt() {

        Connection conn = null;
        Executor executor = Executors.newFixedThreadPool(2);
        try {
            long start = System.currentTimeMillis();
            int i = 1;
            boolean flag = true;
            while (flag) {
                System.out.println("查询当前I的值是："+ i);
                    int finalI = i;
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            processObj(finalI);
                        }
                    });
                if(i % 20==0){
                    int totalNu = count.get();
                    flag=false;
                    log.info("完成："+totalNu+"速度："+totalNu*1.0/(System.currentTimeMillis()-start)+"/ms");
                }
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private int processObj(int i){
        System.out.println("i--->" + i);
        int num = count.addAndGet(1);
        System.out.println("num--->" + num);
        return num;
    }
}
