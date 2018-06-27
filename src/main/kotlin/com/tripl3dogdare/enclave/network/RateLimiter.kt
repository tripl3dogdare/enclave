package com.tripl3dogdare.enclave.network

import com.tripl3dogdare.enclave.util.TimerTask
import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import java.time.Instant
import java.util.*
import org.http4k.format.Jackson.asJsonObject

class RateLimiter(val httpClient:HttpHandler) {
  val global_bucket = Bucket()
  val buckets = mutableMapOf<String, Bucket>()

  fun isLimited(path:String):Boolean =
    global_bucket.remaining < 1 || (buckets[path]?.remaining ?: 1) < 1

  fun limitFrom(path:String, resp:Response):Response {
    if(resp.header("X-RateLimit-Global")?.toBoolean() ?: false) global_bucket.limitFrom(resp)
    else buckets.getOrPut(path) { Bucket() }.limitFrom(resp)
    return resp
  }

  tailrec fun queueRequest(path:String, req: Request):Response {
    return if(isLimited(path)) {
      val timeLeft = (buckets[path]?.resetAt ?: 0) - Instant.now().epochSecond
      println("Request to $path is ratelimited for $timeLeft more seconds! Trying again then.")

      if(timeLeft > 0) Thread.sleep(timeLeft * 1000)
      queueRequest(path, req)
    } else {
      var resp = limitFrom(path, httpClient(req))
      while(resp.status.code == 429) {
        Thread.sleep(resp.bodyString().asJsonObject()["retry_after"].longValue())
        resp = limitFrom(path, httpClient(req))
      }
      resp
    }
  }

  companion object {
    class Bucket {
      var limit:Int = 0
      var remaining:Int = 1
      var resetAt:Long = 0
      val timer = Timer()
      var resetTask:TimerTask? = null

      fun limitFrom(resp:Response) {
        limit = resp.header("X-RateLimit-Limit")?.toInt() ?: 0
        remaining = resp.header("X-RateLimit-Remaining")?.toInt() ?: 1
        resetAt = resp.header("X-RateLimit-Reset")?.toLong() ?: 0

        resetTask?.cancel()
        resetTask = TimerTask { limit = 0; remaining = 1; resetAt = 0 }
        timer.schedule(resetTask, Date.from(Instant.ofEpochSecond(resetAt)))
      }
    }
  }
}
