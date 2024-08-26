package top.xzynb.chatimage;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import top.xzynb.chatimage.events.CustomHandler;
import top.xzynb.chatimage.executor.CommandHandler;

import java.util.HashMap;
import java.util.Objects;

public final class ChatImage extends JavaPlugin {
    public static ChatImage instance;
    public final HashMap<String, Integer> urlMap = new HashMap<>();
    public final HashMap<Integer, String> keyMap = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        // Register the command
        Objects.requireNonNull(getCommand("ci")).setExecutor(new CommandHandler());

        // Register Events
        getServer().getPluginManager().registerEvents(new CustomHandler(), this);

        getLogger().info(ChatColor.GREEN + "Enabled MessageSync Plugin for PBF!");
    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
