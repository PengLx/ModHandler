package com.chsteam.modhandler.network.s2c

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import org.bukkit.Location
import org.bukkit.entity.Entity

class SetBlockModel {
    private val location: Location
    private val modId: String
    private val namespace: String

    constructor(location: Location, modId: String, namespace: String) {
        this.location = location
        this.modId = modId
        this.namespace = namespace
    }

    fun toBytes() : ByteArray {
        val buf: ByteBuf = Unpooled.buffer()
        buf.writeByte(2)
        buf.writeDouble(location.toVector().x)
        buf.writeDouble(location.toVector().y)
        buf.writeDouble(location.toVector().z)

        var bytes = this.modId.toByteArray()
        var len = bytes.size
        buf.writeInt(len)
        buf.writeBytes(bytes)

        bytes = this.namespace.toByteArray()
        len = bytes.size
        buf.writeInt(len)
        buf.writeBytes(bytes)

        return buf.array()
    }
}