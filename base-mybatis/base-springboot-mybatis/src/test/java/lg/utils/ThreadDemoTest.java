package lg.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * author: LG
 * date: 2019-09-20 21:01
 * desc:
 */
public class ThreadDemoTest {

    @Test
    public void testThread (){
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.doIt();
    }
}