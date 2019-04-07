package me.towaha.nshc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListeners implements Listener {
    NSHC main;

    public EventListeners(NSHC main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.setJoinMessage("§8[§2+§8] §a" + e.getPlayer());

        main.lm.updatePlayerList();
        main.sm.updateScoreboard();

        e.getPlayer().spigot().setCollidesWithEntities(false);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage("§8[§4-§8] §c" + e.getPlayer());

        main.lm.updatePlayerList();
        main.sm.updateScoreboard();
        e.getPlayer().spigot().setCollidesWithEntities(false);
    }
}
