package me.towaha.nshc;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class NSHC extends JavaPlugin {

    public EventListeners el;
    public ChatManager cm;
    public ScoreboardManager sm;
    public PlayerListManager lm;
    public BossbarManager bm;
    public GameManager gm;
    public WorldBorderManager wm;

    @Override
    public void onEnable() {
        // Create instances of all the available managers.
        cm = new ChatManager(this);
        sm = new ScoreboardManager(this);
        lm = new PlayerListManager(this);
        bm = new BossbarManager(this);
        gm = new GameManager(this);
        wm = new WorldBorderManager(this);

        el = new EventListeners(this);

        // Register the events class as an event handler.
        getServer().getPluginManager().registerEvents(el, this);

        cm.sendConsoleMessage("Plugin has finished loading.");
        bm.onEnable();
        sm.onEnable();
        gm.onEnable();

        // Register the commands to separate classes.
        this.getCommand("nshcbossbar").setExecutor(bm);
        this.getCommand("nshcscoreboard").setExecutor(sm);
        this.getCommand("nshcgamemanager").setExecutor(gm);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        bm.onDisable();
    }

    public List<String> getAvailableOptions(List<String> currentOptions, String input) {
        List<String> availableOptions = new ArrayList<>();
        for (String option : currentOptions) {
            int amountOfMatches = 0;
            for (int i = 0; i < input.length(); i++) {
                if(option.length() > i && option.charAt(i) == input.charAt(i))
                    amountOfMatches++;
            }
            if(amountOfMatches == input.length())
                availableOptions.add(option);
        }
        return availableOptions;
    }
}
