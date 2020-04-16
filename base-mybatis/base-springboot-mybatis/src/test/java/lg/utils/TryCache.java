package lg.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * author: LG
 * date: 2019-09-23 14:14
 * desc:
 */
@Slf4j
public class TryCache {

    @Test
    public void tesTryCahce(){
        try {
            System.out.println("这里，try start");
            int a = 1/0;

        }catch (Exception e){
            log.error(e.getMessage());
            System.out.println("这里，cache Exception");
        }finally {
            log.warn(" 这里，there is finally");
            System.out.println("这里，there is finally");
        }

        System.out.println("这里，return");

    }
}
