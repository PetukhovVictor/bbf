package com.stepanov.bbf

import com.stepanov.bbf.executor.CompilerArgs
import com.stepanov.bbf.executor.MutationChecker
import com.stepanov.bbf.executor.compilers.JSCompiler
import com.stepanov.bbf.executor.compilers.JVMCompiler
import com.stepanov.bbf.mutator.transformations.Transformation
import com.stepanov.bbf.util.BBFProperties
import com.stepanov.bbf.util.FalsePositivesDeleter
import com.stepanov.reduktor.parser.PSICreator
import net.sourceforge.argparse4j.ArgumentParsers
import net.sourceforge.argparse4j.impl.Arguments
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.apache.log4j.PropertyConfigurator
import org.jetbrains.kotlin.psi.KtPsiFactory
import java.io.File
import kotlin.system.exitProcess


//fun main(args: Array<String>) {
//    //Init log4j
//    PropertyConfigurator.configure("backend-bugfinder/src/main/resources/log4j.properties")
//
//    if (!CompilerArgs.getPropAsBoolean("LOG")) {
//        Logger.getRootLogger().level = Level.OFF
//        Logger.getLogger("bugFinderLogger").level = Level.OFF
//        Logger.getLogger("mutatorLogger").level = Level.OFF
//        Logger.getLogger("reducerLogger").level = Level.OFF
//    }
//
//    val parser = ArgumentParsers.newFor("bbf").build()
//    parser.addArgument("-r", "--reduce")
//            .required(false)
//            .help("Reduce mode")
//    parser.addArgument("-f", "--fuzz")
//            .required(false)
//            .help("Fuzzing mode")
//    parser.addArgument("-c", "--clean")
//            .required(false)
//            .action(Arguments.storeTrue())
//            .help("Clean directories with bugs from bugs that are not reproduced")
//    val arguments = parser.parseArgs(args)
//    arguments.getString("reduce")?.let {
//        val back = BBFProperties.getStringGroupWithoutQuotes("BACKEND_FOR_REDUCE").entries.first()
//        val compiler = when (back.key) {
//            "JVM" -> JVMCompiler(back.value)
//            "JS" -> JSCompiler(back.value)
//            else -> throw IllegalArgumentException("Illegal backend")
//        }
//        val tmpPath = CompilerArgs.pathToTmpFile
//        require(!File(it).isDirectory) { "Specify file to reducing" }
//        File(tmpPath).writeText(File(it).readText())
//        val res = Reducer.reduce(tmpPath, compiler)
//        println("Start to reduce $it")
//        for (r in res) {
//            println("Result of reducing $it:\n${r.text}")
//        }
//        exitProcess(0)
//    }
//    arguments.getString("fuzz")?.let {
//        require(File(it).isDirectory) { "Specify directory to take files for mutation" }
//        val file = File(it).listFiles().random()
//        BugFinder(file.absolutePath).findBugsInFile()
//        exitProcess(0)
//    }
//    arguments.getString("clean")?.let {
//        FalsePositivesDeleter().cleanDirs()
//        exitProcess(0)
//    }
//}

fun main(args: Array<String>) {
    //Init log4j
    PropertyConfigurator.configure("backend-bugfinder/src/main/resources/log4j.properties")
    val psiFile = PSICreator("").getPSIForFile("tmp/arrays/1.kt")
    //Init lateinit vars
    Transformation.file = psiFile
    MutationChecker.factory = KtPsiFactory(psiFile.project)
    MutationChecker.compilers = listOf(JVMCompiler())
    MutationChecker.checkCompiling(psiFile)
    exitProcess(0)
}