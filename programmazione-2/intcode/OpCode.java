package intcode;
/* 
 * contiene:
 * - numero dei parametri
 * - codice implementativo per l'associazione
 * 
 * metodi:
 * - toInstruction() -> metodo che restituisce un Instruction dato il codice
 * 
*/

import java.util.List;

public enum OpCode {
    ADD(1, 3){ 
        @Override
        public Instruction toInstruction(Memory.Location[] params, Registers registers) {
            return new Add(params);
        }
    } ,
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

    private OpCode(int code, int numberOfParams) {
        this.code = code;
        this.numberOfParams = numberOfParams;
    }

    public static OpCode fromCode(int code) {
        for (OpCode m : values()) if (m.code == code) return m;

        throw new IllegalArgumentException("Invalid instruction: " + code);
    }

    public int getNumberOfParams() {
        return this.numberOfParams;
    }

    public abstract Instruction toInstruction(Memory.Location[] params, Registers registers);

}
