package intcode_copy.instruction;

public interface Instruction {

    boolean isHalting();

    void execute();
}
