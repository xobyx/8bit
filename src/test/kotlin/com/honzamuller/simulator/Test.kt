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
        ),
        WITH_COMMENTS(
            """
    ; This is row comment
    0001:LDI 0001
    0010:SUB 1111 ;This is inline comment
    0011:OUT
    0100:HLT
    1111:00000001
            """
        ),
        MULTIPLY_7X9(
            """
    LDA 1100
    ADD 1111
    STA 1100
    LDA 1110
    SUB 1101
    JZ 1000
    STA 1110
    JMP 0000
    LDA 1100
    OUT
    HLT
    1100:00000000
    1101:00000001
    1110:00000111
    1111:00001001
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

    @Test
    fun commentedCode() {
        runProgram(Program.WITH_COMMENTS, 0)
    }

    @Test
    fun multiply() {
        runProgram(Program.MULTIPLY_7X9, 63)
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