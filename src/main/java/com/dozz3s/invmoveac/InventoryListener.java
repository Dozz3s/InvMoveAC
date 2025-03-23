package com.dozz3s.invmoveac;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InventoryListener implements Listener {
    private final PlayerMovementTracker movementTracker;

    public InventoryListener(PlayerMovementTracker movementTracker) {
        this.movementTracker = movementTracker;
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (movementTracker.isPlayerMovingFast(player.getUniqueId())) {
            event.setCancelled(true);
            player.closeInventory();
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (movementTracker.isPlayerMovingFast(player.getUniqueId())) {
            event.setCancelled(true);
            player.closeInventory();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        movementTracker.removePlayer(event.getPlayer().getUniqueId());
    }
}