package net.DarkcrestMC.DCSync.general;

import net.DarkcrestMC.DCSync.Utils;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class DiscordChatEvent  extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        User user = event.getAuthor();

        if (user.isBot() || user.isFake() || event.isWebhookMessage()) return;

        String message = event.getMessage().getContentRaw();

        if (event.getMember().getRoles().stream().filter(role -> role.getName().equals("Synced")).findAny().orElse(null) == null) {
            event.getChannel().sendMessage(user.getAsTag());
            event.getChannel().sendMessage(Utils.createEmbed("Error!", "WOAHHHHH THERE BUDDY YOU GOTTA SLOW DOWN! \n" +
                                                                  "Verify your Minecraft account first by doing !link <Username>  :>").build());
        } else {
            // [Jacksonnn (Discord)] Hey guys, how are you doing?
            Bukkit.broadcastMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "[" + ChatColor.LIGHT_PURPLE + user.getName() + " (Discord)" + ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "]" + ChatColor.GRAY + " " + message);
        }
    }
}
