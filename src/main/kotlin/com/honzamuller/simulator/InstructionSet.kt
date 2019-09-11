package com.honzamuller.simulator

@Suppress("unused")
enum class InstructionSet(val opCode: Byte, val microInstructions: List<MicroInstruction>, val description: String) {

    NOP(0b0000_0000, listOf(),
        "No operation"),
    LDA(0b0000_0001, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.MI),
        MicroInstruction(ControlWords.RO, ControlWords.AI)),
        "Load value at memory location specified by lower 4-bit operand into register A"),
    ADD(0b0000_0010, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.MI),
        MicroInstruction(ControlWords.RO, ControlWords.BI),
        MicroInstruction(ControlWords.EO, ControlWords.AI, ControlWords.FI)),
        "Add value at memory location specified by lower 4-bit operand into register A"),
    SUB(0b0000_0011, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.MI),
        MicroInstruction(ControlWords.RO, ControlWords.BI),
        MicroInstruction(ControlWords.EO, ControlWords.AI, ControlWords.SU, ControlWords.FI)),
        "Subtract value at memory location specified by lower 4-bit operand from register A"),
    STA(0b0000_0100, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.MI),
        MicroInstruction(ControlWords.AO, ControlWords.RI)
    ), "Store value from Register A into memory location specified by lower 4-bit operand "),
    LDI(0b0000_0101, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.AI)
    ), "Load immediate value specified in operand into register A"),
    JMP(0b0000_0110, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.J)
    ), "Jump into address specified by lower 4-bit operand"),
    JZ(0b0000_0111, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.J)
    ), "Jump if zero into address specified by lower 4-bit operand"),
    JC(0b0000_1000, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.J)
    ), "Jump if carry into address specified by lower 4-bit operand"),
    OUT(0b0000_1110, listOf(MicroInstruction(ControlWords.AO, ControlWords.OI)),
    "Copy value from Register A into output register"),
    HLT(0b0000_1111, listOf(MicroInstruction(ControlWords.HLT)),
        "Halt program");
}
class MicroInstruction(vararg val controlWords: ControlWords)