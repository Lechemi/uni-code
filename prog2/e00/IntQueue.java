package e00;
/* 
 * SCELTE IMPLEMENTATIVE
 * per la rep uso un array (chiamato elements) interpretato in maniera ciclica: ossia, tengo la posizione dell'inizio (head)
 * e la posizione della fine (tail) della coda. Queste posizioni possono cambiare con le operazioni di enqueue
 * e di dequeue, così non devo spostare tutti gli elementi della coda quando faccio la dequeue.
 * In caso di coda vuota, head e tail coincidono; in questo caso,
 * porto head a -1 e tail a 0, così quando faccio una enqueue inserisco l'elemento in posizione 0.
 * Se la coda è piena, head e tail coincidono ma li lascio così.
 * 
 * 
 * AF:
 * - head = -1 => []
 * - head ≠ -1 => [elements[i] | i = head, ..., tail-1] se tail > head
 *                [elements[i] | i = head, ..., elements.length-1, 0, ..., tail-1] se tail < head
 * 
 * RI:
 * - -1 ≤ head < elements.lenght
 * - 0 ≤ tail < elements.length
 * - se head = -1 => tail = 0
 * 
*/

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntQueue {
    // OVERVIEW
    // Le istanze di questo tipo sono mutabili. Rappresenta una coda di dimensione massima n. Una coda tipica è [x(0), x(1), ..., x(n-1)].

    // CAMPI
    private int head, tail;
    private int[] elements;
    private boolean modified;

    
    // COSTRUTTORI

    // EFFECTS: Costruisce una coda di dimensione n. 
    //          Solleva un'eccezione di tipo IllegalArgumentException se n <= 0
    public IntQueue(int n) {}



    // METODI

    // MODIFIES: this
    // EFFECTS: aggiunge n in coda a this se la coda non è piena. 
    //          altrimenti solleva un'eccezione di tipo FullQueueException.
    //          this = [x1, x2, ... xk], this_post = [x1, x2, ... xk, n]
    public void enqueue(int n) {
        if (isFull()) throw new FullQueueException("Impossibile inserire elemento. Coda piena.");
        elements[tail] = n;
        tail  = (tail+1) % elements.length;
        modified = true;
    }

    // MODIFIES: this
    // EFFECTS: restituisce l'elemento in testa alla coda e lo elimina da this, se la coda non è vuota.
    //          altrimenti solleva un'eccezione di tipo EmptyQueueException.
    //          this = [x1, x2, ... xk], this_post = [x2, ... xk]
    public int dequeue() {
        if (isEmpty()) throw new EmptyQueueException("Impossibile rimuovere elemento. Coda vuota.");
        int result = elements[head];

        head = (head+1) % elements.length;
        
        if (head == tail) {
            head = -1;
            tail = 0;
        }

        modified = true;

        return result;
    }

    // EFFECTS: restituisce il numero di elementi della coda
    public int size() {return 0;}

    // EFFECTS: restituisce true se la coda è piena, false atrimenti.
    public boolean isFull() {
        return head == tail;
    }

    // EFFECTS: restituisce true se la coda è vuota, false atrimenti.
    public boolean isEmpty() {
        return head == -1;
    }

    // ITERATORE
    public Iterator<Integer> iterator() {
        modified = false;
        return new Iterator<Integer>() {

            private int iteratorHead = head;
            boolean hasNext = isFull();

            @Override
            public boolean hasNext() {
                if (!hasNext) hasNext = iteratorHead != tail && iteratorHead != -1;
                return hasNext;
            }

            @Override
            public Integer next() {
                if (!hasNext()) throw new NoSuchElementException();
                if (modified) throw new ConcurrentModificationException();
                Integer next = elements[iteratorHead];
                iteratorHead = (iteratorHead+1) % elements.length;
                hasNext = false;
                return next;
            }
        };
    }


    // METODI ADDIZIONALI (nota che per questi metodi non specifico effects, modifies o requires perché sono ereditati)
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("IntQueue: [");

        if (size() > 0) {
            int i;
            for (i=0; i< size()-1; i++) {}
        }

        sb.append("]");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof IntQueue)) return false;

        IntQueue other = (IntQueue) o; // casting
        if (elements.length != other.elements.length) return false; // Stessa capienza
        if (size() != other.size()) return false; // Stessa dimensione

        // Confronto elemento per elemento
        // Uso elements[(head+i) & elements.length] perché è un array ciclico
        for (int i = 0; i < size(); i++) 
            if (elements[(head+i) % elements.length] != other.elements[(other.head+i) % other.elements.length]) return false;
        
        return true;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(elements.length);
        // Vogliamo che due code con la stessa lunghezza abbiano hashcode uguale

        for (int i = 0; i < size(); i++) 
            result = result * 31 + Integer.hashCode(elements[(head+i) % elements.length]);
        
        return result;
    }

    private boolean repOk() {
        return head >= -1
        && head < elements.length
        && tail >= 0
        && tail < elements.length
        && head != -1 || tail == 0;
    }
    
}
