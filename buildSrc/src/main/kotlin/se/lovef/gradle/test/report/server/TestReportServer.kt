package se.lovef.gradle.test.report.server

import org.java_websocket.WebSocket
import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ClientHandshake
import org.java_websocket.handshake.ServerHandshake
import org.java_websocket.server.WebSocketServer
import java.net.InetSocketAddress
import java.net.URI

private const val host = "localhost"
private const val port = 8090

private const val stop = "STOP"

class TestReportServer : WebSocketServer(InetSocketAddress(host, port)) {

    override fun onOpen(conn: WebSocket?, handshake: ClientHandshake?) {
        println("onOpen: ws://$host:$port")
    }

    override fun onClose(conn: WebSocket?, code: Int, reason: String?, remote: Boolean) {
        println("onClose: ws://$host:$port")
    }

    override fun onMessage(conn: WebSocket?, message: String?) {
        println("onMessage: $message, ws://$host:$port")
        if (message == stop) {
            println("Will stop now")
            this.stop(1000)
        }
    }

    override fun onStart() {
        println("onStart: ws://$host:$port")
    }

    override fun onError(conn: WebSocket?, ex: Exception?) {
        println("onError: ws://$host:$port")
    }

    class Client : WebSocketClient(URI("ws://$host:$port")) {
        override fun onOpen(handshakedata: ServerHandshake?) {
            println("Client - onOpen")
        }

        override fun onClose(code: Int, reason: String?, remote: Boolean) {
            println("Client - onClose")
        }

        override fun onMessage(message: String?) {
            println("Client - onMessage: $message")
        }

        override fun onError(ex: java.lang.Exception?) {
            println("Client - onError")
        }

        fun requestStop() {
            send(stop)
        }
    }
}
