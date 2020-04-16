package lg.common.service.impl;

import lg.dvo.FileInfoVo;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * author: LG
 * date: 2019-09-04 09:45
 * desc:
 */
@Service
public class LoadServiceImpl {

    public FileInfoVo queryTiltFilePathByLayerName() throws IOException {
        FileInfoVo fileInfo = new FileInfoVo();
        String fullPath = "F:\\burning\\coding\\java\\base-jpa\\base-springboot-jpa\\file-service\\upload\\1583301493454\\f.txt";
        File file = new File(fullPath);
        if (file.exists()) {
            int bLength = 4096;
            FileSystemResource fileSystemResource = new FileSystemResource(file);
            try(InputStream inputStream = fileSystemResource.getInputStream();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();) {
                fileInfo.setLength(fileSystemResource.contentLength());
//                byte[] data = new byte[bLength];
//                int count = -1;
//                while((count = inputStream.read(data,0,bLength)) != -1){
//                    outStream.write(data, 0, count);
//                }
//                fileInfo.setBytes(outStream.toByteArray());
                fileInfo.setInputStreamResource(new InputStreamResource(fileSystemResource.getInputStream()));
                HttpHeaders headers = new HttpHeaders();
                headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
                headers.add("Content-Disposition", String.format("attachment; filename=%s",
                        URLEncoder.encode(fileSystemResource.getFilename(), "UTF-8")));
                headers.add("Pragma", "no-cache");
                headers.add("Expires", "0");
                fileInfo.setHeaders(headers);
                return  fileInfo;
            } catch (IOException e) {

                throw new IOException();
            }
        }
        return  null;
    }

}
