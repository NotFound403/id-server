package cn.felord.idserver.exception;
/**
 * The BindingException.
 *
 * @author felord.cn
 * @since 1.0.0
 */
public class BindingException extends RuntimeException{
    private static final long serialVersionUID = -6921032201661998310L;

    public BindingException() {
    }

    public BindingException(String message) {
        super(message);
    }

    public BindingException(String message, Throwable cause) {
        super(message, cause);
    }

    public BindingException(Throwable cause) {
        super(cause);
    }

    public BindingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
