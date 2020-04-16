package lg.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * author: LG
 * date: 2020-03-12 15:06
 * desc:
 */
public class StaticUtilTest {

    @Test
    public void getProperty() {
        StaticUtils staticUtil1 = new StaticUtils();
        StaticUtils staticUtil2 = new StaticUtils();

        System.out.println(staticUtil1 == staticUtil2);
        System.out.println(staticUtil1.getPropertyObj() == staticUtil2.getPropertyObj());
    }

    @Test
    public void setProperty() {
    }

}