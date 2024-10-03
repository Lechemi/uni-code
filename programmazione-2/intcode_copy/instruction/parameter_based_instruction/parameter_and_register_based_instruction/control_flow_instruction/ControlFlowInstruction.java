package intcode_copy.instruction.parameter_based_instruction.parameter_and_register_based_instruction.control_flow_instruction;

import java.util.List;
import intcode_copy.Memory.MemoryLocation;
import intcode_copy.instruction.parameter_based_instruction.parameter_and_register_based_instruction.ParameterAndRegisterBasedInstruction;

public abstract class ControlFlowInstruction extends ParameterAndRegisterBasedInstruction {

    public final MemoryLocation check;
    public final MemoryLocation IPValue;

    public ControlFlowInstruction(List<MemoryLocation> params) {
        super(params);
        check = params.get(0);
        IPValue = params.get(1);
    }
    
}
