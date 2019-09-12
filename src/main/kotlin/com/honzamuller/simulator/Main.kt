package com.honzamuller.simulator

@ExperimentalStdlibApi
fun main() {
    val clock = Clock(10)

    val computer = Computer(clock) {

    }
    computer.registerErrorCallback {
        println(it)
        clock.stop()
    }
    //computer.printMemory()
    computer.run(ProgramExample.INC_DEC)

    clock.tickAutomatically(10)
    //computer.printOutRegister()
    //computer.printMemory()
}