package top.xzynb.chatimage.commands.impl;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import top.xzynb.chatimage.ChatImage;
import top.xzynb.chatimage.commands.ICommand;

import java.util.Objects;

public class clear extends ICommand {
    public clear(){
        super("clear", "[Player ID]", "Clear Player's Image Map");
    }

    private void clearMaps(Player player){
        Inventory inv = player.getInventory();
        inv.forEach(itemStack -> {
            if (Objects.requireNonNull(Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName()).contains("Image #")){
                inv.remove(itemStack);
            }
        });
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length != 0){
            for (String playerName : args){
                clearMaps(Objects.requireNonNull(ChatImage.instance.getServer().getPlayer(playerName)));
            }
        }else{
            ChatImage.instance.getServer().getOnlinePlayers().forEach(this::clearMaps);
        }

        sender.sendMessage(ChatColor.GREEN + "Already cleared Image Maps");

        return true;
    }

    public String permission(){
        return "chatimage.ci.clear";
    }
}