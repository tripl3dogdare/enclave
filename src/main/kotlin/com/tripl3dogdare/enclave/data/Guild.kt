package com.tripl3dogdare.enclave.data

import com.tripl3dogdare.enclave.util.IdObject
import com.tripl3dogdare.enclave.util.IdObjectNullable
import com.tripl3dogdare.enclave.util.Snowflake
import com.tripl3dogdare.havenjson.Json
import java.time.Instant
import java.time.ZonedDateTime
import java.util.*

data class Guild(
  override val id: Snowflake,
  val name:String,
  val icon:String?,
  val splash:String?,
  val owner:Boolean?,
  val ownerId: Snowflake,
  val permissions:Permissions?,
  val region:String,
  val afkChannelId: Snowflake?,
  val afkTimeout:Int,
  val embedEnabled:Boolean?,
  val embedChannelId: Snowflake?,
  val verificationLevel:VerificationLevel,
  val defaultMessageNotifications:DefaultMessageNotificationLevel,
  val explicitContentFilter:ExplicitContentFilterLevel,
  val roles:List<Role>,
  val emojis:List<Emoji>,
  val features:List<String>,
  val mfaLevel:MFALevel,
  val applicationId: Snowflake?,
  val widgetEnabled:Boolean?,
  val widgetChannelId: Snowflake?,
  val systemChannelId: Snowflake?,
  val large:Boolean?,
  val unavailable:Boolean?,
  val memberCount:Int?,
//  val voiceStates:Array<VoiceState>?,
  val members:List<Member>,
  val channels:List<Channel>,
  val presences:List<Presence>,
  val joinedAt:ZonedDateTime?
) : IdObject {
  companion object {
    fun fromJson(raw:Json) = try { Guild(
      id = Snowflake.fromString(raw["id"].asString)!!,
      name = raw["name"].asString!!,
      icon = raw["icon"].asString,
      splash = raw["splash"].asString,
      owner = raw["owner"].asBoolean,
      ownerId = Snowflake.fromString(raw["owner_id"].asString)!!,
      permissions = if(raw["permissions"].value != null) permissionsFrom(raw["permissions"]) else null,
      region = raw["region"].asString!!,
      afkChannelId = Snowflake.fromString(raw["afk_channel_id"].asString),
      afkTimeout = raw["afk_timeout"].asInt!!,
      embedEnabled = raw["embed_enabled"].asBoolean,
      embedChannelId = Snowflake.fromString(raw["embed_channel_id"].asString),
      verificationLevel = VerificationLevel.values()[raw["verification_level"].asInt!!],
      defaultMessageNotifications = DefaultMessageNotificationLevel.values()[raw["default_message_notifications"].asInt!!],
      explicitContentFilter = ExplicitContentFilterLevel.values()[raw["explicit_content_filter"].asInt!!],
      roles = raw["roles"].asList.orEmpty().map(Role.Companion::fromJson).filterNotNull(),
      emojis = raw["emojis"].asList.orEmpty().map(Emoji.Companion::fromJson).filterNotNull(),
      features = raw["features"].asList.orEmpty().map(Json::asString).filterNotNull(),
      mfaLevel = MFALevel.values()[raw["mfa_level"].asInt!!],
      applicationId = Snowflake.fromString(raw["application_id"].asString),
      widgetEnabled = raw["widget_enabled"].asBoolean,
      widgetChannelId = Snowflake.fromString(raw["widget_channel_id"].asString),
      systemChannelId = Snowflake.fromString(raw["system_channel_id"].asString),
      large = raw["large"].asBoolean,
      unavailable = raw["unavailable"].asBoolean,
      memberCount = raw["member_count"].asInt,
      members = raw["members"].asList.orEmpty().map(Member.Companion::fromJson).filterNotNull(),
      channels = raw["channels"].asList.orEmpty().map(Channel.Companion::fromJson).filterNotNull(),
      presences = raw["presences"].asList.orEmpty().map(Presence.Companion::fromJson).filterNotNull(),
      joinedAt = raw["joined_at"].asString?.let { ZonedDateTime.parse(it) }
    )} catch(_:NullPointerException) { null }
  }

  data class Embed(val enabled:Boolean, val channelId: Snowflake?) { companion object {
    fun fromJson(raw:Json) =
      try { Embed(raw["enabled"].asBoolean!!, Snowflake.fromString(raw["channel_id"].asString))}
      catch(_:NullPointerException) { null }
  }}

  data class Ban(val reason:String?, val user:User) { companion object {
    fun fromJson(raw:Json) =
      try { Ban(raw["reason"].asString, User.fromJson(raw["user"])!!)}
      catch(_:NullPointerException) { null }
  }}

  data class Emoji(
    override val id:Snowflake?,
    val name:String,
    val roles:List<Snowflake>,
    val user:User?,
    val requireColons:Boolean?,
    val managed:Boolean?,
    val animated:Boolean?
  ) : IdObjectNullable { companion object {
    fun fromJson(raw:Json) = try { Emoji(
      id = Snowflake.fromString(raw["id"].asString),
      name = raw["name"].asString!!,
      roles = raw["roles"].asList.orEmpty().map { Snowflake.fromString(it.asString) }.filterNotNull(),
      user = User.fromJson(raw["user"]),
      requireColons = raw["require_colons"].asBoolean,
      managed = raw["managed"].asBoolean,
      animated = raw["animated"].asBoolean
    )} catch(_:NullPointerException) { null }
  }}

  data class Invite(
    val code:String,
    val guild:Guild,
    val channel:Channel,
    val approximatePresenceCount:Int?,
    val approximateMemberCount:Int?
  ) { companion object {
    fun fromJson(raw:Json) = try { Invite(
      code = raw["code"].asString!!,
      guild = Guild.fromJson(raw["guild"])!!,
      channel = Channel.fromJson(raw["channel"])!!,
      approximatePresenceCount = raw["approximate_presence_count"].asInt,
      approximateMemberCount = raw["approximate_member_count"].asInt
    )} catch(_:NullPointerException) { null }
  }}

  data class InviteMetadata(
    val inviter:User,
    val uses:Int,
    val maxUses:Int,
    val maxAge:Int,
    val temporary:Boolean,
    val revoked:Boolean,
    val createdAt: ZonedDateTime
  ) { companion object {
    fun fromJson(raw:Json) = try { InviteMetadata(
      inviter = User.fromJson(raw["user"])!!,
      uses = raw["uses"].asInt!!,
      maxUses = raw["max_uses"].asInt!!,
      maxAge = raw["max_age"].asInt!!,
      temporary = raw["temporary"].asBoolean!!,
      revoked = raw["revoked"].asBoolean!!,
      createdAt = ZonedDateTime.parse(raw["created_at"].asString!!)
    )} catch(_:NullPointerException) { null }
  }}

  enum class DefaultMessageNotificationLevel { ALL_MESSAGES, ONLY_MENTIONS }
  enum class ExplicitContentFilterLevel { DISABLE, MEMBERS_WITHOUT_ROLES, ALL_MEMBERS }
  enum class MFALevel { NONE, ELEVATED }
  enum class VerificationLevel { NONE, LOW, MEDIUM, HIGH, VERY_HIGH }
}

