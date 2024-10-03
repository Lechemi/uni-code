package intcode;
/* 
 * SCELTE IMPLEMENTATIVE
 * composta da: 
 * - celle di memoria
 * 
 * arricchisco le celle di memoria di Memory con le proprietà definite in Locazione, 
 * in questo modo, al posto di passare una cella di memoria come parametro, passo direttamente
 * una Locazione, che comprende anche l'accessMode e, se serve l'RBP
 * ogni cella di memoria è rappresentata a sua volta dalla classe Locazione, composta da
 * - indice di memoria
 * - mode -> AccessMode
 * - copia dell'RBP (se l'istruzione ne ha bisogno)
 * con metodi:
 * - read()
 * - write()
 * 
 * metodi:
 * - set()
 * - get()
 * 
 * non dobbiamo preoccuparci di non esporre la rep perché tanto solo noi useremo Memory 
 * 
*/

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Memory {
    private final List<Integer> memoryCells;
    // final indica che il puntatore a memoryCells è immodificabile, non che memoryCells è immodificabile

    Memory(List<Integer> program) {
        memoryCells = new ArrayList<Integer>(program);
    }

    private void set(int address, int value) {
        if (address < 0) throw new IllegalArgumentException("Invalid memory address: " + address);

        int size = address -memoryCells.size() + 1;

        if (size > 0) memoryCells.addAll(Collections.nCopies(size, 0));

        memoryCells.set(address, value);
    }

    int get(int address) {
        if (address < 0) throw new IllegalArgumentException("Invalid memory address: " + address);

        return address < memoryCells.size() ? memoryCells.get(address) : 0;
    }

    // parameterModes sono le 3 cifre più significative del codice operativo
    public Location[] prepareLocations(int nParams, int parameterModes, Registers registers) {
        Location[] parameters = new Location[nParams];
        for (int i = 0; i< nParams; i++, parameterModes /= 10) {
            parameters[i] = new Location(
                registers.ip + i, 
                registers.rbp,
                AccessMode.fromCode(parameterModes % 10));
        }
        return parameters;
    }

    class Location {
        private final int index;
        private final int RBP;
        private final AccessMode accessMode;

        Location(int index, int relativeBasePointer, AccessMode accessMode) {
            this.index = index;
            this.RBP = relativeBasePointer;
            this.accessMode = accessMode;
        }

        public int read() {
            switch (accessMode) {
                case POSITION:
                    return get(index);
                case IMMEDIATE:
                    return index;
                case RELATIVE:
                    return get(RBP + index);
                default:
                    return 0;                
            }
        }

        public void write(int val) {
            switch (accessMode) {
                case POSITION:
                    set(index, val);
                    break;
                case IMMEDIATE:
                    throw new IllegalStateException("Can't write in IMMEDIATE mode");
                case RELATIVE:
                    set(RBP + index, val);
                    break;               
            }
        }

    }

}
