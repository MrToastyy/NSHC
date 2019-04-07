package me.towaha.nshc;

import org.bukkit.plugin.java.JavaPlugin;

public final class NSHC extends JavaPlugin {

    public EventListeners el;
    public ChatManager cm;
    public ScoreboardManager sm;
    public PlayerListManager lm;

    @Override
    public void onEnable() {
        // Create instances of all the available managers.
        cm = new ChatManager(this);
        sm = new ScoreboardManager(this);
        el = new EventListeners(this);
        lm = new PlayerListManager(this);

        // Register the events class as an event handler.
        getServer().getPluginManager().registerEvents(el, this);

        cm.sendConsoleMessage("Plugin has finished loading.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
