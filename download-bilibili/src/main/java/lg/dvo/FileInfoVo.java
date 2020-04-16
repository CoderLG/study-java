package lg.dvo;

import lombok.Data;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;

import java.io.Serializable;

/**
 * author: LG
 * date: 2019-09-04 09:43
 * desc:
 */
@Data
public class FileInfoVo implements Serializable {
    private Long length;
    private byte[] bytes;
    private HttpHeaders headers;
    private InputStreamResource inputStreamResource;
}