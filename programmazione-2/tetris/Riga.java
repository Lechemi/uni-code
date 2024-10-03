import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Riga {
    /* 
     * Classe concreta che rappresenta una riga di tetris.
     * Le istanze di questa classe sono mutabili.
    */

    // REP
    private final Set<TeraminoConcreto> teramini = new HashSet<>();
    public final short lunghezza;

    /* 
     * AF(c) = La riga è occupata dai teramini contenuti in c.teramini ed ha lunghezza c.lunghezza
     * RI(c) : c.teramini ≠ null && c.teramini non contiene null &&
     *         ogni teramino contenuto in c.teramini è costituito da almeno una coordinata (x, 0),
     *         con 0 < x < c.lunghezza && 0 < c.lunghezza ≤ 1024 && 
     *         la coordinata x maggiore tra quelle caratterizzanti i teramini di this dev'essere < c.lunghezza
    */

    /* 
     * EFFECTS: Crea una riga vuota di lunghezza l.
     *          Solleva IllegalArgumentException se l < 1, se l > 1024.
    */
    public Riga(final short l) {
        if (l < 1 || l > 1024) throw new IllegalArgumentException("Lunghezza della riga non valida.");
        lunghezza = l;
    }

    /* 
     * MODIFIES: this
     * EFFECTS: Aggiunge t a this alla coordinata c, restituendo true se l'operazione va a buon fine. Altrimenti,
     *          lascia this immutato e restituisce false.
     *          L'operazione non va a buon fine se t non occupa nessuna coordinata della riga,
     *          se t occuperebbe una coordinata già occupata da un altro teramino di this, se t occupa
     *          almeno una coordinata la cui x è ≥ this.lunghezza oppure < 0.
     *          Solleva NullPointerException se t è null, se c è null.
     *          Solleva IllegalArgumentException se c.x è minore di 0.
    */
    public boolean aggiungi(final TeraminoConcreto t, final Coordinata c) {
        if (Objects.requireNonNull(c, "La coordinata non può essere nulla.").x() < 0) {
            throw new IllegalArgumentException("La coordinata non può avere la x negativa");
        }

        TeraminoConcreto daAggiungere = Objects.requireNonNull(t, "Il teramino non può essere nullo.").trasla(
            c.x(), c.y()
        );

        if (daAggiungere.occupaColonna(-1) || daAggiungere.occupaColonna(lunghezza)) return false;
        if (!daAggiungere.occupaRiga(0)) return false;
        if (siSovrappone(daAggiungere)) return false;

        teramini.add(daAggiungere);
        return true;
    }

    /* 
     * EFFECTS: Restituisce true se t si sovrappone con almeno uno dei teramini di this.
    */
    private boolean siSovrappone(final TeraminoConcreto t) {
        for (TeraminoConcreto teramino : teramini) {
            if (t.siSovrapponeCon(teramino)) return true;
        }
        return false;
    }

    /* 
     * EFFECTS: Restituisce false se è presente almeno una coordinata di this che non 
     *          è occupata da alcun teramino, true altrimenti.
    */
    public boolean piena() {return false;}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < lunghezza; x++) {
            boolean occupato = false;
            for (TeraminoConcreto t : teramini) {
                if (t.occupaCoordinata(new Coordinata(x, 0))) {
                    sb.append(t.nome);
                    occupato = true;
                    break;
                }
            }
            if (!occupato) sb.append(".");
        }
        sb.append("\n");
        return sb.toString();
    }
}
