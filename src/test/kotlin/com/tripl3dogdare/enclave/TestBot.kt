package com.tripl3dogdare.enclave

import com.tripl3dogdare.enclave.event.*
import com.tripl3dogdare.enclave.network.createMessage
import com.tripl3dogdare.havenjson.Json
import java.io.File

private lateinit var client:Enclave
fun main(args:Array<String>) {
  client = Enclave(File(".auth").readText().trim()).include(TestBotEvents)
}

val TestBotEvents = EventContainer {
  on { ev:ReadyEvent -> println("Ready!") }
//    once { _:PresenceUpdateEvent -> println("boop") }
//    every(3) { _:PresenceUpdateEvent -> println("test") }
//    randomly(75) { _:PresenceUpdateEvent -> println("random!") }

//  on { ev:MessageEvent -> when(ev) {
//    is MessageCreateEvent -> println(ev.raw["content"].textValue())
//    is MessageDeleteEvent -> println("Message deleted.")
//  }}

  on { ev:MessageCreateEvent ->
    if(ev.content.startsWith("!ping"))
      client.rest.createMessage(ev.channelId, Json("content" to "Pong!"))
  }
}
