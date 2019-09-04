package com.honzamuller.simulator

enum class InstructionSet(val value: Byte, val microInstructions: List<MicroInstruction>) {

    NOP(0b0000_0000, listOf()),
    LDA(0b0000_0001, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.MI),
        MicroInstruction(ControlWords.RO, ControlWords.AI))),
    ADD(0b0000_0010, listOf(
        MicroInstruction(ControlWords.IO, ControlWords.MI),
        MicroInstruction(ControlWords.RO, ControlWords.BI),
        MicroInstruction(ControlWords.EO, ControlWords.AI))),
    OUT(0b0000_1110, listOf(MicroInstruction(ControlWords.AO, ControlWords.OI))),
    HLT(0b0000_1111, listOf(MicroInstruction(ControlWords.HLT)));

    fun getMicroInstructionSet(): List<MicroInstruction> {
        val list = getFetchCycle()
        list.addAll(microInstructions)
        return list
    }

}

private fun defineInstruction(instructions: List<MicroInstruction>): List<MicroInstruction> {
    return getFetchCycle().also { it.addAll(instructions)}.toList()
}

private fun getFetchCycle() = mutableListOf (
    MicroInstruction(ControlWords.CO, ControlWords.MI),
    MicroInstruction(ControlWords.RO, ControlWords.II, ControlWords.CE))

class MicroInstruction(vararg val controlWords: ControlWords)