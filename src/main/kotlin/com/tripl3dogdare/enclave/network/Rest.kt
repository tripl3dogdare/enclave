package com.tripl3dogdare.enclave.network

import com.tripl3dogdare.enclave.Enclave

import com.fasterxml.jackson.databind.JsonNode
import org.http4k.client.JavaHttpClient
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.format.Jackson.asJsonObject

class Rest(val token:String, val httpClient:HttpHandler=JavaHttpClient()) {
  fun send(
    method:Method,
    path:String,
    query:Map<String, String>? = null,
    data:JsonNode? = null
  ):JsonNode {
    val req = Request(method, Enclave.API_URL+path)
      .header("User-Agent", "DiscordBot (${Enclave.WEBSITE}, ${Enclave.VERSION})")
      .header("Authorization", "Bot $token")
      .header("Content-Type", "application/json")
      .let { req ->
        (query ?: emptyMap()).entries
          .fold(req) { acc, entry -> acc.query(entry.key, entry.value) }
          .body(data?.toString() ?: "")
      }
    return httpClient(req)
      .bodyString().asJsonObject()
  }
}
