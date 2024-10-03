import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

public class MatriceNulla extends MatriceQuadrataAstratta {
    /* 
     * Classe concreta che rappresenta una Matrice nulla, cioè composta da soli zeri.
     * Le istanze di questa classe sono immutabli.
    */

    /* 
     * EFFECTS: Costruisce una matrice nulla con lato di lunghezza l.
     *          Solleva IllegalArgumentException se l < 0.
    */
    MatriceNulla(int l) {
        super(l);
    }

    /* 
     * EFFECTS: Restituisce il valore della componente avente indice [i, j] di questa matrice (gli indici partono da 0).
     *          Dato che tutte le componenti della matrice nulla sono 0, restituisce 0.
    */
    @Override
    public int val(int i, int j) {
        return 0;
    }

    /* 
     * EFFECTS: Restituisce il prodotto di questa matrice per lo scalare alpha.
     *          Dato che tutte le componenti della matrice nulla sono 0, restituisce una matrice
     *          identica a this.
    */
    @Override
    public MatriceQuadrata perScalare(int alpha) {
        return new MatriceNulla(lato());
    }

    /* 
     * EFFECTS: Restituisce la somma matriciale tra questa matrice e m (ovviamente possibile solo se 
     *          le due matrici hanno lato di dimensione uguale).
     *          Essendo this composta solo da zeri, restituisce una matrice identica a m.
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
                risultato[k] = m.val(i, j);
            }
        }

        return new MatriceQuadrataGenerica(lato(), risultato);
    }

    /* 
     * EFFECTS: Restituisce il prodotto matriciale tra questa matrice e m (ovviamente possibile solo se 
     *          le due matrici hanno lato di dimensione uguale).
     *          Essendo this composta solo da zeri, restituisce una matrice identica a this.
     *          Solleva NullPointerException se m è nulla.
     *          Solleva IllegalArgumentException se la dimensione del lato di m è diversa da quella di questa matrice.
    */
    @Override
    public MatriceQuadrata per(MatriceQuadrata m) {
        if (Objects.requireNonNull(m, "L'altra matrice non può essere nulla").lato() != lato()) {
            throw new IllegalArgumentException("Le due matrici devono avere dimensioni uguali.");
        }
        return new MatriceNulla(lato());
    }

    /* 
     * EFFECTS: Restituisce, uno alla volta, le componenti della matrice, partendo da [0,0]
     *          e facendo passare ogni riga da sinistra a destra.
     *          Chiaramente, essendo this composta solo da zeri, restituisce tanti zeri quante
     *          sono le componenti di this.
    */
    @Override
    public Iterator<Integer> iterator() {
        return Collections.unmodifiableList(new ArrayList<Integer>(Collections.nCopies(dim(), 0))).iterator();
    }
    
}
