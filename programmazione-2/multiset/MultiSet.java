import java.util.Iterator;
import java.util.Objects;

interface MultiSet<E> extends Iterable<E> {
    /* 
     * Interfaccia che rappresenta un MultiSet, ovvero un insieme 
     * in cui ciascun elemento può essere contenuto più di una volta.
    */

    /* 
     * EFFECTS: Aggiunge e a questo multiset, restituendo la molteplicità di e dopo l’inserimento.
     *          Solleva NullPointerException se e è null.
     *          Solleva ClassCastException se il tipo di e non è uguale al (o non è un sottotipo del)
     *          tipo degli elementi del multiset.
    */
    int add(E e);

    /* 
     * EFFECTS: Rimuove o da questo multiset, restituendo la molteplicità di o prima della rimozione 
     *          (ignorando le richieste di rimuovere elementi non presenti nel multiset).
     *          Solleva NullPointerException se o è null.
    */
    int remove(Object o);

    /* 
     * EFFECTS: Restituisce true se o è contenuto in questo multiset, false altrimenti.
     *          Solleva NullPointerException se o è null.
    */
    default boolean contains(Object o) {
        return multiplicity(Objects.requireNonNull(o, "L'elemento non può essere null.")) > 0;
    }

    /* 
     * EFFECTS: Restituisce la molteplicità di o in questo multiset. La molteplicità di un elemento 
     *          è il numero di occorrenze di tale elemento nel multiset (0 se non è presente).
     *          Solleva NullPointerException se o è null.
     */
    int multiplicity(Object o);

    /* 
     * EFFECTS: Restituisce la cardinalità di questo multiset, ossia il numero totale dei suoi
     *          elementi.
    */
    int size();

    /* 
     * EFFECTS: Restitusice, uno alla volta, gli elementi del supporto di questo multiset.
     *          Il supporto di un multiset è l’insieme (senza ripetizioni) dei suoi elementi.
    */
    Iterator<E> iterator();

    /* 
     * EFFECTS: Restituisce un MultiSet formato dall'unione di questo multiset con o.
     *          L'unione di due multiset A e B è il multiset U che ha per supporto l’unione dei 
     *          supporti di A e B tale per cui la molteplicità di ciascuno elemento u in U è 
     *          pari alla massima tra la molteplicità di u in A e in B.
     *          Solleva NullPointerException se o è null.
    */
    MultiSet<E> union(MultiSet<? extends E> o);

    /* 
     * EFFECTS: Restituisce un MultiSet formato dall'intersezione di questo multiset con o.
     *          L’intersezione di due multiset A e B è il multiset I che ha per supporto 
     *          l’intersezione dei supporti di A e B tale per cui la molteplicità di 
     *          ciascuno elemento u in U è pari alla minima tra la molteplicità di u in A e in B.
     *          Solleva NullPointerException se o è null.
    */
    MultiSet<E> intersection(MultiSet<? extends E> o);
  }