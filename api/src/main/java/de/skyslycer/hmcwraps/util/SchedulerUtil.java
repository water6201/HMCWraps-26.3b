package de.skyslycer.hmcwraps.util;

import de.skyslycer.hmcwraps.HMCWraps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * Helpers to run code on the thread that owns an entity or location.
 * On Folia this schedules onto the owning region thread; on Paper/Spigot it runs
 * immediately when already on the main thread (keeping the original behaviour).
 */
public class SchedulerUtil {

    private SchedulerUtil() {
    }

    /**
     * Run a task on the thread that owns the entity.
     *
     * @param plugin   The plugin
     * @param entity   The entity
     * @param runnable The task
     */
    public static void runAtEntity(HMCWraps plugin, Entity entity, Runnable runnable) {
        var foliaLib = plugin.getFoliaLib();
        if (foliaLib.isFolia()) {
            foliaLib.getScheduler().runAtEntity(entity, (ignored) -> runnable.run());
        } else if (Bukkit.isPrimaryThread()) {
            runnable.run();
        } else {
            foliaLib.getScheduler().runNextTick((ignored) -> runnable.run());
        }
    }

    /**
     * Run a task on the thread that owns the location.
     *
     * @param plugin   The plugin
     * @param location The location
     * @param runnable The task
     */
    public static void runAtLocation(HMCWraps plugin, Location location, Runnable runnable) {
        var foliaLib = plugin.getFoliaLib();
        if (foliaLib.isFolia()) {
            foliaLib.getScheduler().runAtLocation(location, (ignored) -> runnable.run());
        } else if (Bukkit.isPrimaryThread()) {
            runnable.run();
        } else {
            foliaLib.getScheduler().runNextTick((ignored) -> runnable.run());
        }
    }

}
