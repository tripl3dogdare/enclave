package com.tripl3dogdare.enclave.data

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.node.ObjectNode
import com.tripl3dogdare.enclave.Enclave
import com.tripl3dogdare.enclave.util.IdObject
import com.tripl3dogdare.enclave.util.Snowflake
import java.util.*

interface MessageLike : IdObject {
  override val id: Snowflake
  val channelId: Snowflake
  val guildId: Snowflake?
  val author:User
  val member:Member?
  val content:String
  val tts:Boolean
  val mentionEveryone:Boolean
  val mentions:Array<User>
  val mentionRoles:Array<Snowflake>
  val attachments:Array<Message.Attachment>
  val embeds:Array<Message.Embed>
  val reactions:Array<Message.Reaction>?
  val nonce: Snowflake?
  val pinned:Boolean
  val webhookId: Snowflake?
  val type: Message.Type
  val activity: Message.Activity?
  val application: Message.Application?
  val timestamp:Date
  val editedTimestamp:Date?
}

data class Message(
  override val id: Snowflake,
  override val channelId: Snowflake,
  override val guildId: Snowflake?,
  override val author:User,
  override val content:String,
  override val tts:Boolean,
  override val mentionEveryone:Boolean,
  override val mentions:Array<User>,
  override val mentionRoles:Array<Snowflake>,
  override val attachments:Array<Attachment>,
  override val embeds:Array<Embed>,
  override val reactions:Array<Reaction>?,
  override val nonce: Snowflake?,
  override val pinned:Boolean,
  override val webhookId: Snowflake?,
  override val type:Type,
  override val activity:Activity?,
  override val application:Application?,
  override val timestamp:Date,
  override val editedTimestamp:Date?,

  @JsonProperty("member") private var rawMember:ObjectNode? // Dangit Discord.
) : MessageLike {
  enum class Type {
    DEFAULT,
    RECIPIENT_ADD,
    RECIPIENT_REMOVE,
    CALL,
    CHANNEL_NAME_CHANGE,
    CHANNEL_ICON_CHANGE,
    CHANNEL_PINNED_MESSAGE,
    GUILD_MEMBER_JOIN
  }

  data class Activity(val type:Type, val partyId:String?) {
    enum class Type { JOIN, SPECTATE, LISTEN, JOIN_REQUEST }
  }

  data class Application(
    override val id: Snowflake,
    val coverImage:String,
    val description:String,
    val icon:String,
    val name:String
  ) : IdObject

  data class Reaction(
    val count:Int,
    val me:Boolean,
    val emoji:Guild.Emoji
  )

  data class Embed(
    val title:String?,
    val type:String?,
    val description:String?,
    val url:String?,
    val color:Int?,
    val footer:Footer?,
    val image:Image?,
    val thumbnail:Image?,
    val video:Video?,
    val provider:Provider?,
    val author:Author?,
    val fields:Array<Field>?,
    val timestamp:Date?
  ) {
    data class Provider(val name:String?, val url:String?)
    data class Video(val url:String?, val height:Int?, val width:Int?)
    data class Field(val name:String?, val value:String?, val inline:Boolean?)
    data class Footer(val text:String?, val iconUrl:String?, val proxyIconUrl:String?)
    data class Image(val url:String?, val proxyUrl:String?, val height:Int?, val width:Int?)
    data class Author(val name:String?, val url:String?, val iconUrl:String?, val proxyIconUrl:String?)
  }

  data class Attachment(
    override val id: Snowflake,
    val filename:String,
    val size:Int,
    val url:String,
    val proxyUrl:String,
    val height:Int?,
    val width:Int?
  ) : IdObject

  // Dangit Discord.
  override var member:Member? = null
    private set(value) { field = value }
  init {
    val node = rawMember
    node?.putPOJO("user", author)
    if(node != null) member = Enclave.mapJson(node, Member::class)
  }
}
