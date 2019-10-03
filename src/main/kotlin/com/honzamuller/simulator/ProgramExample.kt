package com.honzamuller.simulator

enum class ProgramExample(private val code: String) {
    MULTIPLY(
        """
    ; Multiply 7 x 9 - trivial implementation
    LDA 1100
    ADD 1111
    STA 1100
    LDA 1110
    SUB 1101
    JZ 1000
    STA 1110
    JMP 0000
    LDA 1100
    OUT
    HLT
    1100:00000000
    1101:00000001
    1110:00000111
    1111:00001001
        """
    ),
    MULTIPLY_2(
        """
    ; Multiply 7 x 9
    LDA 1110
    SUB 1100
    JC  0110
    LDA 1101
    OUT
    HLT
    STA 1110
    LDA 1101
    ADD 1111
    STA 1101
    JMP 0000
    1100:00000001
    1101:00000000
    1110:00000111
    1111:00001001
        """
    ),
    SUM_3_NUMBERS(
        """
    ; Sum 1 + 1 + 15
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
    ; Calculate Fibonacci set
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
    VON_NEUMANN(
        """
    ; Put data and instructions together
    0000:JMP 0010
    0001:01010101
    0010:LDA 0001
    0011:HLT
        """
    ),
    INC_DEC(
        """
    ; Increment to 20 and decrement back to 0 in infinite loop
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
    LARGEST_DIVISOR(
        """
    ; Find largest divisor of number 45
    ; Can change last 2 bytes to different number
    LDA 1111
    OUT
    LDA 1110
    SUB 1101
    STA 1110
    LDA 1111
    SUB 1110
    JZ  1010
    JNC 0010
    JMP 0110
    LDA 1110
    OUT
    HLT
    1101: 00000001
    1110: 00101101
    1111: 00101101
        """

    );


    fun getCode(): String {
        return code.trimIndent()
    }
}