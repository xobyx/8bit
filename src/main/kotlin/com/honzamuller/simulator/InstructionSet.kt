package com.honzamuller.simulator

@Suppress("unused")
enum class InstructionSet(val value: Byte, val microInstructions: List<MicroInstruction>) {

    NOP(0b0000_0000, listOf()),
    LDA(0b0000_0001, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.MI),
        MicroInstruction(ControlWords.RO, ControlWords.AI))),
    ADD(0b0000_0010, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.MI),
        MicroInstruction(ControlWords.RO, ControlWords.BI),
        MicroInstruction(ControlWords.EO, ControlWords.AI, ControlWords.FI))),
    SUB(0b0000_0011, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.MI),
        MicroInstruction(ControlWords.RO, ControlWords.BI),
        MicroInstruction(ControlWords.EO, ControlWords.AI, ControlWords.SU, ControlWords.FI))),
    STA(0b0000_0100, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.MI),
        MicroInstruction(ControlWords.AO, ControlWords.RI)
    )),
    LDI(0b0000_0101, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.AI)
    )),
    JMP(0b0000_0110, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.J)
    )),
    JZ(0b0000_0111, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.J)
    )),
    JC(0b0000_1000, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.J)
    )),
    OUT(0b0000_1110, listOf(MicroInstruction(ControlWords.AO, ControlWords.OI))),
    HLT(0b0000_1111, listOf(MicroInstruction(ControlWords.HLT)));
}
class MicroInstruction(vararg val controlWords: ControlWords)