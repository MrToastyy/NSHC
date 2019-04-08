package me.towaha.nshc;

import org.bukkit.plugin.java.JavaPlugin;

public final class NSHC extends JavaPlugin {

    public EventListeners el;
    public ChatManager cm;
    public ScoreboardManager sm;
    public PlayerListManager lm;
    public BossbarManager bm;

    @Override
    public void onEnable() {
        // Create instances of all the available managers.
        cm = new ChatManager(this);
        sm = new ScoreboardManager(this);
        el = new EventListeners(this);
        lm = new PlayerListManager(this);
        bm = new BossbarManager(this);

        // Register the events class as an event handler.
        getServer().getPluginManager().registerEvents(el, this);

        cm.sendConsoleMessage("Plugin has finished loading.");
        bm.onStart();

        // Register the commands to separate classes.
        this.getCommand("NSHCBossBar").setExecutor(bm);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
