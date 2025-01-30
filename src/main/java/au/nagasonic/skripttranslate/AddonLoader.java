package au.nagasonic.skripttranslate;

import au.nagasonic.skripttranslate.util.Util;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.util.Version;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.io.IOException;

/**
 * @hidden
 */
@SuppressWarnings("CallToPrintStackTrace")
public class AddonLoader {
    private final Skript_Translate plugin;
    private final PluginManager pluginManager;
    private final Plugin skriptPlugin;
    private SkriptAddon addon;

    public AddonLoader(Skript_Translate plugin) {
        this.plugin = plugin;
        this.pluginManager = plugin.getServer().getPluginManager();
        this.skriptPlugin = pluginManager.getPlugin("Skript");
    }

    boolean canLoadPlugin() {
        if (skriptPlugin == null) {
            Util.log("&cDependency Skript was not found, plugin disabling.");
            return false;
        }
        if (!skriptPlugin.isEnabled()) {
            Util.log("&cDependency Skript is not enabled, plugin disabling.");
            Util.log("&cThis could mean &eSkript-Translate is being forced to load before Skript.");
            return false;
        }

        if (!Skript.isAcceptRegistrations()) {
            Util.log("&cSkript is no longer accepting registrations, addons can no longer be loaded!");
            Util.log("&cNo clue how this could happen.");
            Util.log("&cSeems a plugin is delaying &eSkript-Translate &cloading, which is after Skript stops accepting registrations.");
            return false;
        }
        Version version = new Version(Skript_Translate.EARLIEST_VERSION);
        if (!Skript.isRunningMinecraft(version)) {
            Util.log("&cYour server version &7'&bMC %s&7'&c is not supported, only &7'&bMC %s+&7'&c is supported!", Skript.getMinecraftVersion(), version);
            return false;
        }
        loadSkriptElements();
        return true;
    }

    private void loadSkriptElements() {
        this.addon = Skript.registerAddon(this.plugin);
        this.addon.setLanguageFileDirectory("lang");
        int[] elementCountBefore = Util.getElementCount();
        loadElements();
        loadOtherClasses();
        int[] elementCountAfter = Util.getElementCount();
        int[] finish = new int[elementCountBefore.length];
        int total = 0;
        for (int i = 0; i < elementCountBefore.length; i++) {
            finish[i] = elementCountAfter[i] - elementCountBefore[i];
            total += finish[i];
        }
        String[] elementNames = new String[]{"event", "effect", "expression", "condition", "section"};

        Util.log("Loaded (%s) elements:", total);
        for (int i = 0; i < finish.length; i++) {
            Util.log(" - %s %s%s", finish[i], elementNames[i], finish[i] == 1 ? "" : "s");
        }
    }

    private void loadElements() {
        try {
            this.addon.loadClasses("au.nagasonic.skripttranslate.elements");
            Util.logLoading("&dElements &ahave successfully loaded");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void loadOtherClasses(){
        try {
            this.addon.loadClasses("au.nagasonic.skripttranslate.classes");
            Util.logLoading("&aLoaded all Class Types successfully");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
