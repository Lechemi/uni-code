import java.util.Objects;

public record Indirizzo(int riga, char colonna) {
    /* 
     * Record che appresenta l'indirizzo di una cella in un foglio di calcolo.
     * Essendo un record, le sue istanze sono immutabili.
    */

    /* 
     * AF(c) = Indirizzo della cella: colonna c.colonna, riga c.riga
     * RI(c) : c.riga ≥ 1 && c.colonna dev'essere una lettera maiuscola
    */

    /* 
     * EFFECTS: Costruisce un nuovo indirizzo la cui riga corrisponde a riga e la cui colonna
     *          corrisponde a colonna.
     *          Solleva IllegalArgumentException se riga < 1, se colonna non è una lettera
     *          maiuscola.
    */
    public Indirizzo {
        if (riga < 1) throw new IllegalArgumentException(
            "La riga dev'essere positiva."
        );
        if ((int) colonna < 65 || (int) colonna > 90) throw new IllegalArgumentException(
            "La colonna dev'essere una lettera alfabetica maiuscola."
        );
    }

    /* 
     * EFFECTS: Restituisce un indirizzo a partire da una stringa (s) nel formato LN, dove 
     *          L è una lettera maiuscola e N rappresenta un numero intero positivo.
     *          Solleva NullPointerException se s è nulla.
     *          Solleva InvalidFormatException se il primo carattere non è una lettera
     *          maiuscola, se i caratteri successivi al primo non sono convertibili in un intero positivo, se 
     *          la lunghezza di s è minore o uguale a 1.
    */
    public static Indirizzo daStringa(final String s) throws InvalidFormatException {
        if (Objects.requireNonNull(s, "La stringa non può essere nulla").length() <= 1) {
            throw new InvalidFormatException("Formato della stringa non valido");
        }

        final char colonna = s.charAt(0);
        final String riga = s.substring(1);

        try {
            final int r = Integer.parseInt(riga);
            return new Indirizzo(r, colonna);
        } catch (Exception e) {
            throw new InvalidFormatException("Formato della stringa non valido");
        }
    }

    @Override
    public String toString() {
        return String.valueOf(colonna) + String.valueOf(riga);
    }
}
