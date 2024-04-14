package org.giveall;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConfigParser {
    private final Main plugin;

    public ConfigParser(Main plugin) {
        this.plugin = plugin;
    }

    public static List<Long> TickToHMS(long tick) {
        int ticksPerSecond = 20;
        int ticksPerMinute = ticksPerSecond * 60;
        int ticksPerHour = ticksPerMinute * 60;

        long hours = tick / ticksPerHour;
        long remainingTicksAfterHours = tick % ticksPerHour;

        long minutes = remainingTicksAfterHours / ticksPerMinute;
        long seconds = (remainingTicksAfterHours % ticksPerMinute) / ticksPerSecond;


        return Stream.of(hours, minutes, seconds).collect(Collectors.toList());
    }

    public static int secondsToTick(int s) {
        return 20 * s;
    }

    public Optional<List<Config>> parse() {
        List<Config> gives = new ArrayList<>();
        FileConfiguration config = plugin.getConfig();
        ConfigurationSection configSection = config.getConfigurationSection("gives");

        if (configSection == null) {
            System.out.println("\"gives\" config section not found");
            return Optional.empty();
        }

        // [ Get give ]
        for (String xKey : configSection.getKeys(false)) {
            String msg;
            int msgMinutes;
            int msgSeconds;
            int giveMinutes;
            int giveSeconds;
            List<ConfigurationSection> items = new ArrayList<>();

            // [ Check if is a configuration section ]
            ConfigurationSection subConfig = configSection.getConfigurationSection(xKey);
            if (subConfig != null) {
                msg = subConfig.getString("msg");

                msgMinutes = subConfig.getInt("msg_minutes");
                msgSeconds = subConfig.getInt("msg_seconds");

                giveMinutes = subConfig.getInt("give_minutes");
                giveSeconds = subConfig.getInt("give_seconds");

                // [ Get item configs ]
                for (String item : subConfig.getKeys(false)) {
                    ConfigurationSection itemConfig = subConfig.getConfigurationSection(item);

                    if (itemConfig != null) {
                        String itemKey = itemConfig.getString("key");
                        if (Material.matchMaterial(itemKey) != null) {
                            items.add(itemConfig);
                        } else {
                            System.out.printf("%s don't exist", itemKey);
                            return Optional.empty();
                        }
                    }
                }
                int msgTick = secondsToTick((msgMinutes * 60) + msgSeconds);
                int giveTick = secondsToTick((giveMinutes * 60) + giveSeconds);

                // [ Add new config into 'gives' array ]
                gives.add(new Config(msg, msgTick, giveTick, items));
            }
        }
        return Optional.of(gives);
    }
}
