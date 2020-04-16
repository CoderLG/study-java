package lg.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * author: LG
 * date: 2019-11-07 16:42
 * desc:
 * 操作.bat（windows可执行文件）
 */
@Slf4j
public class BatUtils {

    public static void runBat(String batPath){
        StringBuilder sb = new StringBuilder();
        try {
            Process child = Runtime.getRuntime().exec(batPath);
            InputStream in = child.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(in));
            String line;
            while((line=bufferedReader.readLine())!=null)
            {
                sb.append(line + "\n");
            }
            in.close();
            try {
                child.waitFor();
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            System.out.println("sb:" + sb.toString());
            System.out.println("callCmd execute finished");
        } catch (IOException e) {
            System.out.println(e);
        }

    }
}
