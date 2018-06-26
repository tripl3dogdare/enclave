package com.tripl3dogdare.enclave

import com.tripl3dogdare.enclave.event.*
import java.io.File

fun main(args:Array<String>) {
  Enclave(File(".auth").readText().trim())
    .include(TestBotEvents)
}

object TestBotEvents : EventContainer() {
  init {
    on { ev:ReadyEvent -> println(ev.raw) }
//    once { _:PresenceUpdateEvent -> println("boop") }
//    every(3) { _:PresenceUpdateEvent -> println("test") }
//    randomly(75) { _:PresenceUpdateEvent -> println("random!") }

    on<MessageEvent> { ev -> when(ev) {
      is MessageCreateEvent -> println(ev.raw["content"].textValue())
      is MessageDeleteEvent -> println("Message deleted.")
    }}
  }
}
