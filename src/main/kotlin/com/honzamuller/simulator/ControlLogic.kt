package com.honzamuller.simulator

import kotlin.experimental.and

class ControlLogic(private val components: List<Component>) {

    fun a() {

        val instructionRegister = components.first { it is InstructionRegister } as InstructionRegister

        sendControlWords(ControlWords.CO, ControlWords.MI)
        tick()
        sendControlWords(ControlWords.RO, ControlWords.II)
        tick()
        sendControlWords(ControlWords.CE)
        tick()

        val instructionCode = ((instructionRegister.data.toInt() shr 4) and 0b0000_1111).toByte()
        InstructionSet.values().forEach {
            if (it.value == instructionCode) {
                it.microInstructions.forEach { mi ->
                    sendControlWords(*mi.controlWords)
                    tick()
                }
                println(it.name)
            }
        }

        print()
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
        for (bc in components.filter { it is BusComponent }.sortedBy { (it as BusComponent).mode.ordinal }) {
            (bc as BusComponent).tick()
        }
    }

    private fun sendControlWords(vararg controlWords: ControlWords) {
        println(controlWords.toList().toString())
        for (bc in components) {
            controlWords.forEach { word ->
                bc.controlWord(word)
            }
        }
    }
}