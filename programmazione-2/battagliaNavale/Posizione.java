import java.util.Objects;

public record Posizione(char colonna, int riga) {
    /* 
     * Record (immutabile) che rappresenta la posizione di una griglia di battaglia navale.
    */

    /* 
     * AF(c) = Posizione: colonna c.colonna, riga c.riga
     * RI(c) : colonna dev'essere una lettera maiuscola compresa, secondo l'ordine alfabetico, tra A e J (incluse) &&
     *         riga dev'essere compresa tra 0 e 9 (inclusi)
    */

    /* 
     * EFFECTS: Costruisce una posizione avente colonna uguale a colonna e riga uguale a riga.
     *          Solleva IllegalArgumentException se riga non è compreso tra 0 e 9 (inclusi),
     *          se colonna non è compresa, secondo l'ordine alfabetico, tra 'A' e 'J' (incluse).
     */
    public Posizione(final char colonna, final int riga) {
        if (riga < 0 || riga > 9) throw new IllegalArgumentException("La riga dev'essere compresa tra 0 e 9.");
        if (colonna < 65 || colonna > 74) throw new IllegalArgumentException("La colonna dev'essere compresa tra 'A' e 'J'.");

        this.colonna = colonna;
        this.riga = riga;
    }

    /* 
     * EFFECTS: Restituisce la posizione corrispondente a quella rappresentata da una stringa (s)
     *          nel formato "CR", dove C è la lettera che indica la colonna e R rappresenta l'intero
     *          che indica la riga.
     *          Solleva IllegalArgumentException se s non è nel formato corretto, se non
     *          rappresenta una posizione valida, se è vuota.
     *          Solleva NullPointerException se s è nulla.
    */
    public static Posizione daStringa(final String s) {
        if (Objects.requireNonNull(s, "La stringa non può essere nulla.").isEmpty()) {
            throw new IllegalArgumentException("La stringa non può essere vuota.");
        }

        if (s.length() != 2) throw new IllegalArgumentException("Formato della stringa non valido");

        Integer riga = Integer.valueOf(s.substring(1));
        return new Posizione(s.charAt(0), riga);
    }

    /* 
     * EFFECTS: Restituisce la posizione ottenuta spostandosi sulla griglia di deltaC posizioni in 
     *          orizzontale e di deltaR posizioni in verticale.
     *          Solleva IllegalArgumentException se lo spostamento porta
     *          ad una posizione non valida.
     */
    public Posizione più(final int deltaC, final int deltaR) {
        return new Posizione((char) (colonna + deltaC), riga + deltaR);
    }

    @Override
    public String toString() {
        return String.valueOf(colonna) + String.valueOf(riga);
    }
}