package com.stepanov.bbfexecutor

import org.junit.jupiter.api.RepeatedTest
import kotlin.test.assertFalse

class MainTest {
    @RepeatedTest(10)
    fun testSample() {
        val foundBugs = com.stepanov.bbf.run(arrayOf("-f tmp/arrays"))
        if (foundBugs != null) {
            println("FOUND BUG:\n")
            System.err.println(foundBugs)
        }
        assertFalse(foundBugs != null)
    }
}
