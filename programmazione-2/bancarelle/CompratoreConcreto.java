package bancarelle;

import java.util.ArrayList;
import java.util.List;

public class CompratoreConcreto extends Compratore {
    /* 
     * Rappresenta un compratore che non ha alcuna priorità.
     * Le istanze di questa classe sono mutabili.
    */

    /* 
     * AF_CompratoreConcreto(c) = AF_Compratore(c)
     * RI_CompratoreConcreto(c) = RI_Compratore(c)
    */

    /* 
     * EFFECTS: Crea un compratore chiamato nome e avente budget iniziale pari a budgetIniziale.
     *          Solleva NullPointerException se nome è nulla.
     *          Solleva IllegalArgumentException se nome è vuota, se budgetIniziale è minore di 0.
    */
    public CompratoreConcreto(String nome, int budgetIniziale, final List<Bancarella> bancarelle) {
        super(nome, budgetIniziale, bancarelle);
    }

    /* 
     * EFFECTS: Restituisce un acquisto corrispondente al giocattolo che si vuole 
     *          acquistare (giocattolo) e alla relativa quantità (num).
     *          L'acquisto viene effettuato comprando i giocattoli dalle bancarelle che compaiono
     *          nell'ordine in cui sono in this.bancarelle. 
     * 
     *          Solleva MissingRequiredToysException se giocattolo non è presente nelle bancarelle o è presente in quantità insufficiente.
     *          Solleva NullPointerException se giocattolo è nullo.
     *          Solleva IllegalArgumenException se num ≤ 0.
    */
    @Override
    public Acquisto compra(final Giocattolo giocattolo, int num) throws InsufficientBudgetException, MissingRequiredToysException {
        if (giocattolo == null) throw new NullPointerException("Il giocattolo da comprare non può essere null.");
        if (num <= 0) throw new IllegalArgumentException("La quantità da comprare devessere maggiore di 0.");

        List<Bancarella> venditori = new ArrayList<>();
        List<Integer> quantità = new ArrayList<>();

        // calcolo le quantità che mi servono da ciascuna bancarella secondo il criterio di this
        // se non bastano, sollevo MissingRequiredToysException
        for (Bancarella b : bancarelle) {
            int disponibilità = b.quantitàGiocattolo(giocattolo);
            if (disponibilità == 0) continue;

            venditori.add(b);
            quantità.add(num > disponibilità ? disponibilità : num);

            num -= disponibilità;
            if (num <= 0) break;
        }

        if (num > 0) throw new MissingRequiredToysException("Non ci sono abbastanza giocattoli del tipo desiderato in vendita.");

        return new Acquisto(giocattolo, this, venditori, quantità);
    }
    
}
