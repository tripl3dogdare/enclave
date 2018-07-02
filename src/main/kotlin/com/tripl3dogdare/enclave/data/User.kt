package com.tripl3dogdare.enclave.data

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import com.tripl3dogdare.enclave.util.IdObject
import com.tripl3dogdare.enclave.util.Snowflake
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
) : UserLike

data class Member(
  val user:User,
  val nick:String?,
  val roles:Array<Snowflake>,
  val deaf:Boolean,
  val mute:Boolean,
  val joinedAt: Date?
) : UserLike by user

data class UserConnection(
  val id:String,
  val name:String,
  val type:String,
  val revoked:Boolean,
  val integrations:Array<Integration>
)

data class Presence(
  val user:User,
  val roles:Array<Snowflake>,
  val game:Activity?,
  val guildId: Snowflake,
  val status:Status
) : UserLike by user {
  enum class Status {
    IDLE, DND, ONLINE, OFFLINE;
    @JsonValue fun toValue() = name.toLowerCase()
    @JsonCreator fun fromValue(key:String) = Status.valueOf(key.toUpperCase())
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
  }
}
