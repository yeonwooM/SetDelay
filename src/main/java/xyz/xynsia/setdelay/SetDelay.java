package xyz.xynsia.setdelay;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;

public class SetDelay extends JavaPlugin {
    private SDListener sdListener; // SDListener 클래스 인스턴스
    private SDCommand sdCommand; // SDCommand 클래스 인스턴스

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("§2[§aNoHitDelay§2] Plugin is disabled.");
    }

    public void onEnable() {
        Bukkit.getConsoleSender().sendMessage("§2[§aNoHitDelay§2] Plugin is enabled.");
        sdListener = new SDListener(this); // Instantiate SDListener class
        sdCommand = new SDCommand(this); // Instantiate SDCommand class

        getCommand("nohitdelay").setTabCompleter(new SetDelayTabCompleter());

        File configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            saveResource("config.yml", true);
        }

        reloadConfig();
        sdListener.setup(true);
        sdListener.setupEntities();

        new BukkitRunnable() {
            @Override
            public void run() {
                sdListener.setup(true);
                sdListener.setupEntities();
            }
        }.runTaskTimer(this, 0, 200);
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        return sdCommand.onCommand(sender, command, label, args); // Call the onCommand() method of SDCommand class
    }
}




