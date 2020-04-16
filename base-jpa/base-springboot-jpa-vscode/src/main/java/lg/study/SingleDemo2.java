package lg.study;

/**
 * author: LG
 * date: 2019-10-14 19:45
 * desc:
 */

public class SingleDemo2 {
    /**
     * 真正的单例返回自己，不是自己的话 没有必要，因为也可以单独 去new
     * volatile 保证jvm 内存中的变量同步，高并发的时候有大用
     *
     */
    public  static SingleDemo2 single;

    /**
     * static 这种有赋值， 在对象new 初始化的时候会被执行
     */
 //   public static  String ss = "sdf";

    /**
     * private 保证别的方法无法调用
     * No visible constructors in class
     */
   // private SingleDemo2(){};

    /**
     * static 在对象new 初始化的时候 不会被调用
     * 双检索，保证 并发的时候 可靠
     * @return
     */
    public static SingleDemo2 getSingle(){
        if(single == null){
            synchronized (SingleDemo2.class){
                System.out.println("进入synchronized");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(single == null){   //可能两个线程在等待
                    System.out.println("进入 single == null");
                    single = new SingleDemo2();
                }


            }
        }
        return  single;
    }



}
