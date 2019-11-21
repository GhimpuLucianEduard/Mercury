package com.lucianghimpu.mercury.UI.Connection

import android.util.Log
import androidx.lifecycle.ViewModel
import com.lucianghimpu.mercury.Data.Models.SubscribeAction
import com.lucianghimpu.mercury.Data.Models.TickerRequest
import com.lucianghimpu.mercury.Data.Network.WebSocketService
import com.tinder.scarlet.*
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.ShutdownReason
import com.tinder.scarlet.websocket.WebSocketEvent
import com.tinder.scarlet.websocket.okhttp.OkHttpWebSocket
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

class ConnectionViewModel(
    webSocketService: WebSocketService
) : ViewModel() {

    val TAG: String = "bulala"

    init {

        webSocketService.observeWebSocketEvent()
            .subscribe({
                // If connection is open
                if (it is WebSocketEvent.OnConnectionOpened) {
                    // Initialise parameters
                    val productIds = listOf("ETH-BTC")
                    val tickerRequests = listOf(TickerRequest(productIds = productIds))
                    val bitcoinSubscribeAction = SubscribeAction(productIds = productIds, channels = tickerRequests)

                    // Subscribe to Bitcoin ticker
                    webSocketService.subscribe(bitcoinSubscribeAction)
                }
            }, { error ->
                Log.e(TAG, "Error while observing socket ${error.cause}")
            })

        webSocketService.observeTicker()
            .subscribe({
                Log.d(TAG, "New Bitcoin price: ${it.price}")
            }, { error ->
                Log.e(TAG, "Error while observing ticker ${error.cause}")
            })

    }

}
