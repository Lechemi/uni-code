import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;


/* 
 * SCELTE IMPLEMENTATIVE
 * 
 * non salvo la durata complessiva perché tanto può cambiare.
 * 
*/


public class Playlist implements Iterable<Album.Brano> {
    /* 
     * Classe concreta che rappresenta una playlist musicale.
     * Le istanze di questa classe sono mutabili.
    */

    // REP
    private final String titolo;
    private final List<Album.Brano> brani = new ArrayList<>();

    /* 
     * AF(c) = Titolo della playlist: c.titolo
     *         Lista di brani della playlist: c.brani
     * 
     * RI(c) : c.titolo ≠ null && c.titolo ≠ ""
     *         c.brani ≠ null && c.brani non contiene null
    */

    /* 
     * EFFECTS: Crea una playlist vuota intiolata t.
     *          Solleva NullPointerException se t è null.
     *          Solleva IllegalArgumentException se t è vuota.
    */
    public Playlist(final String t) {
        if (Objects.requireNonNull(t, "il titolo della playlist non può essere nullo") == "") {
            throw new IllegalArgumentException("il titolo della playlist non può essere nullo.");
        }

        titolo = t;
    }

    /* 
     * MODIFIES: this
     * EFFECTS: Se b non è presente in this, lo aggiunge e restituisce true; altrimenti, non fa 
     *          nulla e restituisce false.
     *          Solleva NullPointerException se b è null.
    */
    public boolean aggiungi(final Album.Brano b) {
        if (contieneBrano(Objects.requireNonNull(b, "il brano da aggiungere non può essere null."))) {
            return false;
        }

        brani.add(b);
        return true;
    }

    /* 
     * EFFECTS: Restituisce il titolo di this.
    */
    public String titolo() {return titolo;}

    /* 
     * EFFECTS: Restituisce la durata complessiva di this.
    */
    public Durata durata() { 
        int tot = 0;
        for (Album.Brano b : brani) tot += b.durata().sec;
        return new Durata(tot);
    }

    /* 
     * EFFECTS: Restituisce il numero di brani in this.
    */
    public int numeroBrani() {return brani.size();}

    /* 
     * EFFECTS: Restituisce il primo brano di this intitolato t.
     *          Solleva NullPointerException se t è nullo.
     *          Solleva IllegalArgumentException se t è vuoto.
     *          Solleva NoSuchElementException se this non contiene nessun brano intitolato t. 
    */
    public Album.Brano branoDaTitolo(final String t) {
        if (Objects.requireNonNull(t, "Il titolo del brano non può essere nullo") == "") {
            throw new IllegalArgumentException("Il titolo del brano non può essere vuoto");
        }

        for (Album.Brano b: brani) if (b.titolo().equals(t)) return b;

        throw new NoSuchElementException("l'album non contiene nessun brano con questo titolo");
    }
    
    /* 
     * EFFECTS: Restituisce il brano di this che si trova in posizione p.
     *          Solleva IndexOutOfBoundsException se p è minore o uguale a 0, oppure se p
     *          è maggiore del numero di brani di this.
    */
    public Album.Brano branoDaPosizione(int p) {
        if (p <= 0 || p > brani.size()) throw new IndexOutOfBoundsException("posizione brano invalida, dev'essere compresa tra 1 e " + brani.size() + " (inclusi).");
        p -= 1;
        return brani.get(p);
    }

    /* 
     * EFFECTS: Restituisce la posizione di b in this (le posizioni partono da 1).
     *          Restituisce 0 se b non è presente in this.
     *          Solleva NullPointerException se b è nullo.
    */
    public int posizioneBrano(final Album.Brano b) {
        return brani.indexOf(Objects.requireNonNull(b,"il brano non può essere nullo")) + 1;
    }

    /* 
     * EFFECTS: Restituisce true se b è contenuto in this, false altrimenti.
     *          Solleva NullPointerException se b è null.
    */
    public boolean contieneBrano(final Album.Brano b) {
        return brani.contains(Objects.requireNonNull(b, "il brano non può essere null."));
    }

    /* 
     * EFFECTS: Restituisce true se this contiene almeno un brano intitolato t, false altrimenti.
     *          Solleva NullPointerException se t è nullo.
    */
    public boolean contieneBranoIntitolato(final String t) {
        Objects.requireNonNull(t, "il titolo del brano non può essere nullo");

        for (Album.Brano b : brani) if (b.titolo().equals(t)) return true;

        return false;
    }

