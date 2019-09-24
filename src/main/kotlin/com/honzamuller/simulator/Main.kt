package com.honzamuller.simulator

fun main() {
    val clock = Clock()
    val computer = Computer(clock) {

    }
    clock.registerOnHalt {
        computer.getComponent(Memory::class).printContent()
    }

    computer.registerErrorCallback {
        println(it)
        clock.stop()
    }
    computer.run(ProgramExample.MULTIPLY_2)

    clock.tickAutomatically(1) { tick ->
        printRegisters(computer, tick)
    }
}

private fun printRegisters(computer: Computer, cpuCycle: Int) {
    val registerA = computer.getComponent(RegisterA::class)
    val registerB = computer.getComponent(RegisterB::class)
    val memoryAddressRegister = computer.getComponent(MemoryAddressRegister::class)
    val instructionRegister = computer.getComponent(InstructionRegister::class)
    val outputRegister = computer.getComponent(OutputRegister::class)
    val programCounter = computer.getComponent(ProgramCounter::class)
    print("CPU cycle: $cpuCycle \t| ")
    print("RA: ${registerA.data.formatFancy()} | ")
    print("RB: ${registerB.data.formatFancy()} | ")
    print("IR: ${instructionRegister.data.formatFancy()} | ")
    print("MR: ${memoryAddressRegister.data.formatFancy()} | ")
    print("PC: ${programCounter.data.formatFancy()} | ")
    print("OR: ${outputRegister.data.formatFancy()} | ")
    print("OR (dec): ${outputRegister.data} | ")
    print("\r")
}