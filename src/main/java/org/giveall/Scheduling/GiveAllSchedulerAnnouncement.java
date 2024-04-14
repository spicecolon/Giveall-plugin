package org.giveall.Scheduling;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.giveall.Main;

public class GiveAllSchedulerAnnouncement extends BukkitRunnable {
    private final String msg;

    public GiveAllSchedulerAnnouncement(Main plugin, String msg, long tick) {
        this.msg = msg;

        this.runTaskTimer(plugin, tick, tick);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(this.msg);
        }
    }
}
