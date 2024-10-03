import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/* 
 * SCELTE IMPLEMENTATIVE
 * 
 * in branoDaTitolo sollevo Nosuchelementexception se non c'è il titolo nell'album perché 
 * c'è il metodo per controllare se c'è il titolo e perché altrimenti non saprei come comunicare
 * all'utente che il brano non è stato trovato
 * 
 * 
*/

public class Album implements Iterable<Album.Brano> {
    /* 
     * Classe concreta che rappresenta un album musicale.
     * Le istanze di questa classe sono immutabili.
    */

    public class Brano {
        /* 
         * Classe concreta che rappresenta un brano musicale.
         * Le istanze di questa classe sono immutabili.
        */

        // REP
        private final String titolo;
        private final Durata durata;

        /* 
         * AF(c) = titolo del brano: c.titolo
         *         durata del brano: c.durata
         * RI(c) : c.titolo ≠ null, c.titolo ≠ ""
         *         c.durata ≠ null
        */

        /* 
         * EFFECTS: Costruisce un brano avente titolo t e durata d.
         *          Solleva NullPointerException se d è nulla, se t è nullo.
         *          Solleva IllegalArgumentException se t è vuoto.
        */
        private Brano(final Durata d, final String t) {
            if (Objects.requireNonNull(t, "Il titolo del brano non può essere nullo") == "") {
                throw new IllegalArgumentException("Il titolo del brano non può essere vuoto");
            }

            titolo = t;
            durata = Objects.requireNonNull(d, "La durata del brano non può essere nulla");
        }

        /* 
         * EFFECTS: Restituisce la durata di this.
        */
        public Durata durata() { return durata; }

        /* 
         * EFFECTS: Restituisce il titolo di this.
        */
        public String titolo() { return titolo; }

        /* 
         * EFFECTS: Restituisce l'album a cui this appartiene.
        */
        public Album album() { return Album.this; }

        public String toStringConAlbum() {
            return "\"" + titolo + "\"" + " (" + durata.toString() + ")" + " da " + Album.this.titolo;
        }

        @Override
        public String toString() {
            return "\"" + titolo + "\"" + " (" + durata.toString() + ")";
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Brano)) return false;

