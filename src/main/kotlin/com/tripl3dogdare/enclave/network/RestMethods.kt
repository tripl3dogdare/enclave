package com.tripl3dogdare.enclave.network

import com.tripl3dogdare.enclave.util.Snowflake
import com.tripl3dogdare.havenjson.Json
import org.http4k.core.Method.*

fun Rest.getGatewayUrl() =
  sendSync(GET, "/gateway")["url"].asString!!+"?v=6&encoding=json"
fun Rest.getGuildAuditLogs(gid: Snowflake) =
  send(GET, "/guilds/%s/audit-logs", gid)

fun Rest.getChannel(cid: Snowflake) =
  send(GET, "/channels/%s", cid)
fun Rest.modifyChannel(cid: Snowflake, data:Json?) =
  send(PATCH, "/channels/%s", cid, data=data)
fun Rest.deleteChannel(cid: Snowflake) =
  send(DELETE, "/channels/%s", cid)
fun Rest.getChannelMessages(cid: Snowflake, query:Map<String, String>?) =
  send(GET, "/channels/%s/messages", cid, query=query)
fun Rest.getChannelMessage(cid: Snowflake, mid: Snowflake) =
  send(GET, "/channels/%s/messages/%s", cid, mid)
fun Rest.createMessage(cid: Snowflake, data:Json?) =
  send(POST, "/channels/%s/messages", cid, data=data)
fun Rest.createReaction(cid: Snowflake, mid: Snowflake, emj: Snowflake) =
  send(PUT, "/channels/%s/messages/%s/reactions/%s/@me", cid, mid, emj)
fun Rest.deleteOwnReaction(cid: Snowflake, mid: Snowflake, emj: Snowflake) =
  send(DELETE, "/channels/%s/messages/%s/reactions/%s/@me", cid, mid, emj)
fun Rest.deleteUserReaction(cid: Snowflake, mid: Snowflake, emj: Snowflake, uid: Snowflake) =
  send(DELETE, "/channels/%s/messages/%s/reactions/%s/%s", cid, mid, uid, emj)
fun Rest.getReactions(cid: Snowflake, mid: Snowflake, emj: Snowflake) =
  send(GET, "/channels/%s/messages/%s/reactions/%s", cid, emj, mid)
fun Rest.deleteAllReactions(cid: Snowflake, mid: Snowflake) =
  send(DELETE, "/channels/%s/messages/%s/reactions", cid, mid)
fun Rest.editMessage(cid: Snowflake, mid: Snowflake, data:Json?) =
  send(PATCH, "/channels/%s/messages/%s", cid, mid, data=data)
fun Rest.deleteMessage(cid: Snowflake, mid: Snowflake) =
  send(DELETE, "/channels/%s/messages/%s", cid, mid)
fun Rest.bulkDeleteMessages(cid: Snowflake, data:Json?) =
  send(POST, "/channels/%s/messages/bulk-delete", cid, data=data)
fun Rest.editChannelPermissions(cid: Snowflake, oid: Snowflake, data:Json?) =
  send(PUT, "/channels/%s/permissions/%s", cid, oid, data=data)
fun Rest.getChannelInvites(cid: Snowflake) =
  send(GET, "/channels/%s/invites", cid)
fun Rest.createChannelInvite(cid: Snowflake, data:Json?) =
  send(POST, "/channels/%s/invites", cid, data=data)
fun Rest.deleteChannelPermission(cid: Snowflake, oid: Snowflake) =
  send(DELETE, "/channels/%s/permissions/%s", cid, oid)
fun Rest.triggerTypingIndicator(cid: Snowflake) =
  send(POST, "/channels/%s/typing", cid)
fun Rest.getPinnedMessages(cid: Snowflake) =
  send(GET, "/channels/%s/pins", cid)
fun Rest.addPinnedChannelMessage(cid: Snowflake, mid: Snowflake) =
  send(PUT, "/channels/%s/pins/%s", cid, mid)
fun Rest.deletePinnedChannelMessage(cid: Snowflake, mid: Snowflake) =
  send(DELETE, "/channels/%s/pins/%s", cid, mid)
fun Rest.groupDmAddRecipient(cid: Snowflake, uid: Snowflake, data:Json?) =
  send(PUT, "/channels/%s/recipients/%s", cid, uid, data=data)
fun Rest.groupDmRemoveRecipient(cid: Snowflake, uid: Snowflake) =
  send(DELETE, "/channels/%s/recipients/%s", cid, uid)

fun Rest.listGuildEmojis(gid: Snowflake) =
  send(GET, "/guilds/%s/emojis", gid)
