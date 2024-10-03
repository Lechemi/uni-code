package bancarelle;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Inventario {
    /* 
     * Rappresenta un inventario mutabile di giocattoli.
    */

    // REP
    protected final List<Giocattolo> giocattoli = new ArrayList<>();
    protected final List<Integer> quantità = new ArrayList<>();

    /* 
     * AF(c) = c.giocattoli[i] è presente in quantità c.quantità[i] per ogni i 
     * RI(c) : c.giocattoli ≠ null, c.quantità ≠ null
     *         c.giocattoli.size = c.quantità.size
     *         c.giocattoli[i] ≠ null per ogni i
     *         c.quantità[i] ≠ null per ogni i
    */

    /* 
     * EFFECTS: Crea un inventario vuoto.
    */
    public Inventario() {}

    /* 
     * MODIFIES: this
     * EFFECTS: Aggiunge giocattolo, in quantità num, a this (se giocattolo è già presente in this,
     *          viene solo incrementata la sua quantità). 
     *          Solleva NullPointerException se giocattolo è null.
     *          Solleva IllegalArgumentException se num ≤ 0.
    */
    public void aggiungiGiocattolo(final Giocattolo giocattolo, final int num) {
        if (giocattolo == null) throw new NullPointerException("Il giocattolo da aggiungere non può essere null.");
        if (num <= 0) throw new IllegalArgumentException("La quantità di giocattoli da aggiungere dev'essere maggiore di 0.");

        if (quantitàGiocattolo(giocattolo) > 0) {
            int index = giocattoli.indexOf(giocattolo);
            quantità.set(index, quantità.get(index) + num) ;
            return;
        }

        giocattoli.add(giocattolo);
        quantità.add(num);
        
    }

    /* 
     * MODIFIES: this
     * EFFECTS: Rimuove la quantità num di giocattolo da this, se possibile.
     *          Solleva IllegalArgumentException se num ≤ 0, se giocattolo è presente in this 
     *          in quantità inferiore a num.
     *          Solleva NullPointerException se giocattolo è null.
    */
    public void rimuoviGiocattolo(final Giocattolo giocattolo, final int num) {
        if (giocattolo == null) throw new NullPointerException("Il giocattolo da rimuovere non può essere null.");
        if (num <= 0) throw new IllegalArgumentException("La quantità di giocattoli da rimuovere dev'essere maggiore di 0.");

        if (quantitàGiocattolo(giocattolo) < num) throw new IllegalArgumentException("Non ci sono abbastanza giocattoli.");

        int index = giocattoli.indexOf(giocattolo);

        quantità.set(index, quantità.get(index) - num);

        if (quantità.get(index) <= 0) {
            quantità.remove(index);
            giocattoli.remove(index);
        }

    }

    /* 
     * EFFETCS: Restituisce la quantità di giocattolo in this.
     *          Solleva NullPointerException se giocattolo è null.
    */
    public int quantitàGiocattolo(final Giocattolo giocattolo) { 
        if (giocattolo == null) throw new NullPointerException("Il giocattolo non può essere null.");

        return giocattoli.contains(giocattolo) ? quantità.get(giocattoli.indexOf(giocattolo)) : 0;
    }

    /* 
     * EFFECTS: Restituisce, uno alla volta, i giocattoli in this e le relative quantità.
    */
    public Iterator<List<Object>> iterator() {
        return new Iterator<List<Object>>() {

            Iterator<Giocattolo> giocattoloIterator = giocattoli.iterator();
            Iterator<Integer> quantitàIterator = quantità.iterator();

            @Override
            public boolean hasNext() {
                return giocattoloIterator.hasNext() && quantitàIterator.hasNext();
                // in realtà basta la prima condizione
            }

            @Override
            public List<Object> next() {
                return List.of(giocattoloIterator.next(), quantitàIterator.next());
            }
            
        };
    }

}


