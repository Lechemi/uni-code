package it.unimi.di.sweng.briscola;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.Rank;
import ca.mcgill.cs.stg.solitaire.cards.Suit;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.MockedConstruction;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BriscolaTest {

    @Test
    void carteInizialiTest() {
        Giocatore giocatore1 = new Giocatore("Mickers");
        Giocatore giocatore2 = new Giocatore("Rickers");
        MazzoBriscola mazzo = new MazzoBriscola();
        Briscola SUT = new Briscola(giocatore1, giocatore2, mazzo);

        assertThat(giocatore1).hasSameSizeAs(giocatore2).hasSize(3);
    }

    @Test
    void pescaBriscolaTest() {
        Giocatore giocatore1 = new Giocatore("Mickers");
        Giocatore giocatore2 = new Giocatore("Rickers");
        try(MockedConstruction<MazzoBriscola> mocked = mockConstruction(MazzoBriscola.class, (mock, context) -> {
            when(mock.draw()).thenReturn(Card.get(Rank.ACE, Suit.SPADES));
        })) {
            MazzoBriscola mazzo = new MazzoBriscola();
            Briscola SUT = new Briscola(giocatore1, giocatore2, mazzo);

            assertThat(SUT.getBriscola()).isEqualTo(Card.get(Rank.ACE, Suit.SPADES));
        }
    }

    @ParameterizedTest
    @CsvSource({"'0 5', 1", "'6 2', -1", "'2 2', 0"})
    void confrontaCarteStessoSemeTest(String input, int output) {
        List<Card> daConfrontare = new ArrayList<>();

        for (String i: input.split(" ")) {
            daConfrontare.add(Card.get(i));
        }

        MazzoBriscola mazzo = mock(MazzoBriscola.class);
        Giocatore g1 = mock(Giocatore.class);
        Giocatore g2 = mock(Giocatore.class);
        Briscola SUT = new Briscola(g1, g2, mazzo);

        int risultatoConfronto = SUT.confrontaCarte(daConfrontare.get(0), daConfrontare.get(1));

        assertThat(risultatoConfronto).isEqualTo(output);
    }

    @ParameterizedTest
    @CsvSource({"'1 13', 1", "'29 30', -1", "'51 12', -1", "'37 13', 1"})
    void confrontaCarteTest(String input, int output) {
        List<Card> daConfrontare = new ArrayList<>();

        for (String i: input.split(" ")) {
            daConfrontare.add(Card.get(i));
        }

        Card miaBriscola = Card.get(Rank.ACE, Suit.CLUBS);

        try(MockedConstruction<MazzoBriscola> mocked = mockConstruction(MazzoBriscola.class, (mock, context) -> {
            when(mock.draw()).thenReturn(miaBriscola);
        })) {
            MazzoBriscola mazzo = new MazzoBriscola();
            Giocatore g1 = mock(Giocatore.class);
            Giocatore g2 = mock(Giocatore.class);
            Briscola SUT = new Briscola(g1, g2, mazzo);

            int risultatoConfronto = SUT.confrontaCarte(daConfrontare.get(0), daConfrontare.get(1));

            assertThat(risultatoConfronto).isEqualTo(output);
        }
    }

}
