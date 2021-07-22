package com.elementars.randomitemchallenge;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * @author Elementars
 * @since 7/22/2021 - 11:37 AM
 */
public class RICTask extends BukkitRunnable {

    private final RandomItemChallenge plugin;

    public RICTask(RandomItemChallenge plugin) {
        this.plugin = plugin;
    }

    public void start() {
        runTaskTimer(plugin, 0L, 20L);
    }

    public void stop() {
        Bukkit.getServer().getScheduler().cancelTask(getTaskId());
    }

    @Override
    public void run() {
        if (plugin.enabled && plugin.timer.hasReached(120000)) {
            plugin.giveRandomItems();
        }
    }
}
