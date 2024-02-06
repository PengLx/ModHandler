package com.chsteam.modhandler.custom

import com.chsteam.modhandler.api.ModHandlerAPI
import com.chsteam.modhandler.api.ModItem
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import taboolib.module.nms.ItemTagData
import taboolib.module.nms.getItemTag
import taboolib.module.nms.setItemTag

class CustomItem : ModItem {

    private val pluginId : String
    private val pluginNamespace: String
    private val placeableItem : Pair<String, String>?

    constructor(
        pluginId : String,
        pluginNamespace: String,
        modId: String,
        namespace: String,
        bukkitMaterial: Material,
        itemName: String?,
        itemLore: List<String>?,
        placeableItem: Pair<String, String>?
    ) : super(modId, namespace, bukkitMaterial, itemName, itemLore) {
        this.pluginId = pluginId
        this.pluginNamespace = pluginNamespace
        this.placeableItem = placeableItem
    }

    override fun build(): ItemStack {
        val item = super.build()
        val tag = item.getItemTag()

        tag["ITEM_NAMESPACE"] = ItemTagData(this.pluginNamespace)
        tag["ITEM_ID"] =  ItemTagData(this.pluginId)
        return item.setItemTag(tag)
    }

    fun getID() : String {
        return this.pluginId
    }

    fun getNamespace() : String {
        return this.pluginNamespace;
    }

    fun isPlaceable() : Boolean {
        return this.placeableItem != null
    }

    fun getPlaceableItem() : Pair<String, String>? {
        return this.placeableItem
    }

    companion object {
        fun getFromItemStack(item: ItemStack) : CustomItem? {
            val tag = item.getItemTag()
            val id = tag["ITEM_ID"]?.asString() ?: return null
            val namespace = tag["ITEM_NAMESPACE"]?.asString() ?: return null
            return CustomLoader.registerItems[Pair(namespace, id)]
        }
    }
}