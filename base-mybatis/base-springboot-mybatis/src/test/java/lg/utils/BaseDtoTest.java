package lg.utils;

import lg.domain.TUser;
import lg.dto.BaseDto;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * author: LG
 * date: 2019-09-21 17:01
 * desc:
 */
public class BaseDtoTest {

    @Test
    public void dtoTest() {
        BaseDto<Object> objectBaseDto = new BaseDto<>();
        TUser tUser = new TUser();
        tUser.setTName("mm");
        objectBaseDto.setData(tUser);
        Object data = objectBaseDto.getData();
        System.out.println(data.toString());
    }
}