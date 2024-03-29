package com.chsteam.modhandler.ui.type

import org.bukkit.inventory.ItemStack
import taboolib.common.Isolated
import com.chsteam.modhandler.ui.ClickEvent
import com.chsteam.modhandler.ui.ItemStacker
import taboolib.platform.util.isNotAir

@Isolated
class ActionQuickTake : Action() {

    override fun getCursor(e: ClickEvent): ItemStack {
        return e.clicker.itemOnCursor
    }

    override fun setCursor(e: ClickEvent, item: ItemStack?) {
        if (item.isNotAir()) {
            ItemStacker.MINECRAFT.moveItemFromChest(item, e.clicker)
        }
        e.clicker.setItemOnCursor(null)
    }

    override fun getCurrentSlot(e: ClickEvent): Int {
        return e.rawSlot
    }
}