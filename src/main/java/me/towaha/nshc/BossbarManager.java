package me.towaha.nshc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class BossbarManager implements CommandExecutor {
    NSHC main;

    private BossBar bar;
    private List<Player> playersWithBossBar = new ArrayList<>();
    private List<String> colors = new ArrayList<>(Arrays.asList("white", "blue", "green", "pink", "purple", "red", "yellow"));

    private File listFile;
    private FileConfiguration listStorageFile;

    public BossbarManager(NSHC main) {
        this.main = main;
    }

    public void onEnable() {
        bar = Bukkit.createBossBar("§rThere should be text here", BarColor.PURPLE, BarStyle.SOLID);
        loadListStorageFile();

        for (String uuid : listStorageFile.getStringList("playerswithbossbar"))
            playersWithBossBar.add(Bukkit.getPlayer(UUID.fromString(uuid)));

        for (Player player : playersWithBossBar) {
            addBossbarPlayer(player);
            main.cm.broadcastMessage(player.toString());
        }
    }

    public void onDisable() {
        for (Player player : playersWithBossBar) {
            removeBossbarPlayer(player, false);
        }

        for (Player player : playersWithBossBar)
            listStorageFile.set("playerswithbossbar", player.getUniqueId().toString());

        try {
            listStorageFile.save(listFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadListStorageFile() {
        if(!main.getDataFolder().exists())
            main.getDataFolder().mkdir();

        listFile = new File(main.getDataFolder(), "bmPlayerList.yml");

        if(!listFile.exists()) {
            try {
                listFile.createNewFile();
            } catch (IOException e) {
                main.cm.sendConsoleMessage("§cCould not create the players.yml file. \n" + e);
            }
        }

        listStorageFile = YamlConfiguration.loadConfiguration(listFile);
    }

    public void onJoin(Player player) {
        if(playersWithBossBar.contains(player))
            addBossbarPlayer(player);
    }

    public void onQuit(Player player) {
        if(playersWithBossBar.contains(player))
            removeBossbarPlayer(player, false);
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
                                        "\n§8- §f/nshcbossbar removeplayer §7[player]" +
                                        "\n§8- §f/nshcbossbar setcolor §7[color]", player);
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
                                        if(playersWithBossBar.contains(target)) {
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
                                        if(playersWithBossBar.contains(target)) {
                                            main.cm.sendMessage("§eThat player already has a BossBar.", player);
                                            return true;
                                        } else {
                                            removeBossbarPlayer(target, true);
                                            main.cm.sendMessage("§aSuccessfully removed §2" + target.getDisplayName() + " §afrom the BossBar.", player);
                                            return true;
                                        }
                                    }
                                }
                                main.cm.sendMessage("§eThat player can not be found.", player);
                            } else {
                                main.cm.sendMessage("§ePlease also specify which player needs to be removed.", player);
                            }
                        } else if(args[0].equalsIgnoreCase("setcolor")) {
                            if(args.length >= 2) {
                                if(colors.contains(args[1].toLowerCase())) {
                                    changeBossbarColor(args[1].toLowerCase());
                                    main.cm.sendMessage("§aThe color of the BossBar has been changed to §2" + args[1].toLowerCase() + ".", player);
                                } else if(args[1].equalsIgnoreCase("list")) {
                                    main.cm.sendMessage("§6List of all colors:" +
                                            "\n§8- §fWhite" +
                                            "\n§8- §fBlue" +
                                            "\n§8- §fGreen" +
                                            "\n§8- §fPink" +
                                            "\n§8- §fPurple" +
                                            "\n§8- §fRed" +
                                            "\n§8- §fYellow", player);
                                }   else {
                                    main.cm.sendMessage("§eThe color §6" + args[1].toLowerCase() + " §eis not a valid color." +
                                            "\n§7Type §f/nshcbossbar setcolor list §7to see al available colors.", player);
                                }
                            } else {
                                main.cm.sendMessage("§ePlease also specify what color the bar needs to be set to.", player);
                            }
                        } else {
                            main.cm.sendMessage("§eThe argument §6" + args[0] + " §eis not a valid argument.", player);
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
            bar.setColor(BarColor.valueOf(color.toUpperCase()));
        }
    }

    public void changeBossbarTitle(String msg) {
        bar.setTitle(msg);
    }

    public void addBossbarPlayer(Player player) {
        bar.addPlayer(player);
        playersWithBossBar.add(player);
    }

    public void removeBossbarPlayer(Player player, Boolean removeFromList) {
        bar.removePlayer(player);
        if(removeFromList)
            playersWithBossBar.remove(player);
    }
}
