package top.xzynb.chatimage.commands.impl;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import top.xzynb.chatimage.ChatImage;
import top.xzynb.chatimage.commands.ICommand;

import static org.bukkit.Bukkit.getOnlinePlayers;

public class test extends ICommand {
    public test(){
        super("test", "", "test");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        getOnlinePlayers()
                .forEach(player -> {
                    player.sendMessage(ChatColor.GREEN + "Test");
                });

        return true;
    }

    public String permission(){
        return "chatimage.ms.test";
    }
}