package com.tripl3dogdare.enclave.event

import kotlinx.coroutines.experimental.launch
import java.util.*
import kotlin.reflect.*

abstract class EventContainer {
  val listeners = mutableMapOf<Int, Listener>()

  fun handleDispatch(event:Event) {
    listeners.values.forEach { listener ->
      if(listener.c.isInstance(event)) launch { listener.f(event) }}
  }

  inline fun <reified E : Event> on(noinline f:(E) -> Unit):EventContainer {
    @Suppress("UNCHECKED_CAST")
    listeners[f.hashCode()] = Listener(E::class as KClass<Event>, f as (Event) -> Unit)
    return this
  }

  inline fun <reified E : Event> once(noinline f:(E) -> Unit):EventContainer {
    lateinit var mw:(E) -> Unit
    mw = { ev -> listeners.remove(mw.hashCode()); f(ev) }
    return on(mw)
  }

  inline fun <reified E : Event> every(n:Int, noinline f:(E) -> Unit):EventContainer {
    var x = 0
    return on { ev:E -> x += 1; if(x % n == 0) f(ev) }
  }

  inline fun <reified E : Event> randomly(n:Int, noinline f:(E) -> Unit):EventContainer {
    val random = Random()
    return on { ev:E -> if(random.nextInt(100) < n) f(ev) }
  }

  open fun include(other:EventContainer):EventContainer {
    listeners.putAll(other.listeners)
    return this
  }

  companion object {
    data class Listener(val c:KClass<Event>, val f:(Event) -> Unit)
  }
}
