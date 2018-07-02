package com.tripl3dogdare.enclave.util

abstract class Respondable {
  private val output = mutableListOf<String>()
  val hasResponse:Boolean get() = output.size > 0

  infix fun plus(v:Any):Respondable {
    output += v.toString()
    return this
  }

  fun collate():String =
    output.reduce { a,b -> "$a\n$b" }
}
