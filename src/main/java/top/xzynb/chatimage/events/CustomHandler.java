package top.xzynb.chatimage.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import top.xzynb.chatimage.ChatImage;

public class CustomHandler implements Listener {
    @EventHandler
    public void PlayerChat(AsyncPlayerChatEvent event) {
        if (ChatImage.handle(event.getMessage(), event.getPlayer())){
            event.setCancelled(true);
        }
    }
}