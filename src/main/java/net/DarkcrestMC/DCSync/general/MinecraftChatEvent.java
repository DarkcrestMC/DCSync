package net.DarkcrestMC.DCSync.general;

import net.DarkcrestMC.DCSync.Utils;
import net.DarkcrestMC.DCSync.configuration.ConfigManager;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MinecraftChatEvent implements Listener {

    Guild guild;
    JDA jda;

    @EventHandler
    public void onChatEvent(AsyncPlayerChatEvent event) {
        if (!event.getMessage().startsWith("/")) {
            TextChannel channel = jda.getTextChannelsByName(ConfigManager.defaultConfig.get().getString("Discord.logChannel"), true).get(0);

            channel.sendMessage(Utils.createEmbed(event.getPlayer().getName(), event.getMessage()).build()).complete();
        }
    }
}
