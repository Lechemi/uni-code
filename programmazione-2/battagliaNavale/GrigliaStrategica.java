import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GrigliaStrategica implements Griglia {
    /* 
     * Classe concreta che rappresenta la griglia strategica di battaglia navale, ossia: la griglia
     * su cui vengono memorizzati gli esiti degli attacchi fatti all'avversario.
     * Le istanze di questa classe sono mutabili.
    */

    // REP
    private final Map<Posizione, Esito> griglia = new HashMap<>();

    /* 
     * AF(c) = (uso la notazione [key] per accedere al valore della chiave key in c.griglia)
     *         La griglia è costituita da 100 posizioni, indicate da colonna (A to J) e riga (0 to 9).
     *         Per ogni posizione della griglia (A0, ..., J9):
     *         if (posizione non è presente in c.griglia) {
     *              il giocatore non ha attaccato l'avversario in questa posizione
     *         } else {
     *              in questa posizione è stato attaccato l'avversario, e l'esito di tale attacco
     *              è stato c.griglia[posizione]
     *         }
     * 
     * RI(c) : c.griglia ≠ null && ogni chiave o valore di c.griglia ≠ null
    */

    /* 
     * MODIFIES: this
     * EFFECTS: Inserisce alla posizione pos di this l'esito e.
     *          Se in pos è già presente un altro esito, lo sovrascrive.
     *          Solleva NullPointerException se pos è null, se e è null.
     */
    public void aggiorna(final Posizione pos, final Esito e) {
        griglia.put(Objects.requireNonNull(pos, "La posizione non può essere nulla."), Objects.requireNonNull(e, "L'esito non può essere nullo."));
    }

    /* 
     * EFFECTS: Restituisce l'esito alla posizione pos di this.
     *          Se a pos non corrisponde alcun esito, restituisce null.
     *          Solleva NullPointerException se pos è null.
     */
    public Esito esito(final Posizione pos) {
        return griglia.get(Objects.requireNonNull(pos, "La posizione non può essere nulla."));
    }

    /* 
     * EFFECTS: Restituisce il simbolo corrispondente alla posizione pos su this.
     *          Ossia: restituisce '~' se in pos non è presente nessun Esito, altrimenti 
     *          restituisce il simbolo corrispondente all'Esito.
     *          Solleva NullPointerException se pos è nulla.
    */
    @Override
    public char get(final Posizione pos) {
        Esito e = griglia.get(Objects.requireNonNull(pos, "La posizione non può essere nulla."));
        return e == null ? '~' : e.toString().charAt(0);
    }
    
}
