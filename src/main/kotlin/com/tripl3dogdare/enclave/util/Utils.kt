package com.tripl3dogdare.enclave.util

import java.util.*

fun Timer.setTimeout(time:Long, f:() -> Unit):TimerTask {
  val task = TimerTask(f)
  schedule(task, time)
  return task
}

fun Timer.setInterval(time:Long, f:() -> Unit):TimerTask {
  val task = TimerTask(f)
  schedule(task, time, time)
  return task
}

fun TimerTask(f:() -> Unit) = object:TimerTask() { override fun run() = f() }

inline fun <reified E: Exception, T> tryOrNull(f:() -> T):T? =
  try { f() } catch(e:Exception) { when(e) {
    is E -> null
    else -> throw e
  }}
fun <T> tryOrNullAny(f:() -> T):T? =
  tryOrNull<Exception, T>(f)
fun <T> unassertNotNull(f:() -> T):T? =
  tryOrNull<NullPointerException, T>(f)
