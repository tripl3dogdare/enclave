package com.tripl3dogdare.enclave.event

import com.fasterxml.jackson.databind.JsonNode

interface Event {
  val raw:JsonNode

  companion object {
    fun from(name:String, data:JsonNode):Event = when(name) {
      "READY" -> ReadyEvent(data)
      "RESUMED" -> ResumedEvent(data)
      "CHANNEL_CREATE" -> ChannelCreateEvent(data)
      "CHANNEL_UPDATE" -> ChannelUpdateEvent(data)
      "CHANNEL_DELETE" -> ChannelDeleteEvent(data)
      "CHANNEL_PINS_UPDATE" -> PinEvent(data)
      "GUILD_CREATE" -> GuildCreateEvent(data)
      "GUILD_UPDATE" -> GuildUpdateEvent(data)
      "GUILD_DELETE" -> GuildDeleteEvent(data)
      "GUILD_BAN_ADD" -> BanAddEvent(data)
      "GUILD_BAN_REMOVE" -> BanRemoveEvent(data)
      "GUILD_EMOJIS_UPDATE" -> EmojiUpdateEvent(data)
      "GUILD_INTEGRATIONS_UPDATE" -> IntegrationUpdateEvent(data)
      "GUILD_MEMBER_ADD" -> MemberAddEvent(data)
      "GUILD_MEMBER_REMOVE" -> MemberRemoveEvent(data)
      "GUILD_MEMBER_UPDATE" -> MemberUpdateEvent(data)
      "GUILD_MEMBERS_CHUNK" -> MemberChunkEvent(data)
      "GUILD_ROLE_CREATE" -> RoleCreateEvent(data)
      "GUILD_ROLE_UPDATE" -> RoleUpdateEvent(data)
      "GUILD_ROLE_DELETE" -> RoleDeleteEvent(data)
      "MESSAGE_CREATE" -> MessageCreateEvent(data)
      "MESSAGE_UPDATE" -> MessageUpdateEvent(data)
      "MESSAGE_DELETE" -> MessageDeleteEvent(data)
      "MESSAGE_DELETE_BULK" -> MessageBulkDeleteEvent(data)
      "MESSAGE_REACTION_ADD" -> ReactionAddEvent(data)
      "MESSAGE_REACTION_REMOVE" -> ReactionRemoveEvent(data)
      "MESSAGE_REACTION_REMOVE_ALL" -> ReactionRemoveAllEvent(data)
      "PRESENCE_UPDATE" -> PresenceUpdateEvent(data)
      "TYPING_START" -> TypingStartEvent(data)
      "USER_UPDATE" -> UserUpdateEvent(data)
      "VOICE_STATE_UPDATE" -> VoiceStateUpdateEvent(data)
      "VOICE_SERVER_UPDATE" -> VoiceServerUpdateEvent(data)
      "WEBHOOKS_UPDATE" -> WebhooksUpdateEvent(data)
      else -> UnknownEvent(name, data)
    }
  }
}

/**
 * Default event object used for events that do not have a defined type.
 * You should never encounter this under normal circumstances.
 */
data class UnknownEvent(val name:String, override val raw:JsonNode) : Event

// Event stubs
// TODO: Remove as proper event classes are added
data class ReadyEvent(override val raw:JsonNode) : Event
data class ResumedEvent(override val raw:JsonNode) : Event

interface ChannelEvent : Event
data class ChannelCreateEvent(override val raw:JsonNode) : ChannelEvent
data class ChannelUpdateEvent(override val raw:JsonNode) : ChannelEvent
data class ChannelDeleteEvent(override val raw:JsonNode) : ChannelEvent

data class PinEvent(override val raw:JsonNode) : Event

interface GuildEvent : Event
data class GuildCreateEvent(override val raw:JsonNode) : GuildEvent
data class GuildUpdateEvent(override val raw:JsonNode) : GuildEvent
data class GuildDeleteEvent(override val raw:JsonNode) : GuildEvent

interface BanEvent : Event
data class BanAddEvent(override val raw:JsonNode) : BanEvent
data class BanRemoveEvent(override val raw:JsonNode) : BanEvent

data class EmojiUpdateEvent(override val raw:JsonNode) : Event
data class IntegrationUpdateEvent(override val raw:JsonNode) : Event

interface MemberEvent : Event
data class MemberAddEvent(override val raw:JsonNode) : MemberEvent
data class MemberRemoveEvent(override val raw:JsonNode) : MemberEvent
data class MemberUpdateEvent(override val raw:JsonNode) : MemberEvent
data class MemberChunkEvent(override val raw:JsonNode) : Event

interface RoleEvent : Event
data class RoleCreateEvent(override val raw:JsonNode) : RoleEvent
data class RoleUpdateEvent(override val raw:JsonNode) : RoleEvent
data class RoleDeleteEvent(override val raw:JsonNode) : RoleEvent

interface MessageEvent : Event
data class MessageCreateEvent(override val raw:JsonNode) : MessageEvent
data class MessageUpdateEvent(override val raw:JsonNode) : MessageEvent
data class MessageDeleteEvent(override val raw:JsonNode) : MessageEvent
data class MessageBulkDeleteEvent(override val raw:JsonNode) : Event

interface ReactionEvent : Event
data class ReactionAddEvent(override val raw:JsonNode) : ReactionEvent
data class ReactionRemoveEvent(override val raw:JsonNode) : ReactionEvent
data class ReactionRemoveAllEvent(override val raw:JsonNode) : ReactionEvent

data class PresenceUpdateEvent(override val raw:JsonNode) : Event
data class TypingStartEvent(override val raw:JsonNode) : Event
data class UserUpdateEvent(override val raw:JsonNode) : Event
data class VoiceStateUpdateEvent(override val raw:JsonNode) : Event
data class VoiceServerUpdateEvent(override val raw:JsonNode) : Event
data class WebhooksUpdateEvent(override val raw:JsonNode) : Event
