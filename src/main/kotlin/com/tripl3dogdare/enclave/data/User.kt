package com.tripl3dogdare.enclave.data

import com.tripl3dogdare.enclave.util.IdObject
import com.tripl3dogdare.enclave.util.Snowflake
import com.tripl3dogdare.enclave.util.unassertNotNull
import com.tripl3dogdare.havenjson.Json
import java.time.Instant
import java.time.ZonedDateTime
import java.util.*

interface UserLike : IdObject {
  val username:String
  val discriminator:String
  val avatar:String
  val bot:Boolean?
  val mfaEnabled:Boolean?
  val verified:Boolean?
  val email:String?
}

data class User(
  override val id: Snowflake,
  override val username:String,
  override val discriminator:String,
  override val avatar:String,
  override val bot:Boolean?,
  override val mfaEnabled:Boolean?,
  override val verified:Boolean?,
  override val email:String?
) : UserLike { companion object {
  fun fromJson(raw:Json) = unassertNotNull { User(
    id = Snowflake.fromString(raw["id"].asString)!!,
    username = raw["username"].asString!!,
    discriminator = raw["discriminator"].asString!!,
    avatar = raw["avatar"].asString!!,
    bot = raw["bot"].asBoolean,
    mfaEnabled = raw["mfa_enabled"].asBoolean,
    verified = raw["verified"].asBoolean,
    email = raw["email"].asString
  )}
}}

data class Member(
  val user:User,
  val nick:String?,
  val roles:List<Snowflake>,
  val deaf:Boolean,
  val mute:Boolean,
  val joinedAt: ZonedDateTime?
) : UserLike by user { companion object {
  fun fromJson(raw:Json) = fromJson(raw, raw["user"])
  fun fromJson(raw:Json, user:Json) = unassertNotNull { Member(
    user = User.fromJson(user)!!,
    nick = raw["nick"].asString,
    roles = raw["roles"].asList.orEmpty().map { Snowflake.fromString(it.asString) }.filterNotNull(),
    deaf = raw["deaf"].asBoolean!!,
    mute = raw["mute"].asBoolean!!,
    joinedAt = raw["joined_at"].asString?.let { ZonedDateTime.parse(it) }
  )}
}}

data class UserConnection(
  val id:String,
  val name:String,
  val type:String,
  val revoked:Boolean,
  val integrations:List<Integration>
) { companion object {
  fun fromJson(raw:Json) = unassertNotNull { UserConnection(
    id = raw["id"].asString!!,
    name = raw["name"].asString!!,
    type = raw["type"].asString!!,
    revoked = raw["revoked"].asBoolean!!,
    integrations = raw["integrations"].asList.orEmpty().map(Integration.Companion::fromJson).filterNotNull()
  )}
}}

data class Presence(
  val user:User,
  val roles:List<Snowflake>,
  val game:Activity?,
  val guildId: Snowflake,
  val status:Status
) : UserLike by user {
  enum class Status { IDLE, DND, ONLINE, OFFLINE; }

  companion object {
    fun fromJson(raw:Json) = unassertNotNull { Presence(
      user = User.fromJson(raw["user"])!!,
      roles = raw["roles"].asList.orEmpty().map { Snowflake.fromString(it.asString) }.filterNotNull(),
      game = Activity.fromJson(raw["game"]),
      guildId = Snowflake.fromString(raw["guild_id"].asString)!!,
      status = Status.valueOf(raw["status"].asString!!.toUpperCase())
    )}
  }

  data class Activity(
    val name:String,
    val type:Type,
    val url:String?,
    val timestamps:Timestamps?,
    val applicationId: Snowflake?,
    val details:String?,
    val state:String?,
    val party:Party?,
    val assets:Assets?
  ) {
    enum class Type { Playing, Streaming, Listening }
    data class Timestamps(val start:Int?, val end:Int?)
    data class Party(val id:String?, val size:Pair<Int, Int>?)
    data class Assets(
      val largeImage:String?,
      val largeText:String?,
      val smallImage:String?,
      val smallText:String?
    )

    companion object {
      fun fromJson(raw:Json) = unassertNotNull { Activity(
        name = raw["name"].asString!!,
        type = Type.values()[raw["type"].asInt!!],
        url = raw["url"].asString,
        timestamps = Timestamps(raw["timestamps"]["start"].asInt, raw["timestamps"]["end"].asInt),
        applicationId = Snowflake.fromString(raw["application_id"].asString)!!,
        details = raw["details"].asString,
        state = raw["state"].asString,

        party = raw["party"].asMap?.let { Party(
          it["id"]?.asString,
          it["size"]?.asList?.let { Pair(it[0].asInt!!, it[1].asInt!!) }
        )},

        assets = raw["assets"].asMap?.let { Assets(
          it["large_image"]?.asString,
          it["large_text"]?.asString,
          it["small_image"]?.asString,
          it["small_text"]?.asString
        )}
      )}
    }
  }
}
