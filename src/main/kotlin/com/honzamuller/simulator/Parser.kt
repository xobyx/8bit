package com.honzamuller.simulator

class Parser(private val memory: Memory) {
    private val code: String = """
    0000:LDA 1000
    0001:ADD 1001
    0010:ADD 1101
    0011:OUT 0000
    0100:HLT 0000
    1000:00000001
    1001:00000101
    1101:00000001
""".trimIndent()

    private val code2: String = """
    0000:LDI 0001
    0001:STA 1000
    0010:LDI 0001
    0011:STA 1001
    0100:LDA 1000
    0101:ADD 1001
    0110:OUT 0000
    0111:JMP 0001
""".trimIndent()

    fun parse() {
        val rows = code2.split('\n')
        rows.forEach { row ->
            val splitRow = row.split(":")
            val address = splitRow[0]
            val dataSegment = splitRow[1]
            var data = ""
            if (dataSegment.contains(" ")) {
                val s = dataSegment.split(" ")
                val instructionSet = InstructionSet.valueOf(s[0])
                data = instructionSet.value.format(4) + s[1]
            } else {
                data = dataSegment
            }
            memory.putData(bitsToByte(address), bitsToByte(data))
        }
    }

    private fun bitsToByte(bits: String): Byte {
        var result = 0b0000_0000
        for (element in bits) {
            val strBit = element
            result = result.shl(1)
            if (strBit == '1') result = result or 0b0000_0001
        }
        return result.toByte()
    }

}