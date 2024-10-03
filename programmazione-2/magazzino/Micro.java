public class Micro extends Robot {
    /* 
     * Classe concreta che rappresenta un robot in grado di spostare un solo pacco alla volta.
     * Le istanze di questa classe sono immutabili.
    */

    /* 
     * AF_Micro(c) = AF_Robot(c)
     * RI_Micro(c) = RI_Robot(c)
    */

    /* 
     * EFFECTS: Costruisce un roobot Micro che opera in m.
     *          Solleva NullPointerException se m è nullo.
    */
    public Micro(final Magazzino m) {
        super(m);
    }

    /* 
     * EFFECTS: Sposta n pacchi dalla scaffalatura di this.magazzino di indice da, a quella di indice a,
     *          uno alla volta.
     *          Restituisce true se l'operazione viene completata, false altrimenti.
     *          L'operazione non viene eseguita se n è maggiore del numero di pacchi presenti 
     *          nella scaffalatura di indice da, in tal caso, this.magazzino viene lasciato immutato.
     *          Solleva IllegalArgumentException se da è negativo, se da è maggiore o uguale del 
     *          numero di scaffalature di this.magazzino, e a è negativo, se a è maggiore o uguale del 
     *          numero di scaffalature di this.magazzino, se n è negativo.
     * 
     * il controllo su da e a lo lascio a Magazzino
    */
    @Override
    public boolean sposta(final int da, final int a, final int n) {
        if (n < 0) throw new IllegalArgumentException("Il numero di pacchi da spostare dev'essere positivo.");
        if (n > magazzino.numeroPacchiDi(da)) return false;

        for (int i = 0; i < n; i++) magazzino.depositaIn(a, magazzino.prelevaDa(da));
        return true;
    }
    
}
