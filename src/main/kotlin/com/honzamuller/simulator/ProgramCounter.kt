package com.honzamuller.simulator

class ProgramCounter(bus: Bus) : BusComponent(bus) {

    var data: Byte = 0b0000000
    private var increment: Boolean = false

    override fun onControlWord(word: ControlWords): Boolean {
        when (word) {
            ControlWords.CE -> increment = true
            ControlWords.CO -> enableOutput()
            ControlWords.J ->  enableInput()
            else -> return false
        }
        return true
    }

    override fun onTick() {
        if (increment) {
            data = data.inc()
        }
        when (mode) {
            InputOutputMode.INPUT -> data = bus.data
            InputOutputMode.OUTPUT -> bus.data = data
            else -> {}
        }
    }

    override fun onPrepare() {
        increment = false
    }

    override fun onClear() {
        data = 0b00000000
    }

    override fun printContent() {
        println("PC: ${data.format()} | $data")
    }
}