package com.lucianghimpu.mercury.Data.Models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class SubscribeAction(
    @Json(name = "type") val type: String = "subscribe",
    @Json(name = "product_ids") val productIds: List<String>,
    @Json(name = "channels") val channels: List<TickerRequest>
)

data class TickerRequest(
    @Json(name = "name") val name: String = "ticker",
    @Json(name = "product_ids") val productIds: List<String>
)
data class TickerResponse(
    @Json(name = "price") val price: Double
)