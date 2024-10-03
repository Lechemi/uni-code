public interface Vettore extends Iterable<Integer> {
    /* 
     * Interfaccia che rappresenta un vettore a valori interi.
    */

    /* 
     * EFFECTS: Restituisce la dimensione di questo vettore, ossia il numero delle sue componenti.
    */
    int dim();

    /* 
     * EFFECTS: Restituisce il valore dell’i-esima componente di questo vettore (gli indici partono da 0).
     *          Solleva IndexOutOfBoundsException se i < 0 oppure se i ≥ (dimensione del vettore).
    */
    int val(final int i);

    /* 
     * EFFECTS: Restituisce il prodotto del vettore corrente per lo scalare alpha.
    */
    Vettore per(final int alpha);

    /* 
     * EFFECTS: Restituisce la somma vettoriale tra il vettore corrente e v (ovviamente possibile solo se i vettori sono conformi, 
     *          ossia della stessa dimensione).
     *          Solleva NullPointerException se v è nullo.
     *          Solleva IllegalArgumentException se la dimensione di v è diversa da quella di questo vettore.
    */
    Vettore più(final Vettore v);
}