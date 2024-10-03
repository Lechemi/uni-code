public abstract class MatriceQuadrataAstratta implements MatriceQuadrata {
    /* 
     * Classe astratta che rappresenta una matrice quadrata.
    */

    // REP
    private final int lato;

    /* 
     * AF(c) = c.lato = numero componenti della riga/colonna della matrice
     * RI(c) : c.lato ≥ 0
    */

    /* 
     * EFFECTS: Costruisce una matrice quadrata con lato di lunghezza l.
     *          Solleva IllegalArgumentException se l < 0.
    */
    MatriceQuadrataAstratta(final int l) {
        if (l < 0) throw new IllegalArgumentException("la lunghezza del lato non può essere negativa.");
        lato = l;
    }

    /* 
     * EFFECTS: Restituisce la dimensione di questa matrice, ossia il numero di sue componenti.
    */
    @Override
    public int dim() {
        return lato * lato;
    }

    /* 
     * EFFECTS: Restituisce la dimensione del lato di questa matrice, ossia il numero di componenti
     *          presenti su ogni riga/colonna.
    */
    @Override
    public int lato() {
        return lato;
    }
    
}
