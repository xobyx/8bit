package com.honzamuller.simulator

abstract class Component {

    open fun controlWord(word: ControlWords) {
        onControlWord(word)
    }

    abstract fun onClear()

    /**
     * return true if word was consumed
     */
    protected abstract fun onControlWord(word: ControlWords): Boolean

    abstract fun printContent()
}