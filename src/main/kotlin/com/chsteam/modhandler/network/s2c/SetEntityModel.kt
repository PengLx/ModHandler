package com.chsteam.modhandler.network.s2c

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import org.bukkit.entity.Entity

class SetEntityModel {
    private val entityId: Int
    private val modId: String
    private val namespace: String

    constructor(entity: Entity, modId: String, namespace: String) {
        this.entityId = entity.entityId
        this.modId = modId
        this.namespace = namespace
    }

    fun toBytes() : ByteArray {
        val buf: ByteBuf = Unpooled.buffer()
        buf.writeByte(1)
        buf.writeInt(this.entityId)
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