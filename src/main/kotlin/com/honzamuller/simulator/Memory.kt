package com.honzamuller.simulator

class Memory(bus: Bus, private val memoryAddressRegister: MemoryAddressRegister) : BusComponent(bus) {

    var memory = byteArrayOf(
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

    fun putData(address: Byte, data: Byte) {
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
            InputOutputMode.INPUT -> memory[memoryAddressRegister.data.toInt()] = bus.data
            InputOutputMode.OUTPUT -> bus.data = memory[memoryAddressRegister.data.toInt()]
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
            println("${index.toByte().format(4)}:${value.format()}")
        }
        println("--------------")
        println()
    }
}