package me.towaha.nshc;

import org.bukkit.entity.Player;

public class PlayerListManager {
    NSHC main;

    public PlayerListManager(NSHC main) {
        this.main = main;
    }

    public void updatePlayerList() {
        // If no player is provided, it will update the scoreboard for all players that are currently in the server.
        for (Player player : main.getServer().getOnlinePlayers()) {
            updatePlayerList(player);
        }
    }

    public void updatePlayerList(Player player) {
        // Update the player list for the player here.
        player.setPlayerListHeaderFooter("§aWelcome to §6§lXenos", "");
    }
}
