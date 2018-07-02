package com.tripl3dogdare.enclave.network

import com.tripl3dogdare.enclave.util.*

import com.fasterxml.jackson.databind.JsonNode
import org.http4k.client.WebsocketClient
import org.http4k.core.Uri
import org.http4k.websocket.Websocket
import org.http4k.websocket.WsMessage
import org.http4k.websocket.WsStatus
import com.fasterxml.jackson.module.kotlin.*
import com.tripl3dogdare.enclave.Enclave
import com.tripl3dogdare.enclave.event.Event
import java.util.*

class Gateway(val token:String, val dispatchHandler:(Event) -> Unit, val client:Enclave) {
  private val gateway_url = client.rest.getGatewayUrl()

  private var hbseq = 0
  private var session = ""
  private var lastack = true
  private var ws:Websocket? = null
  private var timer = Timer()
  private var hbtask:TimerTask? = null

  fun login() {
    if(ws != null) disconnect()

    lateinit var task:TimerTask
    task = timer.setInterval(30000) {
      ws = WebsocketClient.nonBlocking(Uri.of(gateway_url), onConnect = { ws ->
        ws.send(if(session != "") pkt_resume() else pkt_identify)
        ws.onClose(::onDisconnect)
        println("Connected!")
        session = ""
        task.cancel()
      })
      ws?.onMessage(::onMessage)
    }
  }

  fun disconnect():Gateway {
    lastack = true
    ws?.close(WsStatus(2953, "Manual disconnect"))
    ws = null
    return this
  }

  data class GatewayMessage(val op:Int, val d:JsonNode, val s:Int?, val t:String?)

  private fun onMessage(msg:WsMessage) {
    val data:GatewayMessage = jacksonObjectMapper().readValue(msg.bodyString())

    if(data.s != null) hbseq = data.s
    if(data.op == DISPATCH && data.t == "READY") session = data.d["session_id"].asText()

    when(data.op) {
      DISPATCH -> dispatchHandler(Event.from(data.t!!, data.d, client))
      RECONNECT -> login()
      INVALID_SESSION -> {
        Thread.sleep(1500)
        if(!data.d.booleanValue()) session = ""
        login()
      }

      HELLO -> {
        hbtask?.cancel()
        hbtask = timer.setInterval(data.d["heartbeat_interval"].asLong()) {
          if(!lastack || ws == null) login()
          else {
            ws?.send(pkt_heartbeat())
            lastack = false
          }
        }
      }

      HEARTBEAT -> ws?.send(pkt_heartbeat())
      HEARTBEAT_ACK -> lastack = true
      else -> println("Received invalid Gateway opcode ${data.op}, ignoring.")
    }
  }

  private fun onDisconnect(status:WsStatus) {
    hbtask?.cancel()
    if(status.code == 2953) return
    println("Disconnected with code ${status.code}: ${status.description}")
    login()
  }

  // Opcodes
  // TODO: Implement sending the opcodes that aren't currently used
  val DISPATCH = 0
  val HEARTBEAT = 1
  val IDENTIFY = 2
  val STATUS_UPDATE = 3
  val VOICE_STATE_UPDATE = 4
  val VOICE_SERVER_PING = 5
  val RESUME = 6
  val RECONNECT = 7
  val REQUEST_GUILD_MEMBERS = 8
  val INVALID_SESSION = 9
  val HELLO = 10
  val HEARTBEAT_ACK = 11

  // Pre-defined packets
  val pkt_identify = WsMessage("""{
    "op": $IDENTIFY,
    "d": {
      "token": "$token",
      "properties": {
        "${'$'}os": "${System.getProperty("os.name")}",
        "${'$'}browser": "discala",
        "${'$'}device": "discala"
      },
      "compress": false,
      "large_threshold": 250,
      "shard": [0, 1]
    }
  }""")
  fun pkt_heartbeat() = WsMessage("""{"op":$HEARTBEAT,"d":$hbseq}""")
  fun pkt_resume() = WsMessage("""{ "op": $RESUME, "d": { "token": "$token", "session_id": "$session", "seq": $hbseq }}""")
}
