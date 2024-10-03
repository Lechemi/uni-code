package e04;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* 
 * [nota: potrei usare direttamente una mappa per la rep, ma renderei l'esercizio banale]
 * 
 * SCELTE IMPLEMENTATIVE
 * utilizzo un ArrayList di record (elements), dove ciascun record rappresenta una coppia key-value;
 * ogni record, KeyValuePair, contiene una stringa (key) e un intero (value)
 * 
 * AF(elements) = { elements[0].key : elements[0].value, elements[1].key : elements[1].value, ...,  elements[n].key : elements[n].value }
 * IR(elements) : elements[i].key != ""
 *                elements[i].key ≠ elements[j].key con i ≠ j
 * 
 * 
*/

public class StringToIntMap {
    /* 
     * OVERVIEW: Le istanze di questa classe sono mutabili. 
     *           La classe rappresenta una mappa da stringhe a interi. 
     *           Una tipica mappa è: {"s1": 0, "s2": 1, ... "sn": n}
    */

    // COPPIA KEY-VALUE
    private record KeyValuePair(String key, int value) {
        public KeyValuePair(String key, int value) {
            if (key == "") throw new IllegalArgumentException();
            this.key = key;
            this.value = value;
        }

        public String toString() {
            return ('"' + key + '"' + " : " + value);   
        }
    }

    // RAPPRESENTAZIONE (vedi sopra per AF e IR)
    ArrayList<KeyValuePair> elements;


    // COSTRUTTORI

    // EFFECTS: restituisce la mappa vuota {}
    public StringToIntMap() {
        elements = new ArrayList<KeyValuePair>();
    }

    
    // METODI

    // MODIFIES: this
    // EFFECTS: Se key è già presente come chiave in this, solleva l'eccezione DuplicateException.
    //          Se key è vuota, solleva l'eccezione IllegalArgumentException.
    //          Altrimenti, inserisce la coppia {key : value} in this.
    public void put(String key, int value) {

        if (key == "") throw new IllegalArgumentException();
        if (contains(key)) throw new DuplicateException();

        KeyValuePair pair = new KeyValuePair(key, value);
        elements.add(pair);
    }

    // MODIFIES: this
    // EFFECTS: Se key non è presente come chiave in this, solleva l'eccezione NoSuchElementException.
    //          Se key è vuota, solleva l'eccezione IllegalArgumentException.
    //          Altrimenti, rimuove da this la coppia {key : value} che ha per chiave key.
    public void remove(String key) {

        if (key == "") throw new IllegalArgumentException();
        
        for (int i=0; i<elements.size(); i++) {
            if (elements.get(i).key == key) {
                elements.remove(i);
                return;
            }
        }

        throw new NoSuchElementException();

    }

    // EFFECTS: Se key non è presente come chiave in this, solleva l'eccezione NoSuchElementException.
    //          Se key è vuota, solleva l'eccezione IllegalArgumentException.
    //          Altrimenti, restituisce l'intero associato a key.
    public int get(String key) {

        if (key == "") throw new IllegalArgumentException();

        for (KeyValuePair p : elements) {
            if (p.key == key) return p.value;
        }

        throw new NoSuchElementException();

    }

    // EFFECTS: Se key è vuota, solleva l'eccezione -.
    //          Altrimenti, ritorna true se key è contenuta in this, false se non lo è.
    public boolean contains(String key) {

        if (key == "") throw new IllegalArgumentException();

        for (KeyValuePair p : elements) {
            if (p.key == key) return true;
        }
        return false;
    }


    // METODI ADDIZIONALI

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        
        for (KeyValuePair pair : elements) {
            if (pair != elements.get(elements.size()-1)) {
                sb.append(pair.toString() + ", ");
            } else {
                sb.append(pair.toString());
            }
        }
        sb.append("}");

        return sb.toString();
    }


    @Override
    public boolean equals(Object obj) {
        
        if (!(obj instanceof StringToIntMap)) return false;

        StringToIntMap other = (StringToIntMap) obj;

        if (elements.size() != other.elements.size()) return false;

        for (KeyValuePair pair : elements) {
            if (!(other.contains(pair.key) && other.get(pair.key) == pair.value)) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    // ITERATORE
    public Iterator<KeyValuePair> iterator() {
        return new Iterator<KeyValuePair>() {

            private int iteratorIndex = 0; // punta sempre a quello che devo stampare

            @Override
            public boolean hasNext() {
                if (iteratorIndex < elements.size()) return true;
                return false;
            }

            @Override
            public KeyValuePair next() {
                if (!hasNext()) throw new NoSuchElementException();
                KeyValuePair next = elements.get(iteratorIndex);
                iteratorIndex++;
                return next;
            }

        };
    }

}
