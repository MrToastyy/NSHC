package me.towaha.nshc;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

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
        org.bukkit.scoreboard.ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("scoreboard","dummy", "§e§l");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score playersAlive = objective.getScore("§9§l> §b§lPlayers Alive:");                                playersAlive.setScore(10);
        Score playersAliveValue = objective.getScore("§r[playersAlive]");                                   playersAliveValue.setScore(9);

        Score blank1 = objective.getScore(" ");                                                             blank1.setScore(8);

        Score timer = objective.getScore("§9§l> §b§lTime until §r[event]§b§l:");                            timer.setScore(7);
        Score timerValue = objective.getScore("§r[timeLeft]");                                              timerValue.setScore(6);

        Score blank2 = objective.getScore("  ");                                                            blank2.setScore(5);

        Score borderDistance = objective.getScore("§9§l> §b§lDistance from border:");                       borderDistance.setScore(4);
        Score borderDistanceValue = objective.getScore("§f§lx: §r[distanceX] §7§l| §f§ly: §r[distanceY]");  borderDistanceValue.setScore(3);

        Score blank3 = objective.getScore("   ");                                                           blank3.setScore(2);

        Score kills = objective.getScore("§9§l> §b§lKills:");                                               kills.setScore(1);
        Score killsValue = objective.getScore("§r[kills]");                                                 killsValue.setScore(0);
    }
}
