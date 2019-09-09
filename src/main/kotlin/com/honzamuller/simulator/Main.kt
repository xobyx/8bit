package com.honzamuller.simulator

@ExperimentalStdlibApi
fun main() {
    val clock = Clock(10)

    val computer = Computer(clock)
    //computer.printMemory()
    computer.run(Program.FIBONACCI)

    clock.tickAutomatically(10)
    //computer.printOutRegister()
    //computer.printMemory()
}