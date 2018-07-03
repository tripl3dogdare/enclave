package com.tripl3dogdare.enclave.data

import com.tripl3dogdare.enclave.util.Snowflake
import com.tripl3dogdare.havenjson.Json

data class AuditLog(
  val webhooks:List<Webhook>,
  val users:List<User>,
  val entries:List<Entry>
) {
  companion object {
    fun fromJson(raw:Json) = try { AuditLog(
      webhooks = raw["webhooks"].asList.orEmpty().map(Webhook.Companion::fromJson).filterNotNull(),
      users = raw["users"].asList.orEmpty().map(User.Companion::fromJson).filterNotNull(),
      entries = raw["audit_log_entries"].asList.orEmpty().map(Entry.Companion::fromJson).filterNotNull()
    )} catch(_:NullPointerException) { null }
}

  data class Entry(
    val targetId: Snowflake?,
    val changes:List<Change>,
    val userId: Snowflake,
    val id: Snowflake,
    val actionType:Event,
    val options:Options?,
    val reason:String?
  ) { companion object {
    fun fromJson(raw:Json) = try { Entry(
      targetId = Snowflake.fromString(raw["target_id"].asString),
      changes = raw["changes"].asList.orEmpty().map { Change(raw["new_value"], raw["old_value"], raw["key"].asString!!)},
      userId = Snowflake.fromString(raw["user_id"].asString)!!,
      id = Snowflake.fromString(raw["id"].asString)!!,
      actionType = Event.fromInt(raw["action_type"].asInt)!!,
      options = Options.fromJson(raw["options"]),
      reason = raw["reason"].asString
    )} catch(_:NullPointerException) { null }
  }}

  data class Change(
    val newValue:Json?,
    val oldValue:Json?,
    val key:String
  )

  data class Options(
    val deleteMemberDays:String?,
    val membersRemoved:String?,
    val channelId: Snowflake?,
    val count:String?,
    val id: Snowflake?,
    val type:String?,
    val roleName:String?
  ) { companion object {
    fun fromJson(raw:Json) = try { Options(
      deleteMemberDays = raw["delete_member_days"].asString,
      membersRemoved = raw["members_removed"].asString,
      channelId = Snowflake.fromString(raw["channel_id"].asString),
      count = raw["count"].asString,
      id = Snowflake.fromString(raw["id"].asString),
      type = raw["type"].asString,
      roleName = raw["role_name"].asString
    )} catch(_:NullPointerException) { null }
  }}

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
      fun fromInt(n:Int?) = values().find { it.eventId == n }
    }
  }
}
