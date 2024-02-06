package com.chsteam.modhandler.api

import com.chsteam.modhandler.ModHandler
import com.chsteam.modhandler.ModHandler.CHANNEL
import com.chsteam.modhandler.custom.CustomItem
import com.chsteam.modhandler.library.BlockDataContainer
import com.chsteam.modhandler.network.s2c.SetEntityModel
import org.bukkit.entity.Entity
import org.bukkit.entity.Player


object ModHandlerAPI {

    val blockDataManager = BlockDataContainer.manager!!

    private val registerScreen = hashMapOf<String, Pair<Int, Screen>>()

    private var registerScreenId = 100;

    fun registryScreen(key: String, screen: Screen) {
        registerScreen[key] = Pair(registerScreenId, screen)
        registerScreenId++
    }

    fun setEntityModel(player: Player, entity: Entity, modId : String, namespace: String) {
        player.sendPluginMessage(ModHandler.plugin, CHANNEL, SetEntityModel(entity, modId, namespace).toBytes())
    }

}