package lg.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Calendar;

/**
 * author: LG
 * date: 2019-11-07 16:42
 * desc:
 * 输入输出流
 */
@Slf4j
public class IoUtils {

    public static void streamWrite(int start ,int end ,String url,String path){

        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path, true)))) {
            String targetPath = url;
            for(int i=start;i<=end;i++){
                out.write("you-get -o ./videos "+url+i);
                out.newLine();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
