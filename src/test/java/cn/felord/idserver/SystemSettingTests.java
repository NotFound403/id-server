package cn.felord.idserver;

import cn.felord.idserver.entity.SystemSettings;
import cn.felord.idserver.repository.SystemSettingsRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author felord.cn
 * @since 1.0.0
 */
@SpringBootTest
public class SystemSettingTests {
    @Autowired
    SystemSettingsRepository repository;

    @Test
    public void testIdGenerator(){
        SystemSettings settings = new SystemSettings();
        settings.setSystemName("ID Server");
        settings.setCopyright("https://felord.cn");
        repository.saveAndFlush(settings);
    }

}
