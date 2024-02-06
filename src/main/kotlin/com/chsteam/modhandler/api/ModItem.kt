package com.chsteam.modhandler.api

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import taboolib.module.nms.ItemTagData
import taboolib.module.nms.getItemTag
import taboolib.module.nms.setItemTag
import taboolib.platform.util.ItemBuilder
import taboolib.platform.util.buildItem

open class ModItem {
    private val modId : String

    private val namespace : String

    private val bukkitMaterial : Material

    private var itemName: String? = null
    private var itemLore: List<String>? = null

    constructor(modId : String, namespace : String, bukkitMaterial : Material) {
        this.modId = modId
        this.namespace = namespace
        this.bukkitMaterial = bukkitMaterial
    }

    constructor(modId : String, namespace : String, bukkitMaterial : Material, name: String?) {
        this.modId = modId
        this.namespace = namespace
        this.bukkitMaterial = bukkitMaterial
        this.itemName = name
    }

    constructor(modId : String, namespace : String, bukkitMaterial : Material, lore: List<String>?) {
        this.modId = modId
        this.namespace = namespace
        this.bukkitMaterial = bukkitMaterial
        this.itemLore = lore
    }

    constructor(modId : String, namespace : String, bukkitMaterial : Material,  name: String?, lore: List<String>?) {
        this.modId = modId
        this.namespace = namespace
        this.bukkitMaterial = bukkitMaterial
        this.itemName = name
        this.itemLore = lore
    }

    open fun build() : ItemStack {
        var item = buildItem(this.bukkitMaterial) {
            name = this@ModItem.itemName
            this@ModItem.itemLore?.let { lore.addAll(it) }
            colored()
        }
        val tag = item.getItemTag()

        tag["IMId"] = ItemTagData(this.modId)

        tag["IMNameSpace"] = ItemTagData(this.namespace)

        item = item.setItemTag(tag)

        return item.setItemTag(tag)
    }
}