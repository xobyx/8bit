package com.honzamuller.simulator

class Parser(private val memory: Memory) {
    private val code: String = """
    0000:LDA 1000
    0001:ADD 1001
    0010:OUT
    0011:ADD 1101
    0100:OUT
    0101:HLT
    1000:00000001
    1001:00000001
    1101:00001111
""".trimIndent()

    private val code2: String = """
    0000:LDI 0001
    0001:STA 1000
    0010:LDI 0001
    0011:STA 1001
    0100:LDA 1000
    0101:ADD 1001
    0110:OUT
    0111:JMP 0001
""".trimIndent()

    private val code3: String = """
    0000:LDI 0001
    0001:STA 1110
    0010:LDI 0000
    0011:OUT
    0100:ADD 1110
    0101:STA 1111
    0110:LDA 1110
    0111:STA 1101
    1000:LDA 1111
    1001:STA 1110
    1010:LDA 1101
    1011:JC 0000
    1100:JMP 0011
""".trimIndent()

    private val code4: String = """
    0000:LDA 1111
    0001:OUT
    0010:ADD 1110
    0011:JC 0000
    0100:OUT
    0101:HLT
    1110:00000010
    1111:11111110
""".trimIndent()

    private val code5: String = """
    0000:NOP
    0001:JMP 0000
""".trimIndent()

    fun parse() {
        val rows = code3.split('\n')
        rows.forEach { row ->
            val splitRow = row.split(":")
            val address = splitRow[0]
            val dataSegment = splitRow[1]
            var data = ""
            var instruction: InstructionSet? = null
            InstructionSet.values().forEach {
                if (dataSegment.contains(it.name, ignoreCase = true)) {
                    instruction = it
                }
            }
            if (instruction != null) {
                val ins = instruction!!
                data = if (ins.name.length == dataSegment.length) dataSegment + "0000" else dataSegment
                data = data.replace(ins.name, ins.value.format(4), ignoreCase = true).replace(" ", "")
            } else {
                data = dataSegment
            }
            memory.putData(bitsToByte(address), bitsToInt(data))
        }
    }

    private fun bitsToByte(bits: String): Byte {
        var result = 0b0000_0000
        for (element in bits) {
            result = result.shl(1)
            if (element == '1') result = result or 0b0000_0001
        }
        return result.toByte()
    }

    private fun bitsToInt(bits: String): Int {
        var result = 0b0000_0000
        for (element in bits) {
            result = result.shl(1)
            if (element == '1') result = result or 0b0000_0001
        }
        return result
    }
}