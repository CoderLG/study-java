package lg.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * author: LG
 * date: 2019-10-31 17:34
 * desc:
 * args填参 --name=gg
 * 读取args中的传参
 */
@Component
@Slf4j
public class AppStartupRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
            log.error(args.getOptionNames().toString());
            log.error(""+ args.getSourceArgs());
            log.error(args.getOptionValues("name").toString());
    }
}
