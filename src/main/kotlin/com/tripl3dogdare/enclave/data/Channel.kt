package com.tripl3dogdare.enclave.data

import com.tripl3dogdare.enclave.util.IdObject
import com.tripl3dogdare.enclave.util.Snowflake
import java.util.*

data class Channel(
  override val id: Snowflake,
  val type:ChannelType,
  val guild_id: Snowflake?,
  val permissionOverwrites:PermissionOverwrite?,
  val name:String?,
  val topic:String?,
  val nsfw:Boolean?,
  val lastMessageId: Snowflake?,
  val bitrate:Int?,
  val userLimit:Int?,
  val recipients:Array<User>?,
  val icon:String?,
  val ownerId: Snowflake?,
  val applicationId: Snowflake?,
  val parentId: Snowflake?,
  val lastPinTimestamp:Date?
) : IdObject

enum class ChannelType {
  GUILD_TEXT, DM, GUILD_VOICE, GROUP_DM, GUILD_CATEGORY;
}

data class Webhook(
  override val id: Snowflake,
  val guildId: Snowflake?,
  val channelId: Snowflake,
  val user:User?,
  val name:String?,
  val avatar:String?,
  val token:String
) : IdObject
