package com.honzamuller.simulator

class Clock(private val freq: Long) : Component() {

    private var counter: Int = 0
    private var running: Boolean = true
    private var onTickCallback: ((Int) -> Unit)? = null
    private var onHaltCallback: (() -> Unit)? = null

    fun tick() {
        if (running) {
            onTickCallback?.invoke(counter++)
        }
    }

    fun tickAutomatically(freq: Int) {
        running = true
        while (running) {
            tick()
            //readLine()
            Thread.sleep(freq.toLong())
        }
    }

    fun registerOnTick(onTick: (Int) -> Unit) {
        onTickCallback = onTick
    }

    fun registerOnHalt(onHalt: () -> Unit) {
        onHaltCallback = onHalt
    }

    override fun onClear() {
        counter = 0
        running = true
    }

    fun stop() {
        running = false
        onHaltCallback?.invoke()
    }

    override fun onControlWord(word: ControlWords): Boolean {
        when (word) {
            ControlWords.HLT -> {
                running = false
                onHaltCallback?.invoke()
            }
            else -> return false
        }
        return true
    }

    override fun printContent() {
        println("Clk tick $counter")
    }
}