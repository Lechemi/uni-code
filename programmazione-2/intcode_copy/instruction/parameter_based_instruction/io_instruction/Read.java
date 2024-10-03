package intcode_copy.instruction.parameter_based_instruction.io_instruction;

import java.util.List;

import intcode_copy.Memory.MemoryLocation;

public class Read extends IOInstruction {

    public Read(List<MemoryLocation> params) {
        super(params);
    }

    @Override
    public void execute() {
        Integer toWrite = 5;
        arg.write(toWrite);
    }
    
}
