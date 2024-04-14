package org.giveall.Scheduling;

import org.giveall.Config;
import org.giveall.Main;


public class GiveAllSchedulerManager {
    private final Main plugin;
    private final Config config;

    public GiveAllSchedulerManager(Main plugin, Config config) {
        this.plugin = plugin;
        this.config = config;

        runAnnouncement();
        runGive();
    }

    public void runAnnouncement() {
        new GiveAllSchedulerAnnouncement(this.plugin, config.msg, config.msgTick);
    }

    public void runGive() {
        new GiveAllSchedulerGive(this.plugin, this.config.items, this.config.giveTick);
    }
}
