package com.tripl3dogdare.enclave.network

import com.fasterxml.jackson.databind.JsonNode
import org.http4k.core.Method.*

fun Rest.getGatewayUrl() =
  send(GET, "/gateway")["url"].asText()+"?v=6&encoding=json"
fun Rest.getGuildAuditLogs(gid:String) =
  send(GET, "/guilds/%s/audit-logs", gid)

fun Rest.getChannel(cid:String) =
  send(GET, "/channels/%s", cid)
fun Rest.modifyChannel(cid:String, data:JsonNode?) =
  send(PATCH, "/channels/%s", cid, data=data)
fun Rest.deleteChannel(cid:String) =
  send(DELETE, "/channels/%s", cid)
fun Rest.getChannelMessages(cid:String, query:Map<String, String>?) =
  send(GET, "/channels/%s/messages", cid, query=query)
fun Rest.getChannelMessage(cid:String, mid:String) =
  send(GET, "/channels/%s/messages/%s", cid, mid)
fun Rest.createMessage(cid:String, data:JsonNode?) =
  send(POST, "/channels/%s/messages", cid, data=data)
fun Rest.createReaction(cid:String, mid:String, emj:String) =
  send(PUT, "/channels/%s/messages/%s/reactions/%s/@me", cid, mid, emj)
fun Rest.deleteOwnReaction(cid:String, mid:String, emj:String) =
  send(DELETE, "/channels/%s/messages/%s/reactions/%s/@me", cid, mid, emj)
fun Rest.deleteUserReaction(cid:String, mid:String, emj:String, uid:String) =
  send(DELETE, "/channels/%s/messages/%s/reactions/%s/%s", cid, mid, uid, emj)
fun Rest.getReactions(cid:String, mid:String, emj:String) =
  send(GET, "/channels/%s/messages/%s/reactions/%s", cid, emj, mid)
fun Rest.deleteAllReactions(cid:String, mid:String) =
  send(DELETE, "/channels/%s/messages/%s/reactions", cid, mid)
fun Rest.editMessage(cid:String, mid:String, data:JsonNode?) =
  send(PATCH, "/channels/%s/messages/%s", cid, mid, data=data)
fun Rest.deleteMessage(cid:String, mid:String) =
  send(DELETE, "/channels/%s/messages/%s", cid, mid)
fun Rest.bulkDeleteMessages(cid:String, data:JsonNode?) =
  send(POST, "/channels/%s/messages/bulk-delete", cid, data=data)
fun Rest.editChannelPermissions(cid:String, oid:String, data:JsonNode?) =
  send(PUT, "/channels/%s/permissions/%s", cid, oid, data=data)
fun Rest.getChannelInvites(cid:String) =
  send(GET, "/channels/%s/invites", cid)
fun Rest.createChannelInvite(cid:String, data:JsonNode?) =
  send(POST, "/channels/%s/invites", cid, data=data)
fun Rest.deleteChannelPermission(cid:String, oid:String) =
  send(DELETE, "/channels/%s/permissions/%s", cid, oid)
fun Rest.triggerTypingIndicator(cid:String) =
  send(POST, "/channels/%s/typing", cid)
fun Rest.getPinnedMessages(cid:String) =
  send(GET, "/channels/%s/pins", cid)
fun Rest.addPinnedChannelMessage(cid:String, mid:String) =
  send(PUT, "/channels/%s/pins/%s", cid, mid)
fun Rest.deletePinnedChannelMessage(cid:String, mid:String) =
  send(DELETE, "/channels/%s/pins/%s", cid, mid)
fun Rest.groupDmAddRecipient(cid:String, uid:String, data:JsonNode?) =
  send(PUT, "/channels/%s/recipients/%s", cid, uid, data=data)
fun Rest.groupDmRemoveRecipient(cid:String, uid:String) =
  send(DELETE, "/channels/%s/recipients/%s", cid, uid)

fun Rest.listGuildEmojis(gid:String) =
  send(GET, "/guilds/%s/emojis", gid)
fun Rest.getGuildEmojis(gid:String, eid:String) =
  send(GET, "/guilds/%s/emojis/%s", gid, eid)
