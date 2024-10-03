import java.util.Iterator;
import java.util.Objects;

public abstract class SerieIndirizzi implements Iterable<Indirizzo> {
    /* 
     * Classe astratta che rappresenta la serie di indirizzi degli operandi di una CellaFormula.
    */

    // REP
    public final Indirizzo o1;
    public final Indirizzo o2;

    /* 
     * AF(c) = Indirizzo iniziale della serie: c.o1
     *         Indirizzo finale della serie: c.o2
     * RI(c) : c.o1 e c.o2 non null && !c.o1.equals(o2)
    */

    /* 
     * EFFECTS: Costruisce una serie di indirizzi, i cui indirizzi iniziale e finale sono 
     *          rispettivamente o1 e o2.
     *          Solleva NullPointerException se o1 è nullo, se o2 è nullo.
     *          Solleva IllegalArgumentException se o1 è uguale a o2.
    */
    public SerieIndirizzi(final Indirizzo o1, final Indirizzo o2) {
        if (Objects.requireNonNull(o1, "L'indirizzo iniziale non può essere nullo.").equals(
            Objects.requireNonNull(o2, "L'indirizzo finale non può essere nullo."))) {
            throw new IllegalArgumentException("I due indirizzi non possono essere uguali.");
        }

        this.o1 = o1;
        this.o2 = o2;
    }

    /* 
     * EFFECTS: Restituisce, uno alla volta, senza ripetizioni e partendo da this.o1, gli indirizzi di this.
    */
    public abstract Iterator<Indirizzo> iterator();

    /* 
     * EFFECTS: Restituisce true se this contiene i.
     *          Solleva NullPointerException se i è null.
    */
    public abstract boolean contiene(final Indirizzo i);

}
