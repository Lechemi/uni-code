import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MapMultset<E> implements MultiSet<E> {
    /* 
     * Implementazione di MultiSet basata su Map.
     * Le istanze di questa classe sono mutabili.
    */

    // REP
    private final Map<E, Integer> elements = new HashMap<>();

    /* 
     * AF(c) = c.elements[k] = numero di occorrenze di k nel multiset
     * 
     * RI(c) : c.elements ≠ null && le chiavi e i valori di c.elements non possono essere null &&
     *         i valori di c.elements devono essere sempre > 0
    */

    /* 
     * EFFECTS: Costruisce un multiset vuoto.
    */
    public MapMultset() {}

    @Override
    public Iterator<E> iterator() {
        List<E> supporto = new ArrayList<>();

        for (var entry : elements.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) supporto.add(entry.getKey());
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

        if (contains(Objects.requireNonNull(e, "L'elemento da aggiungere non può essere null."))) {
            elements.put(e, multiplicity(e) + 1);
            return multiplicity(e);
        }

        elements.put(e, 1);
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
        int m = multiplicity(Objects.requireNonNull(o, "L'elemento da aggiungere non può essere null."));
        if (m == 0) return 0;

        if (m == 1) {
            elements.remove(o);
            return 1;
        }
        
        @SuppressWarnings("unchecked")
        E elemento = (E) o;
        elements.put(elemento, multiplicity(o) + 1);
        return m;
    }

    /* 
     * EFFECTS: Restituisce la molteplicità di o in questo multiset. La molteplicità di un elemento 
     *          è il numero di occorrenze di tale elemento nel multiset (0 se non è presente).
     *          Solleva NullPointerException se o è null.
    */
    @Override
    public int multiplicity(Object o) {
        Integer m = elements.get(Objects.requireNonNull(o, "L'elemento da aggiungere non può essere null."));
        if (m == null) return 0;
        return m;
    }

    /* 
     * EFFECTS: Restituisce la cardinalità di questo multiset, ossia il numero totale dei suoi
     *          elementi.
    */
    @Override
    public int size() {
        int totale = 0;
        for (Integer m : elements.values()) totale += m;
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
        MultiSet<E> result = new MapMultset<>();

        for (var entry : elements.entrySet()) {
            int moltMaggiore = entry.getValue();
            if (o.multiplicity(entry.getKey()) > moltMaggiore) moltMaggiore = o.multiplicity(entry.getKey());
            for (int i = 0; i < moltMaggiore; i++) result.add(entry.getKey());
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
        MultiSet<E> result = new MapMultset<>();

        for (var entry : elements.entrySet()) {
            int moltMinore = entry.getValue();
            if (o.multiplicity(entry.getKey()) < moltMinore) moltMinore = o.multiplicity(entry.getKey());
            for (int i = 0; i < moltMinore; i++) result.add(entry.getKey());
        }

        return result;
    }
    
}
