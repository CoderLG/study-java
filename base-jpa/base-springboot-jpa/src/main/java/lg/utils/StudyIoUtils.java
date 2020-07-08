package lg.utils;

import com.alibaba.fastjson.JSON;
import lg.domain.TsTrackPoint;
import lombok.extern.slf4j.Slf4j;
import org.xnio.streams.BufferPipeOutputStream;

import javax.sound.midi.Soundbank;
import java.io.*;

/**
 * author: LG
 * date: 2019-11-07 16:42
 * desc:输入输出
 * 字节流 inputStream outputStream
 * 字符流 fileReader fileWriter
 *
 * 效率最高的是缓冲字符流
 * BufferedReader  BufferedWriter
 *
 * 缓冲字节流
 * BufferedInputStream  BufferedOutputStream
 *
 * 经过测试得出结论
 * 在纯文本的情况想用  BufferedReader  BufferedWriter ，并用readLine
 * 其他情况下用 BufferedInputStream  BufferedOutputStream
 */
@Slf4j
public class StudyIoUtils {
/**
 * 一行一行的读文件
 */
public static void readFile(){
    String path = "D:\\2017-10-03_sorted.dat";
    File file = new File(path);
    StringBuilder result = new StringBuilder();
    try{
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));//构造一个BufferedReader类来读取文件

        String s = null;
        while((s = br.readLine())!=null){//使用readLine方法，一次读一行
//            result.append( System.lineSeparator() + s);
            TsTrackPoint tsTrackPoint = JSON.parseObject(s, TsTrackPoint.class);

            String temp = "";
            for (int i = 0; i < tsTrackPoint.getMbmc().length() - 2; i++) {
                temp= temp+"*";
            }
            String newName = tsTrackPoint.getMbmc().substring(0, 1) + temp + tsTrackPoint.getMbmc().substring(tsTrackPoint.getMbmc().length() - 1, tsTrackPoint.getMbmc().length());
            tsTrackPoint.setMbmc(newName);
            String parse = JSON.toJSONString(tsTrackPoint) + "\n";
            bufferWriteFile(parse);
        }
        br.close();
    }catch(Exception e){
        e.printStackTrace();
    }
//    return result.toString();
}

    /**
     * 学习读文件
     * 一个字节一个字节的读
     * 使用最简单的字节流读取文件
     * "F:\\burning\\coding\\java\\base-jpa\\base-springboot-jpa\\file-service\\upload\\1583301493454\\f.txt"
     *
     * utf-8 中文占三个字节
     */
    public static void byteReadFile() throws Exception {
        String path = "D:\\2017-10-03_sorted.dat";
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        int read = fileInputStream.read();
        while (read !=-1){
            System.out.println(read);
            read = fileInputStream.read();
        }
        fileInputStream.close();
    }

    /**
     * 学习读文件
     * 使用buffer读取文件，
     * 一次多读取几个字节，提升效率
     *
     * byte 范围： -128  -  127
     */
    public static void bufferReadFile() throws Exception {
        String path = "F:\\burning\\coding\\java\\base-jpa\\base-springboot-jpa\\file-service\\upload\\1583301493454\\f.txt";
        File file = new File(path);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[8];

        int read = fileInputStream.read(bytes);
        while (read !=-1){
            System.out.println("read中的内容，是占用byte数组的长度");
            System.out.println(read);
            System.out.println("byte数组中的内容，是每个字节的内容，每个字节占数组的一位");
            for (int i = 0; i < bytes.length; i++) {
                System.out.println(bytes[i]);
            }
            read = fileInputStream.read(bytes);
        }
        fileInputStream.close();
    }

    /**
     * 学习文件的写入
     * 一个字节一个字节的写入文件
     *
     */

    public static void byteWriteFile(String content) throws Exception {
        String path = "D:\\n.bat";
        File file = new File(path);
        //true 为追加写的意思 ，反之
        FileOutputStream fileOutputStream = new FileOutputStream(file,true);

        byte[] bytes = content.getBytes();
        for (byte aByte : bytes) {
            fileOutputStream.write(aByte);
        }

        fileOutputStream.close();
    }

    /**
     * 学习文件的写入
     *直接写入一个字节数组
     */

    public static void bufferWriteFile(String content) throws Exception {

        String path = "D:\\n.dat";
        File file = new File(path);
        //true 为追加写的意思 ，反之
        FileOutputStream fileOutputStream = new FileOutputStream(file,true);
        byte[] bytes = content.getBytes();
        fileOutputStream.write(bytes);

        fileOutputStream.close();
    }

