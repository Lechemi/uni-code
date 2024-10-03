package bancarelle.listino;

public class PrezzoMoltiplicativo extends Prezzo {
    /* 
     * Rappresenta la politica di prezzo moltiplicativa.
     * Le istanze di questa classe sono immutabili.
    */

    /* 
     * AF_PrezzoUnitario(c) = AF_Prezzo(c)
     * RI_PrezzoUnitario(c) = RI_Prezzo(c)
    */

    /* 
     * EFFECTS: Costruisce un prezzo con politica moltiplicativa, avente prezzo unitario p.
     *          Solleva IllegalArgumentException se p < 0.
    */
    PrezzoMoltiplicativo(int p) {
        super(p);
    }

    /* 
     * EFFETCS: Restituisce il prezzo totale di num articoli secondo la politica 
     *          di prezzo tre per due. 
     *          Solleva IllegalArgumentException se q < 0.
    */
    @Override
    public int calcolaPrezzo(final int q) {
        if (q < 0) throw new IllegalArgumentException("La quantità di articoli dev'essere positiva.");
        
        return q * getPrezzoUnitario();
    }
    
}
