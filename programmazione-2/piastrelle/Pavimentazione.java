import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Pavimentazione implements Pavimento, Iterable<Pavimentazione.Componente> {
    /* 
     * Rappresenta una pavimentazione, composta o da piastrelle o da altre pavimentazioni.
     * Le istanze di questa clase sono immutabili.
    */

    public static class Componente implements Pavimento {
        /* 
         * Classe interna che rappresenta la componente di una pavimentazione, insieme con
         * la quantità in cui essa è presente nella pavimentazione stessa.
         * Le istanze di questa classe sono immutabili.
        */

        // REP
        private final int quantità;
        private final Pavimento componente;

        /* 
         * AF(c) = c.componente : tipologia della componente
         *         c.quantità : quantità in cui c.componente è presente in this.Pavimentazione
         * 
         * RI(c) : c.componente ≠ null && c.quantità > 0
        */

        /* 
         * EFFECTS: Crea una componente di tipologia c, presente in this.Pavimentazione in quantità q.
         *          Solleva NullPointerException se c è null.
         *          Solleva IllegalArgumentException se q ≤ 0.
        */
        public Componente(final Pavimento c, final int q) {
            if (q <= 0) throw new IllegalArgumentException("La quantità dev'essere positiva.");
            quantità = q;
            componente = Objects.requireNonNull(c, "La tipologia della componente non può essere null.");

        }

        /* 
         * EFFECTS: Restituisce il costo della componente, tenendo ovviamente conto della quantità.
        */
        @Override
        public int costo() {
            return componente.costo() * quantità;
        }

        /* 
         * EFFECTS: Restituisce la superficie della componente, tenendo ovviamente conto della quantità.
        */
        @Override
        public int superficie() {
            return componente.superficie() * quantità;
        }
    }

    // REP
    private final Collection<Componente> componenti;

    /* 
     * AF(c) = Componenti della pavimentazione: c.componenti
     * RI(c) : c.componenti non può essere null e non può contenere null
    */

    /* 
     * EFFECTS: Costruisce una pavimentazione formata dalle componenti di comps.
     *          Solleva NullPointerException se comps o uno dei suoi elementi è null.
     *          Solleva IllegalArgumentException se comps è vuota.
    */
    public Pavimentazione(final Collection<Componente> comps) {
        if (Objects.requireNonNull(comps, "la collezione di componenti non può essere null.").contains(null)) {
            throw new NullPointerException("la collezione di componenti non può contenere null.");
        }
        componenti = List.copyOf(comps);
        if (componenti.isEmpty()) throw new IllegalArgumentException(
            "la pavimentazione deve avere almeno una componente."
        );
    }

    /* 
     * EFFECTS: Restituisce il costo di this.
    */
    @Override
    public int costo() {
        int c = 0;
        for (final Componente componente : componenti) c += componente.costo();
        return c;
    }

    /* 
     * EFFECTS: Restituisce la superficie di this.
    */
    @Override
    public int superficie() {
        int s = 0;
        for (final Componente componente : componenti) s += componente.superficie();
        return s;
    }

    /* 
     * EFFECTS: Restituisce, una alla volta e senza ripetizioni, le componenti di this.
    */
    @Override
    public Iterator<Pavimentazione.Componente> iterator() {
        return Collections.unmodifiableCollection(componenti).iterator();
    }
    
}
