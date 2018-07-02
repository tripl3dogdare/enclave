package com.tripl3dogdare.enclave.data

import com.tripl3dogdare.enclave.util.IdObject
import com.tripl3dogdare.enclave.util.IdObjectNullable
import com.tripl3dogdare.enclave.util.Snowflake
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
  val roles:Array<Role>,
  val emojis:Array<Emoji>,
  val features:Array<String>,
  val mfaLevel:MFALevel,
  val applicationId: Snowflake?,
  val widgetEnabled:Boolean?,
  val widgetChannelId: Snowflake?,
  val systemChannelId: Snowflake?,
  val large:Boolean?,
  val unavailable:Boolean?,
  val memberCount:Int?,
//  val voiceStates:Array<VoiceState>?,
  val members:Array<Member>?,
  val channels:Array<Channel>?,
  val presences:Array<Presence>?,
  val joinedAt:Date?
) : IdObject {
  data class Embed(val enabled:Boolean, val channelId: Snowflake?)
  data class Ban(val reason:String?, val user:User)

  data class Emoji(
    override val id: Snowflake?,
    val name:String,
    val roles:Array<Snowflake>?,
    val user:User?,
    val requireColons:Boolean?,
    val managed:Boolean?,
    val animated:Boolean?
  ) : IdObjectNullable

  data class Invite(
    val code:String,
    val guild:Guild,
    val channel:Channel,
    val approximatePresenceCount:Int?,
    val approximateMemberCount:Int?
  )

  data class InviteMetadata(
    val inviter:User,
    val uses:Int,
    val maxUses:Int,
    val maxAge:Int,
    val temporary:Boolean,
    val revoked:Boolean,
    val createdAt: Date
  )

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
) : IdObject


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
  val syncedAt:Date?
) : IdObject {
  data class Account(
    val id:String,
    val name:String
  )
}
