package au.nagasonic.skripttranslate;

import au.nagasonic.skripttranslate.util.Config;
import au.nagasonic.skripttranslate.util.UpdateChecker;
import au.nagasonic.skripttranslate.util.Util;
import ch.njol.skript.Skript;
import ch.njol.skript.util.Version;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Skript_Translate extends JavaPlugin {

    static final int[] EARLIEST_VERSION = new int[]{1, 19, 4};
    private static Skript_Translate instance;
    private static Logger logger;
    private Config config;
    public static String path;
    private AddonLoader addonLoader = null;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        instance = this;
        logger = getLogger();
        path = this.getDataFolder().getPath();
        this.config = new Config(this);
        PluginManager pm = Bukkit.getPluginManager();

        this.addonLoader = new AddonLoader(this);
        if (!addonLoader.canLoadPlugin()){
            pm.disablePlugin(this);
            return;
        }
        String version = getDescription().getVersion();
        if (version.contains("b")) {
            Util.log("&eThis is a Beta build, things may not work as expected, please report any bugs on GitHub");
            Util.log("&ehttps://github.com/NagasonicDev/Skript-Translate/issues");
        }
        new UpdateChecker(this);

        Metrics metrics = new Metrics(this, 24590);
        metrics.addCustomChart(new Metrics.DrilldownPie("skript_version", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();

            Version skriptVersion = Skript.getVersion();
            Map<String, Integer> entry = new HashMap<>();
            entry.put(skriptVersion.toString(), 1);

            map.put(skriptVersion.getMajor()+"."+skriptVersion.getMinor()+"."+skriptVersion.getRevision(), entry);

            return map;
        }));
        Util.log("&aSuccessfully enabled v%s&7 in &b%.2f seconds", version, (float) (System.currentTimeMillis() - start) / 1000);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Skript_Translate getInstance(){ return instance; }

    public static void info(String message){
        logger.info(message);
    }

    public static void log(Level level, String message){
        logger.log(level, message);
    }

    public static String getPath(){ return path; }

    public Config getPluginConfig() {
        return this.config;
    }
}