//--------------------上面为字节流 下面为字符流-----------------------------

    /**
     * 通过字符（char） 流
     * 完成文件的复制
     */

    public static void copyFile() throws Exception {
        String input_path = "F:\\burning\\coding\\java\\base-jpa\\base-springboot-jpa\\file-service\\upload\\1583301493454\\n.txt";
        String outpub_path = "F:\\burning\\coding\\java\\base-jpa\\base-springboot-jpa\\file-service\\upload\\1583301493454\\write.txt";

        FileReader fileReader = new FileReader(input_path);
        FileWriter fileWriter = new FileWriter(outpub_path);
        char[] chars = new char[4];

        int read = fileReader.read(chars);
        while (read != -1){
            //0,read 防止写入多余的字符
            //字符数组的每一个存储，都可以放多个字节，不会出现乱码
            fileWriter.write(chars,0,read);
             read = fileReader.read(chars);
        }
        fileWriter.close();
        fileReader.close();

    }


    /**
     * 使用效率最高的 缓冲字符流
     * 完成文件复制
     *
     * 并试试readLine 是否会对图片视频等产生影响
     * 经过测试字符流，确实会对 视屏，图像造成影像，使文件不可读
     *
     */
    /**
     * 使用字符流
     * 会对视频，图像造成损坏
     * 复制文件
     * @param srcFile
     * @param targetFile
     */
    public static void copyFile1(File srcFile,File targetFile) throws Exception {

        BufferedReader bufferedReader = new BufferedReader(new FileReader(srcFile));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(targetFile));
        //如果是文本类型的可以这么搞，单没有对应的writeLine
//        String s = bufferedReader.readLine();
        char[] chars = new char[1024];
        int len = bufferedReader.read(chars);
        while (len != -1){
            bufferedWriter.write(chars,0,len);
            len = bufferedReader.read(chars);
        }
        bufferedWriter.close();
        bufferedReader.close();

    }

    /**
     * 使用字节流
     * @param srcFile
     * @param targetFile
     */
    public static void copyFile2(File srcFile,File targetFile) throws Exception {

        BufferedInputStream bufferedReader = new BufferedInputStream(new FileInputStream(srcFile));
        BufferedOutputStream bufferedWriter = new BufferedOutputStream(new FileOutputStream(targetFile));
        //如果是文本类型的可以这么搞，单没有对应的writeLine
//        String s = bufferedReader.readLine();
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
     * 使用字符流
     * 会对视频，图像造成损坏
     * 我们使用转换流
     *
     * 视频，图像造成损坏，耗时长
     *
     * 复制文件
     * @param srcFile
     * @param targetFile
     */
    public static void copyFile3(File srcFile,File targetFile) throws Exception {

        InputStreamReader bufferedReader = new InputStreamReader(new FileInputStream(srcFile));
        OutputStreamWriter bufferedWriter = new OutputStreamWriter(new FileOutputStream(targetFile));


        //如果是文本类型的可以这么搞，单没有对应的writeLine
//        String s = bufferedReader.readLine();
        char[] chars = new char[1024];
        int len = bufferedReader.read(chars);
        while (len != -1){
            bufferedWriter.write(chars,0,len);
            len = bufferedReader.read(chars);
        }
        bufferedWriter.close();
        bufferedReader.close();

    }


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
                copyFile3(new File(srcFile, file.getName()), new File(targetFile, file.getName()));
            }
        }
    }
}
