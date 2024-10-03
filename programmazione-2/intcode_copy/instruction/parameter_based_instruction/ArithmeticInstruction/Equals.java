package intcode_copy.instruction.parameter_based_instruction.ArithmeticInstruction;

import java.util.List;

import intcode_copy.Memory.MemoryLocation;

public class Equals extends ArithmeticInstruction {

    public Equals(List<MemoryLocation> params) {
        super(params);
    }

    @Override
    public void execute() {
        resultAddress.write(firstOperand.read() == secondOperand.read() ? 1 : 0);
    }
    
}
