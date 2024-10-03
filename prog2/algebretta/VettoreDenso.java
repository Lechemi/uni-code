import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VettoreDenso implements Vettore {
    /* 
     * Implementazione di Vettore basata su un array, utile per vettori di dimensioni piccole.
     * Le istanze di questa classe sono immutabili.
    */

    // REP
    private final int[] vettore;

    /* 
     * AF(c) = c.vettore[i] = i-esimo elemento del vettore (gli indici partono da 0)
     * La RI è vuota perché posso accettare anche il vettore nullo, composto da un array vuoto.
     * In più, int non può essere nullo.
    */

    /* 
     * EFFECTS: Costruisce un vettore le cui componenti corrispondono agli elementi di v.
    */
    public VettoreDenso(final int[] v) {
        vettore = v;
    }

    /* 
     * EFFECTS: Restituisce la dimensione di this, ossia il numero delle sue componenti.
    */
    @Override
    public int dim() {
        return vettore.length;
    }

    /* 
     * EFFECTS: Restituisce il valore dell’i-esima componente di this (gli indici partono da 0).
     *          Solleva IndexOutOfBoundsException se i < 0 oppure se i ≥ (dimensione del vettore).
    */
    @Override
    public int val(int i) {
        if (i < 0 || i >= dim()) throw new IndexOutOfBoundsException("L'indice deve essere positivo e minore della dimensione.");
        return vettore[i];
    }

    /* 
     * EFFECTS: Restituisce il prodotto di this per lo scalare alpha.
    */
    @Override
    public Vettore per(int alpha) {
        int[] risultato = new int[dim()];

        for (int i = 0; i < dim(); i++) risultato[i] = vettore[i] * alpha;

        return new VettoreDenso(risultato);
    }

    /* 
     * EFFECTS: Restituisce la somma vettoriale tra this e v (ovviamente possibile solo se i vettori sono conformi, 
     *          ossia della stessa dimensione).
     *          Solleva NullPointerException se v è nullo.
     *          Solleva IllegalArgumentException se la dimensione di v è diversa da quella di questo vettore.
    */
    @Override
    public Vettore più(Vettore v) {
        if (Objects.requireNonNull(v, "l'altro vettore non può essere null.").dim() != dim()) {
            throw new IllegalArgumentException("l'altro vettore deve avere la stessa dimensione di questo");
        }

        List<Integer> listaRisultato = new ArrayList<>();

        v.forEach(c -> {
            listaRisultato.add(c + val(listaRisultato.size()));
        });

        int[] arrayRisultato = new int[dim()];
        for(int i = 0; i < listaRisultato.size(); i++) arrayRisultato[i] = listaRisultato.get(i);

        return new VettoreDenso(arrayRisultato);
    }

    /* 
     * EFFECTS: Restituisce, una alla volta, le componenti di this.
    */
    @Override
    public Iterator<Integer> iterator() {
        List<Integer> list = Arrays.stream(vettore).boxed().collect(Collectors.toList());
        return Collections.unmodifiableList(list).iterator();
    }

    @Override
    public String toString() {
        return Arrays.toString(vettore);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof VettoreDenso)) return false;

        VettoreDenso other = (VettoreDenso) obj;
        if (other.dim() != dim()) return false;

        for (int i = 0; i < dim(); i++) {
            if (other.val(i) != val(i)) return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}