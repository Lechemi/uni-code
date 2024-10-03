package intcode_copy.instruction.parameter_based_instruction.parameter_and_register_based_instruction;

import java.util.List;

import intcode_copy.Memory.MemoryLocation;
import intcode_copy.instruction.parameter_based_instruction.ParameterBasedInstruction;

public abstract class ParameterAndRegisterBasedInstruction extends ParameterBasedInstruction {

    public ParameterAndRegisterBasedInstruction(List<MemoryLocation> params) {
        super(params);
    }
    
}
