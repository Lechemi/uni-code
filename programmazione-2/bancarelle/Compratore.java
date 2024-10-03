package bancarelle;

import java.util.Iterator;
import java.util.List;

public abstract class Compratore {
    /* 
     * Rappresenta un compratore di giocattoli.
     * Le istanze di questa classe sono mutabili.
    */

    // REP
    private final String nome;
    private int budget;
    private final Inventario giocattoliAcquistati;
    protected final List<Bancarella> bancarelle;

    /* 
     * AF(c) = Nome compratore: c.nome; Soldi a disposizione: c.budget; Giocattoli acquistati: c.giocattoliAcquistati; Bancarelle da cui comprare: c.bancarelle
     * RI(c) : c.nome ≠ null && c.nome ≠ "" &&
     *         c.budget ≥ 0 &&
     *         c.giocattoliAcquistati ≠ null &&
     *         c.bancarelle ≠ null && c.bancarelle[i] ≠ null per ogni i
    */

    // COSTRUTTORE

    /* 
     * EFFECTS: Crea un compratore chiamato nome, avente budget iniziale pari a budgetIniziale 
     *          e avente a disposizione bancarelle da cui comprare.
     *          Solleva NullPointerException se nome è nulla.
     *          Solleva IllegalArgumentException se nome è vuota, se budgetIniziale è minore di 0.
    */
    public Compratore(final String nome, final int budgetIniziale, final List<Bancarella> bancarelle) {
        if (nome == null) throw new NullPointerException("Il nome del compratore non può essere null.");
        if (bancarelle == null) throw new NullPointerException("Le bancarelle da cui comprare non possono essere null.");
        if (nome == "") throw new IllegalArgumentException("Il nome del compratore non può essere vuoto.");
        if (budgetIniziale < 0) throw new IllegalArgumentException("Il budget iniziale non può essere negativo.");

        this.bancarelle = bancarelle;
        this.nome = nome;
        this.budget = budgetIniziale;
        giocattoliAcquistati = new Inventario();
    }

    /* 
     * EFFECTS: Restituisce un acquisto corrispondente al giocattolo che si vuole 
     *          acquistare (giocattolo) e alla relativa quantità (num).
     *          Solleva InsufficientBudgetException se il budget di this.compratore non è sufficiente a coprire il costo dell'acquisto.
     *          Solleva NullPointerException se giocattolo è nullo.
     *          Solleva IllegalArgumenException se num ≤ 0.
    */
    public abstract Acquisto compra(final Giocattolo giocattolo, int num) throws InsufficientBudgetException, MissingRequiredToysException;

    /* 
     * EFFECTS: Restituisce il nome di this.
    */
    public String getNome() { return nome; }

    /* 
     * EFFECTS: Restituisce il budget di this.
    */
    public int getBudget() { return budget; }

    /* 
     * MODIFIES: this
     * EFFECTS: Toglie prezzo al budget di this.
     *          Se prezzo eccede this.budget, this.budget diventa pari a 0.
     *          Solleva IllegalArgumenException se prezzo < 0.
    */
    void paga(final int prezzo) {
        if (prezzo < 0) throw new IllegalArgumentException("Il prezzo non può essere negativo.");
        
        budget -= prezzo;

        if (budget < 0) budget = 0;
    }

    /* 
     * MODIFIES: this
     * EFFECTS: Aggiunge giocattolo in quantità num ai giocattoli acquistati di this.
     *          Se giocattolo è già presente tra i giocattoli acquistati di this, incrementa di num
     *          la quantità in cui questo è presente.
     *          Solleva NullPointerException se giocattolo è nullo.
     *          Solleva IllegalArgumenException se num ≤ 0.
    */
    public void aggiungiAcquisto(final int num, final Giocattolo giocattolo) {
        if (giocattolo == null) throw new NullPointerException("Il giocattolo da aggiungere non può essere null.");
        if (num <= 0) throw new IllegalArgumentException("La quantità da aggiungere devessere maggiore di 0.");

        giocattoliAcquistati.aggiungiGiocattolo(giocattolo, num);
    }

    /* 
     * Restituisce, uno alla volta, i giocattoli acquistati di this e le relative quantità.
    */
    public Iterator<List<Object>> giocattoli() { 
        return giocattoliAcquistati.iterator();
    }


    // toString stampa nome, budget e giocattoli acquistati di this

}
