package lg.utils;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * author: LG
 * date: 2020-03-15 17:59
 * desc:
 */
public class StudyIoUtilsTest {

    @Test
    public void byteReadFile() throws Exception {
//        StudyIoUtils.byteReadFile();
//        StudyIoUtils.bufferReadFile();
//        StudyIoUtils.byteWriteFile();
//        StudyIoUtils.bufferWriteFile();


        //------------上面为字节流  下面为字符流---------------------
        long start = System.currentTimeMillis();
        File file_in = new File("D:\\test\\srcFile");
        File file_output = new File("D:\\test\\targeFile");
        StudyIoUtils.copyDir(file_in,file_output);
        long res = System.currentTimeMillis() -start;
        System.out.println("用时：" + res + "毫秒");
    }

    @Test
    public void bufferReadFile() throws Exception {

    }

    @Test
    public void byteWriteFile() throws Exception {

    }

    @Test
    public void byteReadFile1() {

        try {
            StudyIoUtils.readFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}