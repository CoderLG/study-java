package lg.study;


import lg.utils.SystemUtils;

/**
 * author: LG
 * date: 2020-01-08 15:21
 * desc:
 */
public class SystemTestMain {
    public static void main(String[] args) {
        SystemUtils.getBaseSystem(1024*1024);
        SystemUtils.getSystemTotalThread();
    }
}
