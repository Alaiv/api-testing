package infrastructure;

import org.aeonbits.owner.Config;

@Config.Sources("classpath:proj.properties")
public interface SettingsConfig extends Config {
    @Config.Key("base_url")
    String baseUrl();
}
