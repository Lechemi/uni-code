package it.unimi.di.sweng.briscola;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Giocatore implements Iterable<Card> {

    private final List<Card> mano = new ArrayList<>();
    private final List<Card> prese = new ArrayList<>();

    private final String nome;

    public Giocatore(@NotNull String n) {
        nome = n;
    }

    public @NotNull String getName() {
        return nome;
    }

    @NotNull
    @Override
    public Iterator<Card> iterator() {
        return Collections.unmodifiableCollection(mano).iterator();
    }

    public void dai(@NotNull Card carta) {
        mano.add(carta);
    }

    public void prendi(@NotNull Card carta) {
        prese.add(carta);
    }

    public @NotNull Iterator<Card> cartePrese() {
        return Collections.unmodifiableCollection(prese).iterator();
    }

    public int getPunti() {
        int punti = 0;

        Iterator<Card> prese = cartePrese();

        while (prese.hasNext()) {
            punti += Briscola.valoreCarta(prese.next());
        }

        return punti;
    }

    public @NotNull Card giocaCarta() {
        assert !mano.isEmpty();
        return mano.remove(0);
    }
}
