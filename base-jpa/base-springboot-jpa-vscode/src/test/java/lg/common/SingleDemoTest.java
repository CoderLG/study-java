package lg.common;

import lg.study.SingleDemo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * author: LG
 * date: 2019-10-15 09:37
 * desc:
 */
@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
public class SingleDemoTest {

    @Test
    public void getSingleDemo2() {
   //     SingleDemo2 single = SingleDemo2.getSingle();
   //     SingleDemo2 single2 = SingleDemo2.getSingle();
    //    System.out.println(single == single2);
    }

    @Test
    public void getSingle() {
        SingleDemo single = SingleDemo.getSingle();
        SingleDemo single2 = SingleDemo.getSingle();
        System.out.println(single == single2);
    }

    @Test
    public void getSing() {
        String str = "mm";
        str="ss";
        String ss = "ss";

        /**
         *  按理来说 == 会判断内存地址
         *  equals  判断内容
         */
        System.out.println(str == ss);
        System.out.println(str.equals(ss));
    }


}