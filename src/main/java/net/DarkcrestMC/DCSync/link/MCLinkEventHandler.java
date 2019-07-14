package net.DarkcrestMC.DCSync.link;

import net.DarkcrestMC.DCSync.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class MCLinkEventHandler implements Listener {



    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        //if player is verified, put them as confirmed so they cannot sync more accounts
        //if player is not verified, but in config, put user's code in userMap (<UUID, String>)
        //if player isn't in the config (not verified), tell the user to verify!

        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();
        if (Utils.getUserStatus(player) == null) {
            try {
                Bukkit.getPlayer(playerUUID).sendMessage(Utils.serverPrefix + "I see you haven't signed up for our discord! Use this link to join and verify your user: " + Utils.discordLink);
            } catch (Exception e) {
                Bukkit.getServer().getLogger().warning(e.getLocalizedMessage());
            }
        } else if (Utils.checkUserUnconfirmed(player)) {
            Bukkit.getPlayer(playerUUID).sendMessage(Utils.serverPrefix + "You have a confirmation code for discord waiting! Please check your private messages with the DCSync Bot!");
        }
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {

    }
}
