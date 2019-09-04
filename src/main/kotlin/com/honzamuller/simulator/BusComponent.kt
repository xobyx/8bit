package com.honzamuller.simulator

abstract class BusComponent(val bus: Bus) : Component() {

    var mode: InputOutputMode = InputOutputMode.NONE

    /**
     * On Clock pulse
     */
    fun tick() {
        onTick()
        mode = InputOutputMode.NONE
    }

    /**
     * CLR
     */
    fun clear() {
        onClear()
    }

    protected fun enableInput() {
        mode = InputOutputMode.INPUT
    }

    protected fun enableOutput() {
        mode = InputOutputMode.OUTPUT
    }

    final override fun controlWord(word: ControlWords) {
        onPrepare()
        super.controlWord(word)
    }

    protected open fun onPrepare() {}
    protected abstract fun onTick()
}

enum class InputOutputMode {
    NONE,
    OUTPUT,
    INPUT
}