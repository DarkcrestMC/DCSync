package net.DarkcrestMC.DCSync.general;

import net.DarkcrestMC.DCSync.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MCDiscordCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        sender.sendMessage(Utils.serverPrefix + "Join the discord server! " + Utils.discordLink);
        return true;
    }
}
