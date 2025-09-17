package com.github.bradleywoodrs.triviacommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.BroadcastMessageEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class Triviacommands extends JavaPlugin implements Listener {

    private String difficulty = null;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBroadcast(BroadcastMessageEvent e) {
        String msg = ChatColor.stripColor(e.getMessage());
        if (msg.contains("Beginner")) {
            difficulty = "beginner";
        } else if (msg.contains("Advanced")) {
            difficulty = "advanced";
        } else if (msg.contains("Expert")){
            difficulty = "expert";
        }

        if (difficulty != null && msg.contains("has won the quiz!")) {
            String playerName = msg.split(" ")[0];
            List<String> commands = getConfig().getStringList("rewards." + difficulty);
            for (String cmd : commands) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),
                        cmd.replace("{player}", playerName));
            }
            difficulty = null;
        }else if (difficulty != null && (msg.contains("The quiz is over! No one has given the correct answer."))){
            difficulty = null;
        }
    }
}
