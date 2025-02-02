package au.nagasonic.skripttranslate.util;

import au.nagasonic.skripttranslate.Skript_Translate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config {

    private final Skript_Translate plugin;
    private FileConfiguration config;
    private File configFile;

    // Config things
    public boolean SETTINGS_DEBUG;
    public boolean SETTINGS_UPDATE_CHECKER_ENABLED;

    public Config(Skript_Translate plugin) {
        this.plugin = plugin;
        loadConfigFile();
    }

    private void loadConfigFile() {
        if (configFile == null) {
            configFile = new File(plugin.getDataFolder(), "config.yml");
        }
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
        matchConfig();
        loadConfigs();
    }

    @SuppressWarnings("ConstantConditions")
    private void matchConfig() {
        try {
            boolean hasUpdated = false;
            InputStream stream = plugin.getResource(configFile.getName());
            assert stream != null;
            InputStreamReader is = new InputStreamReader(stream);
            YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(is);
            for (String key : defConfig.getConfigurationSection("").getKeys(true)) {
                if (!config.contains(key)) {
                    config.set(key, defConfig.get(key));
                    hasUpdated = true;
                }
            }
            for (String key : config.getConfigurationSection("").getKeys(true)) {
                if (!defConfig.contains(key)) {
                    config.set(key, null);
                    hasUpdated = true;
                }
            }
            if (hasUpdated)
                config.save(configFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean getSetting(String setting) {
        return this.config.getBoolean("settings." + setting);
    }

    private void loadConfigs() {
        this.SETTINGS_DEBUG = getSetting("debug");
        this.SETTINGS_UPDATE_CHECKER_ENABLED = getSetting("update-checker.enabled");
    }
}
