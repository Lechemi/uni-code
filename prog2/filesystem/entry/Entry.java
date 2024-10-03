package entry;
import java.util.Objects;

public abstract class Entry {
    /* 
     * Classe astratta che rappresenta la entry di un filesystem.
    */

    // REP 
    private final String name;

    /* 
     * AF(c) = Nome della entry: c.name
     * RI(c) : c.name non nullo e non vuoto
    */

    /* 
     * EFFECTS: Crea una Entry di nome n.
     *          Solleva NoSuchElementException se n è nulla.
     *          Solleva IllegalArgumentException se n è vuota.
    */
    Entry(final String n) {
        if (Objects.requireNonNull(n, "Il nome della entry non può essere null.") == "") {
            throw new IllegalArgumentException("Il nome della entry non può essere vuoto.");
        }

        name = n;
    }

    /* 
     * EFFECTS: Restituisce il nome di this.
    */
    public String getName() {
        return name;
    }

    /* 
     * EFFECTS: Restituisce la dimensione di this.
    */
    public abstract int size();

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Entry)) return false;

        Entry other = (Entry) obj;

        return other.name.equals(name);
    }
}