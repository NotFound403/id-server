package cn.felord.idserver.enumate;

/**
 * The enum Gender.
 *
 * @author felord.cn
 * @since 1.0.0
 */
public enum RootUserConstants implements Enumerable {
    /**
     * Unknown gender.
     */
    ROOT_USE_ID("root_user_id", "root用户id"),
    ROOT_USERNAME("root", "root用户名"),
    /**
     * Female gender.
     */
    ROOT_RAW_PASSWORD("idserver", "root用户密码"),
    /**
     * Male gender.
     */
    ROOT_ROLE_NAME("id_server", "root用户角色");


    private final String val;
    private final String description;

    RootUserConstants(String val, String description) {
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
