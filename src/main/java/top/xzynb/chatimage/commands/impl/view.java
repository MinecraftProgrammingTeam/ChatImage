package top.xzynb.chatimage.commands.impl;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.map.MapView;
import top.xzynb.chatimage.ChatImage;
import top.xzynb.chatimage.commands.ICommand;
import top.xzynb.chatimage.utils.Utils;

public class view extends ICommand {
    public view(){
        super("view", "", "test");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            return true;
        }
        int id = Integer.parseInt(args[0]);
        String url = ChatImage.instance.keyMap.get(id);
        if (url == null) {
            sender.sendMessage(ChatColor.RED + "Image #" + id + " not found!");
            return true;
        }

        ComponentBuilder componentBuilder = new ComponentBuilder();
        componentBuilder.append(ChatColor.YELLOW + "Loading image #" + id + " from " + url + "...");
        Text hover_text = new Text("Click to copy");
        componentBuilder.event(new HoverEvent(hover_text.requiredAction(), hover_text));
        componentBuilder.event(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, url));
        sender.spigot().sendMessage(componentBuilder.create());
        MapView mapView = sender.getServer().createMap(sender.getServer().getWorlds().get(0));
        ItemStack itemStack = Utils.getImageMap(mapView, url, id);
        ((Player) sender).getInventory().addItem(itemStack);

        return true;
    }

    public String permission(){
        return "chatimage.ms.view";
    }
}