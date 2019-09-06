package com.honzamuller.simulator

class Memory(bus: Bus, private val memoryAddressRegister: MemoryAddressRegister) : BusComponent(bus) {

    var memory = intArrayOf(
        0b0000_0000,
        0b0000_0001,
        0b0000_0010,
        0b0000_0011,
        0b0000_0101,
        0b0000_0111,
        0b0000_1001,
        0b0000_1011,
        0b0000_1111,
        0b0000_0010,
        0b0000_0010,
        0b0000_0010,
        0b0000_0010,
        0b0000_0010,
        0b0000_0010,
        0b0000_0010
    )

    fun putData(address: Byte, data: Int) {
        memory[address.toInt()] = data
    }

    override fun onControlWord(word: ControlWords): Boolean {
        when (word) {
            ControlWords.RI -> enableInput()
            ControlWords.RO -> enableOutput()
            else -> return false
        }
        return true
    }

    override fun onTick() {
        when (mode) {
            InputOutputMode.INPUT -> memory[memoryAddressRegister.data] = bus.data
            InputOutputMode.OUTPUT -> {
                try {
                    bus.data = memory[memoryAddressRegister.data]
                } catch (e: MemoryException) {
                    println("Memory overflow")
                }
            }
            else -> {
            }
        }
    }

    override fun onClear() {
        for ((index, _) in memory.withIndex()) {
            memory[index] = 0b0000_0000
        }
    }

    override fun printContent() {
        println("Memory content")
        println("--------------")
        for ((index, value) in memory.withIndex()) {
            println("${index.toByte().formatFancy(4)}:${value.formatFancy()}")
        }
        println("--------------")
        println()
    }
}

class MemoryException : ArrayIndexOutOfBoundsException() {

}