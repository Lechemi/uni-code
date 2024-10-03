package it.unimi.di.sweng.briscola;

import ca.mcgill.cs.stg.solitaire.cards.Card;
import ca.mcgill.cs.stg.solitaire.cards.Rank;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class MazzoBriscolaTest {
    @Test
    void numeroDiCarteTest() {
        MazzoBriscola SUT = new MazzoBriscola();

        int pescate = 0;
        while (!SUT.isEmpty()) {
            SUT.draw();
            pescate++;
        }

        assertThat(pescate).isEqualTo(40);
    }

    @Test
    void occorrenzePerRankCartaTest() {
        MazzoBriscola SUT = new MazzoBriscola();

        Map<Rank, Integer> occorrenze = new HashMap<>();

        while (!SUT.isEmpty()) {
            Rank r = SUT.draw().getRank();
            occorrenze.put(r, occorrenze.getOrDefault(r, 0) + 1);
        }

        for (Integer numeroOccorrenze : occorrenze.values()) {
            assertThat(numeroOccorrenze).isEqualTo(4);
        }
    }
}
