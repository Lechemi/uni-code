import java.util.Objects;

public record Pacco(String prodotto, int altezza) {
    /* 
     * Record che rappresenta un pacco.
     * Essendo un record, le sue istanze sono immutabili
    */

    /* 
     * AF(c) = Il pacco contiene il prodotto di nome c.prodotto, ed ha altezza c.altezza
     * RI(c) : c.prodotto ≠ null && c.prodotto non vuota && c.altezza > 0
    */

    /* 
     * EFFECTS: Costruisce un pacco contenente prodotto e avente un'altezza pari ad altezza.
     *          Solleva IllegalArgumentException se prodotto è vuota, se altezza ≤ 0.
     *          Solleva NullPointerException se prodotto è null.
     */
    public Pacco(final String prodotto, final int altezza) {
        if (Objects.requireNonNull(prodotto, "Il nome del prodotto non può essere null.").isEmpty()) {
            throw new IllegalArgumentException("Il nome del prodotto non può essere vuoto.");
        }
        if (altezza <= 0) throw new IllegalArgumentException("L'altezza del pacco dev'essere maggiore di zero.");

        this.altezza = altezza;
        this.prodotto = prodotto;
    }
}