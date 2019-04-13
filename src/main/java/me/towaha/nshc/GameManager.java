package me.towaha.nshc;

import com.mysql.fabric.xmlrpc.base.Array;
import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class GameManager implements CommandExecutor, TabCompleter {
    NSHC main;

    public GameManager(NSHC main) {
        this.main = main;
    }

    World nshcWorld;

    public void onEnable() {
        generateNewWorld();
    }

    private List<String> seeds = new ArrayList<>(Arrays.asList("-2193929129458320090"));
    private HashMap<Integer, Location> spawnLocation = new HashMap<>();        //Integer = Team | Location = spawnPosition

    private Random random = new Random();

    private void teleportPlayerToWorld(Player player, String worldStr) {
        World world;
        try {
            world = Bukkit.getWorld(worldStr);
            Location destination = new Location(world, nshcWorld.getSpawnLocation().getX(), nshcWorld.getSpawnLocation().getY(), nshcWorld.getSpawnLocation().getZ());
            player.teleport(destination);
        } catch (NullPointerException | IllegalArgumentException e) {
            main.cm.sendConsoleMessage("§cThe world §4" + worldStr + " §ccould not be found.\n§4Exception: " + e);
            for (Player target : Bukkit.getOnlinePlayers()) {
                if(target.hasPermission("gamemanager.admin")) {
                    main.cm.sendMessage("§cThe world §4" + worldStr + " §ccould not be found. §8(§7GameManager.Ln:41§8)\n§4Exception: " + e, target);
                }
            }
        }
    }

    private void generateRandomCords(int teams) {
        main.cm.broadcastMessage(teams + "");
        for (int i = 0; i < teams; i++) {
            main.cm.broadcastMessage("for1: " + i);
            getRandomCords(i);
            main.cm.broadcastMessage("§e" + spawnLocation.get(i).toString());
        }
        main.cm.broadcastMessage("§6-----------\nwow");
        //Bukkit.getPlayer("Twoaster").teleport(spawnLocation.get(teams));
        //TODO: generate cords in a way that every player is not near another player (at least 150 blocks space or so)
        //TODO: also give players in the same team the same cords
        //TODO: check if players are above water or lava and if so generate new cords
    }

    private void getRandomCords(int team) {
        Location destination = null;
        do {
            int x = getRandomInt(-450, 450);
            int z = getRandomInt(-450, 450);
            Block highestBlock = nshcWorld.getHighestBlockAt(x, z);
            if(highestBlock.getRelative(0, -1, 0).getType().equals(Material.WATER) ||
                    highestBlock.getRelative(0, -1, 0).getType().equals(Material.LAVA) ||
                    highestBlock.getRelative(0, -1, 0).getType().equals(Material.SEAGRASS))
                continue;
            int y = highestBlock.getY();
            destination = new Location(nshcWorld, x + 0.5, y, z + 0.5);
            boolean skip = false;
            if(team > 0) {
                for (int i = team; i > 0; i--) {
                    main.cm.broadcastMessage("for2: " + i);
                    main.cm.broadcastMessage(destination.distance(spawnLocation.get(i - 1)) + "");
                    if(destination.distance(spawnLocation.get(i - 1)) < 125) {
                        skip = true;
                        break;
                    }
                }
            }
            if(skip)
                destination = null;
            spawnLocation.put(team, destination);
        } while (destination == null);
    }

    private void generateNewWorld() {
        if(Bukkit.getWorld("nshcWorld") != null) {
            for (Player player : Bukkit.getWorld("nshcWorld").getPlayers())
                player.teleport(new Location(Bukkit.getWorld("world"), 0, 0, 0));
            Bukkit.unloadWorld("nshcWorld", false);
            try {
                FileUtils.deleteDirectory(new File("nshcWorld"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        WorldCreator worldCreator = new WorldCreator("nshcWorld");
        worldCreator.seed(Long.parseLong(seeds.get(0)));
        nshcWorld = main.getServer().createWorld(worldCreator);

        main.wm.setWorldBorder(nshcWorld);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("nshcgamemanager")) {
            if(sender instanceof Player) {
                Player player = (Player) sender;
                if(sender.hasPermission("nshc.gamemanger")) {
                    if(args.length == 0) {
                        main.cm.sendMessage("§6Usage:" +
                                "\n§8- §f/nshcgamemanager broadcast §7[message]", player);
                    } else {
                        if(args[0].equalsIgnoreCase("startgame")) {
                            if(args.length >= 2) {
                                if(args[1].equalsIgnoreCase("xvx")) {

                                } else if(args[1].equalsIgnoreCase("xvxvx")) {

                                } else if(args[1].equalsIgnoreCase("3v3")) {

                                } else if(args[1].equalsIgnoreCase("4v4")) {

                                }
                            } else {
                                main.cm.sendMessage("§ePlease also specify what kind of game needs to be started.", player);
                            }
                        } else if(args[0].equalsIgnoreCase("broadcast")) {
                            if(args.length >= 2) {
                                main.cm.specialBroadcastMessage(ChatColor.translateAlternateColorCodes('&', args[1]));
                            } else {
                                main.cm.sendMessage("§ePlease also specify the message that needs to be broadcast.", player);
                            }
                        } else {
                            main.cm.sendMessage("§eThe argument §6" + args[0] + " §eis not a valid argument.", player);
                        }

                        //Temporary
                        if(args[0].equalsIgnoreCase("test")) {
                            main.wm.setWorldBorderSize(Integer.parseInt(args[0]));
                        }
                    }

                }
                return true;
            } else {
                sender.sendMessage("§7[§bNSHC§7] §cOnly players can use this command.");
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("nshcgamemanager")) {
            if(args.length == 1) {
                List<String> options = new ArrayList<>(Arrays.asList("startgame", "broadcast"));
                return main.getAvailableOptions(options, args[0]);
            }
        }
        return null;
    }

    private int getRandomInt(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        return random.nextInt((max - min) + 1) + min;
    }
}
