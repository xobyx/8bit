package com.honzamuller.simulator

fun Byte.format(binaryDigits: Int = 8): String {
    val sb = StringBuffer(binaryDigits)
    for (i in binaryDigits - 1 downTo 0) {
        sb.append((this.toInt() shr i) and 0b00000001)
    }
    return sb.toString()
}
