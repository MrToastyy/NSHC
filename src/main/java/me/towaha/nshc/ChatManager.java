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

    public void sendConsoleMessage(String message) {
        // This will log a formatted message to the console.
        main.getServer().getConsoleSender().sendMessage("§7[§bNSHC§7] §r" + message);
    }

    public void broadcastMessage(String message) {
        // This will send a message to all the players that are currently online.
        for (Player player : main.getServer().getOnlinePlayers()) {
            sendMessage(message, player);
        }
    }

    public void sendMessage(String message, Player player) {
        player.sendMessage("§9NSHC §8» §r" + message);
    }

    public void actionBarMessage(String message, Player player) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(message));
    }
}
