
public abstract class Piastrella implements Pavimento {
    /* 
     * Classe astratta che rappresenta una piastrella.
    */

    // REP
    private final int costoUnitario;

    /* 
     * AF(c) = Costo della piastrella: c.costoUnitario
     *         Superficie della piastrella: c.superficie
     * RI(c) : c.costoUnitario > 0
    */

    /* 
     * EFFECTS: Costruisce una piastrella avente costo unitario c.
     *          Solleva IllegalArgumentException se c ≤ 0.
    */
    Piastrella(final int c) {
        if (c <= 0) throw new IllegalArgumentException("Il costo unitario dev'essere maggiore di 0.");
        costoUnitario = c;
    }

    /* 
     * EFFECTS: Restituisce il costo della piastrella.
    */
    @Override
    public int costo() {
        return costoUnitario;
    }
}
