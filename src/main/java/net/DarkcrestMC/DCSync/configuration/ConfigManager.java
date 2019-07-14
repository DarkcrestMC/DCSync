package net.DarkcrestMC.DCSync.configuration;

import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class ConfigManager {
    public static Config langConfig;
    public static Config defaultConfig;


    public ConfigManager() {
        defaultConfig = new Config(new File("config.yml"));
        langConfig = new Config(new File("language.yml"));
        configCheck(ConfigType.DEFAULT);
        configCheck(ConfigType.LANGUAGE);
    }

    public static void configCheck(ConfigType type) {
        FileConfiguration config;
        if (type == ConfigType.DEFAULT) {
            config = defaultConfig.get();

            /*
                PlayerCodes:
                    Jacksonnn's UUID:
                        Status: CONFIRMED
                        CODE: 123456AA
                        DiscordID: 54253454325
                    KJeremiah's UUID:
             */
            config.addDefault("Discord", "");
            config.addDefault("Discord.inviteLink", "https://discord.gg/rEtpysw");
            config.addDefault("Discord.serverToken", "NTk4MDA4NDUzNjIwODI2MTEz.XSpzzg.kD2cQ8ZtxXrsZ0ToHRibelUk3uI");
            config.addDefault("Discord.serverPrefix", "?");
            config.addDefault("Discord.logChannel", "600002775618682899");

            config.addDefault("PlayerCodes", "");

            defaultConfig.save();
        } else if (type == ConfigType.LANGUAGE) {
            config = langConfig.get();

            config.addDefault("Language", "");

            config.addDefault("Language.serverPrefix", "&8[&7&lDarkcrest&8]&e ");
            config.addDefault("Language.serverErrorPrefix", "&8[&7&lDarkcrest&8] &4Error!&c ");

            config.addDefault("Language.serverIP", "play.darkcrestmc.net");
            config.addDefault("Language.embedColor.R", 139);
            config.addDefault("Language.embedColor.G", 65);
            config.addDefault("Language.embedColor.B", 196);

            langConfig.save();
        }
    }
}
