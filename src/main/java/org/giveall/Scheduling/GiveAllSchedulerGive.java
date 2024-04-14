package org.giveall.Scheduling;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.giveall.Main;

import java.util.List;

public class GiveAllSchedulerGive extends BukkitRunnable {
    private final long tick;
    private final List<ConfigurationSection> items;

    public GiveAllSchedulerGive(Main plugin, List<ConfigurationSection> items, long tick) {
        this.items = items;
        this.tick = tick;

        this.runTaskTimer(plugin, this.tick, this.tick);
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (ConfigurationSection item : items) {
                String itemKey = item.getString("key");
                Material material = Material.matchMaterial(itemKey);

                if (material != null) {
                    player.getInventory().addItem(new ItemStack(material, item.getInt("amount")));
                }
            }
        }
    }
}
