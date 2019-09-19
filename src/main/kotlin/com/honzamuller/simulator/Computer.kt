package com.honzamuller.simulator

import kotlin.reflect.KClass

class Computer(private val clock: Clock, onCw: ((List<ControlWords>) -> Unit)? = null) {

    private val allComponents = mutableListOf<Component>()
    private val busComponents = mutableListOf<BusComponent>()
    private var bus: Bus
    private var controlLogic: ControlLogic
    private var memory: Memory
    private val parser: Parser

    init {
        allComponents.add(clock)
        bus = Bus()
        val flagsRegister = FlagsRegister().also { allComponents.add(it) }
        val regA = RegisterA(bus).also { allComponents.add(it);busComponents.add(it) }
        val regB = RegisterB(bus).also { allComponents.add(it);busComponents.add(it) }
        ALU(bus, regA, regB, flagsRegister).also { allComponents.add(it);busComponents.add(it) }
        val instructionRegister = InstructionRegister(bus).also { allComponents.add(it);busComponents.add(it) }
        ProgramCounter(bus).also { allComponents.add(it);busComponents.add(it) }
        val mar = MemoryAddressRegister(bus).also { allComponents.add(it);busComponents.add(it) }
        memory = Memory(bus, mar).also { allComponents.add(it);busComponents.add(it) }
        OutputRegister(bus).also { allComponents.add(it);busComponents.add(it) }
        controlLogic = ControlLogic(allComponents, flagsRegister, onCw)
        memory.clear()
        parser = Parser(memory)


        allComponents.add(bus)
    }

    fun run(code: String) {
        parser.parse(code)
        clock.registerOnTick {
            controlLogic.run(it)
        }
    }

    fun registerErrorCallback(callback: (String) -> Unit) {
        memory.registerErrorCallback(callback)
    }

    fun <T : Component> getComponent(clazz: KClass<T>): T {
        return allComponents.first { it::class == clazz } as T
    }

    internal fun run(programExample: ProgramExample) {
        run(programExample.getCode())
    }

    internal fun clear() {
        allComponents.forEach {
            it.onClear()
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

    internal fun printMemory() {
        allComponents.first { it is Memory }.printContent()
    }

    internal fun printOutRegister() {
        val outputRegister = busComponents.first { it is OutputRegister } as OutputRegister
        outputRegister.printContent()
    }

}