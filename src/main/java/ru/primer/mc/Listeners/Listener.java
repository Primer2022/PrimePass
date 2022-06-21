package ru.primer.mc.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import ru.primer.mc.Main;

public class Listener implements org.bukkit.event.Listener {
    public Listener(Main main) {
    }

    @EventHandler
    public void onPrelogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        FileConfiguration cfg = Main.getInstance().getConfig();
        if(!cfg.contains("data." + p.getName())) {
            cfg.set("data." + p.getName() + ".days", 0);
            cfg.set("data." + p.getName() + ".hours", 0);
            cfg.set("data." + p.getName() + ".minutes", 0);
            cfg.set("data." + p.getName() + ".seconds", 0);
            cfg.set("data." + p.getName() + ".infinity", false);
            Main.getInstance().saveConfig();
        }
        int days = cfg.getInt("data." + p.getName() + ".days");
        int hours = cfg.getInt("data." + p.getName() + ".hours");
        int minutes = cfg.getInt("data." + p.getName() + ".minutes");
        int seconds = cfg.getInt("data." + p.getName() + ".seconds");
        if(cfg.getBoolean("data." + p.getName() + ".infinity")) {
            return;
        }
        if(days > 0 || hours > 0 || minutes > 0 || seconds > 0) {
            return;
        }
            else if(!cfg.getBoolean("data." + p.getName() + "infinity")) {
                e.disallow(PlayerLoginEvent.Result.KICK_OTHER, ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.no-pass")));
                return;
            }
        }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        FileConfiguration cfg = Main.getInstance().getConfig();
        Player p = e.getPlayer();
        if(cfg.getBoolean("data." + p.getName() + ".infinity")) {
            return;
        }
        else {
            int days = cfg.getInt("data." + p.getName() + ".days");
            int hours = cfg.getInt("data." + p.getName() + ".hours");
            int minutes = cfg.getInt("data." + p.getName() + ".minutes");
            int seconds = cfg.getInt("data." + p.getName() + ".seconds");
            cfg.getStringList("messages.welcome-msg").forEach(message -> p.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("%days%", String.valueOf(days)).replaceAll("%hours%", String.valueOf(hours)).replaceAll("%minutes%", String.valueOf(minutes)).replaceAll("%seconds%", String.valueOf(seconds)))));
            p.sendTitle(ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.join-title")), ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.join-subtitle").replaceAll("%days%", String.valueOf(days)).replaceAll("%hours%", String.valueOf(hours)).replaceAll("%minutes%", String.valueOf(minutes)).replaceAll("%seconds%", String.valueOf(seconds))));
            return;
        }
    }
}
