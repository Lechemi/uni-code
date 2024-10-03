public class Magazzino {
    /* 
     * Classe concreta che rappresenta un magazzino logistico, avente una serie di scaffalature.
     * Le istanze di questa classe sono immutabili.
     */

    // REP
    private final Scaffalatura[] scaffalature;

    /* 
     * AF(c) = Il magazzino contiene le scaffalature: c.scaffalature[0], ..., c.scaffalature[n]
     *         Dove n = c.scaffalature.length - 1
     * RI(c) : c.scaffalature ≠ null && ogni elemento di c.scaffalature ≠ null && c.scaffalature non vuoto
    */

    /* 
     * EFFECTS: Costruisce un magazzino composto da n scaffalature vuote.
     *          Solleva IllegalArgumentException se n ≤ 0.
     */
    public Magazzino(final int n) {
        if (n <= 0) throw new IllegalArgumentException("Il numero di scaffalature dev'essere maggiore di zero.");
        scaffalature = new Scaffalatura[n];
        for (int i = 0; i < n; i++) scaffalature[i] = new Scaffalatura();
    }

    /* 
     * EFFECTS: Restituisce il numero di scaffalature di this.
     */
    public int numeroScaffalature() { return scaffalature.length; }

    /* 
     * EFFECTS: Preleva e restituisce il pacco in fondo alla scaffalatura di indice n (gli
     *          indici partono da 0).
     *          Restituisce null se la scaffalatura di indice n non contiene pacchi.
     *          Solleva IllegalArgumentException se n è negativo, se n è maggiore o uguale del numero
     *          di scaffalature di this.
     */
    public Pacco prelevaDa(final int n) {
        if (n < 0 || n >= numeroScaffalature()) throw new IllegalArgumentException("Indice di scaffalatura non valido.");
        return scaffalature[n].preleva();
    }

    /* 
     * EFFECTS: Deposita p sulla scaffalatura di indice n (gli
     *          indici partono da 0).
     *          Solleva IllegalArgumentException se n è negativo, se n è maggiore o uguale del numero
     *          di scaffalature di this. 
     *          Solleva NullPointerException se p è nullo.
     */
    public void depositaIn(final int n, final Pacco p) {
        if (n < 0 || n >= numeroScaffalature()) throw new IllegalArgumentException("Indice di scaffalatura non valido.");
        scaffalature[n].deposita(p);
    }

    /* 
     * EFFECTS: Restituisce il numero di pacchi contenuto nella scaffalatura di indice n (gli
     *          indici partono da 0).
     *          Solleva IllegalArgumentException se n è negativo, se n è maggiore o uguale del numero
     *          di scaffalature di this.
     */
    public int numeroPacchiDi(final int n) {
        if (n < 0 || n >= numeroScaffalature()) throw new IllegalArgumentException("Indice di scaffalatura non valido.");
        return scaffalature[n].numeroPacchi();
    }

    public Pacco prossimoPaccoDi(final int n) {
        if (n < 0 || n >= numeroScaffalature()) throw new IllegalArgumentException("Indice di scaffalatura non valido.");
        return scaffalature[n].prossimoPacco();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Scaffalatura s : scaffalature) sb.append(s.toString() + "\n");
        return sb.toString();
    }

}
