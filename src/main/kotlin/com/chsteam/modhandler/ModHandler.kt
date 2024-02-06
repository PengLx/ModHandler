package com.chsteam.modhandler

import com.chsteam.modhandler.network.NetworkHandler
import org.bukkit.Bukkit
import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info
import taboolib.module.configuration.Config
import taboolib.module.configuration.Configuration
import taboolib.platform.BukkitPlugin

object ModHandler : Plugin() {

    const val CHANNEL = "serverhandler:play"

    @Config
    lateinit var conf: Configuration
        private set

    val plugin by lazy {
        BukkitPlugin.getInstance()
    }

    override fun onEnable() {
        Bukkit.getMessenger().registerIncomingPluginChannel(plugin, CHANNEL, NetworkHandler)
        Bukkit.getMessenger().registerOutgoingPluginChannel(plugin, CHANNEL)
        info("Successfully running ModHandler!")
    }
}