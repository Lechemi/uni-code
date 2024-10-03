package intcode_copy;
/* 
 * SCELTE IMPLEMENTATIVE
 * composta da: 
 * - celle di memoria
 * 
 * arricchisco le celle di memoria di Memory con le proprietà definite in Locazione, 
 * in questo modo, al posto di passare una cella di memoria come parametro, passo direttamente
 * una Locazione, che comprende anche l'accessMode e, se serve l'RBP
 * 
 * ogni cella di memoria è rappresentata a sua volta dalla classe Locazione, composta da
 * - indice di memoria
 * - mode -> AccessMode
 * - copia dell'RBP (se l'istruzione ne ha bisogno)
 * con metodi:
 * - read() quando faccio la read, ritorno in base al valore di accessMode
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

import intcode_copy.decoding.AccessMode;

public class Memory {

    private ArrayList<Integer> memoryCells;

    Memory(List<Integer> program) {
        memoryCells = new ArrayList<Integer>(program);
        // Sto creando una nuova ArrayList le cui prime posizioni sono occupate dal programma
    }

    private void set(int address, int value) {
        if (address < 0) throw new IllegalArgumentException("Invalid memory address: " + address);

        int size = address - memoryCells.size() + 1;

        if (size > 0) memoryCells.addAll(Collections.nCopies(size, 0));

        memoryCells.set(address, value);
    }

    int get(int address) {
        if (address < 0) throw new IllegalArgumentException("Invalid memory address: " + address);

        return address < memoryCells.size() ? memoryCells.get(address) : 0;
    }

    // parameterModes sono le 3 cifre più significative del codice operativo
    public MemoryLocation[] prepareLocations(int nParams, int parameterModes, Registers registers) {
        MemoryLocation[] parameters = new MemoryLocation[nParams];
        for (int i = 0; i< nParams; i++, parameterModes /= 10) {
            parameters[i] = new MemoryLocation(
                registers.ip + i,
                registers.rbp,
                AccessMode.fromCode(parameterModes % 10));
        }
        return parameters;
    }

    public class MemoryLocation {
        int index;
        int RBP;
        AccessMode accessMode;

        public MemoryLocation(int index, int RBP, AccessMode accessMode) {
            this.index = index;
            this.RBP = RBP;
            this.accessMode = accessMode;
        } 

        public Integer read() {
            Integer val;
            switch (accessMode) {
                case POSITION:
                    val = get(index);
                    break;
                case IMMEDIATE:
                    val = index;
                    break;
                case RELATIVE:
                    val = get(RBP + index);
                    break;
                default:
                    return 0;                
            }
            return val;
        }

        public void write(Integer val) {
            switch (accessMode) {
                case POSITION:
                    set(index, val);
                    break;
                case IMMEDIATE:
                    // eccezione
                    break;
                case RELATIVE:
                    set(RBP + index, val);
                    break;               
            }
        }

    }
 
}
