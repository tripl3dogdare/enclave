package com.tripl3dogdare.enclave

import com.tripl3dogdare.enclave.event.*
import com.tripl3dogdare.enclave.network.createMessage
import org.http4k.format.Jackson.asJsonObject
import java.io.File

private lateinit var client:Enclave

fun main(args:Array<String>) {
  client = Enclave(File(".auth").readText().trim()).include(TestBotEvents)
}

val TestBotEvents = EventContainer {
  on { ev:ReadyEvent -> println(ev.raw) }
//    once { _:PresenceUpdateEvent -> println("boop") }
//    every(3) { _:PresenceUpdateEvent -> println("test") }
//    randomly(75) { _:PresenceUpdateEvent -> println("random!") }

  on<MessageEvent> { ev -> when(ev) {
    is MessageCreateEvent -> println(ev.raw["content"].textValue())
    is MessageDeleteEvent -> println("Message deleted.")
  }}

  on<MessageCreateEvent> { ev ->
    if(ev.raw["content"].textValue().startsWith("!ping"))
      client.rest.createMessage(ev.raw["channel_id"].textValue(), """ {"content":"Pong!"} """.asJsonObject())
  }
}
