package com.honzamuller.simulator

class Clock : Component() {

    private var counter: Int = 0
    private var running: Boolean = false

    fun start(onTick: (Int) -> (Unit)) {
        running = true
        while (running) {
            onTick.invoke(counter ++)
            Thread.sleep(100)
        }
    }

    override fun onClear() {
        counter = 0
    }

    override fun onControlWord(word: ControlWords): Boolean {
        when (word) {
            ControlWords.HLT -> running = false
            else -> return false
        }
        return true
    }

    override fun printContent() {
        println("Clk tick $counter")
    }
}