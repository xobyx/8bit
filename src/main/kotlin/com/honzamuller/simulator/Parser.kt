package com.honzamuller.simulator

internal class Parser(private val memory: Memory) {

    fun parse (programExample: ProgramExample) {
        parse(programExample.getCode())
    }

    fun parse(code: String) {
        val rows = code.split('\n')
        var index = 0
        for (row in rows) {
            if (row.trimIndent().startsWith(";")) continue
            val splitRow = row.split(":")
            var address: String? = null
            var dataSegment = if (splitRow.size != 2) splitRow[0] else {
                address = splitRow[0]
                splitRow[1]
            }
            dataSegment = dataSegment.replaceAfter(";", "").replace(";", "")
            var data: String
            var instruction: InstructionSet? = null
            InstructionSet.values().forEach {
                if (dataSegment.contains(it.name, ignoreCase = true)) {
                    instruction = it
                }
            }
            if (instruction != null) {
                val ins = instruction!!
                data = if (ins.name.length == dataSegment.length) dataSegment + "0000" else dataSegment
                data = data.replace(ins.name, ins.opCode.format(4), ignoreCase = true).replace(" ", "")
            } else {
                data = dataSegment
            }
            val addressBits = if (address == null) {
                index.toByte()
            } else {
                bitsToByte(address)
            }
            memory.putData(addressBits, bitsToInt(data))
            index ++
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