package cn.felord.idserver.exception;

/**
 * 未找到内容
 *
 * @author zhengchalei
 */
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6908309950272531445L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(final String message) {
        super(message);
    }
}
