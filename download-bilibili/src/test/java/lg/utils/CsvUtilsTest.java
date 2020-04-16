package lg.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * author: LG
 * date: 2019-10-16 14:24
 * desc:
 */
public class CsvUtilsTest {

    @Test
    public void write() {
        FileWriteUtils.write();
    }

    @Test
    public void read() {
        FileWriteUtils.read();
    }
    @Test
    public void streamWrite() {
        FileWriteUtils.writeTiltName();
    }


}