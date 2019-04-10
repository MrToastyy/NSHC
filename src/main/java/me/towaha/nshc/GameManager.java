package me.towaha.nshc;

import org.apache.commons.io.FileUtils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameManager implements CommandExecutor {
    NSHC main;

    public GameManager(NSHC main) {
        this.main = main;
    }

    World nshcWorld;

    public void onEnable() {
        generateNewWorld();
    }

    private List<String> seeds = new ArrayList<>(Arrays.asList("-2193929129458320090"));

    private void teleportPlayerToWorld(Player player) {
        Location destination = new Location(nshcWorld, nshcWorld.getSpawnLocation().getX(), nshcWorld.getSpawnLocation().getY(), nshcWorld.getSpawnLocation().getZ());
        player.teleport(destination);
        //TODO: make this functional
    }

    private void generateRandomCords() {
        //TODO: generate cords in a way that every player is not near another player (at least 500 blocks space or so)
        //TODO: also give players in the same team the same cords
        //TODO: check if players are above water or lava and if so generate new cords
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
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equalsIgnoreCase("nshcgamemanager")) {
            if(sender.hasPermission("nshc.gamemanger")) {
                teleportPlayerToWorld((Player) sender);
            }
        }
        return false;
    }
}
