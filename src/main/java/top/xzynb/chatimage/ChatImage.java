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
import java.util.List;
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

    public static boolean handle(@NotNull String message, Player player){
        return handle(message, player.getName());
    }

    /**
     * 处理消息中的CQ码并转化
     * @param message 消息
     * @param playerName 消息发送者名称
     * @return 是否包含图片
     */
    public static boolean handle(@NotNull String message, String playerName){
        try{
            ComponentBuilder componentBuilder = new ComponentBuilder();
            if (playerName != null) {
                componentBuilder.append(ChatColor.YELLOW + "<" + playerName + "> " + ChatColor.RESET);
            }

            boolean retFlag = false;
            List<CQCode> cqCodes = CQCode.parseCQCodes(message);
            CQCode _lastCqCode = null;
            for (CQCode cqCode : cqCodes){
                if(cqCode.getType().equals("image")){
                    String url = cqCode.getArg("url");
                    if (url == null) {
                        url = cqCode.getArg("file");
                    }
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

                    // 截取从上一段CQCode到这个CQCode中间的纯文本
                    String msg;
                    if (_lastCqCode == null){
                        msg = message.substring(0, cqCode.getStartIndex());
                    }else{
                        msg = message.substring(_lastCqCode.getEndIndex(), cqCode.getStartIndex());
                    }

                    Text hover_text = new Text(url);
                    componentBuilder.append(msg + ChatColor.GREEN + ChatColor.UNDERLINE + "[Click to view Image #" + id + "]" + ChatColor.RESET);
                    componentBuilder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ci view " + id));
                    componentBuilder.event(new HoverEvent(hover_text.requiredAction(), hover_text));

                    retFlag = true;
                    _lastCqCode = cqCode;
                }
            }
            if (retFlag) ChatImage.instance.getServer().spigot().broadcast(componentBuilder.create());
            return retFlag;
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
