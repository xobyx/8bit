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
    computer.run(Program.MULTIPLY)

    clock.tickAutomatically(10)
    //computer.printOutRegister()
    //computer.printMemory()
}