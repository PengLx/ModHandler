package com.chsteam.modhandler.custom

import com.chsteam.modhandler.api.EasySound
import com.chsteam.modhandler.api.ModBlock
import com.chsteam.modhandler.library.redlib.DataBlock
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class CustomBlock : ModBlock {
    private val pluginId : String
    private val pluginNamespace: String
    private val placeSound : EasySound?
    private val breakSound : EasySound?
    private val loot: List<Pair<String, String>>

    constructor( pluginId : String,
                 pluginNamespace: String,
                 modId : String,
                 namespace : String,
                 bukkitMaterial : Material,
                 placeSound: EasySound?,
                 breakSound: EasySound?,
                 loot: List<Pair<String, String>>
    ) : super(modId, namespace, bukkitMaterial) {
        this.pluginId = pluginId
        this.pluginNamespace = pluginNamespace
        this.loot = loot
        this.placeSound = placeSound
        this.breakSound = breakSound
    }

    override fun place(location: Location): DataBlock? {
        val dataBlock = super.place(location) ?: return null
        dataBlock.data["F-ID"] = this.pluginId
        dataBlock.data["F-NM"] = this.pluginNamespace
        return dataBlock
    }

    fun getPlaceSound() : EasySound? {
        return this.placeSound
    }

    fun getBreakSound() : EasySound? {
        return this.breakSound
    }

    fun getLoot() : List<ItemStack> {
        val itemList = mutableListOf<ItemStack>()

        this.loot.forEach {
            CustomLoader.registerItems[it]?.build()?.let { it1 -> itemList.add(it1) }
        }
        return itemList
    }

    companion object {
        fun readFromBlock(dataBlock: DataBlock) : CustomBlock? {
            val id = dataBlock.data["F-ID"] as String? ?: return null
            val namespace = dataBlock.data["F-NM"] as String? ?: return null

            return CustomLoader.registerBlocks[Pair(namespace, id)]
        }
    }
}