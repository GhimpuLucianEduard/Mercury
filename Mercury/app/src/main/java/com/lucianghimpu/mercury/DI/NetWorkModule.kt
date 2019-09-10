package com.lucianghimpu.mercury.DI

import com.lucianghimpu.mercury.Data.Network.WebSocketService
import com.lucianghimpu.mercury.Utilities.NetworkConstants
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.ShutdownReason
import com.tinder.scarlet.websocket.okhttp.OkHttpWebSocket
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module

val networkModule = module {
    single<OkHttpClient>() { provideOkHttpClient() }
    single<WebSocketService>() { provideWebSocketService(get()) }
}

fun provideOkHttpClient(): OkHttpClient {

    // setup request interceptor
    val requestInterceptor = Interceptor { chain ->

        val request = chain.request()
            .newBuilder()
            .build()

        return@Interceptor chain.proceed(request)
    }

    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC

    return OkHttpClient.Builder()
        .addInterceptor(requestInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()
}

fun provideWebSocketService(client: OkHttpClient) : WebSocketService {

    val protocol = OkHttpWebSocket(
        client,
        OkHttpWebSocket.SimpleRequestFactory(
            { Request.Builder().url(NetworkConstants.defaultWsServer).build() },
            { ShutdownReason.GRACEFUL }
        )
    )

    val configuration = Scarlet.Configuration(
        streamAdapterFactories = listOf(RxJava2StreamAdapterFactory())
    )

    val scarletInstance = Scarlet(protocol, configuration)
    return scarletInstance.create()
}