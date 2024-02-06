package com.chsteam.modhandler.ui.virtual

import org.bukkit.event.inventory.InventoryInteractEvent
import org.bukkit.inventory.InventoryView

/**
 * TabooLib
 * com.chsteam.modhandler.ui.virtual.VirtualInventoryInteractEvent
 *
 * @author 坏黑
 * @since 2023/1/16 04:15
 */
class VirtualInventoryInteractEvent(val clickEvent: RemoteInventory.ClickEvent, inventoryView: InventoryView) : InventoryInteractEvent(inventoryView)