            Brano altro = (Brano) obj;
            return altro.titolo.equals(titolo) && altro.durata.equals(durata);
        }

        @Override
        public int hashCode() {
            return Objects.hash(titolo, durata);
        }
    }

    
    // REP
    private final String titolo;
    private final List<Brano> brani;
    private final Durata durataComplessiva;

    /* 
     * AF(c) = Titolo dell'album: c.titolo
     *         Lista dei brani dell'album: c.brani
     *         Durata complessiva dell'album: c.durataComplessiva
     * 
     * RI(c) : c.brani dev'essere non null && non deve contenere null &&
     *         c.durata dev'essere non null && c.durata dev'essere uguale alla somma delle durate di tutti i brani &&
     *         c.titolo ≠ null && c.titolo ≠ ""
    */

    /* 
     * EFFECTS: Crea un album intitolato t, i cui brani hanno ciascuno titolo titoli[i] e durata durate[i],
     *          per ogni i di titoli/durate.
     *          Solleva NullPointerException se titoli (o uno dei suoi elementi) è null, se 
     *          durate (o uno dei suoi elementi) è null, se t è null.
     *          Solleva IllegalArgumentException se la lunghezza di titoli è diversa da quella di durate,
     *          se t è vuoto.
    */
    public Album(final String t, final List<String> titoli, final List<Durata> durate) {
        if (Objects.requireNonNull(titoli, "la lista di titoli non può essere null.").size() != 
        Objects.requireNonNull(durate, "la lista di durate non può essere null.").size()) {
            throw new IllegalArgumentException("la lista dei titoli e la lista delle durate devono avere la stessa lunghezza");
        }

        if (Objects.requireNonNull(t, "il titolo dell'album non può essere nullo") == "") {
            throw new IllegalArgumentException("il titolo dell'album non può essere nullo.");
        }

        titolo = t;

        brani = new ArrayList<>();
        Durata d = new Durata(0);

        for (int i = 0; i < titoli.size(); i ++) {
            brani.add(new Brano(
                Objects.requireNonNull(durate.get(i), "il titolo del brano non può essere null"), 
                Objects.requireNonNull(titoli.get(i), "la durata del brano non può essere null"))
            );
            d = d.somma(durate.get(i));
        }

        durataComplessiva = d;
    }

    /* 
     * EFFECTS: Restituisce il titolo di this.
    */
    public String titolo() { return titolo; }

    /* 
     * EFFECTS: Restituisce il numero di brani di this.
    */
    public int numeroBrani() { return brani.size(); }

    /* 
     * EFFECTS: Restituisce il primo brano di this intitolato t.
     *          Solleva NullPointerException se t è nullo.
     *          Solleva IllegalArgumentException se t è vuoto.
     *          Solleva NoSuchElementException se this non contiene nessun brano intitolato t.
    */
    public Brano branoDaTitolo(final String t) {
        if (Objects.requireNonNull(t, "Il titolo del brano non può essere nullo") == "") {
            throw new IllegalArgumentException("Il titolo del brano non può essere vuoto");
        }

        for (Brano b: brani) if (b.titolo.equals(t)) return b;

        throw new NoSuchElementException("l'album non contiene nessun brano con questo titolo");
    }

    /* 
     * EFFECTS: Restituisce il brano di this che si trova in posizione p.
     *          Solleva IndexOutOfBoundsException se p è minore o uguale a 0, oppure se p
     *          è maggiore del numero di brani di this.
    */
    public Brano branoDaPosizione(int p) {
        if (p <= 0 || p > brani.size()) throw new IndexOutOfBoundsException("posizione brano invalida, dev'essere compresa tra 1 e " + brani.size() + " (inclusi).");
        p -= 1;
        return brani.get(p);
    }

    /* 
     * EFFECTS: Restituisce la posizione di b in this (le posizioni partono da 1).
     *          Restituisce 0 se b non è presente in this.
     *          Solleva NullPointerException se b è nullo.
    */
    public int posizioneBrano(final Brano b) {
        return brani.indexOf(Objects.requireNonNull(b,"il brano non può essere nullo")) + 1;
    }

    /* 
     * EFFECTS: Restituisce la durata complessiva di this.
    */
    public Durata durataComplessiva() {return durataComplessiva;}

    /* 
     * EFFECTS: Restituisce true se this contiene b, false altrimenti.
     *          Solleva NullPointerException se b è nullo.
    */
    public boolean contieneBrano(final Brano b) {
        return brani.contains(Objects.requireNonNull(b, "il brano non può essere nullo"));
    }

    /* 
     * EFFECTS: Restituisce true se this contiene almeno un brano intitolato t, false altrimenti.
     *          Solleva NullPointerException se t è nullo.
    */
    public boolean contieneBranoIntitolato(final String t) {
        Objects.requireNonNull(t, "il titolo del brano non può essere nullo");

        for (Brano b : brani) if (b.titolo.equals(t)) return true;

        return false;
    }

    /* 
     * EFFECTS: Restituisce un iteratore che consente di ottenere, uno alla volta,
     *          i brani di this.
    */
    @Override
    public Iterator<Album.Brano> iterator() {
        return Collections.unmodifiableList(brani).iterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ALBUM: " + "\"" + titolo + "\" \n");
        for (int i = 0; i < brani.size(); i ++) {
            sb.append(i+1);
            sb.append(" - " + brani.get(i).toString() + "\n");
        }
        sb.append("Durata totale: " + durataComplessiva.toString());
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        // due album sono uguali se hanno lo stesso titolo, stessa durata, sono lunghi uguali e hanno gli stessi brani
        if (!(obj instanceof Album)) return false;

        Album altro = (Album) obj;
        if (!altro.titolo.equals(titolo)) return false;
        if (altro.numeroBrani() != numeroBrani()) return false;
        if (!altro.durataComplessiva.equals(durataComplessiva)) return false;

        for (int i = 1; i <= numeroBrani(); i++) if (!branoDaPosizione(i).equals(altro.branoDaPosizione(i))) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(titolo, brani, durataComplessiva);
    }

}


