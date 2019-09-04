package com.honzamuller.simulator

class RegisterA(bus: Bus) : Register(bus) {

    override fun onControlWord(word: ControlWords): Boolean {
        when (word) {
            ControlWords.AO -> enableOutput()
            ControlWords.AI -> enableInput()
            else -> return false
        }
        return true
    }

    override fun printContent() {
        println("RA: ${data.format()} | $data")
    }
}