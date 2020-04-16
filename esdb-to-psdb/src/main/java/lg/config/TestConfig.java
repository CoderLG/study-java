package lg.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * author: LG
 * date: 2019-09-06 13:56
 * desc:
 */
@Data
@Configuration
public class TestConfig {
    @Value("${spring.application.version}")
    private String version;




}
