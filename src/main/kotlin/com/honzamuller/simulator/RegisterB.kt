package com.honzamuller.simulator

class RegisterB internal constructor(bus: Bus) : Register(bus) {

    override fun onControlWord(word: ControlWords): Boolean {
        when (word) {
            ControlWords.BI -> enableInput()
            else -> return false
        }
        return true
    }

    override fun printContent() {
        println("RB: ${data.format()} | $data")
    }
}