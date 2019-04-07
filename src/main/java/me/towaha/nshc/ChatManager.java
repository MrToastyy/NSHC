package me.towaha.nshc;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatManager {
    NSHC main;

    public ChatManager(NSHC main) {
        this.main = main;
    }

    public void sendConsoleMessage(String msg) {
        // This will log a formatted message to the console.
        main.getLogger().info(msg); // TODO: 7-4-2019 - Change to your method @Toaster
    }

    public void broadcastMessage(String msg) {
        // This will send a message to all the players that are currently online.
        for (Player player : main.getServer().getOnlinePlayers()) {
            sendMessage(msg, player);
        }
    }

    public void sendMessage(String msg, Player player) {
        player.sendMessage("§9NSHC §8>> §r" + msg);
    }
}