fun Rest.getGuildEmojis(gid: Snowflake, eid: Snowflake) =
  send(GET, "/guilds/%s/emojis/%s", gid, eid)
fun Rest.createGuildEmoji(gid: Snowflake, data:Json?) =
  send(POST, "/guilds/%s/emojis", gid, data=data)
fun Rest.modifyGuildEmoji(gid: Snowflake, eid: Snowflake, data:Json?) =
  send(PATCH, "/guilds/%s/emojis/%s", gid, eid, data=data)
fun Rest.deleteGuildEmoji(gid: Snowflake, eid: Snowflake) =
  send(DELETE, "/guilds/%s/emojis/%s", gid, eid)

fun Rest.createGuild(data:Json?) =
  send(POST, "/guilds", data=data)
fun Rest.getGuild(gid: Snowflake) =
  send(GET, "/guilds/%s", gid)
fun Rest.modifyGuild(gid: Snowflake, data:Json?) =
  send(PATCH, "/guilds/%s", gid, data=data)
fun Rest.deleteGuild(gid: Snowflake, data:Json?) =
  send(DELETE, "/guilds/%s", gid, data=data)
fun Rest.getGuildChannels(gid: Snowflake) =
  send(GET, "/guilds/%s/channels", gid)
fun Rest.createGuildChannel(gid: Snowflake, data:Json?) =
  send(POST, "/guilds/%s/channels", gid, data=data)
fun Rest.modifyGuildChannelPositions(gid: Snowflake, data:Json?) =
  send(PATCH, "/guilds/%s/channels", gid, data=data)
fun Rest.getGuildMember(gid: Snowflake, uid: Snowflake) =
  send(GET, "/guilds/%s/members/%s", gid, uid)
fun Rest.listGuildMembers(gid: Snowflake) =
  send(GET, "/guilds/%s/members", gid)
fun Rest.addGuildMember(gid: Snowflake, uid: Snowflake, data:Json?) =
  send(PUT, "/guilds/%s/members/%s", gid, uid, data=data)
fun Rest.modifyGuildMember(gid: Snowflake, uid: Snowflake, data:Json?) =
  send(PATCH, "/guilds/%s/members/%s", gid, uid, data=data)
fun Rest.modifyCurrentUserNick(gid: Snowflake, data:Json?) =
  send(PATCH, "/guilds/%s/members/@me/nick", gid, data=data)
fun Rest.addGuildMemberRole(gid: Snowflake, uid: Snowflake, rid: Snowflake) =
  send(PUT, "/guilds/%s/members/%s/roles/%s", gid, rid, uid)
fun Rest.removeGuildMemberRole(gid: Snowflake, uid: Snowflake, rid: Snowflake) =
  send(DELETE, "/guilds/%s/members/%s/roles/%s", gid, rid, uid)
fun Rest.removeGuildMember(gid: Snowflake, uid: Snowflake) =
  send(DELETE, "/guilds/%s/members/%s", gid, uid)
fun Rest.getGuildBans(gid: Snowflake) =
  send(DELETE, "/guilds/%s/bans", gid)
fun Rest.createGuildBan(gid: Snowflake, uid: Snowflake, query:Map<String, String>?) =
  send(PUT, "/guilds/%s/bans/%s", gid, uid, query=query)
fun Rest.removeGuildBan(gid: Snowflake, uid: Snowflake) =
  send(DELETE, "/guilds/%s/bans/%s", gid, uid)
fun Rest.getGuildRoles(gid: Snowflake) =
  send(GET, "/guilds/%s/roles", gid)
fun Rest.createGuildRole(gid: Snowflake, data:Json?) =
  send(POST, "/guilds/%s/roles", gid, data=data)
fun Rest.modifyGuildRolePositions(gid: Snowflake, data:Json?) =
  send(PATCH, "/guilds/%s/roles", gid, data=data)
fun Rest.modifyGuildRole(gid: Snowflake, rid: Snowflake, data:Json?) =
  send(PATCH, "/guilds/%s/roles/%s", gid, rid, data=data)
fun Rest.deleteGuildRole(gid: Snowflake, rid: Snowflake) =
  send(DELETE, "/guilds/%s/roles/%s", gid, rid)
fun Rest.getGuildPruneCount(gid: Snowflake, query:Map<String, String>?) =
  send(GET, "/guilds/%s/prune", gid, query=query)
fun Rest.beginGuildPrune(gid: Snowflake, query:Map<String, String>?) =
  send(POST, "/guilds/%s/prune", gid, query=query)
