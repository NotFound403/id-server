package cn.felord.idserver.enumate;

/**
 * The enum MenuOpenType.
 *
 * @author felord.cn
 * @since 1.0.0
 */
public enum MenuOpenType implements Enumerable {

    BLANK("_blank", "签页"),
    IFRAME("_iframe", "框架");

    private final String val;
    private final String description;

    MenuOpenType(String val, String description) {
        this.val = val;
        this.description = description;
    }

    @Override
    public String val() {
        return this.val;
    }

    @Override
    public String description() {
        return this.description;
    }
}
