package cn.felord.idserver.exception;

/**
 * 未找到内容
 *
 * @author zhengchalei
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super();
    }

    public NotFoundException(final String message) {
        super(message);
    }
}
