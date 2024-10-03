public record Coordinata(int x, int y) {
    /* 
     * Record le cui istanze (immutabili) rappresentano una coppia di coordinate (intere) 
     * del piano cartesiano.
    */

    /* 
     * AF(c) = La coordinata ha valore (c.x, c.y)
     * RI(c) : true
    */

    /* 
     * EFFECTS: Costruisce una coordinata del valore di (x, y).
    */
    public Coordinata(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "<" + String.valueOf(x) + ", " + String.valueOf(y) + ">";
    }
}