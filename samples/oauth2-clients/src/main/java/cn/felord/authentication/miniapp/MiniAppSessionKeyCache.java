package cn.felord.authentication.miniapp;

/**
 * 缓存sessionKey
 *
 * @author felord.cn
 * @since 1.0.8.RELEASE
 */
public interface MiniAppSessionKeyCache {

    /**
     * Put sessionKey.
     *
     * @param cacheKey   {@code clientId::openId}
     * @param sessionKey the session key
     * @return sessionKey
     */
    String put(String cacheKey, String sessionKey);

    /**
     * Get sessionKey.
     *
     * @param cacheKey {@code clientId::openId}
     * @return sessionKey
     */
    String get(String cacheKey);
}
