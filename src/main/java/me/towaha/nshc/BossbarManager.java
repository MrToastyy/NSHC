package me.towaha.nshc;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class BossbarManager {
    NSHC main;

    private BossBar bar;

    public BossbarManager(NSHC main) {
        this.main = main;
    }

    public void onStart() {
         bar = Bukkit.createBossBar("§rThere should be text here", BarColor.WHITE, BarStyle.SOLID);
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
