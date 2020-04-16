package lg.exception;

import lombok.Data;


@Data
public abstract class AbstractException extends RuntimeException {

    private static final long serialVersionUID = -6444357595272662540L;
    private String code;
    private String message;

    public AbstractException(String code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public AbstractException() {
    }
}
