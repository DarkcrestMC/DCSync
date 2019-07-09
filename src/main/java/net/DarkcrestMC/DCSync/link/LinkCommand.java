package net.DarkcrestMC.DCSync.link;

import net.DarkcrestMC.DCSync.Utils;
import net.DarkcrestMC.DCSync.configuration.ConfigManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class LinkCommand implements CommandExecutor {

    Guild guild;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.errorPrefix + "Only players can execute this command!");
        } else {
            Player player = (Player) sender;

            if (Utils.checkUserConfirmed(player)) {
                sender.sendMessage(Utils.errorPrefix + "You are already verified!");
            } else if (Utils.getUserStatus(player) == null) {
                sender.sendMessage(Utils.errorPrefix + "There is not a pending verification code for your user!");
            } else if (args.length != 1) {
                sender.sendMessage(Utils.errorPrefix + "/verify <code>");
            } else if (args[0] != Utils.getUserCode(player)) {
                sender.sendMessage(Utils.errorPrefix + "That is not the valid code. Please check again!");
            } else {
                String discordID = Utils.getUserDiscordID(player);
                Member target = guild.getMemberById(discordID);

                if (target == null) {
                    ConfigManager.defaultConfig.get().set("PlayerCodes." + player.getUniqueId() + ".Status", null);
                    ConfigManager.defaultConfig.get().set("PlayerCodes." + player.getUniqueId() + ".Code", null);
                    ConfigManager.defaultConfig.get().set("PlayerCodes." + player.getUniqueId() + ".DiscordID", null);
                    sender.sendMessage(Utils.errorPrefix + "It seems you've left the discord server. Please join back by clicking here: " + Utils.discordLink);

                    ConfigManager.defaultConfig.save();
                    return true;
                } else {
                    Role verified = guild.getRolesByName("Synced", false).get(0);
                    guild.getController().addRolesToMember(target, verified).queue();
                    target.getUser().openPrivateChannel().complete().sendMessage(Utils.createEmbed("Congratulations!", "You have successfully verified your Minecraft account!").build());
                    sender.sendMessage(Utils.serverPrefix + "Congratulations! You have successfully verified your Discord account!");
                }
            }
        }

        return true;
    }
}
