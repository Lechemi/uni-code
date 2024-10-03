package it.unimi.di.sweng.briscola;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.CardStack;
import ca.mcgill.cs.stg.solitaire.cards.Deck;
import org.jetbrains.annotations.NotNull;

public class MazzoBriscola {

    private final CardStack mazzo = new CardStack();

    public MazzoBriscola() {
        Deck m = new Deck();
        while (!m.isEmpty()) {
            Card carta = m.draw();
            int cardOrdinal = carta.getRank().ordinal();
            if (cardOrdinal < 7 || cardOrdinal > 9) mazzo.push(carta);
        }
    }

    public boolean isEmpty() {
        return mazzo.isEmpty();
    }

    public @NotNull Card draw() {
        return mazzo.pop();
    }
}
