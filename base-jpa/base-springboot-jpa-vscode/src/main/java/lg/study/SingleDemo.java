package lg.study;

/**
 * author: LG
 * date: 2019-10-14 19:45
 * desc:
 */
public class SingleDemo{
    /**
     * 真正的单例返回自己，不是自己的话 没有必要，因为也可以单独 去new
     *  保证volatile jvm 内存中的变量同步，第一个高并发的时候有大用
     * 经过SingleController 验证，volatile 没用 也许新的jvm已经优化过了
     */
  //  public static volatile SingleDemo single;
    public static  SingleDemo single;

    /**
     * static 这种有赋值， 在对象new 初始化的时候会被执行
     */
 //   public static  String ss = "sdf";

    /**
     * private 保证别的方法无法调用
     */
    private SingleDemo(){};

    /**
     * static 在对象new 初始化的时候 不会被调用
     * 双检索，保证 并发的时候 可靠
     * @return
     */
    public  static SingleDemo getSingle(){
        if(single == null){
            synchronized (SingleDemo.class){
                if(single == null)              //可能两个线程在等待
                    single = new SingleDemo();
            }
        }
        return  single;
    }



}
