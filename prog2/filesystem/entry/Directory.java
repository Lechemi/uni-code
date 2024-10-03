package entry;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Directory extends Entry implements Iterable<Entry> {
    /* 
     * Rappresenta la directory di un filesystem.
     * Le istanze di questa classe sono mutabili.
    */

    // REP
    private final List<Entry> children = new ArrayList<>();
    private final Directory parent;
    private int size;

    /* 
     * AF_File(c) = AF_Entry(c)
     *              Dimensione della directory: c.size
     *              Entry figlie della directory: c.children
     *              Directory parent della directory: c.parent
     * 
     * RI_File(c) = RI_Entry(c) && 
     *              c.children diversa non null && ogni suo elemento diverso non null &&
     *              c.size uguale alla somma delle dimensioni delle entry figlie della directory
    */

    /* 
     * EFFETCS: Crea una directory di nome n e "figlia" di p (che può anche essere nulla).
     *          Solleva NullPointerException se n è nulla.
     *          Solleva IllegalArgumentException se n è vuota.
    */
    public Directory(final String n, final Directory p) {
        super(n);

        this.parent = p;
    }

    /* 
     * EFFECTS: Restituisce la directory parent di this.
    */
    public Directory parent() {
        return parent;
    }

    /* 
     * EFFECTS: Restituisce la dimensione di this.
    */
    @Override
    public int size() {
        int s = 0;
        for (Entry e : children) s += e.size();
        return s;
    }

    /* 
     * EFFECTS: Restituisce true se this contiene e, false altrimenti.
     *          Solleva NullPointerException se e è nulla.
    */
    public boolean contains(final Entry e) { 
        return children.contains(Objects.requireNonNull(e, "La entry non può essere nulla."));
    }

    /* 
     * EFFECTS: Restituisce la entry "figlia" di this avente nome n.
     *          Solleva NullPointerException se n è nulla.
     *          Solleva IllegalArgumentException se n è vuota.
     *          Solleva FileNotFoundException se this non contiene una entry di nome n.
    */
    public Entry entryFromName(final String n) throws FileNotFoundException { 
        if (Objects.requireNonNull(n, "Il nome della entry non può essere null.") == "") {
            throw new IllegalArgumentException("Il nome della entry non può essere vuoto.");
        }

        for (Entry e : children) if (e.getName().equals(n)) return e;

        throw new FileNotFoundException("La entry di nome " + n + " non è presente.");
    }

    /* 
     * EFFECTS: Aggiunge e alle entry "figlie" di this.
     *          Solleva NullPointerException se e è nulla.
     *          Solleva FileAlreadyExistsException se this già contiene e.
    */
    public void addEntry(final Entry e) throws FileAlreadyExistsException {
        if (contains(Objects.requireNonNull(e, "La entry non può essere nulla."))) {
            throw new FileAlreadyExistsException("La directory già contiene la entry " + e.getName());
        }

        children.add(e);
    }

    /* 
     * EFFECTS: Restituisce le entry figlie di this una alla volta.
    */
    public Iterator<Entry> children() {
        return Collections.unmodifiableList(children).iterator();
    }
    // Bisognerebbe usare questo qua sotto in realtà
    @Override
    public Iterator<Entry> iterator() {
        return Collections.unmodifiableList(children).iterator();
    }

    @Override
    public String toString() {
        return getName() + "*";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Directory)) return false;

        Directory other = (Directory) obj;
        return super.equals(other) && other.size == size;
    }

    
}
