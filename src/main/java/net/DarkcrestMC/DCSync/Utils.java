package net.DarkcrestMC.DCSync;

import net.DarkcrestMC.DCSync.configuration.ConfigManager;
import net.dv8tion.jda.core.EmbedBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.Random;

public class Utils {
    public static String serverPrefix = colored(ConfigManager.langConfig.get().getString("Language.serverPrefix"));
    public static String errorPrefix = colored(ConfigManager.langConfig.get().getString("Language.serverErrorPrefix"));
    public static String discordLink = ConfigManager.defaultConfig.get().getString("Discord.inviteLink");
    public static String discordPrefix = ConfigManager.defaultConfig.get().getString("Discord.serverPrefix");
    public static String serverIP = ConfigManager.langConfig.get().getString("Language.serverIP");

    public static String randomcode = new Random().nextInt(800000)+200000+"AA"; //6581446AA

    public static EmbedBuilder createEmbed(String title, String description) {
        EmbedBuilder embed = new EmbedBuilder();

        embed.setTitle(title);
        embed.setDescription(description);

        //#8b41c4 = rgb(139,65,196)

        embed.setColor(new Color(
                ConfigManager.langConfig.get().getInt("Language.EmbedColor.R"),
                ConfigManager.langConfig.get().getInt("Language.EmbedColor.G"),
                ConfigManager.langConfig.get().getInt("Language.EmbedColor.B")
        ));

        return embed;
    }

    public static String getUserStatus(Player player) {
        if (ConfigManager.defaultConfig.get().getString("PlayerCodes." + player.getUniqueId().toString() + ".Status") == null) {
            return null;
        }

        return ConfigManager.defaultConfig.get().getString("PlayerCodes." + player.getUniqueId().toString() + ".Status");
    }

    public static String getUserDiscordID (Player player) {
        if (ConfigManager.defaultConfig.get().getString("PlayerCodes." + player.getUniqueId().toString() + ".DiscordID") == null) {
            return null;
        }

        return ConfigManager.defaultConfig.get().getString("PlayerCodes." + player.getUniqueId().toString() + ".DiscordID");
    }

    public static String getUserCode(Player player) {
        if (ConfigManager.defaultConfig.get().getString("PlayerCodes." + player.getUniqueId().toString() + ".Code") == null) {
            return null;
        }

        return ConfigManager.defaultConfig.get().getString("PlayerCodes." + player.getUniqueId().toString() + ".Code");
    }

    public static boolean checkUserConfirmed(Player player) {
        if (ConfigManager.defaultConfig.get().getString("PlayerCodes." + player.getUniqueId().toString() + ".Status").equalsIgnoreCase("CONFIRMED")) {
            return true;
        } else if (ConfigManager.defaultConfig.get().getString("PlayerCodes." + player.getUniqueId().toString() + ".Status").equalsIgnoreCase("UNCONFIRMED")) {
            return false;
        } else {
            return false;
        }
    }

    public static boolean checkUserUnconfirmed(Player player) {
        if (ConfigManager.defaultConfig.get().getString("PlayerCodes." + player.getUniqueId().toString() + ".Status").equalsIgnoreCase("UNCONFIRMED")) {
            return true;
        } else if (ConfigManager.defaultConfig.get().getString("PlayerCodes." + player.getUniqueId().toString() + ".Status").equalsIgnoreCase("CONFIRMED")) {
            return false;
        } else {
            return false;
        }
    }

    public static String colored(String format) {
        return ChatColor.translateAlternateColorCodes('&', format);
    }
}
