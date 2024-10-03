package entry;

public class File extends Entry {
    /* 
     * Rappresenta il file di un filesystem.
     * Le istanze di questa classe sono immutabili.
    */

    // REP
    private final int size;

    /* 
     * AF_File(c) = AF_Entry(c)
     *              Dimensione del file: c.size
     * RI_File(c) = RI_Entry(c) && c.size > 0
    */

    /* 
     * EFFECTS: Crea un file di nome n e di dimensione s.
     *          Solleva NoSuchElementException se n è nulla.
     *          Solleva IllegalArgumentException se n è vuota.
    */
    public File(final String n, final int s) {
        super(n);

        if (s <= 0) throw new IllegalArgumentException("La dimensione del file dev'essere maggiore di 0.");

        size = s;
    }

    /* 
     * EFFECTS: Restituisce la dimensione di this.
    */
    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        return getName() + "(" + size + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof File)) return false;

        File other = (File) obj;
        return super.equals(other) && other.size == size;
    }
}
