package com.tripl3dogdare.enclave.util

import com.fasterxml.jackson.annotation.JsonCreator
import unsigned.Ulong

class Snowflake(val value:Ulong) {
  val discordEpoch = 1420070400000L
  val timestamp = (value shr 22) + discordEpoch
  val workerId = (value and 0x3E0000) shr 17
  val processId = (value and 0x1F000) shr 12
  val increment = value and 0xFFF

  @JsonCreator constructor(from:String) : this(Ulong(from))
  override fun toString() = value.v.toString()
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
