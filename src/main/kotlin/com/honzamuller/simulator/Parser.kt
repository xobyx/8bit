package com.honzamuller.simulator

class Parser(private val memory: Memory) {
    private val code: String = """
    0000:LDA 1000
    0001:ADD 1001
    0010:OUT
    0011:ADD 1101
    0100:OUT
    0101:HLT
    1000:00001001
    1001:00001001
    1101:00001001
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
            memory.putData(bitsToByte(address), bitsToByte(data))
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

}