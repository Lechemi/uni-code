import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

public class Range extends SerieIndirizzi {
    /* 
     * Classe concreta che rappresenta un range di indirizzi.
     * Le istanze di questa classe sono immutabili.
    */

    // REP
    private final Indirizzo[] range;

    /* 
     * AF(c) = - se o1.riga == o2.riga, Range: non ho voglia di scriverlo 
     *         - se o1.colonna == o2.colonna, Range: non ho voglia di scriverlo
     * RI(c) : c.o1 e c.o2 non null && !c.o1.equals(o2) && 
     *         c.o1 e c.o2 devono avere in comune la riga o la colonna &&
     *         c.range.length ≥ 2 && c.range non null &&
     *         ogni elemento di c.range non null && 
     *         il primo e l'ultimo elemento di c.range devono essere rispettivamente o1 e o2 &&
     *         per ogni ogni c.range[i] con 0 < i < (c.range.length - 1):
     *          - se o1.riga è uguale a o2.riga, c.range[i].riga dev'essere uguale a o1.riga
     *          - se o1.colonna è uguale a o2.colonna, c.range[i].colonna dev'essere uguale a o1.colonna
    */

    /* 
     * EFFECTS: Costuisce un range di indirizzi, i cui indirizzi iniziale e finale sono 
     *          rispettivamente o1 e o2.
     *          Solleva NullPointerException se o1 è nullo, se o2 è nullo.
     *          Solleva IllegalArgumentException se o1 è uguale a o2, se o1 e o2 non hanno in comune
     *          nè la riga nè la colonna.
    */
    public Range(final Indirizzo o1, final Indirizzo o2) {
        super(o1, o2);
    
        if (o1.riga() == o2.riga()) {
            range = new Indirizzo[Math.abs(o1.colonna() - o2.colonna()) + 1];
            int colonnaCorrente = o1.colonna();

            for (int i = 0; i < range.length; i++) {
                range[i] = new Indirizzo(o1.riga(), (char) colonnaCorrente);
                if (o1.colonna() < o2.colonna()) {
                    colonnaCorrente++;
                } else {
                    colonnaCorrente--;
                }
            }

            return;
        }

        if (o1.colonna() == o2.colonna()) {
            range = new Indirizzo[Math.abs(o1.riga() - o2.riga()) + 1];
            int rigaCorrente = o1.riga();

            for (int i = 0; i < range.length; i++) {
                range[i] = new Indirizzo(rigaCorrente, o1.colonna());
                if (o1.riga() < o2.riga()) {
                    rigaCorrente++;
                } else {
                    rigaCorrente--;
                }
            }

            return;
        }

        throw new IllegalArgumentException("I due indirizzi devono avere in comune la riga o la colonna.");
    }

    /* 
     * EFFECTS: Restituisce un Range a partire da una stringa nel formato 'I1:I2', dove 
     *          I1 e I2 sono stringhe rappresentanti due indirizzi.
     *          Solleva NullPointerException se s è null.
     *          Solleva InvalidFormatException se s non è nel formato 'I1:I2', se I1 non è convertibile
     *          ad un indirizzo, se I2 non è convertibile ad un indirizzo.
    */
    public static Range daStringa(final String s) throws InvalidFormatException {
        if (!Objects.requireNonNull(s, "La stringa non può essere nulla.").contains(":")) {
            throw new InvalidFormatException("Formato del range di indirizzi non valido.");
        }

        String[] indirizzi = s.split(":");
        if (indirizzi.length < 2) throw new InvalidFormatException("Formato del range di indirizzi non valido.");

        return new Range(Indirizzo.daStringa(indirizzi[0]), Indirizzo.daStringa(indirizzi[1]));
    }

    /* 
     * EFFECTS: Restituisce, uno alla volta, senza ripetizioni e partendo da this.o1, gli indirizzi di this.
    */
    @Override
    public Iterator<Indirizzo> iterator() {
        return Collections.unmodifiableList(Arrays.asList(range)).iterator();
    }

    /* 
     * EFFECTS: Restituisce true se this contiene i.
     *          Solleva NullPointerException se i è null.
    */
    @Override
    public boolean contiene(Indirizzo i) {
        Objects.requireNonNull(i, "L'indirizzo non può essere nullo.");
        for (Indirizzo ind : range) if (i.equals(ind)) return true;
        return false;
    }
    
}
