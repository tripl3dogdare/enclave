# Enclave
A Discord library for JVM Kotlin, designed to be simple, concise, and easy to use.

## Installation
Enclave is not available as a managed dependency yet. 
If you would like to use it ***at your own risk***, you can download and
build the source manually.

***Be warned that Enclave is still under heavy development.
It comes with no guarantees as to functionality or respecting
Discord's Terms of Service, and is not recommended for use by anyone
who isn't absolutely sure they know what they're doing.*** I am striving
to reach the point where I can give those guarantees, but as it
stands, Enclave is definitely not the library you want to use if
you're looking to build a good, API-respecting, bulletproof bot.

## Examples
This example is kept up to date with the current state of the library.
If it seems somewhat over-complicated, that's because it is - and I'm
actively working on making it simpler =)

```kotlin
package testbot

import com.tripl3dogdare.enclave.Enclave
import com.tripl3dogdare.enclave.event.*
import com.tripl3dogdare.enclave.network.*
import org.http4k.format.Jackson.asJsonObject

private lateinit var client:Enclave

fun main(args:Array<String>) {
  client = Enclave("YOUR-TOKEN-HERE")
  client.include(TestBotEvents)
}

object TestBotEvents : EventContainer() {
  init {
    on<ReadyEvent> { ev -> println("Ready!") }

    on<MessageCreateEvent> { ev ->
      if(ev.raw["content"].textValue().startsWith("!ping"))
        client.rest.createMessage(ev.raw["channel_id"].textValue(), """ {"content":"Pong!"} """.asJsonObject())
    }
  }
}
```
