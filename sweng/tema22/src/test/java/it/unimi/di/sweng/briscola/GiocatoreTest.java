package it.unimi.di.sweng.briscola;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.Rank;
import ca.mcgill.cs.stg.solitaire.cards.Suit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class GiocatoreTest {

    @ParameterizedTest
    @ValueSource(strings = {"Michele", "Carlo", "Ricccardo"})
    void nomeTest(String input) {
        Giocatore SUT = new Giocatore(input);
        assertThat(SUT.getName()).isEqualTo(input);
    }

    @Test
    void manoGiocatoreTest() {
        List<Card> carte = List.of(
                Card.get(Rank.THREE, Suit.CLUBS),
                Card.get(Rank.FOUR, Suit.DIAMONDS),
                Card.get(Rank.FIVE, Suit.SPADES)
        );
        Giocatore SUT = new Giocatore("mickers");

        for (Card carta : carte) {
            SUT.dai(carta);
        }

        assertThat(SUT).containsExactlyInAnyOrderElementsOf(carte);

    }

    @Test
    void cartePreseTest() {
        Giocatore SUT = new Giocatore("mickers");
        List<Card> carte = List.of(
                Card.get(Rank.THREE, Suit.CLUBS),
                Card.get(Rank.ACE, Suit.DIAMONDS),
                Card.get(Rank.FIVE, Suit.SPADES),
                Card.get(Rank.KING, Suit.SPADES)
        );

        for (Card carta : carte) {
            SUT.prendi(carta);
        }

        assertThat(SUT.cartePrese()).toIterable().containsExactlyInAnyOrderElementsOf(carte);
    }

    @ParameterizedTest
    @CsvSource({"'0 5 10 10', 15", "'6 5 4 3', 0", "'2 2', 20"})
    void calcolaPuntiTest(String input, int output) {
        List<Card> carte = new ArrayList<>();

        for (String i: input.split(" ")) {
            carte.add(Card.get(i));
        }

        Giocatore SUT = new Giocatore("mickers");

        for (Card carta : carte) {
            SUT.prendi(carta);
        }

        assertThat(SUT.getPunti()).isEqualTo(output);
    }

    @Test
    void giocaTest() {
        Giocatore SUT = new Giocatore("mickers");
        List<Card> carte = List.of(
                Card.get(Rank.THREE, Suit.CLUBS),
                Card.get(Rank.FOUR, Suit.DIAMONDS),
                Card.get(Rank.FIVE, Suit.SPADES)
        );

        for (Card carta : carte) {
            SUT.dai(carta);
        }

        Card giocata = SUT.giocaCarta();

        assertThat(SUT).hasSize(2);
        assertThat(giocata).isIn(carte);
    }
}
