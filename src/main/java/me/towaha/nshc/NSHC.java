package me.towaha.nshc;

import org.bukkit.plugin.java.JavaPlugin;

public final class NSHC extends JavaPlugin {

    public ChatManager cm;
    public ScoreboardManager sm;

    @Override
    public void onEnable() {
        // Create instances of all the available managers.
        cm = new ChatManager(this);
        sm = new ScoreboardManager(this);

        cm.sendConsoleMessage("Plugin has finished loading.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
