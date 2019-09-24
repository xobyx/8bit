package com.honzamuller.simulator

internal class ControlLogic(private val components: List<Component>, private val flagsRegister: FlagsRegister, private val onCw: ((List<ControlWords>) -> Unit)? = null) {
    private var instruction: InstructionSet? = null
    private val instructionRegister: InstructionRegister =
        components.first { it is InstructionRegister } as InstructionRegister

    fun run(iteration: Int) {
        val internalCycle = iteration % 6
        process(internalCycle)
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

    private fun tick() {
        for (bc in components.filterIsInstance<BusComponent>().sortedBy { it.mode.ordinal }) {
            (bc as BusComponent).tick()
        }
    }

    private fun sendControlWords(vararg controlWords: ControlWords) {
        onCw?.invoke(controlWords.toList())
        for (bc in components) {
            controlWords.forEach { word ->
                bc.controlWord(word)
            }
        }
    }
}