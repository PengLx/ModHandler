package com.chsteam.modhandler.ui.virtual

import org.bukkit.entity.HumanEntity
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.InventoryView

/**
 * TabooLib
 * com.chsteam.modhandler.ui.virtual.VirtualInventoryView
 *
 * @author 坏黑
 * @since 2023/1/16 04:11
 */
class VirtualInventoryView(val remoteInventory: RemoteInventory) : InventoryView() {

    override fun getTopInventory(): Inventory {
        return remoteInventory.inventory
    }

    override fun getBottomInventory(): Inventory {
        return VirtualStorageInventory(remoteInventory.inventory)
    }

    override fun getPlayer(): HumanEntity {
        return remoteInventory.viewer
    }

    override fun getType(): InventoryType {
        return remoteInventory.inventory.type
    }

    override fun getTitle(): String {
        return remoteInventory.title
    }
}