package dev.risas.geoip;

import dev.risas.geoip.commands.GeoIPCommand;
import dev.risas.geoip.utils.files.FileConfig;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public class GeoIP extends JavaPlugin {

    private FileConfig configFile;
    private boolean isPlaceholderAPI;

    @Override
    public void onEnable() {
        this.loadFiles();
        this.loadCommands();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            this.isPlaceholderAPI = true;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void loadFiles() {
        this.configFile = new FileConfig(this, "config.yml");
    }

    private void loadCommands() {
        new GeoIPCommand();
    }

    public static GeoIP get() {
        return getPlugin(GeoIP.class);
    }
}
