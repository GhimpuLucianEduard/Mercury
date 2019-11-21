package com.lucianghimpu.mercury.Data.Network

import com.lucianghimpu.mercury.Data.Models.SubscribeAction
import com.lucianghimpu.mercury.Data.Models.TickerResponse
import com.tinder.scarlet.StateTransition
import com.tinder.scarlet.websocket.WebSocketEvent
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable
import okhttp3.WebSocket

interface WebSocketService {

    @Send
    fun subscribe(action: SubscribeAction)

    @Receive
    fun observeTicker(): Flowable<TickerResponse>

    @Receive
    fun observeWebSocketEvent(): Flowable<WebSocketEvent>

}