@file:Suppress("SameParameterValue")

package org.okt.ilivechat.chat

import com.fpnn.rtm.RTMClientConnectCallback
import com.fpnn.rtm.RTMServerClient
import com.fpnn.sdk.ErrorCode
import java.net.InetSocketAddress

class ChatService {
    private var userId = 0L
    private var roomId = 0L
    private val rtmClient: RTMServerClient by lazy {
        getRTMServerClient(
            80000145,
            "1e6adcb9-646b-4026-9d8b-b81b77608cf5",
            "rtm-intl-backgate.ilivedata.com:13315",
        )
    }
    fun handleMsg(msg: String) {

        if(msg.startsWith(":login-")){
            val userId = msg.substringAfter(":login-").toLong()
            if (userId > 0) {
                this.userId = userId
                println("用户[$userId]登录成功")
            } else {
                println("用户ID必须大于0")
            }
            return
        }

        if(msg.startsWith(":room-")) {
            val roomId = msg.substringAfter(":room-").toLong()
            if (roomId > 0) {
                this.roomId = roomId
                println("用户[$userId]加入房间[$roomId]成功")
            } else {
                println("房间ID必须大于0")
            }
            return
        }
        sendMsg(msg)
    }

    private fun sendMsg(msg: String) {
        if (!isInLoginStatus()) {
            return
        }
        //println("用户[$userId]: $msg")
        rtmClient.sendRoomChat(this.userId, this.roomId, msg, "") { _, errorCode, errorMessage ->
            if (errorCode == ErrorCode.FPNN_EC_OK.value()) {
                //logger.info("(room-msg) sender=$fromUid,room=$roomId,answer time = $time1")
                retrieveLatestMessageHistory()
            } else {
                println("(room-msg) sender=${userId},room=$roomId,err-code=$errorCode,err-msg=$errorMessage")
            }
        }
    }

    private fun isInLoginStatus(): Boolean {
        if (userId == 0L) {
            println("请先登录> 输入:login-用户ID整数")
            return false
        }
        if (roomId == 0L) {
            println("请先加入房间> 输入:room-房间ID整数")
            return false
        }
        return true
    }

    private fun getRTMServerClient(pid: Int, secretKey: String, endpoint: String): RTMServerClient {
        val client: RTMServerClient = RTMServerClient.create(pid, secretKey, endpoint)
        val connectCallback =
            RTMClientConnectCallback { peerAddress: InetSocketAddress, connected: Boolean, reConnect: Boolean, regressiveState: RTMServerClient.RegressiveState? ->
                if (connected) {
                    println("rtm client connected $peerAddress")
                } else if (regressiveState != null) {
                    val info =
                        "RTMReconnect time at " + regressiveState.connectSuccessMilliseconds.toString() + " ,currentFailedCount = " + regressiveState.currentFailedCount
                    println("rtm client not connected $peerAddress ,can reconnet: $reConnect ,reconnect infos: $info")
                }
            }
        client.setRTMClientConnectedCallback(connectCallback)
        return client
    }

    private fun retrieveLatestMessageHistory() {
        if (!isInLoginStatus()) {
            return
        }
        rtmClient.getRoomChat(this.userId, this.roomId, true, 10) { result,code,msg ->
            if (code != ErrorCode.FPNN_EC_OK.value()) {
                println("Fail to retrieve latest message history, error code: $code, error message: $msg")
            } else {
                println("History message: ")
                println(result.toString())
            }
        }
    }
}