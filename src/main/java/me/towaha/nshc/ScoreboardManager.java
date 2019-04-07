package me.towaha.nshc;

import org.bukkit.entity.Player;

public class ScoreboardManager {
    NSHC main;

    public ScoreboardManager(NSHC main) {
        this.main = main;
    }

    public void updateScoreboard() {
        // If no player is provided, it will update the scoreboard for all players that are currently in the server.
        for (Player player : main.getServer().getOnlinePlayers()) {
            updateScoreboard(player);
        }
    }

    public void updateScoreboard(Player player) {
        // Update the scoreboard for the player here.

    }
}
