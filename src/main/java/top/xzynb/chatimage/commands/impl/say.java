package top.xzynb.chatimage.commands.impl;

import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.xzynb.chatimage.ChatImage;
import top.xzynb.chatimage.commands.ICommand;

public class say extends ICommand {
    public say(){
        super("say", "<Content>...", "Say something with image inside");
    }

    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage(ChatColor.RED + "What do you want to say?");
        }
        StringBuilder message = new StringBuilder();
        for (String arg : args){
            message.append(arg).append(" ");
        }
        if (!ChatImage.handle(message.toString(), (Player) sender)){
            ComponentBuilder componentBuilder = new ComponentBuilder();
            componentBuilder.append(ChatColor.YELLOW + "<" + sender.getName() + "> " + ChatColor.RESET + message);
            ChatImage.instance.getServer().spigot().broadcast(componentBuilder.create());
        }

        return true;
    }

    public String permission(){
        return "chatimage.ci.say";
    }
}