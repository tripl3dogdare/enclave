package com.tripl3dogdare.enclave.util

import com.fasterxml.jackson.annotation.JsonCreator
import java.time.Instant
import java.time.format.DateTimeFormatter
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

@JsonCreator fun Date.fromString(from:String):Date {
  return Date.from(Instant.parse(from))
}
