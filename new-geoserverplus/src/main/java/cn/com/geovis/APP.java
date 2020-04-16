package cn.com.geovis;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * author: LG
 * date: 2019-09-02 18:39
 * desc:
 */

@EnableDiscoveryClient
@EnableSwagger2Doc
@ImportResource({ "classpath*:/applicationContext.xml", "classpath*:/applicationSecurityContext.xml" })
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class APP {
    public static void main(String[] args) {
        SpringApplication.run(APP.class, args);
    }
}
