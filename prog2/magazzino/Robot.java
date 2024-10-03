import java.util.Objects;

public abstract class Robot {
    /* 
     * Classe astratta che rappresenta un robot per lo spostamento di pacchi di un magazzino logistico.
    */

    // REP
    public final Magazzino magazzino;

    /* 
     * AF(c) = Il robot opera nel magazzino logistico c.magazzino
     * RI(c) : c.magazzino ≠ null
    */

    /* 
     * EFFECTS: Costruisce un robot che opera in m.
     *          Solleva NullPointerException se m è nullo.
     */
    public Robot(final Magazzino m) {
        magazzino = Objects.requireNonNull(m, "Il magazzino non può essere nullo.");
    }

    /* 
     * EFFECTS: Sposta n pacchi dalla scaffalatura di this.magazzino di indice da, a quella di indice a.
     *          Restituisce true se l'operazione viene completata, false altrimenti.
     *          L'operazione non viene eseguita se n è maggiore del numero di pacchi presenti 
     *          nella scaffalatura di indice da, in tal caso, this.magazzino viene lasciato immutato.
     *          Solleva IllegalArgumentException se da è negativo, se da è maggiore o uguale del 
     *          numero di scaffalature di this.magazzino, e a è negativo, se a è maggiore o uguale del 
     *          numero di scaffalature di this.magazzino, se n è negativo.
    */
    public abstract boolean sposta(final int da, final int a, final int n);

}
