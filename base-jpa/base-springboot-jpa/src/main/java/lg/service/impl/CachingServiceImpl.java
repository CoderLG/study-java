package lg.service.impl;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * author: LG
 * date: 2019-09-05 13:23
 * desc:
 *
 * postConstruct bean被注入到容器后立刻执行此方法
 */
@Service
@Slf4j
@Data
public class CachingServiceImpl {
    private Map map = new HashMap<String,String>();

    //postConstruct bean被注入到容器后立刻执行此方法
    @PostConstruct
    public void initMap() {
        map.put("init","helloCache");
        log.error(""+ map.toString());
    }


    public void addMap(String key, String value) {
        map.put(key, value);
        log.error(map.toString());
    }


}
