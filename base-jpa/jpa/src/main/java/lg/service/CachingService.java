package lg.service;

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
 */
@Service
@Slf4j
@Data
public class CachingService {
    private Map map = new HashMap<String,String>();

    //postConstruct 初始化bean的时候被创建
    @PostConstruct
    public void initMap() {
        map.put("init","helloCache");
        log.warn(map.toString());
    }


    public void addMap(String key, String value) {
        map.put(key, value);
        log.error(map.toString());
    }


}
