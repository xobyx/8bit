package com.honzamuller.simulator

import kotlin.experimental.and
import kotlin.experimental.or
import kotlin.experimental.xor

class InstructionRegister(bus: Bus) : Register(bus) {

    override fun onControlWord(word: ControlWords): Boolean {
        when (word) {
            ControlWords.II -> enableInput()
            ControlWords.IO -> enableOutput()
            else -> return false
        }
        return true
    }

    override fun onTick() {
        when (mode) {
            InputOutputMode.INPUT -> data = bus.data
            InputOutputMode.OUTPUT -> {
                data = data and 0b0000_1111
                bus.data = data
            }
            else -> {}
        }
    }

    override fun printContent() {
        println("IR: ${data.format()} | $data")
    }
}