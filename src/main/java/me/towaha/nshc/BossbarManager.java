package me.towaha.nshc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BossbarManager implements CommandExecutor {
    NSHC main;

    private BossBar bar;
    private List<Player> players = new ArrayList<>();
    private List<String> colors = new ArrayList<>(Arrays.asList("white", "blue", "green", "pink", "purple", "red", "yellow"));

    public BossbarManager(NSHC main) {
        this.main = main;
    }

    public void onStart() {
         bar = Bukkit.createBossBar("§rThere should be text here", BarColor.PURPLE, BarStyle.SOLID);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("NSHCBossBar")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(sender.hasPermission("nshc.bossbar")) {
                    if(args.length == 0) {
                        main.cm.sendMessage(
                                "§6Usage:" +
                                        "\n§8- §f/nshcbossbar settitle §7[title]" +
                                        "\n§8- §f/nshcbossbar addplayer §7[player]" +
                                        "\n§8- §f/nshcbossbar removeplayer §7[player]", player);
                        return true;
                    } else {
                        if(args[0].equalsIgnoreCase("setTitle")) {
                            if(args.length >= 2) {
                                StringBuilder input = new StringBuilder();
                                for (int i = 1; i < args.length; i++) {
                                    if(i != 1)
                                        input.append(" ");
                                    input.append(args[i]);
                                }
                                changeBossbarTitle(input.toString().replaceAll("&", "§"));
                                main.cm.sendMessage("§aSuccessfully changed the BossBar title to §r" + ChatColor.translateAlternateColorCodes('&', input.toString()) + "§a.", player);
                            } else {
                                main.cm.sendMessage("§ePlease also specify what the title needs to change to.", player);
                            }
                            return true;
                        } else if(args[0].equalsIgnoreCase("addPlayer")) {
                            if(args.length >= 2) {
                                for (Player target : Bukkit.getOnlinePlayers()) {
                                    if(target.getName().equals(args[1]) || target.getDisplayName().equals(args[1])) {
                                        if(players.contains(target)) {
                                            main.cm.sendMessage("§eThat player already has a BossBar.", player);
                                            return true;
                                        } else {
                                            addBossbarPlayer(target);
                                            main.cm.sendMessage("§aSuccessfully added §2" + target.getDisplayName() + " §ato the BossBar.", player);
                                            return true;
                                        }
                                    }
                                }
                                main.cm.sendMessage("§eThat player can not be found.", player);
                            } else {
                                main.cm.sendMessage("§ePlease also specify which player needs to be added.", player);
                            }
                        } else if(args[0].equalsIgnoreCase("removePlayer")) {
                            if(args.length >= 2) {
                                for (Player target : Bukkit.getOnlinePlayers()) {
                                    if(target.getName().equals(args[1]) || target.getDisplayName().equals(args[1])) {
                                        if(players.contains(target)) {
                                            main.cm.sendMessage("§eThat player already has a BossBar.", player);
                                            return true;
                                        } else {
                                            removeBossbarPlayer(target);
                                            main.cm.sendMessage("§aSuccessfully removed §2" + target.getDisplayName() + " §afrom the BossBar.", player);
                                            return true;
                                        }
                                    }
                                }
                                main.cm.sendMessage("§eThat player can not be found.", player);
                            } else {
                                main.cm.sendMessage("§ePlease also specify which player needs to be removed.", player);
                            }
                        } else {
                            main.cm.sendMessage("§eThe second argument §6" + args[0] + " §eis not a valid argument.", player);
                        }
                        return true;
                    }
                } else {
                    main.cm.sendMessage("§cYou do not have the permission to execute that command.", player);
                    return true;
                }
            } else {
                sender.sendMessage("§7[§bNSHC§7] §cOnly players can use this command.");
                return true;
            }
        }
        return false;
    }

    public void changeBossbarColor(String color) {
        if(colors.contains(color)) {
            //TODO: functionality
        } else {
            //TODO: color doesn't exist
        }
    }

    public void changeBossbarTitle(String msg) {
        bar.setTitle(msg);
    }

    public void addBossbarPlayer(Player player) {
        bar.addPlayer(player);
    }

    public void removeBossbarPlayer(Player player) {
        bar.removePlayer(player);
    }
}
