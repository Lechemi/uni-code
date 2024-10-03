import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Path {
    /* 
     * Rappresenta un percorso nel filesystem.
     * Le istanze di questa classe non sono mutabili.
    */

    static final String SEPARATOR = ":";

    // REP
    private final List<String> entries;
    public final boolean isAbsolute;
    public final boolean rootPath;
    private final String stringPath;

    /* 
     * AF(c) = Lista ordinata dei nomi delle entries da attraversare nel filesystem: c.entries
     *         Indicatore del fatto che il percorso è assoluto: c.isAbsolute (true => sì; false => no)
     * RI(c) : entries e i suoi elementi devono essere non nulli &&
     *         gli elementi di entries non possono essere vuoti
    */

    /* 
     * EFFECTS: Costruisce un Path a partire da p. Se p inizia per SEPARATOR, il percorso creato sarà
     *          assoluto, altrimenti relativo.
     *          Solleva NullPointerException se p è nulla.
     *          Solleva IllegalArgumentException se p è vuota.
     *          Solleva InvalidPathException se p non rappresenta un percorso.
    */
    public Path(final String p) throws InvalidPathException {
        if (Objects.requireNonNull(p, "Il percorso non può essere null.") == "") {
            throw new IllegalArgumentException("Il percorso non può essere vuoto");
        }

        rootPath = p == SEPARATOR ? true : false;

        isAbsolute = p.startsWith(SEPARATOR) ? true : false;

        if (rootPath) {
            entries = new ArrayList<>();
        } else {
            try {
                entries = new ArrayList<>(Arrays.asList(p.split(SEPARATOR)).subList(1, Arrays.asList(p.split(SEPARATOR)).size()));
            } catch (Exception e) {
                throw new InvalidPathException("percorso non valido:", p);
            }
        }

        stringPath = p;
    }

    /* 
     * EFFECTS: Restituisce il nome dell'ultima entry di this.
    */
    public String lastEntryName() { return entries.get(entries.size()-1); }

    /* 
     * EFFECTS: Restituisce in ordine i nomi delle entries che compongono this, tralasciando l'ultima.
    */
    public Iterator<String> entryNamesWithoutLast() {
        return entries.subList(0, entries.size()-1).iterator();
    }

    /* 
     * EFFECTS: Restituisce in ordine i nomi delle entries che compongono this.
    */
    public Iterator<String> entryNames() {
        return entries.iterator();
    }

    @Override
    public String toString() {
        return stringPath;
    }
}
