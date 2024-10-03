import java.util.Iterator;
import java.util.Objects;

public class MatriceDiagonale extends MatriceQuadrataAstratta {
    /* 
     * Implementazione della matrice quadrata basata su array.
     * Le istanze di questa classe sono immutabili.
    */

    // REP
    private final int[] diagonale;

    /* 
     * AF(c) = c.diagonale[i] = valore della componente in posizione [i, i] della matrice 
     *         (con i compreso tra 0 e lato() [escluso]) + AF_MatriceQuadrataAstratta(c)
     * 
     * RI_MatriceDiagonale(c) = RI_MatriceQuadrataAstratta(c) && c.lato = c.diagonale.length
    */

    /* 
     * EFFECTS: Costruisce una matrice diagonale la cui diagonale è formata dagli elementi di d.
    */
    MatriceDiagonale(final int[] d) {
        super(d.length);
        diagonale = d;
    }

    @Override
    public Iterator<Integer> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int val(int i, int j) {
        return i == j ? diagonale[i] : 0;
    }

    @Override
    public MatriceQuadrata perScalare(int alpha) {
        int[] diagonaleRisultato = new int[lato()];

        for (int i = 0; i < lato(); i++) {
            diagonaleRisultato[i] = diagonale[i] * alpha;
        }

        return new MatriceDiagonale(diagonaleRisultato);
    }

    @Override
    public MatriceQuadrata più(MatriceQuadrata m) {
        if (Objects.requireNonNull(m, "L'altra matrice non può essere nulla").lato() != lato()) {
            throw new IllegalArgumentException("Le due matrici devono avere dimensioni uguali.");
        }

        int[] risultato = new int[dim()];
        int k = 0;

        for (int i = 0; i < lato(); i++) {
            for (int j = 0; j < lato(); j++, k++) {
                if (i == j) {
                    risultato[k] = val(i, j) + m.val(i, j);
                } else {
                    risultato[k] = m.val(i, j);
                }
                
            }
        }

        return new MatriceQuadrataGenerica(lato(), risultato);
    }

    @Override
    public MatriceQuadrata per(MatriceQuadrata m) {
        if (Objects.requireNonNull(m, "L'altra matrice non può essere nulla").lato() != lato()) {
            throw new IllegalArgumentException("Le due matrici devono avere dimensioni uguali.");
        }

        int[] risultato = new int[dim()];
        int k = 0;

        for (int i = 0; i < lato(); i++) {
            for (int j = 0; j < lato(); j++, k++) {
                risultato[k] = prodottoRigaColonna(i, j, m);
            }
        }

        return new MatriceQuadrataGenerica(lato(), risultato);
    }

    /* 
     * EFFECTS: Metodo di utilità che restituisce il prodotto riga-colonna tra la riga r 
     *          e la colonna c (con r, c indici);
    */
    private int prodottoRigaColonna(final int r, final int c, final MatriceQuadrata m) {
        int res = 0;

        for (int i = 0; i < lato(); i++) {
            res += (val(r, i) * m.val(i, c));
        }

        return res;
    }
    
}
