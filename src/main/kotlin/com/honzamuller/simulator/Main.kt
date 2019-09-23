package com.honzamuller.simulator

@ExperimentalStdlibApi
fun main() {
    val clock = Clock()
    val computer = Computer(clock) {

    }
    clock.registerOnHalt {
        val outputRegister = computer.getComponent(OutputRegister::class)
        outputRegister.printContent()
    }

    computer.registerErrorCallback {
        println(it)
        clock.stop()
    }
    //computer.printMemory()
    computer.run(ProgramExample.MULTIPLY_2)


    clock.tickAutomatically(1)

    //computer.printOutRegister()
    //computer.printMemory()
}