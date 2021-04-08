package dev.risas.geoip.commands;

import dev.risas.geoip.GeoIP;
import dev.risas.geoip.utils.CC;
import dev.risas.geoip.utils.Country;
import dev.risas.geoip.utils.files.FileConfig;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GeoIPCommand implements CommandExecutor {

    private final FileConfig configFile = GeoIP.get().getConfigFile();

    public GeoIPCommand() {
        Bukkit.getPluginCommand("geoip").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.translate("&cNo Console."));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("geoip.use")) {
            player.sendMessage(CC.translate("&cYou don't have permission to execute this command."));
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(CC.translate("&cUsage: /" + label + " <player>"));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage(CC.translate("&cPlayer '" + args[0] + "' not found."));
            return true;
        }

        String IP = target.getAddress().getAddress().getHostAddress();
        String country = Country.getCountry(IP);
        String message = CC.translate(configFile.getString("GEO_IP_MESSAGE")
                .replace("%TARGET%", target.getName())
                .replace("%COUNTRY%", country));

        player.sendMessage(GeoIP.get().isPlaceholderAPI() ? PlaceholderAPI.setPlaceholders(player, message) : message);
        return true;
    }
}
