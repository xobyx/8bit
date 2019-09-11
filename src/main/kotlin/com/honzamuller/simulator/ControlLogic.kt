package com.honzamuller.simulator

class ControlLogic(private val components: List<Component>, private val flagsRegister: FlagsRegister, private val onCw: ((List<ControlWords>) -> Unit)? = null) {
    private var instruction: InstructionSet? = null
    private val instructionRegister: InstructionRegister =
        components.first { it is InstructionRegister } as InstructionRegister

    fun run(iteration: Int) {
        val internalCycle = iteration % 6
        process(internalCycle)
        printRegisters(iteration)
    }

    private fun process(step: Int) {
        when (step) {
            0 -> {
                instruction = null
                sendControlWords(ControlWords.CO, ControlWords.MI)
            }
            1 -> sendControlWords(ControlWords.RO, ControlWords.II, ControlWords.CE)
            in 2..5 -> {
                if (instruction == null) {
                    instruction = getInstruction(((instructionRegister.data.toInt() shr 4) and 0b0000_1111).toByte())
                }
                val microInstructions = instruction!!.microInstructions
                val internalStep = step - 2
                if (internalStep < microInstructions.size) {

                    if (instruction == InstructionSet.JZ && !flagsRegister.flagZero) {
                        sendControlWords(ControlWords.NONE)
                    } else if (instruction == InstructionSet.JC && !flagsRegister.flagCarry) {
                        sendControlWords(ControlWords.NONE)
                    } else {
                        val mi = microInstructions[internalStep]
                        sendControlWords(*mi.controlWords)
                    }
                } else {
                    sendControlWords(ControlWords.NONE)
                }
            }
        }
        tick()
    }

    private fun getInstruction(instructionCode: Byte): InstructionSet {
        InstructionSet.values().forEach {
            if (it.opCode == instructionCode) {
                return it
            }
        }
        return InstructionSet.NOP
    }

    private fun print() {
        components.forEach { bc ->
            if (bc !is Memory) {
                bc.printContent()
            }
        }
        println()
    }

    private fun tick() {
        for (bc in components.filterIsInstance<BusComponent>().sortedBy { it.mode.ordinal }) {
            (bc as BusComponent).tick()
        }
    }

    private fun sendControlWords(vararg controlWords: ControlWords) {
        onCw?.invoke(controlWords.toList())
        //println(controlWords.toList().toString())
        for (bc in components) {
            controlWords.forEach { word ->
                bc.controlWord(word)
            }
        }
    }

    private fun printRegisters(cpuCycle: Int) {
        val registerA = components.first { it is RegisterA } as RegisterA
        val registerB = components.first { it is RegisterB } as RegisterB
        val memoryAddressRegister = components.first { it is MemoryAddressRegister } as MemoryAddressRegister
        val instructionRegister = components.first { it is InstructionRegister } as InstructionRegister
        val outputRegister = components.first { it is OutputRegister } as OutputRegister
        val programCounter = components.first { it is ProgramCounter } as ProgramCounter
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
}