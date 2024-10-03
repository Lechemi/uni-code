import java.util.Objects;

/* 
 * SCELTE IMPLEMENTATIVE:
 * scelgo di farla immutabile perché non c'è effettivamente bisogno di modificare le durate,
 * in somma e sottrai posso semplicemente restituire una nuova durata.
 * 
 * sec pubilc perché tanto è final e primitivo
*/

public class Durata {
    /* 
     * Classe concreta che rappresenta una durata temporale.
     * Le istanze di questa classe sono immutabili.
    */

    // REP
    public final int sec; 

    /* 
     * AF(sec) = sec : durata in secondi
     * RI(sec) : sec ≥ 0
    */

    /* 
     * EFFECTS: Costruisce una durata di s secondi.
     *          Solleva IllegalArgumentException se s è negativo.
    */
    public Durata(final int s) {
        if (s < 0) throw new IllegalArgumentException("la durata non può essere negativa");

        sec = s;
    }

    /* 
     * EFFECTS: Restituisce una nuova Durata corrispondente ad una stringa (s) nel formato
     *          hh:mm:ss, oppure mm:ss, oppure ss.
     *          Solleva NullPointerException se s è nulla.
     *          Solleva IllegalArgumentException se s è vuota, se s non è in uno dei formati sopra indicati.
    */
    public static Durata daStringa(final String s) { 
        if (Objects.requireNonNull(s, "La stringa non può essere nulla.") == "") {
            throw new IllegalArgumentException("La stringa non può essere vuota.");
        }

        String[] parti = s.split(":");
        if (parti.length > 3) throw new IllegalArgumentException("La stringa non è nel formato corretto");

        if (parti.length == 3) return new Durata(
            convertiTempo(parti[0], false) * 3600 +
            convertiTempo(parti[1], true) * 60 + 
            convertiTempo(parti[2], true)
        );

        if (parti.length == 2) return new Durata(
            convertiTempo(parti[0], true) * 60 + 
            convertiTempo(parti[1], true)
        );
    
        if (parti.length == 1) return new Durata(convertiTempo(parti[0], true));

        throw new IllegalArgumentException("La stringa non è nel formato corretto");
    }

    /* 
     * EFFECTS: Metodo privato di utilità che converte t nel corrispondente valore 
     *          intero di ore, minuti o secondi.
     *          Se limitato è true, t deve rappresentare un valore compreso tra 0 e 60 (escluso).
     *          Solleva IllegalArgumentException se t rappresenta un valore negativo, se t rappresenta 
     *          un valore maggiore di 59 e limitato è true.
    */
    private static int convertiTempo(final String t, final boolean limitato) {
        if (Objects.requireNonNull(t, "La stringa non può essere nulla.") == "") {
            throw new IllegalArgumentException("La stringa non può essere vuota.");
        }

        Integer n = Integer.parseInt(t);
        if (n < 0) throw new IllegalArgumentException("t deve rappresentare un numero positivo.");
        if (n > 59 && limitato) throw new IllegalArgumentException("t non rappresenta un valido valore per minuti o secondi.");
        return n;
    }

    /* 
     * EFFECTS: Restituisce una nuova durata ottenuta sommando i secondi di altra a quelli di this.
     *          Solleva NullPointerException se altra è nulla.
    */
    public Durata somma(final Durata altra) {  
        return new Durata(sec + Objects.requireNonNull(altra, "L'altra durata non può essere null.").sec);
    }

    /* 
     * EFFECTS: Restituisce una nuova durata ottenuta sottraendo i secondi di altra a quelli di this.
     *          Solleva NullPointerException se altra è nulla.
     *          Solleva IllegalArgumentException se altra è maggiore di this.
    */
    public Durata sottrai(final Durata altra) { 
        if (Objects.requireNonNull(altra, "L'altra durata non può essere null.").sec > sec) {
            throw new IllegalArgumentException("L'altra durata non può essere maggiore di questa.");
        }
        return new Durata(sec - altra.sec);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        Integer s = Integer.valueOf(sec);
        Integer h = s / 3600;
        sb.append(h > 0 ? h.toString() + ":" : "");

        s %= 3600;
        Integer m = s / 60;
        sb.append(m < 10 ? "0" + m.toString() : m.toString());
        sb.append(":");

        s %= 60;
        sb.append(s < 10 ? "0" + s.toString() : s.toString());

        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Durata)) return false;

        Durata altra = (Durata) obj;
        return altra.sec == sec;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sec);
    }
}