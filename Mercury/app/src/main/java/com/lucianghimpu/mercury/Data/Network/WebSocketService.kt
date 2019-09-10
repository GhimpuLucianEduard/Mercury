package com.lucianghimpu.mercury.Data.Network

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import io.reactivex.Flowable

interface WebSocketService {

    @Receive
    fun observeWebSocketEvent(): Flowable<WebSocket.Event>

}