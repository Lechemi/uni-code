package intcode_copy.instruction.parameter_based_instruction.ArithmeticInstruction;

import java.util.List;

import intcode_copy.Memory.MemoryLocation;
import intcode_copy.instruction.parameter_based_instruction.ParameterBasedInstruction;

public abstract class ArithmeticInstruction extends ParameterBasedInstruction {

    public final MemoryLocation firstOperand;
    public final MemoryLocation secondOperand;
    public final MemoryLocation resultAddress;

    public ArithmeticInstruction(List<MemoryLocation> params) {
        super(params);
        firstOperand = params.get(0);
        secondOperand = params.get(1);
        resultAddress = params.get(2);
    }
    
}
