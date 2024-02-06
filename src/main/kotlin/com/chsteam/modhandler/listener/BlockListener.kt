package com.chsteam.modhandler.listener

import com.chsteam.modhandler.ModHandler
import com.chsteam.modhandler.api.ModHandlerAPI
import com.chsteam.modhandler.custom.CustomBlock
import com.chsteam.modhandler.custom.CustomItem
import com.chsteam.modhandler.custom.CustomLoader
import com.chsteam.modhandler.library.blockDataContainer
import com.chsteam.modhandler.library.redlib.DataBlock
import com.chsteam.modhandler.network.s2c.SetBlockModel
import net.minecraft.core.BlockPosition
import net.minecraft.world.EnumHand
import net.minecraft.world.EnumHand.*
import net.minecraft.world.phys.MovingObjectPositionBlock
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.SoundCategory
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.event.SubscribeEvent
import taboolib.common.platform.function.info
import taboolib.common.platform.function.submit
import taboolib.library.reflex.Reflex.Companion.getProperty
import taboolib.module.nms.PacketReceiveEvent
import taboolib.module.nms.PacketSendEvent

object BlockListener {

    @SubscribeEvent(ignoreCancelled = false, priority = EventPriority.LOWEST)
    fun e(e: BlockPlaceEvent) {
        if(!e.player.isOp) return
        if(!e.canBuild()) return
        val item = e.itemInHand
        val customItem = CustomItem.getFromItemStack(item) ?: return
        if(!customItem.isPlaceable()) return
        val placeItem = customItem.getPlaceableItem()!!
        val customBlock = CustomLoader.registerBlocks[placeItem] ?: return
        customBlock.place(e.blockPlaced.location) ?: return
        val sound = customBlock.getPlaceSound() ?: return
        e.block.location.world?.playSound(e.block.location, sound.string, SoundCategory.BLOCKS, sound.volume, sound.pitch)
    }

    @SubscribeEvent(ignoreCancelled = false, priority = EventPriority.HIGHEST)
    fun e(e: BlockBreakEvent) {
        val dataBlock = ModHandlerAPI.blockDataManager.getDataBlock(e.block) ?: return
        val customBlock = CustomBlock.readFromBlock(dataBlock) ?: return

        customBlock.getLoot().forEach {
            e.block.world.dropItem(e.block.location, it)
        }
        e.isDropItems = false
        e.block.setBlockData(Material.AIR.createBlockData(), true)

        val sound = customBlock.getBreakSound() ?: return
        e.block.location.world?.playSound(e.block.location, sound.string, SoundCategory.BLOCKS, sound.volume, sound.pitch)
    }

    @SubscribeEvent
    fun e(e: PacketSendEvent) {
        if(e.packet.name == "PacketPlayOutBlockChange") {
            val pos = e.packet.read<BlockPosition>("a") ?: return
            val dataBlock = ModHandlerAPI.blockDataManager.getDataBlock(e.player.world.getBlockAt(pos.u(), pos.v(), pos.w())) ?: return

            val id = dataBlock.data["IMId"] as String?
            val namespace = dataBlock.data["IMNameSpace"] as String?
            if(id != null && namespace != null) {
                e.isCancelled = true
                val location = dataBlock.block.location
                object : BukkitRunnable() {
                    override fun run() {
                        e.player.sendPluginMessage(ModHandler.plugin, ModHandler.CHANNEL, SetBlockModel(location, id, namespace).toBytes())
                    }
                }.runTaskLaterAsynchronously(ModHandler.plugin, 0)
            }
        }
        else if (e.packet.name == "PacketPlayOutLightUpdate") {
            val x = e.packet.read<Int>("a") ?: return
            val z = e.packet.read<Int>("b") ?: return
            loadChunk(e.player, x, z )
        } else if(e.packet.name == "ClientboundLevelChunkWithLightPacket") {
            val x = e.packet.read<Int>("a") ?: return
            val z = e.packet.read<Int>("b") ?: return
            loadChunk(e.player, x, z )
        }
    }

    private fun loadChunk(player: Player, x: Int, z: Int) {
        //blockDataContainer.load(player.world, x, z)
        val blockList = blockDataContainer.getLoaded(player.world, x, z)

        object : BukkitRunnable() {
            override fun run() {
                blockList.forEach {
                    simpleSend(player, it)
                }
            }
        }.runTaskLaterAsynchronously(ModHandler.plugin, 0)
    }

    private fun simpleSend(player: Player, dataBlock: DataBlock) {
        val id = dataBlock.data["IMId"] as String?
        val namespace = dataBlock.data["IMNameSpace"] as String?
        if(id != null && namespace != null) {
            val location = dataBlock.block.location
            player.sendPluginMessage(ModHandler.plugin, ModHandler.CHANNEL, SetBlockModel(location, id, namespace).toBytes())
        }
    }
}