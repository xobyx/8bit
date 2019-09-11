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
    computer.run(Program.INC_DEC)

    clock.tickAutomatically(1)
    //computer.printOutRegister()
    //computer.printMemory()
}