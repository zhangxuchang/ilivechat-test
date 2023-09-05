package org.okt.ilivechat

import org.okt.ilivechat.chat.ChatService

fun main(args: Array<String>) {
    val chatService = ChatService()
    println("==============================================================")
    println("========= Ilive chat 测试程序, 输入:help显示使用帮助 ========= ")
    println("==============================================================")
    while (true) {
        print(">")
        when (val msg = readln()) {
            ":help" -> printHelp()
            ":bye" -> {
                println("再见！")
                break
            }
            else -> chatService.handleMsg(msg)
        }
    }
    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program Finished ${args.joinToString()}")
}

private fun printHelp() {
    println("--------命令说明---------")
    println(":help 帮助")
    println(":bye 退出")
    println(":login-用户ID整数 登录")
    println(":room-房间ID整数 加入房间")
    println("登陆后自由输入是聊天模式")
    println("--------End---------")
}