package lg.study;

/**
 * author: LG
 * date: 2019-10-14 19:45
 * desc:
 */
public class SingleDemo1 {
    /**
     * 真正的单例返回自己，不是自己的话 没有必要，因为也可以单独 去new
     * static 这种有赋值， 在对象new 初始化的时候会被执行
     *
     */
    public static  SingleDemo1 single = new SingleDemo1();


    /**
     * private 保证别的方法无法调用
     */
    private SingleDemo1(){};

    /**
     * static 在对象new 初始化的时候 不会被调用
     *
     * @return
     */
    public  static SingleDemo1 getSingle(){
        return  single;
    }



}
