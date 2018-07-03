package com.tripl3dogdare.enclave.data

import com.tripl3dogdare.enclave.util.IdObject
import com.tripl3dogdare.enclave.util.Snowflake
import com.tripl3dogdare.havenjson.Json
import java.time.Instant
import java.time.ZonedDateTime
import java.util.*

data class Channel(
  override val id: Snowflake,
  val type:Type,
  val guildId: Snowflake?,
  val permissionOverwrites:List<PermissionOverwrite>,
  val name:String?,
  val topic:String?,
  val nsfw:Boolean?,
  val lastMessageId: Snowflake?,
  val bitrate:Int?,
  val userLimit:Int?,
  val recipients:List<User>,
  val icon:String?,
  val ownerId: Snowflake?,
  val applicationId: Snowflake?,
  val parentId: Snowflake?,
  val lastPinTimestamp: ZonedDateTime?
) : IdObject {
  enum class Type { GUILD_TEXT, DM, GUILD_VOICE, GROUP_DM, GUILD_CATEGORY }

  companion object {
    fun fromJson(raw:Json) = try { Channel(
      id = Snowflake.fromString(raw["id"].asString)!!,
      type = Type.values()[raw["type"].asInt!!],
      guildId = Snowflake.fromString(raw["guild_id"].asString!!),
      permissionOverwrites = raw["permission_overwrites"].asList.orEmpty().map(PermissionOverwrite.Companion::fromJson).filterNotNull(),
      name = raw["name"].asString,
      topic = raw["topic"].asString,
      nsfw = raw["nsfw"].asBoolean,
      lastMessageId = Snowflake.fromString(raw["last_message_id"].asString),
      bitrate = raw["bitrate"].asInt,
      userLimit = raw["user_limit"].asInt,
      recipients = raw["recipients"].asList.orEmpty().map(User.Companion::fromJson).filterNotNull(),
      icon = raw["icon"].asString,
      ownerId = Snowflake.fromString(raw["owner_id"].asString),
      applicationId = Snowflake.fromString(raw["application_id"].asString),
      parentId = Snowflake.fromString(raw["parent_id"].asString),
      lastPinTimestamp =
        if(raw["last_pin_timestamp"].value != null) ZonedDateTime.parse(raw["last_pin_timestamp"].asString!!)
        else null
    )} catch(_:NullPointerException) { null }
  }
}

data class Webhook(
  override val id: Snowflake,
  val guildId: Snowflake?,
  val channelId: Snowflake,
  val user:User?,
  val name:String?,
  val avatar:String?,
  val token:String
) : IdObject { companion object {
  fun fromJson(raw:Json) = try { Webhook(
    id = Snowflake.fromString(raw["id"].asString)!!,
    guildId = Snowflake.fromString(raw["guild_id"].asString),
    channelId = Snowflake.fromString(raw["channel_id"].asString)!!,
    user = if(raw["user"].value != null) User.fromJson(raw["user"]) else null,
    name = raw["name"].asString,
    avatar = raw["avatar"].asString,
    token = raw["token"].asString!!
  )} catch(_:NullPointerException) { null }
}}
