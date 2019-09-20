package com.honzamuller.simulator

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class Test {

    private enum class Program(val code: String) {
        SHORTENED_CODE(
            """
    LDI 0001
    ADD 1111
    OUT
    HLT
    1111:00000011
        """
        ),
        ADD(
            """
    0001:LDI 0001
    0010:ADD 1111
    0011:OUT
    0100:HLT
    1111:00000001
        """
        ),
        SUB(
            """
    0001:LDI 0001
    0010:SUB 1111
    0011:OUT
    0100:HLT
    1111:00000001
        """
        );
    }

    @Test
    fun testAdd() {
        runProgram(Program.ADD, 2)
    }

    @Test
    fun testSub() {
        runProgram(Program.SUB, 0)
    }

    @Test
    fun shortenedCode() {
        runProgram(Program.SHORTENED_CODE, 4)
    }

    private fun runProgram(program: Program, result: Int) {
        val clock = Clock()
        val computer = Computer(clock)
        clock.registerOnHalt {
            Assert.assertEquals(result, computer.getComponent(OutputRegister::class).data)
        }
        computer.registerErrorCallback {
            println(it)
            clock.stop()
        }
        computer.run(program.code.trimIndent())
        clock.tickAutomatically(1)
    }
}