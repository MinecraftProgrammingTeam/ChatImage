package top.xzynb.chatimage;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import top.xzynb.chatimage.executor.CommandHandler;

import java.util.Objects;

public final class ChatImage extends JavaPlugin {
    public static ChatImage instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        // Plugin startup logic
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        Objects.requireNonNull(getCommand("ci")).setExecutor(new CommandHandler());

        getLogger().info(ChatColor.GREEN + "Enabled MessageSync Plugin for PBF!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
