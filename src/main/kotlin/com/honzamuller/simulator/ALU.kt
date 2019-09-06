package com.honzamuller.simulator

class ALU(
    bus: Bus,
    private val registerA: RegisterA,
    private val registerB: RegisterB,
    private val flagsRegister: FlagsRegister
) : BusComponent(bus) {

    private var data: Int = 0b00000000

    private var minusOperator = false
    private var enableFlags = false

    override fun onControlWord(word: ControlWords): Boolean {
        when (word) {
            ControlWords.EO -> enableOutput()
            ControlWords.SU -> minusOperator = true
            ControlWords.FI -> enableFlags = true
            else -> return false
        }
        return true
    }

    override fun onTick() {
        data = if (minusOperator) {
            registerA.data.minus(registerB.data)
        } else {
            registerA.data.plus(registerB.data)
        }
        if (enableFlags) {
            flagsRegister.flagZero = data == 0
            flagsRegister.flagCarry = data > 255
        }
        when (mode) {
            InputOutputMode.OUTPUT -> bus.data = data
            else -> {
            }
        }
    }

    override fun onPrepare() {
        minusOperator = false
        enableFlags = false
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