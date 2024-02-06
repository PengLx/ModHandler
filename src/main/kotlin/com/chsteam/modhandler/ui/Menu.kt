package com.chsteam.modhandler.ui

import org.bukkit.inventory.Inventory

abstract class Menu(var title: String) {

    abstract fun build(): Inventory
}