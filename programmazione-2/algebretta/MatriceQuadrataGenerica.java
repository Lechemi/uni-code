import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class MatriceQuadrataGenerica extends MatriceQuadrataAstratta  {
    /* 
     * Implementazione di MatriceQuadrata basata su array.
     * Le istanze di questa classe sono immutabili.
    */

    // REP
    private final int[][] matrice;

    /* 
     * AF_MatriceQuadrataGenerica(c) = AF_MatriceQuadrataAstratta(c) 
     *                                 c.matrice[i][j] = valore dell'intero alla riga i e alla colonna j
     *                                 (con i,j comprese tra 0 e c.lato-1)
     * RI_MatriceQuadrataGenerica(c) = RI_MatriceQuadrataAstratta(c) && lato = sqrt(dimensione della matrice)
    */

    /* 
     * EFFECTS: Costruisce una matrice quadrata avente lato di lunghezza l, e la riempie, partendo
     *          dalla posizione [0, 0] e riempiendo ogni riga da sinistra a destra, con i valori
     *          contenuti in m. Le eventuali posizioni rimaste vengono riempite con degli zeri.
     *          Solleva IllegalArgumentException se la matrice di lato l costruita non basta a contenere 
     *          tutti gli elementi di m (ossia, se sqrt(m) > l), se l < 0.
    */
    public MatriceQuadrataGenerica(final int l, final int[] m) {
        super(l);

        int k = 0;

        matrice = new int[lato()][lato()];
        for (int i=0; i < lato(); i++) {
            for (int j = 0; j < lato(); j++) {
                if (k < m.length) {
                    matrice[i][j] = m[k];
                    k++;
                } else {
                    matrice[i][j] = 0;
                }
            }
        }

        if (k < m.length) throw new IllegalArgumentException("La matrice non può contenere tutti gli elementi dell'array");

    }


    /* 
     * EFFECTS: Restituisce, uno alla volta, le componenti della matrice, partendo da [0,0]
     *          e facendo passare ogni rifa da sinistra a destra.
    */
    @Override
    public Iterator<Integer> iterator() {

        List<Integer> unaRiga = new ArrayList<>();
        for (int i=0; i < lato(); i++) {
            for (int j = 0; j < lato(); j++) {
                unaRiga.add(matrice[i][j]);
            }
        }
        
        return Collections.unmodifiableList(unaRiga).iterator();
    }

    /* 
     * EFFECTS: Restituisce il valore della componente avente indice [i, j] di questa matrice (gli indici partono da 0).
     *          Solleva IndexOutOfBoundsException se i < 0, se j < 0, se i ≥ this.lato, se j ≥ this.lato.
    */
    @Override
    public int val(int i, int j) {
        if (i < 0 || i >= lato()) throw new IndexOutOfBoundsException("L'indice della riga dev'essere compreso tra 0 e la lunghezza del lato (escusa).");
        if (j < 0 || j >= lato()) throw new IndexOutOfBoundsException("L'indice della colonna dev'essere compreso tra 0 e la lunghezza del lato (escusa).");
        
        return matrice[i][j];
    }

    /* 
     * Restituisce il prodotto di questa matrice per lo scalare alpha.
    */
    @Override
    public MatriceQuadrata perScalare(int alpha) {
        int[] risultato = new int[dim()];

        int k = 0;

        for (int i = 0; i < lato(); i++) {
            for (int j = 0; j < lato(); j++, k++) {
                risultato[k] = val(i, j) * alpha;
            }
        }

        return new MatriceQuadrataGenerica(lato(), risultato);
    }

    /* 
     * EFFECTS: Restituisce la somma matriciale tra questa matrice e m (ovviamente possibile solo se 
     *          le due matrici hanno lato di dimensione uguale).
     *          Solleva NullPointerException se m è nulla.
     *          Solleva IllegalArgumentException se la dimensione del lato di m è diversa da quella di questa matrice.
    */
    @Override
    public MatriceQuadrata più(MatriceQuadrata m) {
        if (Objects.requireNonNull(m, "L'altra matrice non può essere nulla").lato() != lato()) {
            throw new IllegalArgumentException("Le due matrici devono avere dimensioni uguali.");
        }

        int[] risultato = new int[dim()];
        int k = 0;

        for (int i = 0; i < lato(); i++) {
            for (int j = 0; j < lato(); j++, k++) {
                risultato[k] = val(i, j) + m.val(i, j);
            }
        }

        return new MatriceQuadrataGenerica(lato(), risultato);
    }

    /* 
     * EFFECTS: Restituisce il prodotto matriciale tra questa matrice e m (ovviamente possibile solo se 
     *          le due matrici hanno lato di dimensione uguale).
     *          Solleva NullPointerException se m è nulla.
     *          Solleva IllegalArgumentException se la dimensione del lato di m è diversa da quella di questa matrice.
    */
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int[] riga : matrice) {
            sb.append(Arrays.toString(riga) + "\n");
        }
        return sb.toString();
    }
    
}
