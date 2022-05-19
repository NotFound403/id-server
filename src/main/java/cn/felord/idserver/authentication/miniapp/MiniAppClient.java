package cn.felord.idserver.authentication.miniapp;

/**
 * @author felord.cn
 * @since 1.0.8.RELEASE
 */
public class MiniAppClient {
    private String clientId;
    private String appId;
    private String secret;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
