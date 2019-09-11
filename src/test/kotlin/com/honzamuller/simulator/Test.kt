package com.honzamuller.simulator

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class Test {

    private enum class Program(val code: String) {
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
        val clock = Clock(10)
        val computer = Computer(clock)
        clock.registerOnHalt {
            Assert.assertEquals(2, computer.getComponent(OutputRegister::class).data)
        }
        computer.registerErrorCallback {
            println(it)
            clock.stop()
        }
        computer.run(Program.ADD.code.trimIndent())
        clock.tickAutomatically(1)
    }

    @Test
    fun testSub() {
        val clock = Clock(10)
        val computer = Computer(clock)
        clock.registerOnHalt {
            Assert.assertEquals(0, computer.getComponent(OutputRegister::class).data)
        }
        computer.registerErrorCallback {
            println(it)
            clock.stop()
        }
        computer.run(Program.SUB.code.trimIndent())
        clock.tickAutomatically(1)
    }
}