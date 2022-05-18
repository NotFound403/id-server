package cn.felord.authentication.miniapp;

/**
 * The interface Mini app client service.
 *
 * @author felord.cn
 * @since 1.0.8.RELEASE
 */
@FunctionalInterface
public interface MiniAppClientService {
    /**
     * Get mini app client.
     *
     * @param clientId the client id
     * @return {@link MiniAppClient}
     * @see MiniAppClient#getAppId() MiniAppClient#getAppId()
     * @see MiniAppClient#getSecret() MiniAppClient#getSecret()
     */
    MiniAppClient get(String clientId);
}
