package top.xzynb.chatimage.commands.impl;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import top.xzynb.chatimage.ChatImage;
import top.xzynb.chatimage.commands.ICommand;

public class version extends ICommand {
    public version(){
        super("version", "", "Show version");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        sender.sendMessage(ChatColor.YELLOW + "ChatImage v" + ChatImage.instance.getDescription().getVersion());

        return true;
    }

    public String permission(){
        return "chatimage.ci.version";
    }
}