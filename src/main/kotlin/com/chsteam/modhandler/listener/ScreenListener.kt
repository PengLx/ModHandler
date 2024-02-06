package com.chsteam.modhandler.listener

import org.bukkit.event.inventory.InventoryClickEvent
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.info
import taboolib.module.nms.PacketReceiveEvent
import taboolib.module.nms.PacketSendEvent

object ScreenListener {
    @SubscribeEvent
    fun e(e: PacketReceiveEvent) {
        if(e.packet.name == "PacketPlayInWindowClick") {

        }
    }
}