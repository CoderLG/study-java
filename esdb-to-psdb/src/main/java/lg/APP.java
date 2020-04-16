package lg;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * author: LG
 * date: 2019-09-06 13:36
 * desc:
 */
@SpringBootApplication
@EnableSwagger2Doc
@EnableAsync
@MapperScan(basePackages = {"lg.dao.dao1","lg.dao.dao2"})
public class APP {
    public static void main(String[] args) {
        SpringApplication.run(APP.class, args);
    }
}

