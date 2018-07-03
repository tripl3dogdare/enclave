package com.tripl3dogdare.enclave.data

import com.tripl3dogdare.enclave.util.IdObject
import com.tripl3dogdare.enclave.util.Snowflake
import com.tripl3dogdare.havenjson.Json
import java.time.Instant
import java.time.ZonedDateTime
import java.util.*

interface MessageLike : IdObject {
  override val id: Snowflake
  val channelId: Snowflake
  val guildId: Snowflake?
  val author:User?
  val authorWebhook:Webhook?
  val member:Member?
  val content:String
  val tts:Boolean
  val mentionEveryone:Boolean
  val mentions:List<User>
  val mentionRoles:List<Snowflake>
  val attachments:List<Message.Attachment>
  val embeds:List<Message.Embed>
  val reactions:List<Message.Reaction>?
  val nonce: Snowflake?
  val pinned:Boolean
  val webhookId: Snowflake?
  val type: Message.Type
  val activity: Message.Activity?
  val application: Message.Application?
  val timestamp:ZonedDateTime
  val editedTimestamp:ZonedDateTime?
}

data class Message(
  override val id: Snowflake,
  override val channelId: Snowflake,
  override val guildId: Snowflake?,
  override val author:User?,
  override val authorWebhook:Webhook?,
  override val member:Member?,
  override val content:String,
  override val tts:Boolean,
  override val mentionEveryone:Boolean,
  override val mentions:List<User>,
  override val mentionRoles:List<Snowflake>,
  override val attachments:List<Attachment>,
  override val embeds:List<Embed>,
  override val reactions:List<Reaction>,
  override val nonce: Snowflake?,
  override val pinned:Boolean,
  override val webhookId: Snowflake?,
  override val type:Type,
  override val activity:Activity?,
  override val application:Application?,
  override val timestamp:ZonedDateTime,
  override val editedTimestamp:ZonedDateTime?
) : MessageLike {
  companion object {
    fun fromJson(raw:Json) = try { Message(
      id =              Snowflake.fromString(raw["id"].asString)!!,
      channelId =       Snowflake.fromString(raw["channel_id"].asString)!!,
      guildId =         Snowflake.fromString(raw["guild_id"].asString),
      content =         raw["content"].asString!!,
      tts =             raw["tts"].asBoolean!!,
      mentionEveryone = raw["mention_everyone"].asBoolean!!,
      mentions =        raw["mentions"].asList.orEmpty().map(User.Companion::fromJson).filterNotNull(),
      mentionRoles =    raw["mention_roles"].asList.orEmpty().map { Snowflake.fromString(it.asString) }.filterNotNull(),
      attachments =     raw["attachments"].asList.orEmpty().map(Attachment.Companion::fromJson).filterNotNull(),
      embeds =          raw["embeds"].asList.orEmpty().map(Embed.Companion::fromJson).filterNotNull(),
      reactions =       raw["reactions"].asList.orEmpty().map(Reaction.Companion::fromJson).filterNotNull(),
      nonce =           Snowflake.fromString(raw["nonce"].asString),
      pinned =          raw["pinned"].asBoolean!!,
      webhookId =       Snowflake.fromString(raw["webhook_id"].asString),
      type =            Type.values()[raw["type"].asInt!!],
      activity =        Activity.fromJson(raw["activity"]),
      application =     Application.fromJson(raw["application"]),
      timestamp =       ZonedDateTime.parse(raw["timestamp"].asString),
      editedTimestamp = raw["edited_timestamp"].asString?.let { ZonedDateTime.parse(it) },

      author =
        if(raw["webhook_id"].value == null) User.fromJson(raw["author"]) else null,
      authorWebhook =
        if(raw["webhook_id"].value != null) Webhook.fromJson(raw["author"]) else null,
      member =
        if(raw["member"]["user"].value != null) Member.fromJson(raw["member"])
        else if(raw["member"].value != null) Member.fromJson(raw["member"], raw["author"])
        else null
    )} catch(e:NullPointerException) { null }
  }

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
    companion object {
      fun fromJson(raw:Json) =
        try { Activity(Type.values()[raw["type"].asInt!!], raw["party_id"].asString) }
        catch (_:NullPointerException) { null }
    }
  }

  data class Application(
    override val id: Snowflake,
    val coverImage:String,
    val description:String,
    val icon:String,
    val name:String
  ) : IdObject {
    companion object {
      fun fromJson(raw:Json) = try { Application(
        id = Snowflake.fromString(raw["id"].asString)!!,
        coverImage = raw["cover_image"].asString!!,
        description = raw["description"].asString!!,
        icon = raw["icon"].asString!!,
        name = raw["name"].asString!!
      )} catch(_:NullPointerException) { null }
    }
  }

  data class Reaction(
    val count:Int,
    val me:Boolean,
    val emoji:Guild.Emoji
  ) { companion object {
    fun fromJson(raw:Json) = try { Reaction(
      count = raw["count"].asInt!!,
      me = raw["me"].asBoolean!!,
      emoji = Guild.Emoji.fromJson(raw["emoji"])!!
    )} catch(_:NullPointerException) { null }
  }}

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
    val fields:List<Field>?,
    val timestamp:ZonedDateTime?
  ) {
    data class Provider(val name:String?, val url:String?)
    data class Video(val url:String?, val height:Int?, val width:Int?)
    data class Field(val name:String?, val value:String?, val inline:Boolean?)
    data class Footer(val text:String?, val iconUrl:String?, val proxyIconUrl:String?)
    data class Image(val url:String?, val proxyUrl:String?, val height:Int?, val width:Int?)
    data class Author(val name:String?, val url:String?, val iconUrl:String?, val proxyIconUrl:String?)

    companion object {
      fun fromJson(raw:Json) = try { Embed(
        title = raw["title"].asString,
        type = raw["type"].asString,
        description = raw["description"].asString,
        url = raw["url"].asString,
        color = raw["color"].asInt,
        footer =
          if(raw["footer"].value != null) Footer(
            raw["footer"]["text"].asString,
            raw["footer"]["icon_url"].asString,
            raw["footer"]["proxy_icon_url"].asString
          ) else null,
        image =
          if(raw["image"].value != null) Image(
            raw["image"]["url"].asString,
            raw["image"]["proxy_url"].asString,
            raw["image"]["height"].asInt,
            raw["image"]["width"].asInt
          ) else null,
        thumbnail =
          if(raw["thumbnail"].value != null) Image(
            raw["thumbnail"]["url"].asString,
            raw["thumbnail"]["proxy_url"].asString,
            raw["thumbnail"]["height"].asInt,
            raw["thumbnail"]["width"].asInt
          ) else null,
        video =
          if(raw["video"].value != null) Video(
            raw["video"]["url"].asString,
            raw["video"]["height"].asInt,
            raw["video"]["width"].asInt
          ) else null,
        provider =
          if(raw["provider"].value != null) Provider(
            raw["provider"]["name"].asString,
            raw["provider"]["url"].asString
          ) else null,
        author =
          if(raw["author"].value != null) Author(
            raw["author"]["name"].asString,
            raw["author"]["url"].asString,
            raw["author"]["icon_url"].asString,
            raw["author"]["proxy_icon_url"].asString
          ) else null,
        fields = raw["fields"].asList?.map { Field(it["name"].asString, it["value"].asString, it["inline"].asBoolean)},
        timestamp = raw["timestamp"].asString?.let { ZonedDateTime.parse(it) }
      )} catch(_:NullPointerException) { null }
    }
  }

  data class Attachment(
    override val id: Snowflake,
    val filename:String,
    val size:Int,
    val url:String,
    val proxyUrl:String,
    val height:Int?,
    val width:Int?
  ) : IdObject { companion object {
    fun fromJson(raw:Json) = try { Attachment(
      id = Snowflake.fromString(raw["id"].asString)!!,
      filename = raw["filename"].asString!!,
      size = raw["size"].asInt!!,
      url = raw["url"].asString!!,
      proxyUrl = raw["proxy_url"].asString!!,
      height = raw["height"].asInt,
      width = raw["width"].asInt
    )} catch(_:NullPointerException) { null }
  }}
}
