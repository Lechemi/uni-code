package bancarelle.listino;

public class PrezzoTrePerDue extends Prezzo {
    /* 
     * Rappresenta la politica di prezzo tre per due.
     * Le istanze di questa classe sono immutabili.
    */

    /* 
     * AF_PrezzoUnitario(c) = AF_Prezzo(c)
     * RI_PrezzoUnitario(c) = RI_Prezzo(c)
    */

    /* 
     * EFFECTS: Costruisce un prezzo con politica tre per due avente prezzo unitario p.
     *          Solleva IllegalArgumentException se p < 0.
    */
    PrezzoTrePerDue(int p) {
        super(p);
    }

    /* 
     * EFFETCS: Restituisce il prezzo totale di num articoli secondo la politica 
     *          moltiplicativa (num * prezzoUnitario).
     *          Solleva IllegalArgumentException se q < 0.
    */
    @Override
    public int calcolaPrezzo(final int q) {
        if (q < 0) throw new IllegalArgumentException("La quantità di articoli dev'essere positiva.");
        
        int remaining = q % 3;
        return (((q - remaining)/3)*2)*getPrezzoUnitario() + remaining * getPrezzoUnitario();
    }
}
