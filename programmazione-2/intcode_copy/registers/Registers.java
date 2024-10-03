package intcode_copy.registers;
/* 
 * SCELTE IMPLEMENTATIVE
 * composta da:
 * - IP
 * - RBP
 * 
 * non dobbiamo preoccuparci di non esporre la rep perché tanto solo noi useremo Registers
 * 
*/

// NB: tutte le classi a parte IntcodeVM saranno private

class Registers {
    
    public int ip;
    public int rbp;

    Registers() {
        ip = 0;
        rbp = 0;
    }

}
