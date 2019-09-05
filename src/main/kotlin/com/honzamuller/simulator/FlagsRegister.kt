package com.honzamuller.simulator

class FlagsRegister(bus: Bus, alu: ALU) : Register(bus) {

    override fun onControlWord(word: ControlWords): Boolean {
        when (word) {
            ControlWords.AO -> enableOutput()
            ControlWords.AI -> enableInput()
            else -> return false
        }
        return true
    }

    override fun printContent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}