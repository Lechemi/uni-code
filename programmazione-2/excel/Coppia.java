import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Coppia extends SerieIndirizzi {
    /* 
     * Classe concreta che rappresenta una coppia di indirizzi.
     * Le istanze di questa classe sono immutabili.
    */

    /* 
     * AF(c) = Coppia di indirizzi: c.o1, c.o2
     * RI(c) : c.o1 e c.o2 non null && !c.o1.equals(o2)
    */

    /* 
     * EFFECTS: Costruisce una coppia di indirizzi costituita da o1 e o2.
     *          Solleva NullPointerException se o1 è nullo, se o2 è nullo.
     *          Solleva IllegalArgumentException se o1 è uguale a o2.
    */
    public Coppia(final Indirizzo o1, final Indirizzo o2) {
        super(o1, o2);
    }

    /* 
     * EFFECTS: Restituisce una Coppia a partire da una stringa nel formato 'I1;I2', dove 
     *          I1 e I2 sono stringhe rappresentanti due indirizzi.
     *          Solleva NullPointerException se s è null.
     *          Solleva InvalidFormatException se s non è nel formato 'I1;I2', se I1 non è convertibile
     *          ad un indirizzo, se I2 non è convertibile ad un indirizzo.
    */
    public static Coppia daStringa(final String s) throws InvalidFormatException {
        if (!Objects.requireNonNull(s, "La stringa non può essere nulla.").contains(";")) {
            throw new InvalidFormatException("Formato della coppia di indirizzi non valido.");
        }

        String[] indirizzi = s.split(";");
        if (indirizzi.length < 2) throw new InvalidFormatException("Formato della coppia di indirizzi non valido.");

        return new Coppia(Indirizzo.daStringa(indirizzi[0]), Indirizzo.daStringa(indirizzi[1]));
    }

    /* 
     * EFFECTS: Restituisce, uno alla volta, senza ripetizioni e partendo da this.o1, gli indirizzi di this.
    */
    @Override
    public Iterator<Indirizzo> iterator() {
        return Collections.unmodifiableList(List.of(o1, o2)).iterator();
    }

    /* 
     * EFFECTS: Restituisce true se this contiene i.
     *          Solleva NullPointerException se i è null.
    */
    @Override
    public boolean contiene(Indirizzo i) {
        Objects.requireNonNull(i, "L'indirizzo non può essere nullo.");
        return o1.equals(i) || o2.equals(i);
    }
    
}
