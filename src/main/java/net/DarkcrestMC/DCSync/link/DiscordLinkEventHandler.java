package net.DarkcrestMC.DCSync.link;

import net.DarkcrestMC.DCSync.Utils;
import net.DarkcrestMC.DCSync.configuration.ConfigManager;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DiscordLinkEventHandler extends ListenerAdapter {
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        User user = event.getAuthor();
        Member member = event.getMember();

        if (user.isBot() || user.isFake() || event.isWebhookMessage()) return;

        String[] args = event.getMessage().getContentRaw().split(" ");

        if (args[0].equalsIgnoreCase(Utils.discordPrefix + "link")) {

            if (member.getRoles().stream().filter(role -> role.getName().equals("Synced")).findAny().orElse(null) != null) {
                event.getChannel().sendMessage(Utils.createEmbed("Error!", "You're already verified. Nice try though :>").build());
                return;
            } else {
                Player target = Bukkit.getPlayer(args[1]);
                if (Utils.checkUserUnconfirmed(target)) {
                    event.getChannel().sendMessage(Utils.createEmbed("Error!", "You already have a code waiting, you sneaky devil :>").build());
                    return;
                } else {
                    if (args.length != 2) {
                        event.getChannel().sendMessage(Utils.createEmbed("Error!", "You must specify a username, silly goose :>").build());
                        return;
                    } else {
                        if (target == null) {
                            event.getChannel().sendMessage(Utils.createEmbed("Error!", "The specified player isn't online, duh :>").build());
                            return;
                        } else if (Utils.checkUserConfirmed(target)) {
                            event.getChannel().sendMessage(Utils.createEmbed("Error!", "Yeah... it's okay... we all want to be" + target.getName()).build());
                            return;
                        } else {
                            String randCode = Utils.randomcode;

                            user.openPrivateChannel().complete().sendMessage(Utils.createEmbed("Congratulations!", "Hey! Your verification code has been generated :o \n" +
                                    "Use this command in-game (" + Utils.serverIP + "): /verify " + randCode).build());

                            ConfigManager.defaultConfig.get().addDefault("PlayerCodes." + target.getUniqueId() + ".Status", "UNCONFIRMED");
                            ConfigManager.defaultConfig.get().addDefault("PlayerCodes." + target.getUniqueId() + ".Code", randCode);
                            ConfigManager.defaultConfig.get().addDefault("PlayerCodes." + target.getUniqueId() + ".DiscordID", user.getId());

                            ConfigManager.defaultConfig.save();
                        }
                    }
                }
            }
        } else {
            return;
        }
    }
}
