package bancarelle;

import java.util.List;

public class Acquisto {
    /* 
     * Rappresenta l'acquisto di un giocattolo.
     * Le istanze di questa classe non sono mutabili.
    */

    // REP
    private final Giocattolo giocattolo;
    private final Compratore compratore;
    private final List<Bancarella> venditori;
    private final List<Integer> quantitàPerVenditore;
    private final int prezzoTotale;

    /* 
     * AF(c) = Tipo di giocattolo acquistato: c.giocattolo
     *         Prezzo totale dell'acquisto: c.prezzoTotale
     *         c.venditori[i], c.quantitàPerVenditore[i] : venditore, quantità di giocattoli acquistata da esso
     * 
     * RI(c) = c.giocattolo ≠ null
     *         c.prezzoTotale ≥ 0
     *         c.venditori ≠ null, c.venditori.size > 0, c.venditori[i] ≠ null per ogni i
     *         c.quantitàPerVenditore ≠ null, c.quantitàPerVenditore.size > 0, c.quantitàPerVenditore[i] ≠ null per ogni i
     *         c.venditori.size = c.quantitàPerVenditore.size
    */

    /* 
     * EFFECTS: Crea un acquisto di giocattolo, del costo di totalePagato.
     *          L'acquisto viene effettuato dai venditori e nelle quantità indicate rispettivamente 
     *          delle liste "parallele" venditori e quantitàPerVenditore.
     *          Solleva NullPointerException se giocattolo è null, se compratore è null, 
     *          se venditori (o uno dei suoi elementi) è null, se quantitàPerVenditore (o uno dei suoi elementi) è null.
     *          Solleva InsufficientBudgetException se il budget di compratore non è sufficiente a coprire il costo dell'acquisto.
     *
     * REQUIRES: Ad ogni elemento di venditori deve corrispondere la relativa quantità di giocattoli venduti.
    */
    Acquisto(final Giocattolo giocattolo, final Compratore compratore, final List<Bancarella> venditori, final List<Integer> quantitàPerVenditore)
    throws InsufficientBudgetException {

        this.giocattolo = giocattolo;
        this.compratore = compratore;
        this.venditori = venditori;
        this.quantitàPerVenditore = quantitàPerVenditore;
        this.prezzoTotale = prezzoTotale();
        if (prezzoTotale > compratore.getBudget()) throw new InsufficientBudgetException("Il compratore ha abbastanza soldi.");
    }

    private int prezzoTotale() {
        int p = 0;

        for (int i = 0; i < venditori.size(); i++) {
            Bancarella venditore = venditori.get(i);
            int numeroGiocattoliDaVendere = quantitàPerVenditore.get(i);

            p += venditore.prezzoGiocattolo(giocattolo, numeroGiocattoliDaVendere);
        }

        return p;
    }

    /* 
     * EFFECTS: Effettua this, modificando di conseguenza i relativi attributi di compratore e venditori.
    */
    public void effettua() {

        for (int i = 0; i < venditori.size(); i++) {
            Bancarella venditore = venditori.get(i);
            int numeroGiocattoliDaVendere = quantitàPerVenditore.get(i);

            venditore.vendi(giocattolo, numeroGiocattoliDaVendere);
            compratore.aggiungiAcquisto(numeroGiocattoliDaVendere, giocattolo);
        }

        compratore.paga(prezzoTotale);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Acquisto di: ");
        sb.append(giocattolo.toString() + ", prezzo totale: " + prezzoTotale + ", di cui: \n");
        
        for (int i = 0; i < venditori.size(); i++) {
            Bancarella venditore = venditori.get(i);
            int numeroGiocattoliDaVendere = quantitàPerVenditore.get(i);

            sb.append(numeroGiocattoliDaVendere + " da " + venditore.getProprietario() + "\n");
        }

        return sb.toString();
    }

    /* 
    esempio di toString:
    Acquisto di: soldatino di stagno, per un costo di: 23, numero: 11 di cui:
    10 da Federico
    1 da Massimo    
    */

}

