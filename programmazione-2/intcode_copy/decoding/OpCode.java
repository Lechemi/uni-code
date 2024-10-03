package intcode_copy.decoding;
/* 
 * contiene:
 * - numero dei parametri
 * - codice implementativo per l'associazione
 * 
 * metodi:
 * - toInstruction() -> metodo che restituisce un Instruction dato il codice
 * 
 * op = new OpCode(codice)
 * numeroParametri = op.numberOfParams()
 * in base a numeroParametri prendo le access modes che mi servono
 * in base a numeroParametri prendo i parametri che mi servono
 * myInstruction = op.toInstruction(new MemoryLocation(parametro, accessmode, rbp), new MemoryLocation(parametro, accessmode, rbp), new MemoryLocation(parametro, accessmode, rbp))
 * myInstruction.execute()
 * 
*/

import java.util.List;

import intcode_copy.Memory.MemoryLocation;
import intcode_copy.instruction.Halt;
import intcode_copy.instruction.Instruction;
import intcode_copy.instruction.parameter_based_instruction.ArithmeticInstruction.Add;
import intcode_copy.instruction.parameter_based_instruction.ArithmeticInstruction.Equals;
import intcode_copy.instruction.parameter_based_instruction.ArithmeticInstruction.LessThan;
import intcode_copy.instruction.parameter_based_instruction.ArithmeticInstruction.Mul;
import intcode_copy.instruction.parameter_based_instruction.io_instruction.Read;
import intcode_copy.instruction.parameter_based_instruction.io_instruction.Write;
import intcode_copy.instruction.parameter_based_instruction.parameter_and_register_based_instruction.control_flow_instruction.JumpIfZero;
import intcode_copy.instruction.parameter_based_instruction.parameter_and_register_based_instruction.control_flow_instruction.JumpNotZero;

public enum OpCode {

    ADD(1, 3),
    MUL(2, 3),
    WRITE(4, 2),
    READ(3, 2),
    JUMP_NOT_ZERO(5, 2),
    JUMP_IF_ZERO(6, 2),
    LESS_THAN(7, 3),
    EQUALS(8, 3),
    ADJ_RBP(9, 1),
    HALT(99, 0);

    private final int code;
    private final int numberOfParams;

    private OpCode(int code, int number) {
        this.code = code;
        this.numberOfParams = number;
    }

    public static OpCode fromCode(int code) {
        for (OpCode m : values()) if (m.code == code) return m;

        throw new IllegalArgumentException("Invalid instruction: " + code);
    }

    public int getNumberOfParams() {
        return this.numberOfParams;
    }

    public Instruction toInstruction(List<MemoryLocation> params) {
        switch (this) {
            case ADD:
                return new Add(params);
            case ADJ_RBP:
                return null;
            case EQUALS:
                return new Equals(params);
            case HALT:
                return new Halt();
            case JUMP_IF_ZERO:
                return new JumpIfZero(params);
            case JUMP_NOT_ZERO:
                return new JumpNotZero(params);
            case LESS_THAN:
                return new LessThan(params);
            case MUL:
                return new Mul(params);
            case READ:
                return new Read(params);
            case WRITE:
                return new Write(params);
            default:
                return null;
        }
    }

}
