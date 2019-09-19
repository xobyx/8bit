package com.honzamuller.simulator

class OutputRegister internal constructor(bus: Bus) : Register(bus) {

    override fun onControlWord(word: ControlWords): Boolean {
        when (word) {
            ControlWords.OI -> enableInput()
            else -> return false
        }
        return true
    }

    override fun printContent() {
        println("OU: ${data.format()} | $data")
    }
}