data class Role(
  override val id: Snowflake,
  val name:String,
  val color:Int,
  val hoist:Boolean,
  val position:Int,
  val permissions:Permissions,
  val managed:Boolean,
  val mentionable:Boolean
) : IdObject { companion object {
  fun fromJson(raw:Json) = try { Role(
    id = Snowflake.fromString(raw["id"].asString)!!,
    name = raw["name"].asString!!,
    color = raw["color"].asInt!!,
    hoist = raw["hoist"].asBoolean!!,
    position = raw["position"].asInt!!,
    permissions = permissionsFrom(raw["permissions"])!!,
    managed = raw["managed"].asBoolean!!,
    mentionable = raw["mentionable"].asBoolean!!
  )} catch(_:NullPointerException) { null }
}}


data class Integration(
  override val id: Snowflake,
  val name:String,
  val type:String,
  val enabled:Boolean,
  val syncing:Boolean,
  val roleId: Snowflake,
  val expireBehavior:Int,
  val expireGracePeriod:Int,
  val user:User,
  val account:Account,
  val syncedAt:ZonedDateTime?
) : IdObject {
  data class Account(val id:String, val name:String)

  companion object {
    fun fromJson(raw:Json) = try { Integration(
      id = Snowflake.fromString(raw["id"].asString)!!,
      name = raw["name"].asString!!,
      type = raw["type"].asString!!,
      enabled = raw["enabled"].asBoolean!!,
      syncing = raw["syncing"].asBoolean!!,
      roleId = Snowflake.fromString(raw["role_id"].asString)!!,
      expireBehavior = raw["expire_behavior"].asInt!!,
      expireGracePeriod = raw["expire_grace_period"].asInt!!,
      user = User.fromJson(raw["user"])!!,
      account = Account(raw["account"]["id"].asString!!, raw["account"]["name"].asString!!),
      syncedAt = raw["synced_at"].asString?.let { ZonedDateTime.parse(it) }
    )} catch(_:NullPointerException) { null }
  }
}
