package intcode_copy.instruction.parameter_based_instruction.io_instruction;

import java.util.List;

import intcode_copy.Memory.MemoryLocation;
import intcode_copy.instruction.parameter_based_instruction.ParameterBasedInstruction;

public abstract class IOInstruction extends ParameterBasedInstruction {

    // arg è l'unico parametro che serve a read e a write (quello che va memorizzato o stampato)
    public final MemoryLocation arg;

    public IOInstruction(List<MemoryLocation> params) {
        super(params);
        arg = parameters.get(0);
    }
    
}
