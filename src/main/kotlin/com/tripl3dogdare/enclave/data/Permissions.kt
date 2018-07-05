package com.tripl3dogdare.enclave.data

import com.tripl3dogdare.enclave.util.Snowflake
import com.tripl3dogdare.enclave.util.unassertNotNull
import com.tripl3dogdare.havenjson.Json
import java.util.*

enum class Permission(val value:Int) {
  CREATE_INSTANT_INVITE(0x00000001),
  KICK_MEMBERS(0x00000002),
  BAN_MEMBERS(0x00000004),
  ADMINISTRATOR(0x00000008),
  MANAGE_CHANNELS(0x00000010),
  MANAGE_GUILD(0x00000020),
  ADD_REACTIONS(0x00000040),
  VIEW_AUDIT_LOG(0x00000080),
  VIEW_CHANNEL(0x00000400),
  SEND_MESSAGES(0x00000800),
  SEND_TTS_MESSAGES(0x00001000),
  MANAGE_MESSAGES(0x00002000),
  EMBED_LINKS(0x00004000),
  ATTACH_FILES(0x00008000),
  READ_MESSAGE_HISTORY(0x00010000),
  MENTION_EVERYONE(0x00020000),
  USE_EXTERNAL_EMOJIS(0x00040000),
  CONNECT(0x00100000),
  SPEAK(0x00200000),
  MUTE_MEMBERS(0x00400000),
  DEAFEN_MEMBERS(0x00800000),
  MOVE_MEMBERS(0x01000000),
  USE_VAD(0x02000000),
  CHANGE_NICKNAME(0x04000000),
  MANAGE_NICKNAMES(0x08000000),
  MANAGE_ROLES(0x10000000),
  MANAGE_WEBHOOKS(0x20000000),
  MANAGE_EMOJIS(0x40000000);

  infix fun and(other: Permission) = Permissions.of(this, other)
}

typealias Permissions = EnumSet<Permission>
fun Permissions.toInt() = this.toTypedArray().fold(0) { a, b -> a or b.value }
infix fun Permissions.and(other:Permission):Permissions = Permissions.of(other, *this.toTypedArray())

fun permissionsFrom(raw:Json) =
  unassertNotNull { permissionsFrom(raw.asInt!!) }
fun permissionsFrom(n:Int):Permissions {
  val perms = Permission.values().filter { n and it.value != 0 }
  return Permissions.of(perms[0], *perms.drop(1).toTypedArray())
}

data class PermissionOverwrite(
  val id: Snowflake,
  val type:String,
  val allow:Permissions,
  val deny:Permissions
) { companion object {
  fun fromJson(raw:Json) = unassertNotNull { PermissionOverwrite(
    id = Snowflake.fromString(raw["id"].asString)!!,
    type = raw["type"].asString!!,
    allow = permissionsFrom(raw["allow"])!!,
    deny = permissionsFrom(raw["deny"])!!
  )}
}}
