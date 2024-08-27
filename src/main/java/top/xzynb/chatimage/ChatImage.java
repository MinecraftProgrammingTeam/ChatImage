package top.xzynb.chatimage;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import top.xzynb.chatimage.events.CustomHandler;
import top.xzynb.chatimage.exceptions.NotCQCodeException;
import top.xzynb.chatimage.executor.CommandHandler;
import top.xzynb.chatimage.utils.CQCode;

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

        getLogger().info(ChatColor.GREEN + "Enabled ChatImage Plugin!");
        getLogger().info(ChatColor.GREEN + "More Info on " + ChatColor.BLUE + ChatColor.UNDERLINE + "https://github.com/MinecraftProgrammingTeam/ChatImage");
    }

    /**
     * 处理消息中的CQ码并转化
     * @param message 消息
     * @param event_player 消息发送者
     * @return 是否包含图片
     */
    public static boolean handle(@NotNull  String message, @NotNull Player event_player){
        try{
            CQCode cqCode = new CQCode(message);
            if(cqCode.getType().equals("image")){
                String url = cqCode.getArg("url");
                Integer id;
                if (url == null) {
                    return false;
                }
                if (ChatImage.instance.urlMap.containsKey(url)) {
                    id = ChatImage.instance.urlMap.get(url);
                }else{
                    id = ChatImage.instance.urlMap.size();
                    ChatImage.instance.urlMap.put(url, id);
                    ChatImage.instance.keyMap.put(id, url);
                }

                String msg = "<" + event_player.getName() + "> " +
                        message.replace(cqCode.getCqCode(),
                                ChatColor.GREEN + "[Click to view image #" + id + "]" + ChatColor.RESET);

                Text hover_text = new Text(url);
                ComponentBuilder componentBuilder = new ComponentBuilder();
                componentBuilder.append(msg);
                componentBuilder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ci view " + id));
                componentBuilder.event(new HoverEvent(hover_text.requiredAction(), hover_text));
                ChatImage.instance.getServer().spigot().broadcast(componentBuilder.create());

                return true;
            }
        }catch (NotCQCodeException e){
            // ignore
        }
        return false;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
