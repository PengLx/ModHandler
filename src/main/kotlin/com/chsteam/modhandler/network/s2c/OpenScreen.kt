package com.chsteam.modhandler.network.s2c

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled

class OpenScreen {
    private val syncId: Int
    private val modId: String
    private val namespace: String
    private val title: String

    constructor(syncId: Int,title: String, modId: String, namespace: String) {
        this.syncId = syncId
        this.modId = modId
        this.namespace = namespace
        this.title = title
    }

    fun toBytes() : ByteArray {
        val buf: ByteBuf = Unpooled.buffer()
        buf.writeByte(5)

        buf.writeInt(this.syncId)

        var bytes = this.modId.toByteArray()
        var len = bytes.size
        buf.writeInt(len)
        buf.writeBytes(bytes)

        bytes = this.namespace.toByteArray()
        len = bytes.size
        buf.writeInt(len)
        buf.writeBytes(bytes)

        bytes = this.title.toByteArray()
        len = bytes.size
        buf.writeInt(len)
        buf.writeBytes(bytes)

        return buf.array()
    }
}