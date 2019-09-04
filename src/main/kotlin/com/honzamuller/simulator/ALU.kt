package com.honzamuller.simulator

class ALU(bus: Bus, private val registerA: RegisterA, private val registerB: RegisterB) : BusComponent(bus) {

    private var data: Byte = 0b00000000

    private var minusOperator = false

    override fun onControlWord(word: ControlWords): Boolean {
        when (word) {
            ControlWords.EO -> enableOutput()
            ControlWords.SU -> minusOperator = true
            ControlWords.FI -> TODO()
            else -> return false
        }
        return true
    }

    override fun onTick() {
        data = if (minusOperator) {
            registerA.data.minus(registerB.data).toByte()
        } else {
            registerA.data.plus(registerB.data).toByte()
        }
        when (mode) {
            InputOutputMode.OUTPUT -> bus.data = data
            else -> {}
        }
    }

    override fun onPrepare() {
        minusOperator = false
    }

    override fun onClear() {
        data = 0b00000000
    }

    override fun printContent() {
        println("AL: ${data.format()} | $data")
    }

    fun minus() {
        minusOperator = true
    }
}