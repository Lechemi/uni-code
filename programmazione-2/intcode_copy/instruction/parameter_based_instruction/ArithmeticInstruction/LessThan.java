package intcode_copy.instruction.parameter_based_instruction.ArithmeticInstruction;

import java.util.List;

import intcode_copy.Memory.MemoryLocation;

public class LessThan extends ArithmeticInstruction {

    public LessThan(List<MemoryLocation> params) {
        super(params);
    }

    @Override
    public void execute() {
        resultAddress.write(firstOperand.read() < secondOperand.read() ? 1 : 0);
    }
    
}
