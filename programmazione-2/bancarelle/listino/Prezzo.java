package bancarelle.listino;

public abstract class Prezzo {
    /* 
     * Classe astratta che rappresenta il prezzo di un articolo.
    */

    // REP
    private final int prezzoUnitario;

    /* 
     * AF(c) = Prezzo unitario dell'articolo = c.prezzoUnitario
     * RI(c) : c.prezzoUnitario ≥ 0
    */

    /* 
     * EFFECTS: Costruisce un prezzo avente prezzo unitario p.
     *          Solleva IllegalArgumentException se p < 0.
    */
    Prezzo(final int p) {
        if (p < 0) throw new IllegalArgumentException("p non può essere negativo.");
        prezzoUnitario = p;
    }

    /* 
     * EFFECTS: Restituisce il prezzo unitario di this.
    */
    public int getPrezzoUnitario() { return prezzoUnitario; }

    /* 
     * EFFECTS: Restituisce il prezzo per una quantità q di articoli.
     *          Solleva IllegalArgumentException se q < 0.
    */
    public abstract int calcolaPrezzo(final int q);

}
