package com.stepanov.bbfexecutor

import org.apache.commons.exec.*
import java.io.File

const val PATH_TO_JAR = "java -jar backend-bugfinder/target/backendBugFinder-1.0-jar-with-dependencies.jar"
const val TIMEOUT_SEC = 1800L //timeout 30 minutes

data class BBFProcess(val cmd: CommandLine, val file: File,
                      val handler: DefaultExecuteResultHandler, val executor: DefaultExecutor){
    fun execute() {
        executor.execute(cmd, handler)
    }
}


fun createBBFProcess(f: File): BBFProcess {
    val cmd = CommandLine.parse("$PATH_TO_JAR ${f.absolutePath}")
    val executor = DefaultExecutor().also {
        it.watchdog = ExecuteWatchdog(TIMEOUT_SEC * 1000)
    }
    val handler = DefaultExecuteResultHandler()
    return BBFProcess(cmd, f, handler, executor)
}


fun main(args: Array<String>) {
    //val dir = File(args[0]).listFiles()
//    val dir = File("tmp/arrays").listFiles()
//    val threads = 1
//    val processes = ArrayList<Pair<BBFProcess, Double>>(threads)
//    repeat(threads){ processes.add(createBBFProcess(dir.random()) to 0.0) }
//    processes.forEach { (pr, _) -> pr.executor.execute(pr.cmd, pr.handler) }
//    val step = 1000
//    while (true) {
//        for ((i, procToTime) in processes.withIndex()) {
//            val (proc, time) = procToTime
//            println("Process $i. Time elapsed: $time Res: ${proc.handler.hasResult()}")
//            if (proc.handler.hasResult()) {
//                processes[i] = createBBFProcess(dir.random()) to 0.0
//                processes[i].first.execute()
//            }
//            processes[i] = proc to time + step
//        }
//        Thread.sleep(1000)
//    }
    var file = File(args[0]).listFiles().random()
    var cmdLine = CommandLine.parse("$PATH_TO_JAR $file")
    var executor = DefaultExecutor().also {
        it.watchdog = ExecuteWatchdog(TIMEOUT_SEC * 1000)
    }
    var handler = DefaultExecuteResultHandler()
    executor.execute(cmdLine, handler)

    var timeElapsed = 0
    while (true) {
        println("Elapsed: $timeElapsed")
        if (handler.hasResult()) {
            file = File(args[0]).listFiles().random()
            cmdLine = CommandLine.parse("$PATH_TO_JAR $file")
            handler = DefaultExecuteResultHandler()
            executor = DefaultExecutor().also {
                it.watchdog = ExecuteWatchdog(TIMEOUT_SEC * 1000)
            }
            executor.execute(cmdLine, handler)
            timeElapsed = 0
        }
        timeElapsed += 1000
        Thread.sleep(1000)
    }
}