package cn.felord.idserver.enumate;

/**
 * The enum Enabled.
 *
 * @author felord.cn
 * @since 1.0.0
 */
public enum Enabled implements Enumerable {
    /**
     * Enable enabled.
     */
    DISABLE("false", "不可用"),
    /**
     * Male gender.
     */
    ENABLE("true", "可用");


    private final String val;
    private final String description;

    Enabled(String val, String description) {
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
