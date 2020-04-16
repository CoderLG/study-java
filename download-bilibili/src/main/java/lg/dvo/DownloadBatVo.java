package lg.dvo;


import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * author: LG
 * date: 2019-08-18 13:06
 * desc:
 */
@Getter
@Setter
@Slf4j
@Component
public class DownloadBatVo {

    private String name;
    private String age;

    @Override
    public String toString() {
        return super.toString();
    }

}
