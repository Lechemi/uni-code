import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class GrigliaFlotta implements Griglia {
    /* 
     * Classe concreta che rappresenta la griglia della flotta di battaglia navale.
     * Le istanze di questa classe sono mutabili.
    */

    // REP
    private final List<Nave> navi = new ArrayList<>();

    /* 
     * AF(c) = La griglia è composta da posizioni caratterizzate da colonna (A to J) e riga (0 to 9)
     *         Per ogni posizione della griglia (A0, ..., J9):
     *         - se c.navi contiene una nave di indice k | c.navi[k] occupa la posizione => griglia[posizione] = c.navi[k]
     *         - altrimenti, griglia[posizione] è vuota
     * 
     * RI(c) : c.navi ≠ null && ogni elemento di c.navi ≠ null
    */

    /* 
     * EFFECTS: Restituisce la nave di this che occupa pos.
     *          Restituisce null se pos non è occupata da nessuna nave.
     */
    private Nave daPosizione(final Posizione pos) {
        for (Nave nave : navi) if (nave.occupa(pos)) return nave;
        return null;
    }

    /* 
     * MODIFIES: this
     * EFFECTS: Posiziona n su this, restituendo true se l'operazione va a buon fine.
     *          L'operazione non viene terminata se almeno una delle posizioni occupate da n
     *          è già occupata da un'altra nave di this. In tal caso, this rimane immutato e viene 
     *          restituito false.
     *          Solleva NullPointerException se n è null.
     */
    public boolean posiziona(final Nave n) {
        Iterator<Posizione> occupate = Objects.requireNonNull(n, "La nave non può essere nulla.").posizioniOccupate();
        while (occupate.hasNext()) if (daPosizione(occupate.next()) != null) return false;      

        navi.add(n);
        return true;
    }

    /* 
     * EFFECTS: Riceve un attacco avversario in posizione pos, restituendo l'esito di tale attacco e
     *          aggiornando this in modo che lo registri.
     *          Solleva NullPointerException se pos è null.
     */
    public Esito riceviAttacco(final Posizione pos) {
        Nave n = daPosizione(Objects.requireNonNull(pos, "La posizione non può essere nulla."));
        if (n == null) return Esito.Mancato;
        return n.attacca(pos);
    }

    /* 
     * EFFECTS: Restituisce il simbolo corrispondente alla posizione pos su this.
     *          Ossia: 
     *              - '~' se pos non è occupata da nessuna nave
     *              - la lettera rappresentante la nave se pos è occupata da tale nave (intatta)
     *              - '*' se pos è occupata da una nave colpita in tale posizione
     *              - '#' se pos è occupata da una nave affondata
     *          Solleva NullPointerException se pos è nulla.
    */
    @Override
    public char get(final Posizione pos) {
        Nave n = daPosizione(Objects.requireNonNull(pos, "La posizione non può essere nulla."));
        if (n == null) return '~';

        if (n.affondata()) return '#';
        return n.colpita(pos) ? '*' : n.tipologia.toString().charAt(0);
    }
    
}
