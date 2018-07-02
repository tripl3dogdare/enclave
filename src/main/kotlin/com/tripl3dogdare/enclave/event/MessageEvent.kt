package com.tripl3dogdare.enclave.event

import com.fasterxml.jackson.databind.JsonNode
import com.tripl3dogdare.enclave.Enclave
import com.tripl3dogdare.enclave.data.Message
import com.tripl3dogdare.enclave.data.MessageLike
import com.tripl3dogdare.enclave.util.IdObject
import com.tripl3dogdare.enclave.util.Respondable
import com.tripl3dogdare.enclave.util.Snowflake

abstract class MessageEvent : Event, Respondable() {
  abstract val channelId: Snowflake
}

data class MessageCreateEvent(
  val message:Message,
  override val client:Enclave
) : MessageEvent(), MessageLike by message

data class MessageUpdateEvent(
  val message:Message,
  override val client:Enclave
) : MessageEvent(), MessageLike by message

data class MessageDeleteEvent(
  override val id:Snowflake,
  override val channelId:Snowflake,
  override val client:Enclave
) : MessageEvent(), IdObject

data class MessageBulkDeleteEvent(
  val raw: JsonNode,
  override val client:Enclave
) : Event
