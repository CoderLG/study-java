package cn.com.geovis.exception;


import lombok.Data;

@Data
public class ImageException extends AbstractException{

    private static final long serialVersionUID = -4748056846518891747L;

    public ImageException(String code, String msg) {
        super(code,msg);
    }
}
