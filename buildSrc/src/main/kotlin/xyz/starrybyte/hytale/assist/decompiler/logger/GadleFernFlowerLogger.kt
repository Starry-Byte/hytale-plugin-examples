package xyz.starrybyte.hytale.assist.decompiler.logger

import org.jetbrains.java.decompiler.main.extern.IFernflowerLogger

class GradleFernflowerLogger : IFernflowerLogger() {
    private object Ansi {
        const val RESET  = "\u001B[0m"
        const val RED    = "\u001B[31m"
        const val YELLOW = "\u001B[33m"
    }
    override fun writeMessage(message: String, severity: Severity) {
        log(message, severity, null)
    }

    override fun writeMessage(
        message: String?,
        severity: Severity?,
        t: Throwable?
    ) {
        log(message ?: "", severity, t)
    }

    override fun writeMessage(message: String, t: Throwable) {
        log(message, Severity.ERROR, t)
    }

    private fun log(message: String, severity: Severity?, t: Throwable?) {
        when (severity) {
            Severity.ERROR -> {
                System.err.println("${Ansi.RED}VINEFLOWER ERROR: $message${Ansi.RESET}")
                t?.printStackTrace(System.err)
            }

            Severity.WARN -> {
                System.err.println("${Ansi.YELLOW}VINEFLOWER WARN:  $message${Ansi.RESET}")
                t?.printStackTrace(System.err)
            }

            else -> {
                println("VINEFLOWER INFO:  $message")
                t?.printStackTrace()
            }
        }
    }
}