    /* 
     * EFFECTS: Restituisce true se this contiene almeno un brano di a, false altrimenti.
     *          Solleva NullPointerException se a è nullo.
    */
    public boolean contieneBraniDaAlbum(final Album a) {
        Objects.requireNonNull(a, "l'album non può essere nullo");

        Iterator<Album> albums = album();

        while (albums.hasNext()) if (albums.next().equals(a)) return true;

        return false;
    }

    /* 
     * EFFECTS: Restituisce una playlist, chiamata t, ottenuta dalla fusione di this con p.
     *          La nuova playlist che contiene tutti i brani di this (nell’ordine in cui compaiono), 
     *          seguiti dai brani che compaiono in p (nell’ordine in cui compaiono, a meno che 
     *          non siano già comparsi in precedenza nella fusione).
     *          Solleva NullPointerException se p è null, se t è null.
     *          Solleva IllegalArgumentException se t è vuota.
    */
    public Playlist fondiCon(final Playlist p, final String t) {
        if (Objects.requireNonNull(t, "il titolo della playlist non può essere nullo") == "") {
            throw new IllegalArgumentException("il titolo della playlist non può essere nullo.");
        }

        Playlist fusione = new Playlist(t);

        this.forEach(brano -> {fusione.aggiungi(brano);});
        Objects.requireNonNull(p, "l'altra playlist non può essere null").forEach(brano -> {fusione.aggiungi(brano);});

        return fusione;
    }

    /* 
     * EFFECTS: Restituisce un iteratore che consente di ottenere, uno alla volta,
     *          i brani di this.
    */
    @Override
    public Iterator<Album.Brano> iterator() {
        return Collections.unmodifiableList(brani).iterator();
    }

    /* 
     * EFFECTS: Restituisce un iteratore che consente di ottenere, uno alla volta,
     *          i brani di this che appartengono ad a.
     *          Solleva NullPointerException se a è null.
    */
    public Iterator<Album.Brano> braniDaAlbum(final Album a) {
        Objects.requireNonNull(a, "l'album non può essere null.");

        return new Iterator<Album.Brano>(){

            private Iterator<Album.Brano> source = iterator();
            private boolean hasNext = false;
            private Album.Brano next;

            @Override
            public boolean hasNext() {
                while (source.hasNext() && !hasNext) {
                    next = source.next();
                    hasNext = next.album().equals(a);
                }
                return hasNext;
            }

            @Override
            public Album.Brano next() {
                if (!hasNext()) throw new NoSuchElementException();
                hasNext = false; // cosi posso rientrare nel while di hasNext() e passare al prossimo elemento
                return next;
            }

        };
    }

    /* 
     * EFFECTS: Restituisce un iteratore che consente di ottenere, uno alla volta e senza ripetizioni,
     *          gli album dei brani di this.
    */
    public Iterator<Album> album() {
        return new Iterator<Album>(){

            private List<Album> giàRestituiti = new LinkedList<>();
            private Iterator<Album.Brano> source = iterator();
            private boolean hasNext = false;
            private Album next;

            @Override
            public boolean hasNext() {
                while (source.hasNext() && !hasNext) {
                    next = source.next().album();
                    hasNext = !giàRestituiti.contains(next);
                }
                return hasNext;
            }

            @Override
            public Album next() {
                if (!hasNext()) throw new NoSuchElementException();
                hasNext = false; // cosi posso rientrare nel while di hasNext() e passare al prossimo elemento
                giàRestituiti.add(next);
                return next;
            }

        };
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("PLAYLIST: " + "\"" + titolo + "\" \n");
        for (int i = 0; i < brani.size(); i ++) {
            sb.append(i+1);
            sb.append(" - " + brani.get(i).toStringConAlbum() + "\n");
        }
        sb.append("Durata totale: " + durata().toString());
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        // due playlist sono uguali se hanno lo stesso titolo, stessa durata, sono lunghe uguali e hanno gli stessi brani
        if (!(obj instanceof Playlist)) return false;

        Playlist altra = (Playlist) obj;
        if (altra.numeroBrani() != numeroBrani()) return false;
        if (!altra.durata().equals(durata())) return false;
        if (!altra.titolo.equals(titolo)) return false;

        for (int i = 1; i <= numeroBrani(); i++) if (!branoDaPosizione(i).equals(altra.branoDaPosizione(i))) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hash(titolo, brani);
    }
}
