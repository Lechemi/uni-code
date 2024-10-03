package e04;

import java.util.LinkedList;

/* 
 * SCELTE IMPLEMENTATIVE:
 * uso una LinkedList (chiamata elements), che è effettivamente una lista composta da nodi e puntatori a nodi. 
 * grazie a LinkedList posso svolgere rapidamente operazioni che riguardano il primo e l'ultimo elemento.
 * 
 * dubbio: come faccio a dire che ogni elemento della lista rappresenta un elemento della coda?
 * non posso fare elements[i] perché LinkedList non funziona così. come faccio a dire, per la RI, che 
 * ogni elemento deve essere diverso da null?
 * AF(c) = [c]
 * IR(c): 
 * 
 */


public class UnboundedIntQueue {
    // OVERVIEW
    // Le istanze di questo tipo sono mutabili. Rappresenta una coda di dimensione illimitata.
    // Una coda tipica è [x0, x1, ..., xn].

    // CAMPI
    private LinkedList<Integer> elements;

    // COSTRUTTORI

    // EFFECTS: restituisce la coda vuota []
    public UnboundedIntQueue() {
        elements = new LinkedList<>();
    }

    // METODI

    // MODIFIES: this
    // EFFECTS: Aggiunge val in coda a this
    public void enqueue(Integer val) {
        elements.addLast(val);
    }

    // MODIFIES: this
    // EFFECTS: restituisce l'elemento in testa alla coda e lo elimina da this, se la coda non è vuota.
    //          altrimenti solleva un'eccezione di tipo EmptyQueueException.
    //          this = [x1, x2, ... xk], this_post = [x2, ... xk] 
    public Integer dequeue() {
        if (elements.isEmpty()) throw new EmptyQueueException("Impossibile rimuovere elemento. Coda vuota.");
        assert repOK();
        return elements.removeFirst();
    }

    // EFFECTS: restituisce true se this è vuota, false altrimenti
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    // EFFECTS: restituisce il numero di elementi presenti nella coda
    public int size() {
        return elements.size();
    }

    @Override
    public String toString() {
        return elements.toString();
    }

    private boolean repOK() {
        if (elements.contains(null)) return false;
        return true;
    }
}

