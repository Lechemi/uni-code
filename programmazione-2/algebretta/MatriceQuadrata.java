public interface MatriceQuadrata extends Iterable<Integer> {
    /* 
     * Interfaccia che rappresenta una matrice quadrata a valori interi.
    */

    /* 
     * EFFECTS: Restituisce la dimensione di questa matrice, ossia il numero di sue componenti.
    */
    int dim();

    /* 
     * EFFECTS: Restituisce la dimensione del lato di questa matrice, ossia il numero di componenti
     *          presenti su ogni riga/colonna.
    */
    int lato();

    /* 
     * EFFECTS: Restituisce il valore della componente avente indice [i, j] di questa matrice (gli indici partono da 0).
     *          Solleva IndexOutOfBoundsException se i < 0, se j < 0, se i ≥ (dimensione del lato), se j ≥ (dimensione del lato).
    */
    int val(final int i, final int j);

    /* 
     * Restituisce il prodotto di questa matrice per lo scalare alpha.
    */
    MatriceQuadrata perScalare(final int alpha);

    /* 
     * EFFECTS: Restituisce la somma matriciale tra questa matrice e m (ovviamente possibile solo se 
     *          le due matrici hanno lato di dimensione uguale).
     *          Solleva NullPointerException se m è nulla.
     *          Solleva IllegalArgumentException se la dimensione del lato di m è diversa da quella di questa matrice.
    */
    MatriceQuadrata più(final MatriceQuadrata m);

    /* 
     * EFFECTS: Restituisce il prodotto matriciale tra questa matrice e m (ovviamente possibile solo se 
     *          le due matrici hanno lato di dimensione uguale).
     *          Solleva NullPointerException se m è nulla.
     *          Solleva IllegalArgumentException se la dimensione del lato di m è diversa da quella di questa matrice.
    */
    MatriceQuadrata per(final MatriceQuadrata m);
}
