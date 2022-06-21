package ru.primer.mc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import ru.primer.mc.Commands.Commands;
import ru.primer.mc.Listeners.Listener;

public final class Main extends JavaPlugin {
    private static Main plugin;

    public static Main getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        timer();
        timeroffline();
        Bukkit.getPluginManager().registerEvents(new Listener(this), this);
        Bukkit.getPluginCommand("primepass").setExecutor(new Commands());
        plugin = this;
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public void timer()
    {
        this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
                    public void run() {
                        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
                            if(getConfig().getBoolean("data." + p.getName() + ".infinity")) {
                                return;
                            }
                            int seconds = getConfig().getInt("data." + p.getName() + ".seconds");
                            int minutes = getConfig().getInt("data." + p.getName() + ".minutes");
                            int hours = getConfig().getInt("data." + p.getName() + ".hours");
                            int days = getConfig().getInt("data." + p.getName() + ".days");
                            if(seconds > 0) {
                                int secondsamount = seconds - 1;
                                getConfig().set("data." + p.getName() + ".seconds", secondsamount);
                                saveConfig();
                            }
                            if (seconds <= 0 && minutes > 0) {
                                int secondsamount = 60;
                                int minutesamount = minutes - 1;
                                getConfig().set("data." + p.getName() + ".seconds", secondsamount);
                                getConfig().set("data." + p.getName() + ".minutes", minutesamount);
                                saveConfig();
                            }
                            if(seconds <= 0 && minutes <= 0 && hours > 0) {
                                int secondsamount = 60;
                                int minutesamount = 59;
                                int hoursamount = hours - 1;
                                getConfig().set("data." + p.getName() + ".seconds", secondsamount);
                                getConfig().set("data." + p.getName() + ".minutes", minutesamount);
                                getConfig().set("data." + p.getName() + ".hours", hoursamount);
                                saveConfig();
                            }
                            else if (seconds <= 0 && minutes <= 0 && hours <= 0 && days > 0) {
                                int secondsamount = 60;
                                int minutesamount = 59;
                                int hoursamount = 23;
                                int daysamount = days -1;
                                getConfig().set("data." + p.getName() + ".seconds", secondsamount);
                                getConfig().set("data." + p.getName() + ".minutes", minutesamount);
                                getConfig().set("data." + p.getName() + ".hours", hoursamount);
                                getConfig().set("data." + p.getName() + ".days", daysamount);
                                saveConfig();
                            }
                            else if (seconds <= 0 && minutes <= 0 && hours <= 0 && days <= 0) {
                                Bukkit.getScheduler().runTask(plugin, new Runnable() {
                                    public void run() {
                                        p.kickPlayer(ChatColor.translateAlternateColorCodes('&', getConfig().getString("messages.kick-reason")));
                                    }
                                });
                            }
                            }
                        }
                }
                , 20, 20);
    }
    public void timeroffline()
    {
        this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
                    public void run() {
                        for (OfflinePlayer p : Bukkit.getServer().getOfflinePlayers()) {
                            if (!p.isOnline()) {
                                if (getConfig().getBoolean("data." + p.getName() + ".infinity")) {
                                    return;
                                }
                                int seconds = getConfig().getInt("data." + p.getName() + ".seconds");
                                int minutes = getConfig().getInt("data." + p.getName() + ".minutes");
                                int hours = getConfig().getInt("data." + p.getName() + ".hours");
                                int days = getConfig().getInt("data." + p.getName() + ".days");
                                if (seconds > 0) {
                                    int secondsamount = seconds - 1;
                                    getConfig().set("data." + p.getName() + ".seconds", secondsamount);
                                    saveConfig();
                                }
                                if (seconds <= 0 && minutes > 0) {
                                    int secondsamount = 60;
                                    int minutesamount = minutes - 1;
                                    getConfig().set("data." + p.getName() + ".seconds", secondsamount);
                                    getConfig().set("data." + p.getName() + ".minutes", minutesamount);
                                    saveConfig();
                                }
                                if (seconds <= 0 && minutes <= 0 && hours > 0) {
                                    int secondsamount = 60;
                                    int minutesamount = 59;
                                    int hoursamount = hours - 1;
                                    getConfig().set("data." + p.getName() + ".seconds", secondsamount);
                                    getConfig().set("data." + p.getName() + ".minutes", minutesamount);
                                    getConfig().set("data." + p.getName() + ".hours", hoursamount);
                                    saveConfig();
                                } else if (seconds <= 0 && minutes <= 0 && hours <= 0 && days > 0) {
                                    int secondsamount = 60;
                                    int minutesamount = 59;
                                    int hoursamount = 23;
                                    int daysamount = days - 1;
                                    getConfig().set("data." + p.getName() + ".seconds", secondsamount);
                                    getConfig().set("data." + p.getName() + ".minutes", minutesamount);
                                    getConfig().set("data." + p.getName() + ".hours", hoursamount);
                                    getConfig().set("data." + p.getName() + ".days", daysamount);
                                    saveConfig();
                                }
                            }
                        }
                    }
                }
                , 20, 20);
    }
}
