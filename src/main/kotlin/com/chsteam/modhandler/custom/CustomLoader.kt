package com.chsteam.modhandler.custom

import com.chsteam.modhandler.api.EasySound
import org.bukkit.Material
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake
import taboolib.common.platform.function.getDataFolder
import taboolib.common.platform.function.releaseResourceFile
import taboolib.library.configuration.ConfigurationSection
import taboolib.module.configuration.Configuration
import java.io.File

object CustomLoader {
    val registerItems = hashMapOf<Pair<String, String>, CustomItem>()
    val registerBlocks = hashMapOf<Pair<String, String>, CustomBlock>()

    @Awake(LifeCycle.ENABLE)
    fun load() {
        val file = File(getDataFolder(),"context")
        if(!file.exists()) {
            releaseResourceFile("context/example.yml", true)
        }

        registerBlocks.clear()
        registerItems.clear()

        load(file)
    }

    private fun load(file: File) {
        when {
            file.isDirectory -> file.listFiles()?.forEach { load(it) }
            file.name.endsWith(".yml") -> load(Configuration.loadFromFile(file))
        }
    }

    private fun load(file: Configuration) {
        val namespace = file.getString("info.namespace") ?: return

        val item = file.getConfigurationSection("items")

        val block = file.getConfigurationSection("blocks")

        item?.getKeys(false)?.map{
            registerItems[Pair(namespace, it)] = loadItem(it, namespace ,file.getConfigurationSection("items.$it")!!)
        }

        block?.getKeys(false)?.map {
            registerBlocks[Pair(namespace, it)] = loadBlock(it, namespace ,file.getConfigurationSection("blocks.$it")!!)
        }
    }

    private fun loadItem(key: String, namespace: String, root: ConfigurationSection) : CustomItem {
        val name = root.getString("name")
        val lore = root.getStringList("lore")
        val material = Material.getMaterial(root.getString("material") ?: "") ?: Material.DIAMOND
        val modID = root.getString("Mod-ID") ?: key
        val modNamespace = root.getString("Mod-Namespace") ?: namespace
        val placeItemString = root.getString("specific_properties.block.placed_model")

        val placeItem = placeItemString?.split(":")
        var pair: Pair<String, String>? = null
        if(!placeItem.isNullOrEmpty()) {
            pair = Pair(placeItem[0], placeItem[1])
        }

        return CustomItem(key, namespace, modID, modNamespace, material, name, lore, pair)
    }

    private fun loadBlock(key: String,namespace: String, root: ConfigurationSection) : CustomBlock {
        val material = Material.getMaterial(root.getString("material") ?: "") ?: Material.DIAMOND_BLOCK
        val modID = root.getString("Mod-ID") ?: key
        val modNamespace = root.getString("Mod-Namespace") ?: namespace

        val lootString = root.getStringList("loot")
        val itemList = mutableListOf<Pair<String, String>>()
        lootString.forEach {
            val lootItem = it.split(":")
            if(lootItem.isNotEmpty()) {
                val pair = Pair(lootItem[0], lootItem[1])
                itemList.add(pair)
            }
        }

        var breakSound : EasySound? = null
        if(root.getString("sound.break.name") != null) {
            breakSound = EasySound(root.getString("sound.break.name")!!.lowercase(),root.getDouble("sound.break.volume").toFloat(), root.getDouble("sound.break.ptich").toFloat())
        }
        var placeSound : EasySound? = null
        if(root.getString("sound.place.name") != null) {
            placeSound = EasySound(root.getString("sound.place.name")!!.lowercase(),root.getDouble("sound.place.volume").toFloat(), root.getDouble("sound.place.ptich").toFloat())
        }

        return CustomBlock(key, namespace, modID, modNamespace, material, placeSound, breakSound, itemList)
    }
}