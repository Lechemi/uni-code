import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Rettangolo implements Iterable<Coordinata> {
    /* 
     * Classe concreta le cui istanze immutabili rappresentano un rettangolo 
     * nel piano cartesiano (a coordinate intere).
    */

    // REP
    public final Coordinata bassoSinistra;
    public final Coordinata altoDestra;

    /* 
     * AF(c) = Il rettangolo ha il vertice in basso a sinistra di coordinate c.bassoSinistra 
     *         e il vertice in alto a destra di coordinate c.altoDestra.
     * RI(c) : c.bassoSinistra ≠ null && c.altoDestra ≠ null &&
     *         c.bassoSinistra.x ≤ c.altoDestra.x && c.bassoSinistra.y ≤ c.altoDestra.y
    */

    /* 
     * EFFECTS: Costruisce un rettangolo i cui vertici in basso a sinistra e in alto a destra
     *          hanno rispettivamente coordinate bs e ad.
     *          Solleva NullPointerException se bs è null, se ad è null.
     *          Solleva IllegalArgumentException se bs.x > ad.x, se bs.y > ad.y.
     */
    public Rettangolo(final Coordinata bs, final Coordinata ad) {
        Objects.requireNonNull(bs, "Le coordinate del vertice in basso a sinistra non possono essere nulle.");
        Objects.requireNonNull(ad, "Le coordinate del vertice in alto a destra non possono essere nulle.");
        
        if (bs.x() > ad.x()) throw new IllegalArgumentException("La coordinata x del vertice in basso a sinistra non può essere maggiore di quella del vertice in alto a destra.");
        if (bs.y() > ad.y()) throw new IllegalArgumentException("La coordinata y del vertice in basso a sinistra non può essere maggiore di quella del vertice in alto a destra.");

        bassoSinistra = bs;
        altoDestra = ad;
    }

    /* 
     * EFFECTS: Restituisce il rettangolo di area minima che racchiude tutte le coordinate presenti
     *          in c.
     *          Solleva NullPointerException se c è null, se almeno uno degli elementi di c è null.
     *          Solleva IllegalArgumentException se c è vuoto.
     */
    public static Rettangolo boundingBox(final Coordinata[] c) {
        Objects.requireNonNull(c, "L'insieme di coordinate non può essere nullo.");
        if (c.length < 1) throw new IllegalArgumentException("L'insieme di coordinate non può essere vuoto.");

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Coordinata cor : c) {
            if (cor.x() < minX) minX = cor.x();
            if (cor.y() < minY) minY = cor.y();

            if (cor.x() > maxX) maxX = cor.x();
            if (cor.y() > maxY) maxY = cor.y();
        }

        return new Rettangolo(new Coordinata(minX, minY), new Coordinata(maxX, maxY));
    }

    /* 
     * EFFECTS: Restituisce l'altezza di this.
    */
    public int altezza() {
        return altoDestra.y() - bassoSinistra.y();
    }

    /* 
     * EFFECTS: Restituisce la larghezza di this.
    */
    public int larghezza() {
        return altoDestra.x() - bassoSinistra.y();
    }

    /* 
     * EFFECTS: Restituisce, una alla volta e senza ripetizioni, tutte le coordinate incluse in this,
     *          partendo da quella in alto a sinistra e finendo con quella in basso a destra.
    */
    public Iterator<Coordinata> iterator() {
        return new Iterator<Coordinata>() {

            private int x = bassoSinistra.x();
            private int y = altoDestra.y();
            private final Coordinata out = new Coordinata(bassoSinistra.x(), bassoSinistra.y()-1);

            @Override
            public boolean hasNext() {
                return !(new Coordinata(x, y).equals(out));
            }

            @Override
            public Coordinata next() {
                if (!hasNext()) throw new NoSuchElementException();
                final Coordinata next = new Coordinata(x, y);

                if (x < altoDestra.x()) {
                    x++;
                } else {
                    x = bassoSinistra.x();
                    y--;
                }

                return next;
            }
            
        };
    }

    @Override
    public String toString() {
        return "[" + bassoSinistra.toString() + ", " + altoDestra.toString() + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Rettangolo)) return false;

        Rettangolo altro = (Rettangolo) obj;
        return bassoSinistra.equals(altro.bassoSinistra) && altoDestra.equals(altro.altoDestra);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bassoSinistra, altoDestra);
    }

}
