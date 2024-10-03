package intcode;

public interface Instruction {
    void execute();
    boolean isHalting();
}
