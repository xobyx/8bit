package com.honzamuller.simulator

@ExperimentalStdlibApi
fun main() {
    val clock = Clock()

    val computer = Computer(clock) {

    }
    computer.registerErrorCallback {
        println(it)
        clock.stop()
    }
    //computer.printMemory()
    computer.run(ProgramExample.MULTIPLY)

    clock.tickAutomatically(10)
    //computer.printOutRegister()
    //computer.printMemory()
}