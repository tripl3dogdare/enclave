# Enclave
A Discord library for JVM Kotlin, designed to be simple, concise, and easy to use.

Enclave takes heavy inspiration from [discordrb](https://github.com/meew0/discordrb/)
for Ruby and [discordcr](https://github.com/meew0/discordcr/), so a lot of the basic
code structure should feel familiar to users of those libraries. With that said, no
code here was directly taken from any other existing Discord library, right down to
the Gateway implementation.

#### Why use Enclave instead of...

- A Java library like [JDA](https://github.com/DV8FromTheWorld/JDA) or 
  [Discord4J](https://github.com/Discord4J/Discord4J)?
  - Can't write idiomatic Kotlin code without an extra "wrapper" library to deal with
  - No guarantee on proper handling of nulls
  - Much more complex structure
  - Heavy use of the builder pattern
- A different language with a better-established library?
  - I think these articles do the topic justice better than I can:
    - [Why you should totally switch to Kotlin by Magnus Vinther](https://medium.com/@magnus.chatt/why-you-should-totally-switch-to-kotlin-c7bbde9e10d5)
    - [Why Kotlin is my next programming language by Mike Hearn](https://medium.com/@octskyward/why-kotlin-is-my-next-programming-language-c25c001e26e3)
    - [Why Kotlin is better than whatever dumb language you're using by Steve Yegge](https://steve-yegge.blogspot.com/2017/05/why-kotlin-is-better-than-whatever-dumb.html)
- [xaanit/Artemis](https://github.com/xaanit/Artemis)?
  - Seems unfinished
  - Inspired by JDA/D4J so retains a lot of their idioms and patterns
  - Apparently moving towards being built on top of D4J
- [Kiskae/DiscordKt](https://github.com/Kiskae/DiscordKt)?
  - Extremely opinionated design, relies on some pretty heavy libraries like
    RxJava, Guava, etc.
  - Overly complicated structure leaves a lot to be desired on the usability
    front
  - Heavy use of the builder pattern
  - Strong emphasis on functional-style code with no good way to use it otherwise
  - Hasn't been updated in 2 years
- [nerd/discord.kt](https://github.com/nerd/discord.kt)?
  - No good way to modularize event handlers
  - Hasn't been updated for most of a year
  - This library actually hits pretty close to what I'm trying to do with Enclave.
    Great job, Nerd!
- [endreman0/KDA](https://github.com/endreman0/KDA)?
  - Code is somewhat awkward and hard to read, albeit very concise
  - Very unfinished
  - Infrequently updated

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
  client = Enclave("YOUR-TOKEN-HERE").include(TestBotEvents)
}

val TestBotEvents = EventContainer {
  on<ReadyEvent> { ev -> println("Ready!") }
    
  on<MessageCreateEvent> { ev ->
    if(ev.raw["content"].textValue().startsWith("!ping"))
      client.rest.createMessage(ev.raw["channel_id"].textValue(), """ {"content":"Pong!"} """.asJsonObject())
  }
}
```
