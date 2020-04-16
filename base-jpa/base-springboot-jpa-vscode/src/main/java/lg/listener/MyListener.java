package lg.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * author: LG
 * date: 2019-10-31 17:04
 * desc:  监听器 springmvc  拦截器（aop） spring
 * 有很多listener 的注册方法，
 * 只弄个了最简的一个
 */
@Slf4j
@Component
public class MyListener {

    @EventListener
    public void listener(MyEvent event){

        log.warn(String.format("%s监听到事件：%s",MyListener.class.getName(),event.getSource()));
    }
}
