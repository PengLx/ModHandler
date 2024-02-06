package com.chsteam.modhandler.ui.type

import org.bukkit.inventory.ItemStack
import taboolib.common.Isolated
import com.chsteam.modhandler.ui.ClickEvent

@Isolated
class ActionClick : Action() {

    override fun getCursor(e: ClickEvent): ItemStack {
        return e.clicker.itemOnCursor
    }

    override fun setCursor(e: ClickEvent, item: ItemStack?) {
        e.clicker.setItemOnCursor(item)
    }

    override fun getCurrentSlot(e: ClickEvent): Int {
        return e.rawSlot
    }
}