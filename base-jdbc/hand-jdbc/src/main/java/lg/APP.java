package lg;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * author: LG
 * date: 2019-09-04 10:57
 * desc:
 */
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@EnableSwagger2Doc
public class APP {
    public static void main(String[] args) {
        SpringApplication.run(APP.class, args);
    }
}
