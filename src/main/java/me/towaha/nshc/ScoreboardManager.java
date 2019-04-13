package me.towaha.nshc;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScoreboardManager implements CommandExecutor, TabCompleter {
    //TODO: store the variable animatedScoreboard in the config.

    NSHC main;

    private boolean animatedScoreboard;

    public void onEnable() {
        updateScoreboard();
    }

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
        if(animatedScoreboard)
            return;

        // Update the scoreboard for the player here.
        org.bukkit.scoreboard.ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = scoreboardManager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("scoreboard","dummy", "§6§lXenos");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score playersAlive = objective.getScore("§9§l> §b§lPlayers Alive:");                playersAlive.setScore(10);
        Score playersAliveValue = objective.getScore("§r[playersAlive]");                   playersAliveValue.setScore(9);

        Score blank1 = objective.getScore(" ");                                             blank1.setScore(8);

        Score timer = objective.getScore("§9§l> §b§lTime until §r[event]§b§l:");            timer.setScore(7);
        Score timerValue = objective.getScore("§r[timeLeft]");                              timerValue.setScore(6);

        Score blank2 = objective.getScore("  ");                                            blank2.setScore(5);

        Score borderDistance = objective.getScore("§9§l> §b§lBorder position:");            borderDistance.setScore(4);
        Score borderDistanceValue = objective.getScore("§r+[x], -[x]");                 borderDistanceValue.setScore(3);

        Score blank3 = objective.getScore("   ");                                           blank3.setScore(2);

        Score kills = objective.getScore("§9§l> §b§lKills:");                               kills.setScore(1);
        Score killsValue = objective.getScore("§r[kills]");                                 killsValue.setScore(0);

        player.setScoreboard(scoreboard);
    }

    private int animation;

    private void enableAnimation() {
        animatedScoreboard = true;

        animation = Bukkit.getScheduler().scheduleSyncRepeatingTask(main, new Runnable() {
            int sec = 0;

            org.bukkit.scoreboard.ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
            Scoreboard scoreboard = scoreboardManager.getNewScoreboard();

            Objective objective = scoreboard.registerNewObjective("scoreboard","dummy", "§6§lXenos");
            @Override
            public void run()
            {
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);

                switch (sec) {
                    case 0:
                        objective.setDisplayName("§6§l<<<§f§l< §d§lXenos §f§l>§6§l>>>");
                        break;
                    case 1:
                        objective.setDisplayName("§6§l<<§f§l<§6§l< §d§lXenos §6§l>§f§l>§6§l>>");
                        break;
                    case 2:
                        objective.setDisplayName("§6§l<§f§l<§6§l<< §d§lXenos §6§l>>§f§l>§6§l>");
                        break;
                    case 3:
                        objective.setDisplayName("§f§l<§6§l<<< §d§lXenos §6§l>>>§f§l>");
                        break;
                    case 4: case 5: case 6: case 7:
                        objective.setDisplayName("§6§l<<<< §d§lXenos §6§l>>>>");
                        break;
                    case 8:
                        objective.setDisplayName("§f§l<§6§l<<< §d§lXenos §6§l>>>§f§l>");
                        break;
                    case 9:
                        objective.setDisplayName("§6§l<§f§l<§6§l<< §d§lXenos §6§l>>§f§l>§6§l>");
                        break;
                    case 10:
                        objective.setDisplayName("§6§l<<§f§l<§6§l< §d§lXenos §6§l>§f§l>§6§l>>");
                        break;
                    case 11:
                        objective.setDisplayName("§6§l<<<§f§l< §d§lXenos §f§l>§6§l>>>");
                        break;
                    case 12: case 13: case 14: case 15: case 16: case 17: case 18: case 19: case 20:
                        objective.setDisplayName("§6§l<<<< §d§lXenos §6§l>>>>");
                        break;
                }

                Score playersAlive = objective.getScore("§9§l> §b§lPlayers Alive:");                playersAlive.setScore(10);
                Score playersAliveValue = objective.getScore("§r[playersAlive]");                   playersAliveValue.setScore(9);

                Score blank1 = objective.getScore(" ");                                             blank1.setScore(8);

                Score timer = objective.getScore("§9§l> §b§lTime until §r[event]§b§l:");            timer.setScore(7);
                Score timerValue = objective.getScore("§r[timeLeft]");                              timerValue.setScore(6);

                Score blank2 = objective.getScore("  ");                                            blank2.setScore(5);

                Score borderDistance = objective.getScore("§9§l> §b§lBorder position:");            borderDistance.setScore(4);
                Score borderDistanceValue = objective.getScore("§r+[x], -[x]");                 borderDistanceValue.setScore(3);

                Score blank3 = objective.getScore("   ");                                           blank3.setScore(2);

                Score kills = objective.getScore("§9§l> §b§lKills:");                               kills.setScore(1);
                Score killsValue = objective.getScore("§r[kills]");                                 killsValue.setScore(0);

                for(Player player : Bukkit.getOnlinePlayers()) {
                    player.setScoreboard(scoreboard);
                }

                if(sec == 20) {
                    sec = -1;
                }
                sec++;
            }
        }, 5, 1);
    }

    private void disableAnimation() {
        animatedScoreboard = false;
        Bukkit.getScheduler().cancelTask(animation);
        updateScoreboard();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("nshcscoreboard")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(main.hasPermission(player, "nshc.scoreboard")) {
                    if(args.length == 0) {
                        main.cm.sendMessage(
                                "§6Usage:" +
                                        "\n§8- §f/nshcscoreboard animated §7[true/false]", player);
                    } else if(args.length >= 1) {
                        if(args[0].equalsIgnoreCase("animated")) {
                            if(args.length >= 2) {
                                if(args[1].equalsIgnoreCase("yes") || args[1].equalsIgnoreCase("true")) {
                                    main.cm.sendMessage("§aThe scoreboard animation has been turned §2on§a.", player);
                                    enableAnimation();
                                } else if(args[1].equalsIgnoreCase("no") || args[1].equalsIgnoreCase("false")) {
                                    main.cm.sendMessage("§aThe scoreboard animation has been turned §coff§a.", player);
                                    disableAnimation();
                                } else {
                                    main.cm.sendMessage("§eThe state §6" + args[1] + " §eis not a valid state." +
                                            "\n§7It can only be true or false.", player);
                                }
                            } else {
                                main.cm.sendMessage("§ePlease also input what it needs to be toggled to §7[true/false]§e.", player);
                            }
                        } else {
                            main.cm.sendMessage("§eThe argument §6" + args[0] + " §eis not a valid argument.", player);
                        }
                    }
                }
            } else {
                sender.sendMessage("§7[§bNSHC§7] §cOnly players can use this command.");
            }
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("nshcscoreboard")) {
            if(args.length == 1) {
                List<String> options = new ArrayList<>(Arrays.asList("animated"));
                return main.getAvailableOptions(options, args[0]);
            } else if(args.length == 2) {
                if(args[0].equalsIgnoreCase("animated")) {
                    List<String> options = new ArrayList<>(Arrays.asList("true", "false"));
                    return main.getAvailableOptions(options, args[1]);
                }
            }
        }
        return null;
    }
}
