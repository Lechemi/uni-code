package intcode;

public abstract class ParameterBasedInstruction implements Instruction {
    Memory.Location[] parameters;

    public ParameterBasedInstruction(Memory.Location[] parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean isHalting() {
        return false;
    }
}
