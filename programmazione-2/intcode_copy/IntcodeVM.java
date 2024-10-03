package intcode_copy;
/* 
 * nb: questa è l'unica classe con cui gli utenti si interfacciano
 * 
 * SCELTE IMPLEMENTATIVE
 * composta da:
 * - registri (tipo Registers)
 * - memoria
 * 
 * metodo run() 
*/

import java.util.List;

import intcode_copy.decoding.OpCode;
import intcode_copy.instruction.Instruction;


public class IntcodeVM {
    Registers registers;
    Memory memory;

    public IntcodeVM(List<Integer> program) {
        memory = new Memory(program);
        registers = new Registers();
    }

    void run() {
        boolean running;
        do {
            int rawInstruction = fetch(registers.ip);
            Instruction i = decode(rawInstruction);
            running = execute(i);
        } while (running);
    }

    private boolean execute(Instruction i) {
        i.execute();
        return i.isHalting();
    }

    private Instruction decode(int i){
        OpCode op = OpCode.fromCode(i);
        Memory.Location[] parameters = memory.prepareLocations(op.getNumberOfParams(), i/100, registers);
        return op.toInstruction(parameters, registers);
    }

    private int fetch(int ip) {
        return 0;
    }
}
