package lg.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * author: LG
 * date: 2019-09-06 13:56
 * desc:
 */
@Configuration
@Data
public class BaseConfig {
    @Value("${spring.application.version}")
    private String version;

}
