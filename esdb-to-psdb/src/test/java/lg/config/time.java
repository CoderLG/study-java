package lg.config;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * author: LG
 * date: 2019-09-11 17:04
 * desc:
 */
public class time {
    @Test
    public void getTime () throws InterruptedException {

        Date date = new Date(System.currentTimeMillis());
        long startEnd = System.currentTimeMillis();
        Thread.sleep(500);
        long saveEnd = System.currentTimeMillis();
        System.out.println( startEnd - saveEnd);
    }
    @Test
    public  void  boo(){
        System.out.println(6100000/(1568629856318l - 1568628634187l));
    }
}
