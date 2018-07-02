package com.tripl3dogdare.enclave.network

import com.tripl3dogdare.enclave.Enclave

import com.fasterxml.jackson.databind.JsonNode
import com.tripl3dogdare.enclave.util.Snowflake
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.runBlocking
import org.http4k.client.JavaHttpClient
import org.http4k.core.HttpHandler
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.format.Jackson.asJsonObject

class Rest(val token:String, httpClient:HttpHandler=JavaHttpClient()) {
  val rateLimiter = RateLimiter(httpClient)

  fun send(
    method:Method,
    path:String,
    vararg urlParams: Snowflake,
    query:Map<String, String>? = null,
    data:JsonNode? = null
  ) = async {
    val req = Request(method, Enclave.API_URL+path.format(*urlParams))
      .header("User-Agent", "DiscordBot (${Enclave.WEBSITE}, ${Enclave.VERSION})")
      .header("Authorization", "Bot $token")
      .header("Content-Type", "application/json")
      .let { req ->
        (query ?: emptyMap()).entries
          .fold(req) { acc, entry -> acc.query(entry.key, entry.value) }
          .body(data?.toString() ?: "")
      }
    rateLimiter
      .queueRequest(path.format(urlParams.getOrElse(0) {""}), req)
      .bodyString().asJsonObject()
  }

  fun sendSync(
    method:Method,
    path:String,
    vararg urlParams: Snowflake,
    query:Map<String, String>? = null,
    data:JsonNode? = null
  ) =
    runBlocking { send(method, path, *urlParams, query=query, data=data).await() }
}
