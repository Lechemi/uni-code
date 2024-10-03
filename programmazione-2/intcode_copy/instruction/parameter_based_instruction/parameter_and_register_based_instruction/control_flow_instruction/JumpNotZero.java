package intcode_copy.instruction.parameter_based_instruction.parameter_and_register_based_instruction.control_flow_instruction;

import java.util.List;

import intcode_copy.Memory.MemoryLocation;

public class JumpNotZero extends ControlFlowInstruction {

    public JumpNotZero(List<MemoryLocation> params) {
        super(params);
    }

    @Override
    public void execute() {
    }
    
}
