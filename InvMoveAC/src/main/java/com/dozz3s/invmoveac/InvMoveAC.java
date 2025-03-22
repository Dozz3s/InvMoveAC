package com.dozz3s.invmoveac;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class InvMoveAC extends JavaPlugin implements Listener {

    private static final double SPEED_THRESHOLD = 0.2;
    private final Map<UUID, LocationData> playerLocations = new HashMap<>();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("§6>==========================================<");
        getLogger().info("  §aInvMoveAC успешно включен!");
        getLogger().info("  §fСпасибо за использование моего плагина");
        getLogger().info("  §aPlugin by Dozz3s");
        getLogger().info("§6>==========================================<");

        startMovementChecker();
    }

    @Override
    public void onDisable() {
        getLogger().info("InvMoveAC плагин выключен!");
        playerLocations.clear();
    }

    private void startMovementChecker() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : getServer().getOnlinePlayers()) {
                    UUID uuid = player.getUniqueId();
                    LocationData data = playerLocations.get(uuid);
                    double currentX = player.getLocation().getX();
                    double currentZ = player.getLocation().getZ();

                    if (data == null) {
                        playerLocations.put(uuid, new LocationData(currentX, currentZ));
                    } else {
                        double distance = calculateDistance(data.x, data.z, currentX, currentZ);
                        data.update(currentX, currentZ, distance);
                    }
                }
            }
        }.runTaskTimer(this, 0L, 2L);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        handleInventoryEvent(event);
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        handleInventoryEvent(event);
    }

    private void handleInventoryEvent(Object event) {
        Player player = event instanceof InventoryClickEvent ?
                (Player) ((InventoryClickEvent) event).getWhoClicked() :
                (Player) ((InventoryDragEvent) event).getWhoClicked();

        LocationData data = playerLocations.get(player.getUniqueId());
        if (data != null && data.speed > SPEED_THRESHOLD) {
            if (event instanceof InventoryClickEvent) {
                ((InventoryClickEvent) event).setCancelled(true);
            } else {
                ((InventoryDragEvent) event).setCancelled(true);
            }
            player.closeInventory();
        }
    }

    private double calculateDistance(double x1, double z1, double x2, double z2) {
        double dx = x2 - x1;
        double dz = z2 - z1;
        return Math.sqrt(dx * dx + dz * dz);
    }

    private static class LocationData {
        private double x, z;
        private double speed;

        LocationData(double x, double z) {
            this.x = x;
            this.z = z;
            this.speed = 0;
        }

        void update(double x, double z, double speed) {
            this.x = x;
            this.z = z;
            this.speed = speed;
        }
    }
}