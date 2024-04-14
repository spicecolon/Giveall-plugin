package org.giveall;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.stream.Collectors;

public class Config {
    public final String msg;
    public final long msgTick;
    public final long giveTick;
    public List<ConfigurationSection> items;

    public Config(String msg, long msgTick, long giveTick, List<ConfigurationSection> items) {
        this.msgTick = msgTick;
        this.giveTick = giveTick;
        this.items = items;

        // [ Convert tick to hours, minutes, seconds ]
        List<Long> giveTime = ConfigParser.TickToHMS(giveTick);

        for (ConfigurationSection itemConfig : items) {
            String configName = itemConfig.getName();

            // [ replaces placeholders with various formats ]

            msg = msg
                    .replace("%" + configName + "%", Material.matchMaterial(
                            itemConfig.getString("key")).name().toLowerCase().replace("_", " "))
                    .replace("%n" + configName + "%", itemConfig.getString("amount"))
                    .replace("%time%", giveTime.stream().map(String::valueOf).collect(Collectors.joining(":")));
        }

        this.msg = msg;
        System.out.println(msg);
    }
}
