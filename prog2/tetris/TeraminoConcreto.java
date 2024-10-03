import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TeraminoConcreto implements Teramino {
    /* 
     * Implementazione dell'interfaccia Teramino.
     * Le istanze di questa classe sono immutabili.
    */

    // REP
    public final char nome;
    private final Set<Coordinata> coordinate;

    /* 
     * AF(c) = Teramino:
     *         Nome : c.nome
     *         Blocchi di cui è composto: { x (Coordinata) | c.coordinate.contains(x) }
     * RI(c) : c.coordinate ≠ null && ogni elemento di c.coordinate ≠ null
    */

    /* 
     * EFFECTS: Costruisce un teramino di nome n, a partire dalle coordinate contenute in c.
     *          Ad ogni coordinata di c corrisponde un blocco (avente tale coordinata) del nuovo teramino.
     *          Solleva NullPointerException se c è null, se c contiene almeno un null.
     *          Solleva IllegalArgumentException se c è vuoto.
    */
    public TeraminoConcreto(final Set<Coordinata> c, final char n) {
        if (Objects.requireNonNull(c, "L'insieme di coordinate non può essere nullo").isEmpty()) {
            throw new IllegalArgumentException("L'insieme di coordinate non può essere vuoto");
        }

        coordinate = Set.copyOf(c);
        nome = n;
    }

    /* 
     * EFFECTS: Restituisce il nome di this.
    */
    @Override
    public char nome() {
        return nome;
    }

    /* 
     * EFFECTS: Restituisce l'insieme delle coordinate che descrivono this.
    */
    @Override
    public Set<Coordinata> coordinate() {
        return Collections.unmodifiableSet(coordinate);
    }

    /* 
     * EFFECTS: Restituisce un nuovo teramino, ottenuto dalla rotazione di 90° verso destra 
     *          di this.
    */
    @Override
    public TeraminoConcreto ruota() {
        Set<Coordinata> c = new HashSet<>();
        for (Coordinata cor : coordinate) c.add(new Coordinata(cor.y(), cor.x()*(-1)));
        return new TeraminoConcreto(c, nome);
    }

    /* 
     * EFFECTS: Restituisce un nuovo teramino, ottenuto traslando ogni coordinata di this
     *          orizzontalmente di deltaX e verticalmente di deltaY.
    */
    public TeraminoConcreto trasla(final int deltaX, final int deltaY) {
        Set<Coordinata> c = new HashSet<>();
        for (Coordinata cor : coordinate) c.add(new Coordinata(cor.x()+deltaX, cor.y()+deltaY));
        return new TeraminoConcreto(c, nome);
    }

    /* 
     * EFFECTS: Restituisce false se this occupa la riga r, false altrimenti.
    */
    public boolean occupaRiga(final int r) {
        for (Coordinata c : coordinate) if (c.y() == r) return true;
        return false;
    }

    /* 
     * EFFECTS: Restituisce false se this occupa la colonna col, false altrimenti.
    */
    public boolean occupaColonna(final int col) {
        for (Coordinata c : coordinate) if (c.x() == col) return true;
        return false;
    }

    /* 
     * EFFECTS: Restituisce false se this occupa la coordinata cor, false altrimenti.
     *          Solleva NullPointerException se cor è null.
    */
    public boolean occupaCoordinata(final Coordinata cor) {
        Objects.requireNonNull(cor, "La coordinata non può essere nulla.");
        for (Coordinata c : coordinate) if (c.equals(cor)) return true;
        return false;
    }

    /* 
     * EFFECTS: Restituisce false se this occupa almeno una delle posizioni occupate da t,
     *          false altrimenti.
     *          Solleva NullPointerException se t è null.
    */
    public boolean siSovrapponeCon(final TeraminoConcreto t) {
        Objects.requireNonNull(t, "Il tipo di teramino non può essere null.");

        // si può rendere più efficiente controllando prima se i boundingBox dei due teramini
        // si sovrappongono o no (più facile)
        for (Coordinata coordinata : t.coordinate) if (occupaCoordinata(coordinata)) return true;

        return false;
    }

    /* 
     * EFFECTS: Restituisce il bounding box di this.
    */
    @Override
    public Rettangolo boundingBox() {
        Coordinata c[] = new Coordinata[coordinate.size()];
        int i = 0;
        for (Coordinata cor : coordinate) c[i++] = cor;
        
        return Rettangolo.boundingBox(c);
    }

    /* 
     * EFFECTS: Restituisce un teramino convenzionale di tipo t, di nome n e ruotato r volte.
     *          Solleva NullPointerException se t è null.
     *          Solleva IllegalArgumentException se r è negativo.
    */
    public static TeraminoConcreto teraminoConvenzionale(final char n, final TipoTeramino t, final int r) {
        return Objects.requireNonNull(t, "Il tipo di teramino non può essere null.").teramino(n, r);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Rettangolo bound = boundingBox();
        bound.forEach(coordinata -> {
            if (coordinate.contains(coordinata)) {
                sb.append(nome);
            } else {
                sb.append(".");
            }

            if (coordinata.x() == bound.altoDestra.x()) sb.append("\n");
        });

        return sb.toString();
    }
    
}
