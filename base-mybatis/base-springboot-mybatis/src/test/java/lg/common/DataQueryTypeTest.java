package lg.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * author: LG
 * date: 2019-09-23 16:29
 * desc:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class DataQueryTypeTest {

    @Test
    public void getCode() {
        RestError[] values = RestError.values();

        RestError baseimageQueryDadabaseError = RestError.BASEIMAGE_QUERY_DADABASE_ERROR;
        RestError aaa = baseimageQueryDadabaseError.setReason("aaa");

    }
    @Test
    public void testString (){
        String str = "中国%s%%是否";
        String name = "小明";
        String format = String.format(str, name, name, name);
        System.out.println(format);
    }

}