package net.DarkcrestMC.DCSync;

import net.DarkcrestMC.DCSync.configuration.ConfigManager;
import net.DarkcrestMC.DCSync.general.MCDiscordCommand;
import net.DarkcrestMC.DCSync.link.DiscordLinkEventHandler;
import net.DarkcrestMC.DCSync.link.LinkCommand;
import net.DarkcrestMC.DCSync.link.MCLinkEventHandler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class DCSync extends JavaPlugin {

    public static DCSync plugin;
    JDA jda;
    PluginManager pm = Bukkit.getServer().getPluginManager();

    @Override
    public void onEnable() {
        plugin = this;
        new ConfigManager();
        registerCommands();
        registerListeners();

        startBot();

        Bukkit.getServer().getLogger().info("DCSync is enabled!");

    }

    @Override
    public void onDisable() {

        Bukkit.getServer().getLogger().info("DCSync is disabled!");
    }

    void startBot() {
        try {
            jda = new JDABuilder(AccountType.BOT).setToken(ConfigManager.defaultConfig.get().getString("Discord.serverToken")).buildBlocking();
        } catch (LoginException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    void registerListeners() {
        pm.registerEvents(new MCLinkEventHandler(), this);
        jda.addEventListener(new DiscordLinkEventHandler());
    }

    void registerCommands() {
        this.getCommand("link").setExecutor(new LinkCommand());
        this.getCommand("discord").setExecutor(new MCDiscordCommand());
    }
}
