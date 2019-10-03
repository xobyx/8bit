package com.honzamuller.simulator

internal class FlagsRegister : Component() {

    var flagZero = false
    var flagCarry = false
    var flagNotCarry = false

    override fun onClear() {
        flagZero = false
        flagCarry = false
        flagNotCarry = false
    }

    override fun onControlWord(word: ControlWords): Boolean {
        return false
    }

    override fun printContent() {
        println("Flag zero:$flagZero, carry:$flagCarry")
    }
}