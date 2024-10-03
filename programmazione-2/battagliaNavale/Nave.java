import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class Nave {
    /* 
     * Classe concreta che rappresenta una nave di battaglia navale.
     * Le istanze di questa classe sono mutabili.
    */

    // REP
    private boolean affondata;
    public final Posizione posizione;
    public final boolean orizzontale;
    public final TipologiaNave tipologia;
    private final List<Posizione> posizioniColpite = new LinkedList<>();

    /* 
     * AF(c) = Nave:
     *         Tipologia: c.tipologia
     *         Posizioni occupate sulla griglia di gioco:
     *         if (orizzontale) {
     *              c.posizione, c.posizione.più(1, 0), ..., c.posizione.più(n, 0)
     *         } else {
     *              c.posizione, c.posizione.più(0, 1), ..., c.posizione.più(0, n)
     *         }
     *         Dove n = c.tipologia.lunghezza - 1
     *         Posizioni colpite: c.posizioniColpite
     *         if (affondata) {
     *              la nave è affondata, ossia: tutte le sue posizioni sono presenti in c.posizioniColpite
     *         } else {
     *              la nave non è affondata, ossia: non tutte le sue posizioni sono presenti in c.posizioniColpite
     *         }
     *               
     * RI(c) : c.posizione ≠ null && c.tipologia ≠ null && 
     *         tutte le posizioni di c.posizioniColpite devono essere posizioni occupate dalla nave &&
     *         c.posizioniColpite ≠ null && c.posizioniColpite non deve contenere duplicati &&
     *         tutti gli elementi di c.posizioniColpite ≠ null && 
     *         c.affondata = true se e solo se c.posizioniColpite contiene tutte le posizioni occupate dalla nave
     *         
    */

    /* 
     * EFFECTS: Costruisce una nave di tipo t con posizione pos.
     *          Se orizzontale è true, la nave avrà orientamento orizzontale, altrimenti verticale.
     *          Solleva NullPointerException se pos è null, se t è null.
     *          Solleva IllegalArgumentException se non è possibile costruire la nave perchè uscirebbe
     *          dalla griglia di gioco.
    */
    public Nave(final Posizione pos, final boolean orizzontale, final TipologiaNave t) {
        posizione = Objects.requireNonNull(pos, "La posizione non può essere nulla.");
        tipologia = Objects.requireNonNull(t, "La tipologia di nave non può essere nulla.");
        this.orizzontale = orizzontale;
        affondata = false;

        // Questro try-catch serve solo a verificare che la "poppa" della nave sia sulla griglia
        try {
            if (orizzontale) {
                posizione.più(t.lunghezza()-1, 0);
            } else {
                posizione.più(0, t.lunghezza()-1);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("La nave non ci sta nella griglia.");
        }
    }

    /* 
     * EFFECTS: Restituisce true se this è affondata, false altrimenti.
    */
    public boolean affondata() {return affondata;}

    /* 
     * EFFECTS: Restituisce la lunghezza di this.
    */
    public int lunghezza() {return tipologia.lunghezza();}

    /* 
     * EFFECTS: Restituisce true se this occupa pos, false altrimenti.
    */
    public boolean occupa(final Posizione pos) {
        Objects.requireNonNull(pos, "La posizione non può essere nulla.");
        for (int i = 0; i < tipologia.lunghezza(); i++) {
            if (orizzontale) {
                Posizione p = posizione.più(i, 0);
                if (pos.equals(p)) return true;
            } else {
                Posizione p = posizione.più(0, i);
                if (pos.equals(p)) return true;
            }
        }
        return false;
    }

    /* 
     * MODIFIES: this
     * EFFECTS: Attacca this in posizione pos, registrando e restituendo l'esito dell'attacco.
     *          Se viene attaccata una posizione già colpita (o se this è già affondata),
     *          restituisce null, lasciando però this immutato.
     *          Solleva NullPointerException se pos è null.
     *          Solleva IndexOutOfBoundsException se pos non è occupata da this.
     * 
     * Nota: attaccare una posizione già colpita non costituisce un'eccezione, ma attaccare
     *       la barca in una posizione che non occupa sì. È come se accedessi al decimo elemento di 
     *       una lista con 5 elementi. È alla griglia che spetta di controllare se sto attaccando
     *       una posizione che appartene alla barca o no.
    */
    public Esito attacca(final Posizione pos) {
        if (!occupa(Objects.requireNonNull(pos, "La posizione non può essere nulla."))) {
            throw new IndexOutOfBoundsException("La nave non occupa la posizione " + pos.toString());
        }

        if (affondata) return null;
        if (posizioniColpite.contains(pos)) return null;

        posizioniColpite.add(pos);
        if (posizioniColpite.size() == tipologia.lunghezza()) {
            affondata = true;
            return Esito.ColpitoEAffondato;
        } else {
            return Esito.Colpito;
        }  
    }

    /* 
     * EFFECTS: Restituisce true se this è colpita in posizione pos (o se this è affondata), false
     *          se this è intatta in posizione pos.
     *          Solleva NullPointerException se pos è null.
     *          Solleva IndexOutOfBoundsException se pos non è occupata da this.
    */
    public boolean colpita(final Posizione pos) {
        if (!occupa(Objects.requireNonNull(pos, "La posizione non può essere nulla."))) {
            throw new IndexOutOfBoundsException("La nave non occupa la posizione " + pos.toString());
        }
        if (affondata) return true;
        return posizioniColpite.contains(pos);
    }

    /* 
     * EFFECTS: Restituisce, una alla volta e senza ripetizioni, le posizioni occupate da this.
     */
    public Iterator<Posizione> posizioniOccupate() {
        return new Iterator<Posizione>() {

            private int posCounter = 0;

            @Override
            public boolean hasNext() {
                return posCounter < tipologia.lunghezza();
            }

            @Override
            public Posizione next() {
                if (!hasNext()) throw new NoSuchElementException();
                Posizione next = orizzontale ? posizione.più(posCounter, 0) : posizione.più(0, posCounter);
                posCounter ++;
                return next;
            }
            
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Posizione: " + posizione.toString() + "\n");
        Iterator<Posizione> it = posizioniOccupate();
        while (it.hasNext()) {
            if (affondata) {
                sb.append("#");
                it.next();
            } else {
                sb.append(colpita(it.next()) ? "*" : tipologia.toString());
            }

            if (!orizzontale) sb.append("\n");
        }

        if (orizzontale) sb.append("\n");
        return sb.toString();
    }

}
