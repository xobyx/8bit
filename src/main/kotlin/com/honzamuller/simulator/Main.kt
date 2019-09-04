package com.honzamuller.simulator

@ExperimentalStdlibApi
fun main() {
    Main().run()
}

@ExperimentalStdlibApi
class Main {

    private val allComponents = mutableListOf<Component>()
    private val busComponents = mutableListOf<BusComponent>()
    private lateinit var bus: Bus

    private lateinit var controlLogic: ControlLogic
    fun run() {


        val clock = Clock()
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
        printMemory()
        //println("XXXX ${parser.bitsToByte("0101111")}")

        allComponents.add(bus)

        /*clock.start {
            sendControlWords(ControlWords.CO, ControlWords.MI)
        }*/
        p2()
    }

    fun program0() {
        //LDB
        bus.data = 0b00000011
        sendControlWords(ControlWords.BI)
        tick()

        while (true) {
            // LDA
            //bus.data = 0b01010101
            sendControlWords(ControlWords.AI)
            tick()

            // ALU -> BUS
            sendControlWords(ControlWords.EO)
            tick()
            print()

            print("Enter control word (Q=quit): ")
            val input = readLine() ?: ""
            if (input.equals("q", ignoreCase = true)) {
                return
            }
            if (input.equals("m", ignoreCase = true)) {
                printMemory()
                continue
            }
            if (input.isNotEmpty()) {
                val cw = ControlWords.valueOf(input)
                sendControlWords(ControlWords.SU)
                tick()
                print()

                // ALU -> BUS
                sendControlWords(ControlWords.EO)
                tick()
                print()
            }
        }
        /*sendControlWords(ControlWords.CE)
        tick()
        print()
        sendControlWords(ControlWords.CE)
        tick()
        print()*/


    }

    private fun program1() {

        for (i in 1..10) {
            sendControlWords(ControlWords.CO, ControlWords.MI)
            tick()
            sendControlWords(ControlWords.RO, ControlWords.II)
            tick()
            sendControlWords(ControlWords.CE)
            tick()
            print()


        }

        sendControlWords(ControlWords.AO, ControlWords.II)
        tick()
        print()
    }

    private fun p2() {
        controlLogic.a()
        controlLogic.a()
        controlLogic.a()
        controlLogic.a()
        controlLogic.a()
        controlLogic.a()
    }

    private fun tick() {
        for (bc in busComponents.sortedBy { it.mode.ordinal }) {
            bc.tick()
        }
    }

    private fun sendControlWords(vararg controlWords: ControlWords) {
        for (bc in allComponents) {
            controlWords.forEach { word ->
                bc.controlWord(word)
            }
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

    private fun printMemory() {
        allComponents.first { it is Memory }.printContent()
    }
}