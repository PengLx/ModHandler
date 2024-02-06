package com.chsteam.modhandler.api

import com.chsteam.modhandler.library.redlib.DataBlock
import org.bukkit.Location
import org.bukkit.Material

open class ModBlock {
    private val modId : String

    private val namespace : String

    private val bukkitMaterial : Material

    constructor(modId : String, namespace : String, bukkitMaterial : Material) {
        this.modId = modId
        this.namespace = namespace
        this.bukkitMaterial = bukkitMaterial
    }

    open fun place(location: Location) : DataBlock? {
        location.world?.setBlockData(location, bukkitMaterial.createBlockData()) ?: return null

        val block = location.world?.getBlockAt(location) ?: return null

        val dataBlock = ModHandlerAPI.blockDataManager.getDataBlock(block)
        dataBlock.data["IMId"] = this.modId
        dataBlock.data["IMNameSpace"] = this.namespace

        return dataBlock
    }


    companion object {
        fun getModBlock(location: Location) : ModBlock? {
            val block = location.world?.getBlockAt(location) ?: return null

            val dataBlock = ModHandlerAPI.blockDataManager.getDataBlock(block)
            val id = dataBlock.data["IMId"] as String? ?: return null
            val namespace = dataBlock.data["IMNameSpace"] as String? ?: return null

            return ModBlock(id, namespace, block.type)
        }

        fun getDataBlock(location: Location) : DataBlock? {
            val block = location.world?.getBlockAt(location) ?: return null

            return ModHandlerAPI.blockDataManager.getDataBlock(block)
        }
    }
}