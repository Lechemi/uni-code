package intcode_copy.instruction.parameter_based_instruction.io_instruction;

import java.util.List;

import intcode_copy.Memory.MemoryLocation;

public class Write extends IOInstruction {
    
    public Write(List<MemoryLocation> params) {
        super(params);
    }

    @Override
    public void execute() {
        System.out.println(arg.read());
    }
}
