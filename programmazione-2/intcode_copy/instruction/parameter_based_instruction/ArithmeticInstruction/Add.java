package intcode_copy.instruction.parameter_based_instruction.ArithmeticInstruction;

import java.util.List;

import intcode_copy.Memory.MemoryLocation;

public class Add extends ArithmeticInstruction {

    public Add(List<MemoryLocation> params) {
        super(params);
    }

    @Override
    public void execute() {
        resultAddress.write(firstOperand.read() + secondOperand.read());
    }
    
}
