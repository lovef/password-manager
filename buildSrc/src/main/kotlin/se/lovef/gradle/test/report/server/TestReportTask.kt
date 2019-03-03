package se.lovef.gradle.test.report.server

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import java.lang.Exception
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

open class TestReportTask : DefaultTask() {

    private val testReportServer by lazy { TestReportServer() }

    @TaskAction
    @Suppress("unused")
    fun run() {
        println("Hello from task $path!")
        thread {
            sendStopMessage()
            testReportServer.run()
        }
    }

    private fun sendStopMessage() {
        try {
            TestReportServer.Client().also {
                it.connectBlocking(1000, TimeUnit.MILLISECONDS)
                it.requestStop()
                it.close()
                Thread.sleep(1000)
            }
        } catch (e: Exception) {
            println("Couldn't connect: $e")
        }
    }
}
