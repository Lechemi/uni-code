import java.util.LinkedList;
import java.util.List;

public class Cauto extends Robot {
    /* 
     * Classe concreta che rappresenta un robot in grado di spostare più di un pacco se e solo se, 
     * la somma delle altezze dei pacchi accumulati sulla sua superficie di carico non eccede 
     * mai l'altezza massima consentita.
     * Le istanze di questa classe sono immutabili.
    */

    // REP
    public final int altezzaMassima;

    /* 
     * AF_Cauto(c) = AF_Robot(c)
     *               L'altezza dei pacchi che il robot sposta contemporaneamente non può eccedere c.altezzaMassima
     * RI_Cauto(c) = RI_Robot(c) && c.altezzaMassima > 0
    */

    /* 
     * EFFECTS: Costruisce un robot Cauto che opera in m e consente un'altezza massima a.
     *          Solleva NullPointerException se m è nullo.
     */
    public Cauto(final Magazzino m, final int a) {
        super(m);
        if (a <= 0) throw new IllegalArgumentException("L'altezza massima dev'essere maggiore di zero.");
        altezzaMassima = a;
    }

    /* 
     * EFFECTS: Sposta n pacchi dalla scaffalatura di this.magazzino di indice da, a quella di indice a.
     *          Qua spiego come Cauto sposta i pacchi bla bla bla.
     *          Restituisce true se l'operazione viene completata, false altrimenti.
     *          L'operazione non viene eseguita se n è maggiore del numero di pacchi presenti 
     *          nella scaffalatura di indice da, in tal caso, this.magazzino viene lasciato immutato.
     *          Solleva IllegalArgumentException se da è negativo, se da è maggiore o uguale del 
     *          numero di scaffalature di this.magazzino, e a è negativo, se a è maggiore o uguale del 
     *          numero di scaffalature di this.magazzino, se n è negativo.
    */
    @Override
    public boolean sposta(int da, int a, int n) {
        if (n < 0) throw new IllegalArgumentException("Il numero di pacchi da spostare dev'essere positivo.");
        if (n > magazzino.numeroPacchiDi(da)) return false;

        List<Pacco> prelevati = new LinkedList<>();
        prelevati.add(magazzino.prelevaDa(da));
        for (int i = 0; i < n-1; i++) {

            if (magazzino.prossimoPaccoDi(da).altezza() + altezzaPrelevati(prelevati) > altezzaMassima) {
                for (int j = prelevati.size(); j >= 0; j--) magazzino.depositaIn(a, prelevati.get(j));
                prelevati.clear();
            }

            prelevati.add(magazzino.prelevaDa(da));
        }

        return true;
    }

    private int altezzaPrelevati(final List<Pacco> prelevati) {
        int tot = 0;
        for (Pacco p : prelevati) tot += p.altezza();
        return tot;
    }
    
}
