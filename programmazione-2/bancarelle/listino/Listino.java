package bancarelle.listino;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;

import bancarelle.Giocattolo;

public class Listino {
    /* 
     * Classe rappresentante un listino prezzi per giocattoli.
    */

    // REP
    private final Map<Giocattolo, Prezzo> prezzi = new HashMap<>();

    /* 
     * AF(c) = c.prezzi[k] = prezzo del giocattolo k 
     * RI(c) : c.prezzi ≠ null
     *         ogni chiave e ogni valore di c.prezzi dev'essere non null
    */

    /* 
     * EFFECTS: Costruisce un listino vuoto.
    */
    Listino() {}

    /* 
     * EFFECTS: Restituisce true se il listino conosce il prezzo di giocattolo, false altrimenti.
     *          Solleva NullPointerException se giocattolo è nullo.
    */
    public boolean conosce(final Giocattolo giocattolo) {
        return prezzi.containsKey(
            Objects.requireNonNull(giocattolo, "Il giocattolo non può essere nullo.")
        );
    }
    
    /* 
     * EFFECTS: Restituisce il prezzo unitario di giocattolo.
     *          Solleva NoSuchElementException se this non conosce il prezzo unitario di giocattolo.
     *          Solleva NullPointerException se giocattolo è nullo.
    */
    public int prezzoUnitario(final Giocattolo giocattolo) { 
        if (!conosce(
            Objects.requireNonNull(giocattolo, "Il giocattolo non può essere nullo.")
        )) throw new NoSuchElementException("Il giocattolo non è presente nel listino");

        return prezzi.get(giocattolo).getPrezzoUnitario();
    }

    /* 
     * MODIFIES: this
     * EFFECTS: Imposta a nuovoPrezzo il prezzo di giocattolo.
     *          Se giocattolo non è presente in this, lo aggiunge con prezzo nuovoPrezzo.
     *          Solleva NullPointerException se giocattolo è nullo, se prezzo è nullo.
    */
    public void impostaPrezzoUnitario(final Giocattolo giocattolo, final Prezzo nuovoPrezzo) {
        prezzi.put(
            Objects.requireNonNull(giocattolo, "Il giocattolo non può essere nullo."), 
            Objects.requireNonNull(nuovoPrezzo, "Il prezzo non può essere nullo.")
        );
    }

    /* 
     * EFFETCS: Restituisce il prezzo per q giocattoli di tipo giocattolo.
     *          Solleva NoSuchElementException se this non conosce il prezzo di giocattolo.
     *          Solleva NullPointerException se giocattolo è nullo.
     *          Solleva IllegalArgumentException se q è negativa.
    */
    public int calcolaPrezzo(final int q, final Giocattolo giocattolo) {
        if (q < 0) throw new IllegalArgumentException("La quantità non può essere negativa.");

        if (!conosce(
            Objects.requireNonNull(giocattolo, "Il giocattolo non può essere nullo.")
        )) throw new NoSuchElementException("Il giocattolo non è presente nel listino");

        return prezzi.get(giocattolo).calcolaPrezzo(q);
    }

}
