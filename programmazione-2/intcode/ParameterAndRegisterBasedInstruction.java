package intcode;

import intcode.Memory.Location;

public abstract class ParameterAndRegisterBasedInstruction extends ParameterBasedInstruction {

    public ParameterAndRegisterBasedInstruction(Location[] parameters) {
        super(parameters);
    }
    
}
