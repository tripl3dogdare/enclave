package com.tripl3dogdare.enclave.util

import unsigned.Ulong

class Snowflake(val value:Ulong) {
  val discordEpoch = 1420070400000L
  val timestamp = (value shr 22) + discordEpoch
  val workerId = (value and 0x3E0000) shr 17
  val processId = (value and 0x1F000) shr 12
  val increment = value and 0xFFF

  override fun toString() = value.v.toString()
  companion object {
    fun fromString(raw:String?) = raw?.let { Snowflake(Ulong(it)) }
  }
}

interface IdObjectNullable {
  val id: Snowflake?
  val createdAt: Ulong
    get() = id?.timestamp ?: Ulong(0)

  fun isEqual(other:Any?) =
    this === other ||
    this.javaClass == other?.javaClass &&
    this.id == (other as IdObject).id
}
interface IdObject : IdObjectNullable { override val id: Snowflake }
