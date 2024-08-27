package top.xzynb.chatimage.commands.impl;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.xzynb.chatimage.ChatImage;
import top.xzynb.chatimage.commands.ICommand;

public class send extends ICommand {
    public send(){
        super("send", "<Image_URL>", "Send image");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "What image do you want to send?");
        }
        String url = args[0];
        ChatImage.handle("[CQ:image,url=" + url + "]", (Player) sender);

        return true;
    }

    public String permission(){
        return "chatimage.ci.send";
    }
}