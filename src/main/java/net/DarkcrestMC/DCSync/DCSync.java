package net.DarkcrestMC.DCSync;

import net.DarkcrestMC.DCSync.configuration.ConfigManager;
import net.DarkcrestMC.DCSync.general.DiscordChatEvent;
import net.DarkcrestMC.DCSync.general.MCDiscordCommand;
import net.DarkcrestMC.DCSync.general.MinecraftChatEvent;
import net.DarkcrestMC.DCSync.link.DiscordLinkEventHandler;
import net.DarkcrestMC.DCSync.link.LinkCommand;
import net.DarkcrestMC.DCSync.link.MCLinkEventHandler;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.LoginException;

public final class DCSync extends JavaPlugin {

    public static DCSync plugin;
    public static JDA jda;
    PluginManager pm = Bukkit.getServer().getPluginManager();
    public static Guild guild;

    @Override
    public void onEnable() {
        plugin = this;

        new ConfigManager();

        startBot();

        Bukkit.getScheduler().runTaskLater(plugin,()->guild = jda.getGuilds().get(0),100L);

        registerCommands();
        registerListeners();


        Bukkit.getServer().getLogger().info("DCSync is enabled!");

    }

    @Override
    public void onDisable() {

        Bukkit.getServer().getLogger().info("DCSync is disabled!");
    }

    void startBot() {
        try {
            jda = new JDABuilder(ConfigManager.defaultConfig.get().getString("Discord.serverToken")).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

    void registerListeners() {
        pm.registerEvents(new MCLinkEventHandler(), this);
        pm.registerEvents(new MinecraftChatEvent(), this);
        jda.addEventListener(new DiscordChatEvent());
        jda.addEventListener(new DiscordLinkEventHandler());
    }

    void registerCommands() {
        this.getCommand("link").setExecutor(new LinkCommand(guild, plugin, jda));
        this.getCommand("discord").setExecutor(new MCDiscordCommand());
    }
}
