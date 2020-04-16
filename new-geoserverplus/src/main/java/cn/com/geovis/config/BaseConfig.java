package cn.com.geovis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * author: LG
 * date: 2019-09-06 13:56
 * desc:
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.application")
public class BaseConfig {

    private String version;
    private String name;

}
