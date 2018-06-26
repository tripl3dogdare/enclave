package com.tripl3dogdare.enclave.network

import com.fasterxml.jackson.databind.JsonNode
import org.http4k.core.Method.*

fun Rest.getGatewayUrl() =
  send(GET, "/gateway")["url"].asText()+"?v=6&encoding=json"
fun Rest.getGuildAuditLogs(gid:String) =
  send(GET, "/guilds/$gid/audit-logs")

fun Rest.getChannel(cid:String) =
  send(GET, "/channels/$cid")
fun Rest.modifyChannel(cid:String, data:JsonNode?) =
  send(PATCH, "/channels/$cid", data=data)
fun Rest.deleteChannel(cid:String) =
  send(DELETE, "/channels/$cid")
fun Rest.getChannelMessages(cid:String, query:Map<String, String>?) =
  send(GET, "/channels/$cid/messages", query)
fun Rest.getChannelMessage(cid:String, mid:String) =
  send(GET, "/channels/$cid/messages/$mid")
fun Rest.createMessage(cid:String, data:JsonNode?) =
  send(POST, "/channels/$cid/messages", data=data)
fun Rest.createReaction(cid:String, mid:String, emj:String) =
  send(PUT, "/channels/$cid/messages/$mid/reactions/$emj/@me")
fun Rest.deleteOwnReaction(cid:String, mid:String, emj:String) =
  send(DELETE, "/channels/$cid/messages/$mid/reactions/$emj/@me")
fun Rest.deleteUserReaction(cid:String, mid:String, emj:String, uid:String) =
  send(DELETE, "/channels/$cid/messages/$mid/reactions/$emj/$uid")
fun Rest.getReactions(cid:String, mid:String, emj:String) =
  send(GET, "/channels/$cid/messages/$mid/reactions/$emj")
fun Rest.deleteAllReactions(cid:String, mid:String) =
  send(DELETE, "/channels/$cid/messages/$mid/reactions")
fun Rest.editMessage(cid:String, mid:String, data:JsonNode?) =
  send(PATCH, "/channels/$cid/messages/$mid", data=data)
fun Rest.deleteMessage(cid:String, mid:String) =
  send(DELETE, "/channels/$cid/messages/$mid")
fun Rest.bulkDeleteMessages(cid:String, data:JsonNode?) =
  send(POST, "/channels/$cid/messages/bulk-delete", data=data)
fun Rest.editChannelPermissions(cid:String, oid:String, data:JsonNode?) =
  send(PUT, "/channels/$cid/permissions/$oid", data=data)
fun Rest.getChannelInvites(cid:String) =
  send(GET, "/channels/$cid/invites")
fun Rest.createChannelInvite(cid:String, data:JsonNode?) =
  send(POST, "/channels/$cid/invites", data=data)
fun Rest.deleteChannelPermission(cid:String, oid:String) =
  send(DELETE, "/channels/$cid/permissions/$oid")
fun Rest.triggerTypingIndicator(cid:String) =
  send(POST, "/channels/$cid/typing")
fun Rest.getPinnedMessages(cid:String) =
  send(GET, "/channels/$cid/pins")
fun Rest.addPinnedChannelMessage(cid:String, mid:String) =
  send(PUT, "/channels/$cid/pins/$mid")
fun Rest.deletePinnedChannelMessage(cid:String, mid:String) =
  send(DELETE, "/channels/$cid/pins/$mid")
fun Rest.groupDmAddRecipient(cid:String, uid:String, data:JsonNode?) =
  send(PUT, "/channels/$cid/recipients/$uid", data=data)
fun Rest.groupDmRemoveRecipient(cid:String, uid:String) =
  send(DELETE, "/channels/$cid/recipients/$uid")

fun Rest.listGuildEmojis(gid:String) =
  send(GET, "/guilds/$gid/emojis")
fun Rest.getGuildEmojis(gid:String, eid:String) =
  send(GET, "/guilds/$gid/emojis/$eid")
fun Rest.createGuildEmoji(gid:String, data:JsonNode?) =
  send(POST, "/guilds/$gid/emojis", data=data)
fun Rest.modifyGuildEmoji(gid:String, eid:String, data:JsonNode?) =
  send(PATCH, "/guilds/$gid/emojis/$eid", data=data)
fun Rest.deleteGuildEmoji(gid:String, eid:String) =
  send(DELETE, "/guilds/$gid/emojis/$eid")

fun Rest.createGuild(data:JsonNode?) =
  send(POST, "/guilds", data=data)
fun Rest.getGuild(gid:String) =
  send(GET, "/guilds/$gid")
