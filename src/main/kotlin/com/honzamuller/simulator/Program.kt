package com.honzamuller.simulator

enum class Program(private val code: String) {
    SUM_2_NUMBERS(
        """
    0000:LDA 1000
    0001:ADD 1001
    0010:OUT
    0011:ADD 1101
    0100:OUT
    0101:HLT
    1000:00000001
    1001:00000001
    1101:00001111
        """
    ),
    FIBONACCI(
        """
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
        """
    ),
    TEST_NOP(
        """
    0000:NOP
    0001:JMP 0000
        """
    ),
    TEST_1(
        """
    0000:LDI 0001
    0001:STA 1000
    0010:LDI 0001
    0011:STA 1001
    0100:LDA 1000
    0101:ADD 1001
    0110:OUT
    0111:JMP 0001
    """
    ),
    TEST_2(
        """
    0000:LDA 1111
    0001:OUT
    0010:ADD 1110
    0011:JC 0000
    0100:OUT
    0101:HLT
    1110:00000010
    1111:11111110
        """
    );

    fun getCode(): String {
        return code.trimIndent()
    }
}