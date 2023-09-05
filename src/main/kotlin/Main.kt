import java.util.Scanner
fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    while (true) {
        println("请输入您的命令：")
        when (scanner.nextLine()) {
            "hello" -> println("您好！")
            "bye" -> {
                println("再见！")
                break
            }
            else -> println("无法识别的命令，请重新输入。")
        }
    }

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program finished arguments: ${args.joinToString()}")
}