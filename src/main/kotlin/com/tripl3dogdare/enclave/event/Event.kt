package com.tripl3dogdare.enclave.event

import com.fasterxml.jackson.databind.JsonNode
import com.tripl3dogdare.enclave.Enclave
import com.tripl3dogdare.enclave.Enclave.Companion.with
import com.tripl3dogdare.enclave.data.Message

interface Event {
//  val raw:JsonNode
  val client:Enclave

  companion object {
    fun from(name:String, data:JsonNode, client:Enclave):Event = when(name) {
      "READY" -> ReadyEvent(data, client)
      "RESUMED" -> ResumedEvent(data, client)
      "CHANNEL_CREATE" -> ChannelCreateEvent(data, client)
      "CHANNEL_UPDATE" -> ChannelUpdateEvent(data, client)
      "CHANNEL_DELETE" -> ChannelDeleteEvent(data, client)
      "CHANNEL_PINS_UPDATE" -> PinEvent(data, client)
      "GUILD_CREATE" -> GuildCreateEvent(data, client)
      "GUILD_UPDATE" -> GuildUpdateEvent(data, client)
      "GUILD_DELETE" -> GuildDeleteEvent(data, client)
      "GUILD_BAN_ADD" -> BanAddEvent(data, client)
      "GUILD_BAN_REMOVE" -> BanRemoveEvent(data, client)
      "GUILD_EMOJIS_UPDATE" -> EmojiUpdateEvent(data, client)
      "GUILD_INTEGRATIONS_UPDATE" -> IntegrationUpdateEvent(data, client)
      "GUILD_MEMBER_ADD" -> MemberAddEvent(data, client)
      "GUILD_MEMBER_REMOVE" -> MemberRemoveEvent(data, client)
      "GUILD_MEMBER_UPDATE" -> MemberUpdateEvent(data, client)
      "GUILD_MEMBERS_CHUNK" -> MemberChunkEvent(data, client)
      "GUILD_ROLE_CREATE" -> RoleCreateEvent(data, client)
      "GUILD_ROLE_UPDATE" -> RoleUpdateEvent(data, client)
      "GUILD_ROLE_DELETE" -> RoleDeleteEvent(data, client)
      "MESSAGE_CREATE" -> Enclave.mapJson(data, MessageCreateEvent::class)
      "MESSAGE_UPDATE" -> Enclave.mapJson(data, MessageUpdateEvent::class)
      "MESSAGE_DELETE" -> Enclave.mapJson(data, MessageDeleteEvent::class)
      "MESSAGE_DELETE_BULK" -> MessageBulkDeleteEvent(data, client)
      "MESSAGE_REACTION_ADD" -> ReactionAddEvent(data, client)
      "MESSAGE_REACTION_REMOVE" -> ReactionRemoveEvent(data, client)
      "MESSAGE_REACTION_REMOVE_ALL" -> ReactionRemoveAllEvent(data, client)
      "PRESENCE_UPDATE" -> PresenceUpdateEvent(data, client)
      "TYPING_START" -> TypingStartEvent(data, client)
      "USER_UPDATE" -> UserUpdateEvent(data, client)
      "VOICE_STATE_UPDATE" -> VoiceStateUpdateEvent(data, client)
      "VOICE_SERVER_UPDATE" -> VoiceServerUpdateEvent(data, client)
      "WEBHOOKS_UPDATE" -> WebhooksUpdateEvent(data, client)
      else -> UnknownEvent(name, data, client)
    }
  }
}

/**
 * Default event object used for events that do not have a defined type.
 * You should never encounter this under normal circumstances.
 */
data class UnknownEvent(val name:String, val raw:JsonNode, override val client:Enclave) : Event

// Event stubs
// TODO: Remove as proper event classes are added
data class ReadyEvent(val raw:JsonNode, override val client:Enclave) : Event
data class ResumedEvent(val raw:JsonNode, override val client:Enclave) : Event

interface ChannelEvent : Event
data class ChannelCreateEvent(val raw:JsonNode, override val client:Enclave) : ChannelEvent
data class ChannelUpdateEvent(val raw:JsonNode, override val client:Enclave) : ChannelEvent
data class ChannelDeleteEvent(val raw:JsonNode, override val client:Enclave) : ChannelEvent

data class PinEvent(val raw:JsonNode, override val client:Enclave) : Event

interface GuildEvent : Event
data class GuildCreateEvent(val raw:JsonNode, override val client:Enclave) : GuildEvent
data class GuildUpdateEvent(val raw:JsonNode, override val client:Enclave) : GuildEvent
data class GuildDeleteEvent(val raw:JsonNode, override val client:Enclave) : GuildEvent

interface BanEvent : Event
data class BanAddEvent(val raw:JsonNode, override val client:Enclave) : BanEvent
data class BanRemoveEvent(val raw:JsonNode, override val client:Enclave) : BanEvent

data class EmojiUpdateEvent(val raw:JsonNode, override val client:Enclave) : Event
data class IntegrationUpdateEvent(val raw:JsonNode, override val client:Enclave) : Event

interface MemberEvent : Event
data class MemberAddEvent(val raw:JsonNode, override val client:Enclave) : MemberEvent
data class MemberRemoveEvent(val raw:JsonNode, override val client:Enclave) : MemberEvent
data class MemberUpdateEvent(val raw:JsonNode, override val client:Enclave) : MemberEvent
data class MemberChunkEvent(val raw:JsonNode, override val client:Enclave) : Event

interface RoleEvent : Event
data class RoleCreateEvent(val raw:JsonNode, override val client:Enclave) : RoleEvent
data class RoleUpdateEvent(val raw:JsonNode, override val client:Enclave) : RoleEvent
data class RoleDeleteEvent(val raw:JsonNode, override val client:Enclave) : RoleEvent

interface ReactionEvent : Event
data class ReactionAddEvent(val raw:JsonNode, override val client:Enclave) : ReactionEvent
data class ReactionRemoveEvent(val raw:JsonNode, override val client:Enclave) : ReactionEvent
data class ReactionRemoveAllEvent(val raw:JsonNode, override val client:Enclave) : ReactionEvent

data class PresenceUpdateEvent(val raw:JsonNode, override val client:Enclave) : Event
data class TypingStartEvent(val raw:JsonNode, override val client:Enclave) : Event
data class UserUpdateEvent(val raw:JsonNode, override val client:Enclave) : Event
data class VoiceStateUpdateEvent(val raw:JsonNode, override val client:Enclave) : Event
data class VoiceServerUpdateEvent(val raw:JsonNode, override val client:Enclave) : Event
data class WebhooksUpdateEvent(val raw:JsonNode, override val client:Enclave) : Event
