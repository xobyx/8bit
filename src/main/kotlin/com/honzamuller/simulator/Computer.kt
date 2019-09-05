package com.honzamuller.simulator

class Computer {

    private val allComponents = mutableListOf<Component>()
    private val busComponents = mutableListOf<BusComponent>()
    private lateinit var bus: Bus
    private lateinit var controlLogic: ControlLogic
    private val clock: Clock


    init {
        clock = Clock().also { allComponents.add(it) }
        bus = Bus()
        val regA = RegisterA(bus).also { allComponents.add(it);busComponents.add(it) }
        val regB = RegisterB(bus).also { allComponents.add(it);busComponents.add(it) }
        ALU(bus, regA, regB).also { allComponents.add(it);busComponents.add(it) }
        val instructionRegister = InstructionRegister(bus).also { allComponents.add(it);busComponents.add(it) }
        ProgramCounter(bus).also { allComponents.add(it);busComponents.add(it) }
        val mar = MemoryAddressRegister(bus).also { allComponents.add(it);busComponents.add(it) }
        val memory = Memory(bus, mar).also { allComponents.add(it);busComponents.add(it) }
        OutputRegister(bus).also { allComponents.add(it);busComponents.add(it) }
        controlLogic = ControlLogic(allComponents)
        memory.clear()
        val parser = Parser(memory)
        parser.parse()
        allComponents.add(bus)
    }

    fun run() {
        clock.start {
            controlLogic.run(it)
        }
    }

    private fun print() {
        allComponents.forEach { bc ->
            if (bc !is Memory) {
                bc.printContent()
            }
        }
        println()
    }

    fun printMemory() {
        allComponents.first { it is Memory }.printContent()
    }

    fun printOutRegister() {
        val outputRegister = busComponents.first { it is OutputRegister } as OutputRegister
        outputRegister.printContent()
    }

}