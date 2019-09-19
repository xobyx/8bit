package com.honzamuller.simulator

abstract class Component {

    internal open fun controlWord(word: ControlWords) {
        onControlWord(word)
    }

    internal abstract fun onClear()

    /**
     * return true if word was consumed
     */
    protected abstract fun onControlWord(word: ControlWords): Boolean

    internal abstract fun printContent()
}