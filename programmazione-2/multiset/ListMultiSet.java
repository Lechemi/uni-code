import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ListMultiSet<E> implements MultiSet<E> {
    /* 
     * Implementazione di MultiSet basata su una List di elementi.
     * Le istanze di questa classe sono mutabili.
    */

    private static class Elemento<E> {
        /* 
         * Classe interna che rappresenta l'elemento, con la relativa molteplicità, di un MultiSet.
         * Le istanze di questa classe sono mutabili.
        */

        // REP
        private final E oggetto; // avrei voluto usare "item" al posto di "oggetto"
        private int molteplicità;

        /* 
         * AF(c) = Elemento del multiset: c.oggetto
         *         Molteplicità dell'elemento nel multiset: c.molteplicità.
         * RI(c) = c.oggetto ≠ null
         *         c.molteplicità > 0
        */

        /* 
         * EFFECTS: Costruisce un elemento composto da o e avente molteplicità 1.
         *          Solleva NullPointerException se o è null.
        */
        private Elemento(final E o) {
            oggetto = Objects.requireNonNull(o, "L'elemento non può essere nullo.");
            molteplicità = 1;
        }

        /* 
         * MODIFIES: this
         * EFFECTS: Cambia la molteplicità di this di una quantità n.
         *          Solleva IllegalArgumentException se n è negativo e |n| ≥ this.molteplicità.
        */
        private void cambiaMolteplicità(final int n) {
            molteplicità += n;
        }
    }

    // REP
    private final List<Elemento<E>> elementi = new ArrayList<>();

    /* 
     * AF(c) = Elementi del multiset: c.elementi
     *         
     * RI(c) : c.elementi ≠ null, c.elementi[i] ≠ null per ogni i di c.elementi
     *         c.elementi[i] compare solo una volta in c.elementi (per ogni i di c.elementi)
    */

    /* 
     * EFFECTS: Costruisce un ListMultiSet vuoto.
    */
    public ListMultiSet() {}

    /* 
     * EFFECTS: Restitusice, uno alla volta, gli elementi del supporto di questo multiset.
     *          Il supporto di un multiset è l’insieme (senza ripetizioni) dei suoi elementi.
    */
    @Override
    public Iterator<E> iterator() {

        List<E> supporto = new ArrayList<>();
        for (Elemento<E> e : elementi) {
            for (int i = 0; i < e.molteplicità; i++) {
                supporto.add(e.oggetto);
            }
        }

        return Collections.unmodifiableList(supporto).iterator();
    }

    /* 
     * MODIFIES: this
     * EFFECTS: Aggiunge e a questo multiset, restituendo la molteplicità di e dopo l’inserimento.
     *          Solleva NullPointerException se e è null.
    */
    @Override
    public int add(final E e) {
        Objects.requireNonNull(e, "L'elemento da aggiungere non può essere null.");

        for (final Elemento<E> el : elementi) {
            if (e.equals(el.oggetto)) {
                el.cambiaMolteplicità(1);
                return el.molteplicità;
            }
        }

        Elemento<E> nuovo = new Elemento<E>(e);
        elementi.add(nuovo);
        return 1;
    }

    /* 
     * MODIFIES: this
     * EFFECTS: Aggiunge q volte e a questo multiset, restituendo la molteplicità di e dopo l’inserimento.
     *          Solleva NullPointerException se e è null.
     *          Solleva IllegalArgumentException se q ≤ 0.
    */
    public int addAll(final E e, final int q) {
        if (q <= 0) throw new IllegalArgumentException("La quantità da aggiungere dev'essere maggiore di 0.");
        Objects.requireNonNull(e, "L'elemento da aggiungere non può essere null.");

        for (final Elemento<E> el : elementi) {
            if (e.equals(el.oggetto.equals(e))) el.cambiaMolteplicità(q);
            return el.molteplicità;
        }

        Elemento<E> nuovo = new Elemento<E>(e);
        nuovo.cambiaMolteplicità(q-1);
        elementi.add(nuovo);
        return 1;
        
    }

    /* 
     * MODIFIES: this
     * EFFECTS: Rimuove o da questo multiset, restituendo la molteplicità di o prima della rimozione 
     *          (ignorando le richieste di rimuovere elementi non presenti nel multiset).
     *          Solleva NullPointerException se o è null.
    */
    @Override
    public int remove(Object o) {
        
        for (final Elemento<E> el : elementi) {
            if (o.equals(el.oggetto.equals(o))) {
                int m = el.molteplicità;
                if (m > 1) {
                    el.cambiaMolteplicità(-1);
                } else {
                    elementi.remove(el);
                }
                return m;
            }
        }

        return 0;
    }

    /* 
     * EFFECTS: Restituisce la molteplicità di o in questo multiset. La molteplicità di un elemento 
     *          è il numero di occorrenze di tale elemento nel multiset (0 se non è presente).
     *          Solleva NullPointerException se o è null.
    */
    @Override
    public int multiplicity(final Object o) {
        Objects.requireNonNull(o, "L'elemento non può essere null.");

        for (final Elemento<E> e : elementi) if (e.oggetto.equals(o)) return e.molteplicità;

        return 0;
    }

    /* 
     * EFFECTS: Restituisce la cardinalità di questo multiset, ossia il numero totale dei suoi
     *          elementi.
    */
    @Override
    public int size() {
        int totale = 0;
        for (final Elemento<E> e : elementi) totale += e.molteplicità;
        return totale;
    }

    /* 
     * EFFECTS: Restituisce un MultiSet formato dall'unione di questo multiset con o.
     *          L'unione di due multiset A e B è il multiset U che ha per supporto l’unione dei 
     *          supporti di A e B tale per cui la molteplicità di ciascuno elemento u in U è 
     *          pari alla massima tra la molteplicità di u in A e in B.
     *          Solleva NullPointerException se o è null.
    */
    @Override
    public MultiSet<E> union(MultiSet<? extends E> o) {
        Objects.requireNonNull(o, "L'altro multiset non può essere null.");
        MultiSet<E> result = new ListMultiSet<>();

        for (Elemento<E> e : elementi) {
            int moltMaggiore = e.molteplicità;
            if (o.multiplicity(e.oggetto) > moltMaggiore) moltMaggiore = o.multiplicity(e.oggetto);
            for (int i = 0; i < moltMaggiore; i++) result.add(e.oggetto);
        }

        o.forEach(e -> {
            if (!result.contains(e)) {
                for (int i = 0; i < o.multiplicity(e); i++) result.add(e);
            }
        });

        return result;
    }

    /* 
     * EFFECTS: Restituisce un MultiSet formato dall'intersezione di questo multiset con o.
     *          L’intersezione di due multiset A e B è il multiset I che ha per supporto 
     *          l’intersezione dei supporti di A e B tale per cui la molteplicità di 
     *          ciascuno elemento u in U è pari alla minima tra la molteplicità di u in A e in B.
     *          Solleva NullPointerException se o è null.
    */
    @Override
    public MultiSet<E> intersection(MultiSet<? extends E> o) {
        Objects.requireNonNull(o, "L'altro multiset non può essere null.");
        MultiSet<E> result = new ListMultiSet<>();

        for (Elemento<E> e : elementi) {
            int moltMinore = e.molteplicità;
            if (o.multiplicity(e.oggetto) < moltMinore) moltMinore = o.multiplicity(e.oggetto);
            for (int i = 0; i < moltMinore; i++) result.add(e.oggetto);
        }

        return result;
    }
    
}
