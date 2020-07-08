package lg.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * author: LG
 * date: 2020-04-26 15:19
 * desc:
 */
public class TimeUtilsTest {

    @Test
    public void format() {
        String format = TimeUtils.format(1587885091456l);
        System.out.println(format);
    }
}