fun Rest.modifyGuild(gid:String, data:JsonNode?) =
  send(PATCH, "/guilds/$gid", data=data)
fun Rest.deleteGuild(gid:String, data:JsonNode?) =
  send(DELETE, "/guilds/$gid", data=data)
fun Rest.getGuildChannels(gid:String) =
  send(GET, "/guilds/$gid/channels")
fun Rest.createGuildChannel(gid:String, data:JsonNode?) =
  send(POST, "/guilds/$gid/channels", data=data)
fun Rest.modifyGuildChannelPositions(gid:String, data:JsonNode?) =
  send(PATCH, "/guilds/$gid/channels", data=data)
fun Rest.getGuildMember(gid:String, uid:String) =
  send(GET, "/guilds/$gid/members/$uid")
fun Rest.listGuildMembers(gid:String) =
  send(GET, "/guilds/$gid/members")
fun Rest.addGuildMember(gid:String, uid:String, data:JsonNode?) =
  send(PUT, "/guilds/$gid/members/$uid", data=data)
fun Rest.modifyGuildMember(gid:String, uid:String, data:JsonNode?) =
  send(PATCH, "/guilds/$gid/members/$uid", data=data)
fun Rest.modifyCurrentUserNick(gid:String, data:JsonNode?) =
  send(PATCH, "/guilds/$gid/members/@me/nick", data=data)
fun Rest.addGuildMemberRole(gid:String, uid:String, rid:String) =
  send(PUT, "/guilds/$gid/members/$uid/roles/$rid")
fun Rest.removeGuildMemberRole(gid:String, uid:String, rid:String) =
  send(DELETE, "/guilds/$gid/members/$uid/roles/$rid")
fun Rest.removeGuildMember(gid:String, uid:String) =
  send(DELETE, "/guilds/$gid/members/$uid")
fun Rest.getGuildBans(gid:String) =
  send(DELETE, "/guilds/$gid/bans")
fun Rest.createGuildBan(gid:String, uid:String, query:Map<String, String>?) =
  send(PUT, "/guilds/$gid/bans/$uid", query=query)
fun Rest.removeGuildBan(gid:String, uid:String) =
  send(DELETE, "/guilds/$gid/bans/$uid")
fun Rest.getGuildRoles(gid:String) =
  send(GET, "/guilds/$gid/roles")
fun Rest.createGuildRole(gid:String, data:JsonNode?) =
  send(POST, "/guilds/$gid/roles", data=data)
fun Rest.modifyGuildRolePositions(gid:String, data:JsonNode?) =
  send(PATCH, "/guilds/$gid/roles", data=data)
fun Rest.modifyGuildRole(gid:String, rid:String, data:JsonNode?) =
  send(PATCH, "/guilds/$gid/roles/$rid", data=data)
fun Rest.deleteGuildRole(gid:String, rid:String) =
  send(DELETE, "/guilds/$gid/roles/$rid")
fun Rest.getGuildPruneCount(gid:String, query:Map<String, String>?) =
  send(GET, "/guilds/$gid/prune", query=query)
fun Rest.beginGuildPrune(gid:String, query:Map<String, String>?) =
  send(POST, "/guilds/$gid/prune", query=query)
fun Rest.getGuildVoiceRegions(gid:String) =
  send(GET, "/guilds/$gid/regions")
fun Rest.getGuildInvites(gid:String) =
  send(GET, "/guilds/$gid/invites")
fun Rest.getGuildIntegrations(gid:String) =
  send(GET, "/guilds/$gid/integrations")
fun Rest.createGuildIntegration(gid:String, data:JsonNode?) =
  send(POST, "/guilds/$gid/integrations", data=data)
fun Rest.modifyGuildIntegration(gid:String, iid:String, data:JsonNode?) =
  send(PATCH, "/guilds/$gid/integrations/$iid", data=data)
fun Rest.deleteGuildIntegration(gid:String, iid:String) =
  send(DELETE, "/guilds/$gid/integrations/$iid")
fun Rest.syncGuildIntegration(gid:String, iid:String) =
  send(POST, "/guilds/$gid/integrations/$iid/sync")
fun Rest.getGuildEmbed(gid:String) =
  send(GET, "/guilds/$gid/embed")
fun Rest.modifyGuildEmbed(gid:String, data:JsonNode?) =
  send(PATCH, "/guilds/$gid/embed", data=data)
fun Rest.getGuildVanityUrl(gid:String) =
  send(GET, "/guilds/$gid/vanity-url")

// TODO: Pick up from Invite endpoints