fun Rest.createGuildEmoji(gid:String, data:JsonNode?) =
  send(POST, "/guilds/%s/emojis", gid, data=data)
fun Rest.modifyGuildEmoji(gid:String, eid:String, data:JsonNode?) =
  send(PATCH, "/guilds/%s/emojis/%s", gid, eid, data=data)
fun Rest.deleteGuildEmoji(gid:String, eid:String) =
  send(DELETE, "/guilds/%s/emojis/%s", gid, eid)

fun Rest.createGuild(data:JsonNode?) =
  send(POST, "/guilds", data=data)
fun Rest.getGuild(gid:String) =
  send(GET, "/guilds/%s", gid)
fun Rest.modifyGuild(gid:String, data:JsonNode?) =
  send(PATCH, "/guilds/%s", gid, data=data)
fun Rest.deleteGuild(gid:String, data:JsonNode?) =
  send(DELETE, "/guilds/%s", gid, data=data)
fun Rest.getGuildChannels(gid:String) =
  send(GET, "/guilds/%s/channels", gid)
fun Rest.createGuildChannel(gid:String, data:JsonNode?) =
  send(POST, "/guilds/%s/channels", gid, data=data)
fun Rest.modifyGuildChannelPositions(gid:String, data:JsonNode?) =
  send(PATCH, "/guilds/%s/channels", gid, data=data)
fun Rest.getGuildMember(gid:String, uid:String) =
  send(GET, "/guilds/%s/members/%s", gid, uid)
fun Rest.listGuildMembers(gid:String) =
  send(GET, "/guilds/%s/members", gid)
fun Rest.addGuildMember(gid:String, uid:String, data:JsonNode?) =
  send(PUT, "/guilds/%s/members/%s", gid, uid, data=data)
fun Rest.modifyGuildMember(gid:String, uid:String, data:JsonNode?) =
  send(PATCH, "/guilds/%s/members/%s", gid, uid, data=data)
fun Rest.modifyCurrentUserNick(gid:String, data:JsonNode?) =
  send(PATCH, "/guilds/%s/members/@me/nick", gid, data=data)
fun Rest.addGuildMemberRole(gid:String, uid:String, rid:String) =
  send(PUT, "/guilds/%s/members/%s/roles/%s", gid, rid, uid)
fun Rest.removeGuildMemberRole(gid:String, uid:String, rid:String) =
  send(DELETE, "/guilds/%s/members/%s/roles/%s", gid, rid, uid)
fun Rest.removeGuildMember(gid:String, uid:String) =
  send(DELETE, "/guilds/%s/members/%s", gid, uid)
fun Rest.getGuildBans(gid:String) =
  send(DELETE, "/guilds/%s/bans", gid)
fun Rest.createGuildBan(gid:String, uid:String, query:Map<String, String>?) =
  send(PUT, "/guilds/%s/bans/%s", gid, uid, query=query)
fun Rest.removeGuildBan(gid:String, uid:String) =
  send(DELETE, "/guilds/%s/bans/%s", gid, uid)
fun Rest.getGuildRoles(gid:String) =
  send(GET, "/guilds/%s/roles", gid)
fun Rest.createGuildRole(gid:String, data:JsonNode?) =
  send(POST, "/guilds/%s/roles", gid, data=data)
fun Rest.modifyGuildRolePositions(gid:String, data:JsonNode?) =
  send(PATCH, "/guilds/%s/roles", gid, data=data)
fun Rest.modifyGuildRole(gid:String, rid:String, data:JsonNode?) =
  send(PATCH, "/guilds/%s/roles/%s", gid, rid, data=data)
fun Rest.deleteGuildRole(gid:String, rid:String) =
  send(DELETE, "/guilds/%s/roles/%s", gid, rid)
fun Rest.getGuildPruneCount(gid:String, query:Map<String, String>?) =
  send(GET, "/guilds/%s/prune", gid, query=query)
fun Rest.beginGuildPrune(gid:String, query:Map<String, String>?) =
  send(POST, "/guilds/%s/prune", gid, query=query)
fun Rest.getGuildVoiceRegions(gid:String) =
  send(GET, "/guilds/%s/regions", gid)
