package com.honzamuller.simulator

enum class ProgramExample(private val code: String) {
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
    LDI 0001
    STA 1000
    LDI 0001
    STA 1001
    LDA 1000
    ADD 1001
    OUT
    JMP 0001
    """
    ),
    TEST_2(
        """
    LDA 1111
    OUT
    ADD 1110
    JC 0000
    OUT
    HLT
    1110:00000010
    1111:11111110
        """
    ),
    VON_NEUMAN(
        """
    0000:JMP 0010
    0001:01010101
    0010:LDA 0001
    0011:HLT
        """
    ),
    INC_DEC(
        """
    0000:LDA 1110
    0001:ADD 1111
    0010:OUT
    0011:STA 1110
    0100:SUB 1101
    0101:JZ 0111
    0110:JMP 0000
    0111:LDA 1110 
    1000:SUB 1111
    1001:OUT
    1010:STA 1110
    1011:JZ 0000
    1100:JMP 1000
    1101:00010100
    1110:00000000
    1111:00000001
        """
    ),
    MULTIPLY(
        """
    0001:LDI 0001
    0010:OUT
        """
    );

    fun getCode(): String {
        return code.trimIndent()
    }
}