package com.dozz3s.invmoveac;

import org.bukkit.plugin.java.JavaPlugin;

public class InvMoveAC extends JavaPlugin {

    private static final double SPEED_THRESHOLD = 0.22;
    private final PlayerMovementTracker movementTracker = new PlayerMovementTracker(this);

    @Override
    public void onEnable() {
        InventoryListener listener = new InventoryListener(movementTracker);
        getServer().getPluginManager().registerEvents(listener, this);
        movementTracker.start();

        getLogger().info("§6>==========================================<");
        getLogger().info("  §aInvMoveAC успешно включен!");
        getLogger().info("  §7Спасибо за использование моего плагина");
        getLogger().info("  §aPlugin by Dozz3s");
        getLogger().info("§6>==========================================<");
    }

    @Override
    public void onDisable() {
        getLogger().info("InvMoveAC плагин выключен!");
        movementTracker.stop();
    }

    public static double getSpeedThreshold() {
        return SPEED_THRESHOLD;
    }
}