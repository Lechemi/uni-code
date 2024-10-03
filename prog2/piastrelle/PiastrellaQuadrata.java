public class PiastrellaQuadrata extends Piastrella {
    /* 
     * Rappresenta una Piastrella quadrata.
     * Le istanze di questa classe sono immutabili.
    */

    // REP
    private final int superficie;

    /* 
     * AF_PiastrellaQuadrata(c) = AF_Piastrella(c)
     *                            Superficie della piastrella = c.superficie
     *                            Lato della piastrella = c.lato
     * RI_PiastrellaQuadrata(c) = RI_Piastrella(c) && c.superficie > 0
    */

    /* 
     * EFFECTS: Costruisce una piastrella quadrata di costo unitario c e lato lungo l.
     *          Solleva IllegalArgumentException se c ≤ 0, se l ≤ 0.
    */
    PiastrellaQuadrata(final int c, final int l) {
        super(c);
        if (l <= 0) throw new IllegalArgumentException("Il lato della piastrella dev'essere positivo.");
        superficie = l * l;
    }

    /* 
     * EFFECTS: Restituisce la superficie della piastrella.
    */
    @Override
    public int superficie() {
        return superficie;
    }
    
}