fun Rest.getGuildInvites(gid:String) =
  send(GET, "/guilds/%s/invites", gid)
fun Rest.getGuildIntegrations(gid:String) =
  send(GET, "/guilds/%s/integrations", gid)
fun Rest.createGuildIntegration(gid:String, data:JsonNode?) =
  send(POST, "/guilds/%s/integrations", gid, data=data)
fun Rest.modifyGuildIntegration(gid:String, iid:String, data:JsonNode?) =
  send(PATCH, "/guilds/%s/integrations/%s", gid, iid, data=data)
fun Rest.deleteGuildIntegration(gid:String, iid:String) =
  send(DELETE, "/guilds/%s/integrations/%s", gid, iid)
fun Rest.syncGuildIntegration(gid:String, iid:String) =
  send(POST, "/guilds/%s/integrations/%s/sync", gid, iid)
fun Rest.getGuildEmbed(gid:String) =
  send(GET, "/guilds/%s/embed", gid)
fun Rest.modifyGuildEmbed(gid:String, data:JsonNode?) =
  send(PATCH, "/guilds/%s/embed", gid, data=data)
fun Rest.getGuildVanityUrl(gid:String) =
  send(GET, "/guilds/%s/vanity-url", gid)

fun Rest.getInvite(inv:String, query: Map<String, String>?) =
  send(GET, "/invites/%s", inv, query=query)
fun Rest.deleteInvite(inv:String) =
  send(DELETE, "/invites/%s", inv)

fun Rest.getCurrentUser() =
  send(GET, "/users/@me")
fun Rest.getUser(uid:String) =
  send(GET, "/users/%s", uid)
fun Rest.modifyCurrentUser(data: JsonNode?) =
  send(PATCH, "/users/@me", data=data)
fun Rest.getCurrentUserGuilds(query: Map<String, String>?) =
  send(GET, "/users/@me/guilds", query=query)
fun Rest.leaveGuild(gid:String) =
  send(DELETE, "/users/@me/guilds/%s", gid)
fun Rest.getUserDMs() =
  send(GET, "/users/@me/channels")
fun Rest.createDM(data: JsonNode?) =
  send(POST, "/users/@me/channels", data=data)
fun Rest.createGroupDM(data: JsonNode?) =
  send(POST, "/users/@me/channels", data=data)
fun Rest.getUserConnections() =
  send(GET, "/users/@me/connections")

fun Rest.listVoiceRegions() =
  send(GET, "/voice/regions")

fun Rest.createWebhook(cid:String, data:JsonNode?) =
  send(POST, "/channels/%s/webhooks", cid, data=data)
fun Rest.getChannelWebhooks(cid:String) =
  send(GET, "/channels/%s/webhooks", cid)
fun Rest.getGuildWebhooks(gid:String) =
  send(GET, "/guilds/%s/webhooks", gid)
fun Rest.getWebhook(wid:String) =
  send(GET, "/webhooks/%s", wid)
fun Rest.getWebhookWithToken(wid:String, wtk:String) =
  send(GET, "/webhooks/%s/%s", wid, wtk)
fun Rest.modifyWebhook(wid:String, data:JsonNode?) =
  send(PATCH, "/webhooks/%s", wid, data=data)
fun Rest.modifyWebhookWithToken(wid:String, wtk:String, data:JsonNode?) =
  send(PATCH, "/webhooks/%s/%s", wid, wtk, data=data)
fun Rest.deleteWebhook(wid:String) =
  send(DELETE, "/webhooks/%s", wid)
fun Rest.deleteWebhookWithToken(wid:String, wtk: String) =
  send(DELETE, "/webhooks/%s/%s", wid, wtk)
fun Rest.executeWebhook(wid:String, wtk: String, data:JsonNode?) =
  send(POST, "/webhooks/%s/%s", wid, wtk, data=data)
fun Rest.executeSlackCompatibleWebhook(wid:String, wtk: String, data:JsonNode?) =
  send(POST, "/webhooks/%s/%s/slack", wid, wtk, data=data)
fun Rest.executeGitHubCompatibleWebhook(wid:String, wtk: String, data:JsonNode?) =
  send(POST, "/webhooks/%s/%s/github", wid, wtk, data=data)
