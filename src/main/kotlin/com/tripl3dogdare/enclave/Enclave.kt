package com.tripl3dogdare.enclave

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.tripl3dogdare.enclave.network.Gateway
import com.tripl3dogdare.enclave.network.Rest
import com.tripl3dogdare.enclave.event.EventContainer
import kotlin.reflect.KClass

class Enclave(val token:String, startImmediately:Boolean=true) : EventContainer() {
  val rest = Rest(token)
  private val gateway = Gateway(token, ::handleDispatch, this)

  fun start():Enclave {
    gateway.login()
    return this
  }

  fun stop():Enclave {
    gateway.disconnect()
    return this
  }

  override fun include(other:EventContainer):Enclave {
    super.include(other)
    return this
  }

  companion object {
    val WEBSITE = "https://github.com/tripl3dogdare/enclave"
    val VERSION = "0.0.1a"
    val API_URL = "https://discordapp.com/api/v6"
    val AVI_URL = "https://cdn.discordapp.com/"

    val jsonMapper = jacksonObjectMapper().apply {
      propertyNamingStrategy = PropertyNamingStrategy.SNAKE_CASE
      enable(SerializationFeature.WRITE_ENUMS_USING_INDEX)
    }
    fun <T: Any> mapJson(data:JsonNode, clazz:KClass<T>):T =
      jsonMapper.treeToValue<T>(data, clazz.java)
    fun JsonNode.with(name:String, value:Any):JsonNode =
      (this.deepCopy() as ObjectNode).putPOJO(name, value)
  }

  init { if(startImmediately) start() }
}