fun Rest.getGuildVoiceRegions(gid: Snowflake) =
  send(GET, "/guilds/%s/regions", gid)
fun Rest.getGuildInvites(gid: Snowflake) =
  send(GET, "/guilds/%s/invites", gid)
fun Rest.getGuildIntegrations(gid: Snowflake) =
  send(GET, "/guilds/%s/integrations", gid)
fun Rest.createGuildIntegration(gid: Snowflake, data:Json?) =
  send(POST, "/guilds/%s/integrations", gid, data=data)
fun Rest.modifyGuildIntegration(gid: Snowflake, iid: Snowflake, data:Json?) =
  send(PATCH, "/guilds/%s/integrations/%s", gid, iid, data=data)
fun Rest.deleteGuildIntegration(gid: Snowflake, iid: Snowflake) =
  send(DELETE, "/guilds/%s/integrations/%s", gid, iid)
fun Rest.syncGuildIntegration(gid: Snowflake, iid: Snowflake) =
  send(POST, "/guilds/%s/integrations/%s/sync", gid, iid)
fun Rest.getGuildEmbed(gid: Snowflake) =
  send(GET, "/guilds/%s/embed", gid)
fun Rest.modifyGuildEmbed(gid: Snowflake, data:Json?) =
  send(PATCH, "/guilds/%s/embed", gid, data=data)
fun Rest.getGuildVanityUrl(gid: Snowflake) =
  send(GET, "/guilds/%s/vanity-url", gid)

fun Rest.getInvite(inv: Snowflake, query: Map<String, String>?) =
  send(GET, "/invites/%s", inv, query=query)
fun Rest.deleteInvite(inv: Snowflake) =
  send(DELETE, "/invites/%s", inv)

fun Rest.getCurrentUser() =
  send(GET, "/users/@me")
fun Rest.getUser(uid: Snowflake) =
  send(GET, "/users/%s", uid)
fun Rest.modifyCurrentUser(data: Json?) =
  send(PATCH, "/users/@me", data=data)
fun Rest.getCurrentUserGuilds(query: Map<String, String>?) =
  send(GET, "/users/@me/guilds", query=query)
fun Rest.leaveGuild(gid: Snowflake) =
  send(DELETE, "/users/@me/guilds/%s", gid)
fun Rest.getUserDMs() =
  send(GET, "/users/@me/channels")
fun Rest.createDM(data: Json?) =
  send(POST, "/users/@me/channels", data=data)
fun Rest.createGroupDM(data: Json?) =
  send(POST, "/users/@me/channels", data=data)
fun Rest.getUserConnections() =
  send(GET, "/users/@me/connections")

fun Rest.listVoiceRegions() =
  send(GET, "/voice/regions")

fun Rest.createWebhook(cid: Snowflake, data:Json?) =
  send(POST, "/channels/%s/webhooks", cid, data=data)
fun Rest.getChannelWebhooks(cid: Snowflake) =
  send(GET, "/channels/%s/webhooks", cid)
fun Rest.getGuildWebhooks(gid: Snowflake) =
  send(GET, "/guilds/%s/webhooks", gid)
fun Rest.getWebhook(wid: Snowflake) =
  send(GET, "/webhooks/%s", wid)
fun Rest.getWebhookWithToken(wid: Snowflake, wtk: Snowflake) =
  send(GET, "/webhooks/%s/%s", wid, wtk)
fun Rest.modifyWebhook(wid: Snowflake, data:Json?) =
  send(PATCH, "/webhooks/%s", wid, data=data)
fun Rest.modifyWebhookWithToken(wid: Snowflake, wtk: Snowflake, data:Json?) =
  send(PATCH, "/webhooks/%s/%s", wid, wtk, data=data)
fun Rest.deleteWebhook(wid: Snowflake) =
  send(DELETE, "/webhooks/%s", wid)
fun Rest.deleteWebhookWithToken(wid: Snowflake, wtk: Snowflake) =
  send(DELETE, "/webhooks/%s/%s", wid, wtk)
fun Rest.executeWebhook(wid: Snowflake, wtk: Snowflake, data:Json?) =
  send(POST, "/webhooks/%s/%s", wid, wtk, data=data)
fun Rest.executeSlackCompatibleWebhook(wid: Snowflake, wtk: Snowflake, data:Json?) =
  send(POST, "/webhooks/%s/%s/slack", wid, wtk, data=data)
fun Rest.executeGitHubCompatibleWebhook(wid: Snowflake, wtk: Snowflake, data:Json?) =
  send(POST, "/webhooks/%s/%s/github", wid, wtk, data=data)
