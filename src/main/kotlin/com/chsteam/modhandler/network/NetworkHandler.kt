package com.chsteam.modhandler.network

import org.bukkit.entity.Player
import org.bukkit.plugin.messaging.PluginMessageListener

object NetworkHandler : PluginMessageListener {
    override fun onPluginMessageReceived(p0: String, p1: Player, p2: ByteArray) {
    }
}