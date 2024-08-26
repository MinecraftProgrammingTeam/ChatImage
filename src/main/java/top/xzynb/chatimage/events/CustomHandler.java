package top.xzynb.chatimage.events;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import top.xzynb.chatimage.ChatImage;
import top.xzynb.chatimage.exceptions.NotCQCodeException;
import top.xzynb.chatimage.utils.CQCode;

public class CustomHandler implements Listener {
    @EventHandler
    public void PlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        try{
            CQCode cqCode = new CQCode(message);
            if(cqCode.getType().equals("image")){
                event.setCancelled(true);
                String url = cqCode.getArg("url");
                Integer id;
                if (url == null) {
                    return;
                }
                if (ChatImage.instance.urlMap.containsKey(url)) {
                    id = ChatImage.instance.urlMap.get(url);
                }else{
                    id = ChatImage.instance.urlMap.size();
                    ChatImage.instance.urlMap.put(url, id);
                    ChatImage.instance.keyMap.put(id, url);
                }

                String msg = "<" + event.getPlayer().getName() + "> " +
                        message.replace(cqCode.getCqCode(),
                                ChatColor.GREEN + "[Click to view image #" + id + "]" + ChatColor.RESET);

                Text hover_text = new Text(url);
                ComponentBuilder componentBuilder = new ComponentBuilder();
                componentBuilder.append(msg);
                componentBuilder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ci view " + id));
                componentBuilder.event(new HoverEvent(hover_text.requiredAction(), hover_text));
                event.getPlayer().spigot().sendMessage(componentBuilder.create());
            }
        }catch (NotCQCodeException e){
            // ignore
        }
    }
}