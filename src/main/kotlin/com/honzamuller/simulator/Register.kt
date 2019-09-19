package com.honzamuller.simulator

abstract class Register internal constructor(bus: Bus) : BusComponent(bus) {
    var data: Int = 0b0000000

    override fun onClear() {
        data = 0b0000000
    }

    override fun onTick() {
        when (mode) {
            InputOutputMode.INPUT -> data = bus.data
            InputOutputMode.OUTPUT -> bus.data = data
            else -> {}
        }
    }
}