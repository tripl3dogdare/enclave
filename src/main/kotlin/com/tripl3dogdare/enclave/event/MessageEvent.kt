package com.tripl3dogdare.enclave.event

import com.tripl3dogdare.enclave.Enclave
import com.tripl3dogdare.enclave.data.Message
import com.tripl3dogdare.enclave.data.MessageLike
import com.tripl3dogdare.enclave.util.IdObject
import com.tripl3dogdare.enclave.util.Snowflake
import com.tripl3dogdare.enclave.util.unassertNotNull
import com.tripl3dogdare.havenjson.Json

abstract class MessageEvent : Event {
  abstract val channelId: Snowflake
}

data class MessageCreateEvent(
  val message:Message,
  override val client:Enclave
) : MessageEvent(), MessageLike by message { companion object {
  fun fromJson(raw:Json, client:Enclave) =
    unassertNotNull { MessageCreateEvent(Message.fromJson(raw)!!, client) }
}}

data class MessageUpdateEvent(
  val message:Message,
  override val client:Enclave
) : MessageEvent(), MessageLike by message{ companion object {
  fun fromJson(raw:Json, client:Enclave) =
    unassertNotNull { MessageUpdateEvent(Message.fromJson(raw)!!, client) }
}}


data class MessageDeleteEvent(
  override val id:Snowflake,
  override val channelId:Snowflake,
  override val client:Enclave
) : MessageEvent(), IdObject { companion object {
  fun fromJson(raw:Json, client:Enclave) = unassertNotNull { MessageDeleteEvent(
    Snowflake.fromString(raw["id"].asString)!!,
    Snowflake.fromString(raw["channel_id"].asString)!!,
    client
  )}
}}


data class MessageBulkDeleteEvent(
  val raw: Json,
  override val client:Enclave
) : Event
