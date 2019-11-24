package com.stepanov.bbf

import org.junit.jupiter.api.RepeatedTest
import kotlin.test.assertFalse

class MainTest {
    @RepeatedTest(10000)
    fun testSample() {
        val foundBugs = run(arrayOf("-f ../tmp/arrays"))
        if (foundBugs != null) {
            println("FOUND BUG:\n")
            System.err.println(foundBugs)
        }
        assertFalse(foundBugs != null)
    }
}
