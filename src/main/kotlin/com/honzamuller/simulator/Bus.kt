package com.honzamuller.simulator

class Bus internal constructor(): Component() {
    var data: Int = 0b000_0000

    override fun onClear() {
        data = 0b0000_0000
    }

    override fun onControlWord(word: ControlWords): Boolean {
        return false
    }

    override fun printContent() {
        println("BS: ${data.format()} | $data")
    }
}