package me.towaha.nshc;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;

public class WorldBorderManager {
    NSHC main;

    private WorldBorder worldBorder;

    public WorldBorderManager(NSHC main) {
        this.main = main;
    }

    public void setWorldBorder(World world) {
        worldBorder = world.getWorldBorder();
    }

    public void setWorldBorderSize(int size) {
        worldBorder.setSize(size);
    }

    public void setWorldBorderSize(int size, int seconds) {
        worldBorder.setSize(size, seconds);
    }
}
