package com.honzamuller.simulator

class FlagsRegister : Component() {

    var flagZero = false
    var flagCarry = false

    override fun onClear() {
        flagZero = false
        flagCarry = false
    }

    override fun onControlWord(word: ControlWords): Boolean {
        return false
    }

    override fun printContent() {
        println("Flag zero:$flagZero, carry:$flagCarry")
    }
}