package com.chsteam.modhandler.command

import com.chsteam.modhandler.ModHandler
import com.chsteam.modhandler.api.ModBlock
import com.chsteam.modhandler.custom.CustomItem
import com.chsteam.modhandler.custom.CustomLoader
import com.chsteam.modhandler.network.s2c.OpenScreen
import com.chsteam.modhandler.network.s2c.SetBlockModel
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.common.platform.command.subCommand
import taboolib.expansion.createHelper

@CommandHeader("modhandler")
object Command {
    @CommandBody
    val main = subCommand {
        createHelper()
    }

    @CommandBody
    val reload = subCommand {
        execute<CommandSender> { _, _, _ ->
            CustomLoader.load()
        }
    }

    @CommandBody
    val getItem = subCommand {
        dynamic(comment = "namespace") {
            dynamic(comment = "id") {
                dynamic(comment = "amount") {
                    execute<Player> { sender, context, _ ->
                        val customItem = CustomLoader.registerItems[Pair(context.argument(-2), context.argument(-1))]

                        customItem?.let {
                            val item = it.build()
                            item.amount = context.argument(0).toInt()

                            sender.inventory.addItem(item)
                        }
                    }
                }
            }
        }
    }

}