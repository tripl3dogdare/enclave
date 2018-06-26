package com.tripl3dogdare.enclave

import com.tripl3dogdare.enclave.network.Gateway
import com.tripl3dogdare.enclave.network.Rest
import com.tripl3dogdare.enclave.event.EventContainer

class Enclave(val token:String, startImmediately:Boolean=true) : EventContainer() {
  val rest = Rest(token)
  private val gateway = Gateway(token, ::handleDispatch)

  fun start():Enclave {
    gateway.login()
    return this
  }

  fun stop():Enclave {
    gateway.disconnect()
    return this
  }

  companion object {
    val WEBSITE = "https://github.com/tripl3dogdare/enclave"
    val VERSION = "0.0.1a"
    val API_URL = "https://discordapp.com/api/v6"
    val AVI_URL = "https://cdn.discordapp.com/"
  }

  init { if(startImmediately) start() }
}
