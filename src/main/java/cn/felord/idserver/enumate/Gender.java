package cn.felord.idserver.enumate;

/**
 * The enum Gender.
 *
 * @author felord.cn
 * @since 1.0.0
 */
public enum Gender implements Enumerable {
    /**
     * Unknown gender.
     */
    UNKNOWN("-1", "未知"),
    /**
     * Female gender.
     */
    FEMALE("0", "女性"),
    /**
     * Male gender.
     */
    MALE("1", "男性");


    private final String val;
    private final String description;

    Gender(String val, String description) {
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
