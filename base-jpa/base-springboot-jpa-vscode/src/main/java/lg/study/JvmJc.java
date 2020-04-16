package lg.study;



import lg.utils.JvmUtil;

import java.util.HashMap;
import java.util.Vector;

/**
 * author: LG
 * date: 2020-01-08 14:35
 * desc:
 */
public class JvmJc {

    private static void getSimpleArgs(){

        /**
         * -Xms5m -Xmx20m -XX:+PrintGCDetails -XX:+UseSerialGC -XX:+PrintCommandLineFlags
         * 初始化  最大     打印gc             使用串行的gc方法   打印命令行
         *
         * [GC (Allocation Failure) [DefNew: 1117K->112K(1856K), 0.0011067 secs] 1697K->883K(5952K), 0.0011290 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
         * 解释 https://blog.csdn.net/zc19921215/article/details/83029952
         *
         *
         */
        System.out.println("程序初始化");
        JvmUtil.printJvmSpace(1024*1024);

        byte[] bytes = new byte[10*1024 * 1024];

        System.out.println("程序使用1M之后");
        JvmUtil.printJvmSpace(1024*1024);

    }

    private static void initYoung(){

        /**
         *
         * -Xms16m -Xmx20m -Xmn1m -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC -XX:+PrintCommandLineFlags
         *
         * -Xms16m 初始化
         * -Xmx20m  最大
         * -Xmn1m 年轻代
         * -XX:SurvivorRatio=2 eden:s0=eden:s1=2:1
         * -XX:+PrintGCDetails 打印gc
         * -XX:+UseSerialGC  使用串行的gc方法
         * -XX:+PrintCommandLineFlags 打印命令行
         *
         *老年代 = total - 年轻代
         *
         */


    }

    private static void initTenured(){
        /**
         *
         * -Xms16m -Xmx20m  -XX:NewRatio=3 -XX:SurvivorRatio=2 -XX:+PrintGCDetails -XX:+UseSerialGC -XX:+PrintCommandLineFlags
         *
         * -Xms16m 初始化
         * -Xmx20m  最大
         * -XX:NewRatio=2  老年代:新生代=2:1
         * -XX:SurvivorRatio=2 eden:s0=eden:s1=2:1
         * -XX:+PrintGCDetails 打印gc
         * -XX:+UseSerialGC  使用串行的gc方法
         * -XX:+PrintCommandLineFlags 打印命令行
         *
         *
         */
    }

    private static void OOM(){

        /**
         *
         * -Xms4m -Xmx4m -XX:NewRatio=1 -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=./oom.dump -XX:+PrintGCDetails -XX:+UseSerialGC -XX:+PrintCommandLineFlags
         *
         * -Xms16m 初始化
         * -Xmx20m  最大
         * -XX:NewRatio=1  老年代:新生代=1:1
         *
         * -XX:+HeapDumpOnOutOfMemoryError  导出内存异常信息
         * -XX:HeapDumpPath=./oom.dump      导出路径
         *
         * -XX:+PrintGCDetails 打印gc
         * -XX:+UseSerialGC  使用串行的gc方法
         * -XX:+PrintCommandLineFlags 打印命令行
         *
         */
        Vector vector = new Vector();
        for (int i = 1; i <= 6; i++) {
            System.out.println("第 " + i + " 次申请内存");
            byte[] bytes = new byte[1024 * 1024];
            vector.add(bytes);
            System.out.println(bytes.length +  " 内存被申请");
        }
    }

    private static void toTenured(){
        /**
         *
         * -Xms1024m -Xmx1024m -XX:MaxTenuringThreshold=15 -XX:+PrintGCDetails -XX:+UseSerialGC
         *
         * -Xms20m 初始化
         * -Xmx20m  最大
         *
         *
         *
         * -XX:+PrintGCDetails 打印gc
         * -XX:+UseSerialGC  使用串行的gc方法
         * -XX:+PrintCommandLineFlags 打印命令行
         *
         */
        HashMap<Integer, byte[]> hashMap = new HashMap<Integer, byte[]>();
        for (int i = 0; i < 15; i++) {
           // System.out.println("hashmap " + i);
            byte[] b = new byte[1024 * 1024];
            hashMap.put(i, b);
        }

        for (int i = 0; i < 6000; i++) {
          //  System.out.println("bates " + i);
            byte[] bytes = new byte[1024 * 1024];
        }

    }


    public static void main(String[] args) {
//        getSimpleArgs();
//        initYoung();
//        OOM();

        toTenured();


    }

}
