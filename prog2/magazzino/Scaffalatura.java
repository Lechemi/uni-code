import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Scaffalatura {
    /* 
     * Classe concreta che rappresenta una scaffalatura, ossia una serie di pacchi impilati uno sopra l'altro.
     * Le istanze di questa classe sono mutabili.
    */

    // REP
    private final List<Pacco> pacchi = new LinkedList<>();

    /* 
     * AF(c) = (utilizzo la notazione [i] per accedere all'i-esimo elemento di c.pacchi)
     *         Scaffalatura: c.pacchi[n]
     *                       ...
     *                       c.pacchi[0]
     *         Dove n = c.pacchi.length - 1
     * RI(c) : c.pacchi ≠ null && ogni elemento di c.pacchi ≠ null
    */

    /* 
     * MODIFIES: this
     * EFFECTS: Deposita p in cima a this.
     *          Solleva NullPointerException (lasciando this immutato) se p è null.
    */
    public void deposita(final Pacco p) {
        pacchi.add(Objects.requireNonNull(p, "Il pacco non può essere nullo."));
    }

    /* 
     * MODIFIES: this
     * EFFECTS: Preleva e restituisce il pacco in fondo a this.
     *          Restituisce null (ovviamente lasciando this immutato) se this non contiene pacchi.
     *          Solleva NullPointerException (lasciando this immutato) se p è null.
    */
    public Pacco preleva() {
        if (èVuota()) return null;

        Pacco p = pacchi.get(0);
        pacchi.remove(0);
        return p;
    }

    /* 
     * EFFECTS: Restituisce true se this non contiene pacchi, false altrimenti.
    */
    public boolean èVuota() {
        return pacchi.isEmpty();
    }

    /* 
     * EFFECTS: Restituisce il pacco in fondo a this, senza rimuoverlo.
     *          Se this non contiene pacchi, restituisce null.
    */
    public Pacco prossimoPacco() {
        if (èVuota()) return null;
        return pacchi.get(0);
    }

    /* 
     * EFFECTS: Restituisce il numero di pacchi contenuti in this.
     */
    public int numeroPacchi() {
        return pacchi.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = numeroPacchi()-1; i >= 0; i--) {
            sb.append(pacchi.get(i).prodotto() + "\n");
        }
        return sb.toString();
    }

}
