import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class FoglioDiCalcolo {
    /* 
     * Classe concreta che rappresenta un foglio di calcolo a valori interi.
     * Le istanze di questa classe sono mutabili.
    */
    
    public class CellaFormula implements Cella {
        /* 
         * Classe concreta che rappresenta una cella avente contenuto uguale al valore di 
         * un'operazione applicata al contenuto di altre celle.
         * Le istanze di questa classe sono immutabili.
        */
    
        // REP
        private final SerieIndirizzi operandi;
        private final Operazione op;

        /* 
         * AF(c) = (uso la notazione op() per indicare che applico l'operazione op a tutti gli interi contenuti tra le parentesi)
         *         La cella contiene: c.op(FoglioDiCalcolo.this[c.riferimenti[0]].contenuto, ..., FoglioDiCalcolo.this[c.riferimenti[n]].contenuto)
         * RI(c) : c.riferimenti ≠ null && ogni elemento di c.riferimenti ≠ null && c.op ≠ null
         *         && c.riferimenti.length != 0
        */
    
        /* 
         * EFFECTS: Costruisce una cella che fa riferimento alle celle i cui 
         *          indirizzi, in FoglioDiCalcolo.this, sono contenuti in s, e che applica ai
         *          valori contenuti in tali celle l'operazione o.
         *          Solleva NullPointerException se s è null, se o è null.
        */
        public CellaFormula(final SerieIndirizzi s, final Operazione o) {
            op = Objects.requireNonNull(o, "L'operazione non può essere nulla.");
            operandi = Objects.requireNonNull(s, "Gli indirizzi non possono essere nulli.");
        }
    
        /* 
         * EFFECTS: Restituisce il valore del contenuto di this.
         *          Solleva RiferimentoVuotoException se this fa riferimento a celle vuote in FoglioDiCalcolo.this.
        */
        @Override
        public int contenuto() throws RiferimentoVuotoException {
            return op.esegui(operandi, FoglioDiCalcolo.this);
        }
        
    }

    public class CellaCostante implements Cella{
        /* 
         * Classe concreta che rappresenta una cella avente contenuto uguale ad una costante.
         * Le istanze di questa classe sono immutabili.
        */
    
        // REP
        public final int valore;
    
        /* 
         * AF(c) = La cella contiene c.valore
         * RI(c) : true
        */
    
        /* 
         * EFFECTS: Costruisce una cella con valore costante pari a n.
        */
        public CellaCostante(final int n) {
            valore = n;
        }
    
        /* 
         * EFFECTS: Restituisce il valore del contenuto di this.
        */
        @Override
        public int contenuto() {
            return valore;
        }
        
    }
    
    // REP
    private final Map<Indirizzo, Cella> cellePiene = new HashMap<>();

    /* 
     * AF(c) = (uso la notazione Foglio[i] per riferirmi alla cella del foglio di calcolo all'indirizzo i)
     *         (uso la notazione mappa[x] per riferirmi al valore associato alla chiave x)
     *         Foglio[i] = c.cellePiene[i], se i è una chiave in c.cellePiene
     *         Foglio[i] = cella vuota, se i non è una chiave in c.cellePiene
     * RI(c) : c.cellePiene ≠ null && ogni chiave di c.cellePiene ≠ null && ogni valore di c.cellePiene ≠ null
    */

    /* 
     * EFFECTS: Restituisce il valore contenuto nella cella di this all'indirizzo i.
     *          Se tale cella è vuota, o fa riferimento a celle vuote, restituisce null.
     *          Solleva NullPointerException se i è nullo.
    */
    public Integer leggi(final Indirizzo i) {
        try {
            return cellePiene.get(Objects.requireNonNull(i, "L'indirizzo non può essere nullo.")).contenuto();
        } catch (Exception e) {
            return null;
        }
    }

    /* 
     * EFFECTS: Restituisce il valore del contenuto della cella di this all'indirizzo rappresentato da i.
     *          Se tale cella è vuota, o fa riferimento a celle vuote, restituisce null.
     *          Solleva InvalidFormatException se i non rappresenta un indirizzo valido.
     *          Solleva NullPointerException se i è nulla.
    */
    public Integer leggi(final String i) throws InvalidFormatException {
        Indirizzo address = Indirizzo.daStringa(i);
        return leggi(address);
    }

    /* 
     * EFFECTS: Restituisce true se la cella all'indirizzo i si riferisce, direttamente o non, a sè stessa.
     * 
     * Funzione ricorsiva: 
     * - se foglio[i] è costante o è nulla, restituisce false
     * - se foglio[i] contiene, tra i suoi riferimenti, i, restituisce true
     * - se foglio[i] non contiene, tra i suoi riferimenti, i, controlla singolarmente ogni cella a cui foglio[i] si riferisce
    */
    private boolean contieneRiferimento(final Indirizzo i, Cella corrente) {
        if (corrente == null || corrente instanceof CellaCostante) return false;

        CellaFormula c = (CellaFormula) corrente;
        if (c.operandi.contiene(i)) return true;

        Iterator<Indirizzo> it = c.operandi.iterator();
        while (it.hasNext()) if (contieneRiferimento(i, cellePiene.get(it.next()))) return true;

        return false;
    }

    /* 
     * MODIFIES: this
     * EFFECTS: Imposta a c la cella di this all'indirizzo i, eventualmente sovrascrivendo la cella
     *          già presente all'indirizzo i.
     *          Solleva AutoRiferimentoException (lasciando this immutato) se c fa riferimento, direttamente o 
     *          indirettamente, a sè stessa.
     *          Solleva NullPointerException (lasciando this immutato) se i è nullo, se c è nulla.
    */
    public void set(final Indirizzo i, final Cella c) throws AutoRiferimentoException {
        if (contieneRiferimento(
            Objects.requireNonNull(i, "L'indirizzo non può essere nullo."), 
            Objects.requireNonNull(c, "La cella non può essere nulla."))
        ) throw new AutoRiferimentoException("La cella non può riferirsi direttamente o indirettamente a sè stessa.");
        
        cellePiene.put(i, c);
    }

    /* 
     * EFFECTS: Scrive sulla cella all'indirizzo rappresentato da i, inserendo il valore costante v.
     *          Solleva InvalidFormatException se i non rappresenta un indirizzo.
     * 
     * dubbio: qua non posso togliere AutoRiferimentoException perché set la solleva
     *         l'unica cosa che mi viene in mente di fare è usare un try catch che tanto
     *         non finirà mai nel catch.
    */
    public void scrivi(final String i, final int v) throws InvalidFormatException {
        Indirizzo address = Indirizzo.daStringa(i);
        CellaCostante c = new CellaCostante(v);
        try {
            set(address, c);
        } catch (AutoRiferimentoException e) {}
    }

    /* 
     * EFFECTS: Scrive sulla cella all'indirizzo rappresentato da i, inserendo una formula composta
     *          dall'operazione rappresentata da op, riferita alle celle i cui indirizzi sono rappresentati in r.
     *          Solleva InvalidFormatException se i non rappresenta un indirizzo, se op non rappresenta un'operazione valida,
     *          se r non rappresenta una serie di indirizzi.
    */
    public void scrivi(final String i, final String op, final String r) throws InvalidFormatException, AutoRiferimentoException {
        Indirizzo address = Indirizzo.daStringa(i);
        Operazione o = Operazione.fromCode(op);

        SerieIndirizzi serie;
        if (r.contains(";")) {
            serie = Coppia.daStringa(r);
        } else if (r.contains(":")) {
            serie = Range.daStringa(r);
        } else {
            throw new InvalidFormatException("Formato della serie di indirizzi non valido.");
        }

        CellaFormula c = new CellaFormula(serie, o);
        set(address, c);
    }

    

}
