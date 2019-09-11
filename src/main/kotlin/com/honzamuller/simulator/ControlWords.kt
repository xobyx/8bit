package com.honzamuller.simulator

enum class ControlWords(val description: String) {
    HLT("Halt"),
    MI("Memory Address Register - In"),
    RI("RAM - In"),
    RO("RAM - Out"),
    II("Instruction Register - In"),
    IO("Instruction Register - Out"),
    AI("Register A - In"),
    AO("Register A - Out"),
    EO("ALU - Out"),
    SU("ALU - Subtraction"),
    BI("Register B - In"),
    OI("Output Register - In"),
    CE("Register Counter - Increment"),
    CO("Register Counter - Out"),
    J("Register Counter - Jump"),
    FI("ALU - Flags"),
    NONE("Do Nothing")
}