package intcode_copy.instruction;

public class Halt implements Instruction {

    @Override
    public boolean isHalting() {
        return true;
    }

    @Override
    public void execute() {
        return;
    }
    
}
