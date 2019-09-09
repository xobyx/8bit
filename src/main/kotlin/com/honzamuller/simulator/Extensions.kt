package com.honzamuller.simulator

fun Byte.format(binaryDigits: Int = 8): String {
    val sb = StringBuffer(binaryDigits)
    for (i in binaryDigits - 1 downTo 0) {
        val bit = (this.toInt() shr i) and 0b00000001
        sb.append(bit)
    }
    return sb.toString()
}

fun Byte.formatFancy(binaryDigits: Int = 8): String {
    val sb = StringBuffer(binaryDigits)
    for (i in binaryDigits - 1 downTo 0) {
        val bit = (this.toInt() shr i) and 0b00000001
        sb.append(if (bit == 1) "●" else "○")
    }
    return sb.toString()
}

fun Int.format(binaryDigits: Int = 8): String {
    val sb = StringBuffer(binaryDigits)
    for (i in binaryDigits - 1 downTo 0) {
        val bit = (this shr i) and 0b00000001
        sb.append(bit)
    }
    return sb.toString()
}

fun Int.formatAsArray(binaryDigits: Int = 8): Array<Boolean> {
    val result = Array(binaryDigits) {
        false
    }
    val sb = StringBuffer(binaryDigits)
    for (i in binaryDigits - 1 downTo 0) {
        val bit = (this shr i) and 0b00000001
        result[i] = bit == 1
    }
    return result
}

fun Int.formatFancy(binaryDigits: Int = 8): String {
    val sb = StringBuffer(binaryDigits)
    for (i in binaryDigits - 1 downTo 0) {
        val bit = (this shr i) and 0b00000001
        sb.append(if (bit == 1) "●" else "○")
    }
    return sb.toString()
}
