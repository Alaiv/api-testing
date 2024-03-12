package tests;

import infrastructure.SettingsConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BaseTest {
    protected SettingsConfig settingsConfig;

    @BeforeAll
    public void init() {
        settingsConfig = ConfigFactory.create(SettingsConfig.class);
    }
}
