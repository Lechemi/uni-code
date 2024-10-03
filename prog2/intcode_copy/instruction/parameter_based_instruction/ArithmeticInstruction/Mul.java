package intcode_copy.instruction.parameter_based_instruction.ArithmeticInstruction;

import java.util.List;

import intcode_copy.Memory.MemoryLocation;

public class Mul extends ArithmeticInstruction {

    public Mul(List<MemoryLocation> params) {
        super(params);
    }

    @Override
    public void execute() {
        resultAddress.write(firstOperand.read() * secondOperand.read());
    }
    
}
