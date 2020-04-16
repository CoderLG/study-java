package lg.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

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
 * 深入了解 看 StudyIoUtils
 */
@Slf4j
public class IoUtils {

    /**
     * 复制文件夹
     */
    public static void copyDir(File srcFile,File targetFile) throws Exception {
        //如果文件不存在，创建
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }

        //变量srcFile下的文件和文件夹，文件进行复制，文件夹进行递归
        File[] files = srcFile.listFiles();
        for (File file : files) {

            if (file.isDirectory()){
                //如果是文件夹，递归
                copyDir(new File(srcFile, file.getName()), new File(targetFile, file.getName()));
            }else{
                //如果是文件，复制
//                copyFile1(new File(srcFile, file.getName()), new File(targetFile, file.getName()));
                copyFile(new File(srcFile, file.getName()), new File(targetFile, file.getName()));
            }
        }
    }


    /**
     *
     * 使用字节流
     * 进行文件的复制
     *
     */
    public static void copyFile(File srcFile,File targetFile) throws Exception {

        BufferedInputStream bufferedReader = new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bufferedWriter = new BufferedOutputStream(new FileOutputStream(targetFile));
        byte[] chars = new byte[1024];
        int len = bufferedReader.read(chars);
        while (len != -1){
            bufferedWriter.write(chars,0,len);
            len = bufferedReader.read(chars);
        }
        bufferedWriter.close();
        bufferedReader.close();

    }


    /**
     * 纯文本是用字节流
     * B视频网址生成
     *
     * 这里使用了转换流
     * 可以直接使用fileWriter
     * @param start
     * @param end
     * @param url
     * @param path
     */
    public static void biliUrl(int start ,int end ,String url,String path){

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

    /**
     * 读取resources下的资源文件
     * utf-8 格式的是没问题的
     * 其他格式不一定
     */
    public static void readResourcesFile() throws Exception {
        ClassPathResource listRes = new ClassPathResource("data/list");
        InputStream inputStream = listRes.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String s = bufferedReader.readLine();
        while (s != null){
            System.out.println(s);
            s = bufferedReader.readLine();
        }
    }


}
