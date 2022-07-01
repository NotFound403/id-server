package cn.felord.idserver.jackson2;

import cn.felord.idserver.authentication.captcha.CaptchaAuthenticationToken;
import cn.felord.idserver.authentication.miniapp.MiniAppAuthenticationToken;
import cn.felord.idserver.authentication.providers.WechatOAuth2User;
import cn.felord.idserver.authentication.providers.WorkWechatOAuth2User;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.security.jackson2.SecurityJackson2Modules;

/**
 * @author felord.cn
 * @since 1.0.0
 */
public class IdServerJackson2Module extends SimpleModule {

    private static final long serialVersionUID = 3137282573480717744L;

    public IdServerJackson2Module() {
        super(IdServerJackson2Module.class.getName(), new Version(1, 0, 0, null, null, null));
    }

    @Override
    public void setupModule(SetupContext context) {
        SecurityJackson2Modules.enableDefaultTyping(context.getOwner());
        context.setMixInAnnotations(CaptchaAuthenticationToken.class, CaptchaAuthenticationTokenMixin.class);
        context.setMixInAnnotations(MiniAppAuthenticationToken.class, MiniAppAuthenticationTokenMixin.class);
        context.setMixInAnnotations(WechatOAuth2User.class,WechatOAuth2UserMixin.class);
        context.setMixInAnnotations(WorkWechatOAuth2User.class,WorkWechatOAuth2UserMixin.class);
    }
}
