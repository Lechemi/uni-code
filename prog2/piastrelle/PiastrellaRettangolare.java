public class PiastrellaRettangolare extends Piastrella {
    /* 
     * Rappresenta una Piastrella rettangolare.
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
     * EFFECTS: Costruisce una piastrella rettangolare di costo unitario c e lati a, b.
     *          Solleva IllegalArgumentException se c ≤ 0, se a ≤ 0, se b ≤ 0.
    */
    PiastrellaRettangolare(final int c, final int a, final int b) {
        super(c);
        if (a <= 0 || b <= 0) throw new IllegalArgumentException("Il lato della piastrella dev'essere positivo.");
        superficie = a * b;
    }

    /* 
     * EFFECTS: Restituisce la superficie della piastrella.
    */
    @Override
    public int superficie() {
        return superficie;
    }
}
