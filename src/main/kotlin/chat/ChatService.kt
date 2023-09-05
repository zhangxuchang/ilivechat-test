package org.okt.ilivechat.chat
class ChatService {
    private var userId = 0
    private var roomId = 0
    fun handleMsg(msg: String) {

        if(msg.startsWith(":login-")){
            val userId = msg.substringAfter(":login-").toInt()
            if (userId > 0) {
                this.userId = userId
                println("用户[$userId]登录成功")
            } else {
                println("用户ID必须大于0")
            }
            return
        }

        if(msg.startsWith(":room-")) {
            val roomId = msg.substringAfter(":room-").toInt()
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
        println("用户[$userId]: $msg")
    }

    private fun isInLoginStatus(): Boolean {
        if (userId == 0) {
            println("请先登录> 输入:login-用户ID整数")
            return false
        }
        if (roomId == 0) {
            println("请先加入房间> 输入:room-房间ID整数")
            return false
        }
        return true
    }
}