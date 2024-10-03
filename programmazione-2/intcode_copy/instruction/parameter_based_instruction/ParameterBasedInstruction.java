package intcode_copy.instruction.parameter_based_instruction;

import java.util.List;

import intcode_copy.Memory.MemoryLocation;
import intcode_copy.instruction.Instruction;

public abstract class ParameterBasedInstruction implements Instruction {

    public final List<MemoryLocation> parameters;

    // Costruttore
    public ParameterBasedInstruction(List<MemoryLocation> params) {
        parameters = params;
    }

    @Override
    public boolean isHalting() {
        return false;
    }

    @Override
    public abstract void execute();
    
}
