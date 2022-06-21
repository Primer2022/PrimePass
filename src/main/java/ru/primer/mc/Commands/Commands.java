package ru.primer.mc.Commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import ru.primer.mc.Main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Commands implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        FileConfiguration cfg = Main.getInstance().getConfig();
        if(s.hasPermission("primepass.admin")) {
            if(args.length <= 0) {
                cfg.getStringList("messages.help-admin").forEach(message -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));
                return true;
            }
            if(args.length > 0) {
                if(args[0].equals("reload")){
                    Main.getInstance().reloadConfig();
                    Main.getInstance().saveDefaultConfig();
                    s.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.reload-config")));
                    return true;
                }
                if(args[0].equals("give")) {
                    if (args.length >= 3) {
                        if (cfg.contains("data." + args[1])) {
                            try {
                                int argsint = Integer.parseInt(args[2]);
                                int amountnow = cfg.getInt("data." + args[1] + ".days");
                                int amountadd = amountnow + argsint;
                                Date date = new Date();
                                SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                                cfg.set("data." + args[1] + ".days", amountadd);
                                cfg.set("data." + args[1] + ".date", formatForDateNow.format(date));
                                Main.getInstance().saveConfig();
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.succes-give").replaceAll("%player%", args[1]).replaceAll("%amount%", String.valueOf(argsint))));
                                return true;
                            } catch (NumberFormatException e) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.no-int")));
                            }
                        }
                    }
                    else {
                        cfg.getStringList("messages.help-admin").forEach(message -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));
                        return true;
                    }
                }
                if(args[0].equals("set")) {
                    if (args.length >= 3) {
                        if (cfg.contains("data." + args[1])) {
                            try {
                                int argsint = Integer.parseInt(args[2]);
                                Date date = new Date();
                                SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                                cfg.set("data." + args[1] + ".seconds", 0);
                                cfg.set("data." + args[1] + ".minutes", 0);
                                cfg.set("data." + args[1] + ".hours", 0);
                                cfg.set("data." + args[1] + ".days", argsint);
                                cfg.set("data." + args[1] + ".date", formatForDateNow.format(date));
                                Main.getInstance().saveConfig();
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.succes-set").replaceAll("%player%", args[1]).replaceAll("%amount%", String.valueOf(argsint))));
                                return true;
                            } catch (NumberFormatException e) {
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.no-int")));
                            }
                        }
                    }
                    else {
                        cfg.getStringList("messages.help-admin").forEach(message -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));
                        return true;
                    }
                }
                if(args[0].equals("addinf")) {
                    if (args.length >= 2) {
                                cfg.set("data." + args[1] + ".infinity", true);
                                Date date = new Date();
                                SimpleDateFormat formatForDateNow = new SimpleDateFormat("dd.MM.yyyy hh:mm:ss");
                                cfg.set("data." + args[1] + ".date", formatForDateNow.format(date));
                                Main.getInstance().saveConfig();
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.add-infinity").replaceAll("%player%", args[1])));
                                return true;
                    }
                    else {
                        cfg.getStringList("messages.help-admin").forEach(message -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));
                        return true;
                    }
                }
                if(args[0].equals("delinf")) {
                    if (args.length >= 2) {
                        if (cfg.contains("data." + args[1])) {
                            if (cfg.getBoolean("data." + args[1] + ".infinity")){
                                cfg.set("data." + args[1] + ".infinity", false);
                                Main.getInstance().saveConfig();
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.del-infinity").replaceAll("%player%", args[1])));
                                return true;
                            }
                            else{
                                s.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.no-infinity")));
                            }
                        }
                    }
                    else {
                        cfg.getStringList("messages.help-admin").forEach(message -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));
                        return true;
                    }
                }
                if (args[0].equals("info")) {
                    if (args.length > 1) {
                        if (cfg.contains("data." + args[1])) {
                            String name = args[1];
                            int seconds = cfg.getInt("data." + args[1] + ".seconds");
                            int minutes = cfg.getInt("data." + args[1] + ".minutes");
                            int hours = cfg.getInt("data." + args[1] + ".hours");
                            int days = cfg.getInt("data." + args[1] + ".days");
                            String date = cfg.getString("data." + args[1] + ".date");
                            Boolean status = cfg.getBoolean("data." + s.getName() + ".infinity");
                            String statusstr = String.valueOf(status).replaceAll("false", "нету").replaceAll("true", "есть");
                            cfg.getStringList("messages.pass-info").forEach(message -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("%player%", name).replaceAll("%days%", String.valueOf(days)).replaceAll("%hours%", String.valueOf(hours)).replaceAll("%date%", date).replaceAll("%minutes%", String.valueOf(minutes)).replaceAll("%seconds%", String.valueOf(seconds)).replaceAll("%status%", String.valueOf(statusstr)))));
                        } else if (args.length < 2) {
                            cfg.getStringList("messages.help-admin").forEach(message -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));
                            return true;
                        } else {
                            s.sendMessage(ChatColor.translateAlternateColorCodes('&', cfg.getString("messages.invalid-player")));
                            return true;
                        }
                    }
                    if (args.length == 1) {
                        String name = s.getName();
                        int seconds = cfg.getInt("data." + s.getName() + ".seconds");
                        int minutes = cfg.getInt("data." + s.getName() + ".minutes");
                        int hours = cfg.getInt("data." + s.getName() + ".hours");
                        int days = cfg.getInt("data." + s.getName() + ".days");
                        String date = cfg.getString("data." + s.getName() + ".date");
                        Boolean status = cfg.getBoolean("data." + s.getName() + ".infinity");
                        String statusstr = String.valueOf(status).replaceAll("false", "нету").replaceAll("true", "есть");
                        cfg.getStringList("messages.pass-info").forEach(message -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("%player%", name).replaceAll("%days%", String.valueOf(days)).replaceAll("%hours%", String.valueOf(hours)).replaceAll("%date%", date).replaceAll("%minutes%", String.valueOf(minutes)).replaceAll("%seconds%", String.valueOf(seconds)).replaceAll("%status%", String.valueOf(statusstr)))));
                        return true;
                    }
                }
                }
            }
        if (args.length == 0) {
            cfg.getStringList("messages.help-player").forEach(message -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', message)));
            return true;
        }
        if (args[0].equals("info")) {
            if (args.length >= 1) {
                String name = s.getName();
                int seconds = cfg.getInt("data." + s.getName() + ".seconds");
                int minutes = cfg.getInt("data." + s.getName() + ".minutes");
                int hours = cfg.getInt("data." + s.getName() + ".hours");
                int days = cfg.getInt("data." + s.getName() + ".days");
                String date = cfg.getString("data." + s.getName() + ".date");
                Boolean status = cfg.getBoolean("data." + s.getName() + ".infinity");
                String statusstr = String.valueOf(status).replaceAll("false", "нету").replaceAll("true", "есть");
                cfg.getStringList("messages.pass-info").forEach(message -> s.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replaceAll("%player%", name).replaceAll("%days%", String.valueOf(days)).replaceAll("%hours%", String.valueOf(hours)).replaceAll("%date%", date).replaceAll("%minutes%", String.valueOf(minutes)).replaceAll("%seconds%", String.valueOf(seconds)).replaceAll("%status%", String.valueOf(statusstr)))));
                return true;
            }
        }
        return true;
    }
}
