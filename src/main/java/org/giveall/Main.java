package org.giveall;

import org.giveall.Scheduling.GiveAllSchedulerManager;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends JavaPlugin {

    public void onEnable() {
        saveDefaultConfig();
        Logger logger = getLogger();

        // [ Parse config file ]

        ConfigParser configParser = new ConfigParser(this);
        Optional<List<Config>> gives = configParser.parse();

        if (!gives.isPresent()) {
            logger.log(Level.SEVERE, "Parsing failed");
            return;
        }

        // [ Start async schedulers ]
        for (Config give : gives.get()) {
            new GiveAllSchedulerManager(this, give);
        }

        logger.info("{ GIVEALL PLUGIN ENABLED }");
    }
}