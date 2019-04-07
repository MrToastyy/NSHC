package me.towaha.nshc;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ChatManager {
    NSHC main;

    public ChatManager(NSHC main) {
        this.main = main;
    }

    public void sendConsoleMessage(String msg) {
        // This will log a formatted message to the console.
        main.getServer().getConsoleSender().sendMessage("§7[§bNSHC§7] §r" + msg);
    }

    public void broadcastMessage(String msg) {
        // This will send a message to all the players that are currently online.
        for (Player player : main.getServer().getOnlinePlayers()) {
            sendMessage(msg, player);
        }
    }

    public void sendMessage(String msg, Player player) {
        player.sendMessage("§9NSHC §8» §r" + msg);
    }

    public void actionBarMessage(String msg, Player player) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(msg));
    }
}
