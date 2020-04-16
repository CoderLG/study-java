package lg.listener;

import org.springframework.context.ApplicationEvent;

/**
 * author: LG
 * date: 2019-10-31 17:01
 * desc:
 */
public class MyEvent extends ApplicationEvent {


    private static final long serialVersionUID = -283953572180955496L;
    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public MyEvent(Object source) {
        super(source);
    }
}
