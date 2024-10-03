package astro;

class Punto {
    /* 
     * Overview:
     * Rappresenta un punto tridimensionale, con coordinate x,y e z. Un tipico punto è P(x, y, z).
     * Le istanze di questa classe sono immutabili.
    */

    // REP
    private final int x;
    private final int y;
    private final int z;

    /* 
     * AF(x, y, z) = P(x, y, z)
    */

    // EFFECTS: Restituisce un punto di coordinate x, y e z.
    Punto(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    // EFFECTS: Restituisce la coordinata x di this.
    public int getX() {
        return x;
    }

    // EFFECTS: Restituisce la coordinata y di this.
    public int getY() {
        return y;
    }

    // EFFECTS: Restituisce la coordinata z di this.
    public int getZ() {
        return z;
    }

    // EFFECTS: Restituisce il punto ottenuto sommando q a this coordinata per coordinata.
    //          Solleva un'eccezione di tipo IllegalArgumentException se q è null.
    public Punto somma(Punto q) {
        if (q == null) throw new IllegalArgumentException();

        return new Punto(this.x + q.x, this.y + q.y, this.z + q.z);
    }

    // EFFECTS: Restituisce il punto ottenuto sottraendo q a this coordinata per coordinata.
    //          Solleva un'eccezione di tipo IllegalArgumentException se q è null.
    public Punto sottrai(Punto q) {
        if (q == null) throw new IllegalArgumentException();

        return new Punto(this.x - q.x, this.y - q.y, this.z - q.z);
    }

    // EFFECTS: Restitusice la somma dei valori assoluti delle coordinate.
    public int norma() {
        return Math.abs(x) + Math.abs(y) + Math.abs(z);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ", " + z + ")"; 
    }

}
