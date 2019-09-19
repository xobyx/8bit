package com.honzamuller.simulator

class MemoryAddressRegister internal constructor(bus: Bus) : Register(bus) {

    override fun onControlWord(word: ControlWords): Boolean {
        when (word) {
            ControlWords.MI -> enableInput()
            else -> return false
        }
        return true
    }

    override fun printContent() {
        println("MR: ${data.format()} | $data")
    }
}