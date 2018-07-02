package com.tripl3dogdare.enclave.data

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.JsonNode
import com.tripl3dogdare.enclave.util.Snowflake

data class AuditLog(
  val webhooks:Array<Webhook>,
  val users:Array<User>,
  @JsonProperty("audit_log_entries") val entries:Array<Entry>
) {
  data class Entry(
    val targetId: Snowflake?,
    val changes:Array<Change>,
    val user_id: Snowflake,
    val id: Snowflake,
    val action_type:Event,
    val options:Options?,
    val reason:String?
  )

  data class Change(
    val newValue:JsonNode?,
    val oldValue:JsonNode?,
    val key:String
  )

  data class Options(
    val delete_member_days:String?,
    val members_removed:String?,
    val channel_id: Snowflake?,
    val count:String?,
    val id: Snowflake?,
    val type:String?,
    val role_name:String?
  )

  enum class Event(val eventId:Int) {
    GUILD_UPDATE(1),
    CHANNEL_CREATE(10),
    CHANNEL_UPDATE(11),
    CHANNEL_DELETE(12),
    CHANNEL_OVERWRITE_CREATE(13),
    CHANNEL_OVERWRITE_UPDATE(14),
    CHANNEL_OVERWRITE_DELETE(15),
    MEMBER_KICK(20),
    MEMBER_PRUNE(21),
    MEMBER_BAN_ADD(22),
    MEMBER_BAN_REMOVE(23),
    MEMBER_UPDATE(24),
    MEMBER_ROLE_UPDATE(25),
    ROLE_CREATE(30),
    ROLE_UPDATE(31),
    ROLE_DELETE(32),
    INVITE_CREATE(40),
    INVITE_UPDATE(41),
    INVITE_DELETE(42),
    WEBHOOK_CREATE(50),
    WEBHOOK_UPDATE(51),
    WEBHOOK_DELETE(52),
    EMOJI_CREATE(60),
    EMOJI_UPDATE(61),
    EMOJI_DELETE(62),
    MESSAGE_DELETE(72);

    companion object {
      @JvmStatic @JsonCreator
      fun deserialize(n:Int) = values().find { it.eventId == n }
    }
  }
}
