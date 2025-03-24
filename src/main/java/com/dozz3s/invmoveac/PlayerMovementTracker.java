package com.dozz3s.invmoveac;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerMovementTracker {

    private final Map<UUID, LocationData> playerLocations = new HashMap<>();
    private final JavaPlugin plugin;
    private BukkitRunnable task;

    public PlayerMovementTracker(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {
        task = new BukkitRunnable() {
            @Override
            public void run() {
                updatePlayerLocations();
            }
        };
        task.runTaskTimerAsynchronously(plugin, 0L, 2L);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
            synchronized (playerLocations) {
                playerLocations.clear();
            }
        }
    }

    private void updatePlayerLocations() {
        try {
            for (Player player : plugin.getServer().getOnlinePlayers()) {
                UUID uuid = player.getUniqueId();
                LocationData data = playerLocations.get(uuid);

                if (data == null) {
                    synchronized (playerLocations) {
                        data = playerLocations.get(uuid); // <3 eva
                        if (data == null) {
                            data = new LocationData(player.getLocation().getX(), player.getLocation().getZ());
                            playerLocations.put(uuid, data);
                        }
                    }
                }

                double currentX = player.getLocation().getX();
                double currentZ = player.getLocation().getZ();
                double distance = calculateDistance(data.getX(), data.getZ(), currentX, currentZ);
                data.update(currentX, currentZ, distance);
            }
        } catch (Exception ignored) {
        }
    }

    public boolean isPlayerMovingFast(UUID uuid) {
        LocationData data = playerLocations.get(uuid);
        return data != null && data.getSpeed() > InvMoveAC.getSpeedThreshold();
    }

    public void removePlayer(UUID uuid) {
        synchronized (playerLocations) {
            playerLocations.remove(uuid);
        }
    }

    private double calculateDistance(double x1, double z1, double x2, double z2) {
        double dx = x2 - x1;
        double dz = z2 - z1;
        return Math.sqrt(dx * dx + dz * dz);
    }
}