package bancarelle;

import java.util.Iterator;
import java.util.List;

import bancarelle.listino.Listino;

public class Bancarella {
    /* 
     * Rappresenta una bancarella.
     * Le istanze di questa classe sono mutabili.
    */

    // REP
    private final String proprietario;
    private final Inventario giocattoliInVendita;
    private final Listino listino;
    private int guadagno;

    /* 
     * AF(c) = Nome del proprietario della bancarella: c.proprietario
     *         Giocattoli in vendita: c.giocattoliInVendita
     *         Soldi guadagnati: c.guadagno
     * RI(c) : c.proprietario ≠ null, c.proprietario ≠ ""
     *         c.giocattoliInVendita ≠ null
     *         c.guadagno ≥ 0
    */ 

    /* 
     * EFFECTS: Crea una bancarella senza giocattoli il cui proprietario si chiama nome.
     *          Solleva NullPointerException se nome è nulla.
     *          Solleva IllegalArgumentException se nome è vuota.
    */
    public Bancarella(final String nome) {
        proprietario = nome;
        giocattoliInVendita = new Inventario();
        guadagno = 0;
    }

    /* 
     * EFFECTS: Restituisce il nome del proprietario di this.
    */
    public String getProprietario() { return proprietario; }

    /* 
     * EFFECTS: Restituisce il guadagno di this.
    */
    public int getGuadagno() { return guadagno; }

    /* 
     * EFFECTS: Restituisce, uno per volta, i giocattoli in vendita di this.
    */
    public Iterator<List<Object>> giocattoli() {
        return null;
    }


    /* 
     * MODIFIES: this
     * EFFECTS: Mette in vendita la quantità num di giocattolo, con prezzo definito da p.
     *          Se giocattolo è già in vendita, incrementa la quantità di giocattolo in vendita e, 
     *          se p cambia da quello già presente, lo aggiorna.
     *          Solleva NullPointerException se giocattolo è nullo, se p è nullo.
     *          Solleva IllegalArgumentException se num ≤ 0.
    */
    public void mettiInVendita(final Giocattolo giocattolo, final int num, final Listino p) {
        if (giocattolo == null) throw new NullPointerException("Il giocattolo non può essere nullo.");
        if (p == null) throw new NullPointerException("Il prezzo non può essere nullo.");
        if (num <= 0) throw new IllegalArgumentException("La quantità di giocattoli non può essere ≤ 0.");

        giocattoliInVendita.aggiungiGiocattoloConPrezzo(giocattolo, num, p);
    }


    /* 
     * MODIFIES: this
     * EFFECTS: Vende la quantità num di giocattolo, rimuovendola dall'inventario e aggiungendo il ricavato a this.guadagno.
     *          Se num eccede la quantità di giocattolo in vendita, vende tutti i giocattoli di tipo giocattolo in vendita.
     *          Solleva NullPointerException se giocattolo è nullo.
     *          Solleva IllegalArgumentException se num ≤ 0.
    */
    void vendi(final Giocattolo giocattolo, final int num) {
        if (giocattolo == null) throw new NullPointerException("Il giocattolo non può essere nullo.");
        if (num <= 0) throw new IllegalArgumentException("La quantità di giocattoli non può essere ≤ 0.");

        int daVendere = num > quantitàGiocattolo(giocattolo) ? quantitàGiocattolo(giocattolo) : num;
        
        guadagno += prezzoGiocattolo(giocattolo, daVendere);

        giocattoliInVendita.rimuoviGiocattolo(giocattolo, daVendere);

    }

    /* 
     * EFFECTS: Restituisce la quantità di giocattolo di this.
     *          Solleva NullPointerException se giocattolo è nullo.
    */
    public int quantitàGiocattolo(final Giocattolo giocattolo) { 
        if (giocattolo == null) throw new NullPointerException("Il giocattolo non può essere nullo.");

        return giocattoliInVendita.quantitàGiocattolo(giocattolo);
    }

    /* 
     * EFFECTS: Restituisce il prezzo della quantità num di giocattolo in this.
     *          Se num eccede la quantità di giocattolo in vendita, restituisce -1.
     *          Solleva NullPointerException se giocattolo è nullo.
     *          Solleva IllegalArgumentException se num ≤ 0.
    */
    public int prezzoGiocattolo(final Giocattolo giocattolo, final int num) {
        if (giocattolo == null) throw new NullPointerException("Il giocattolo non può essere nullo.");
        if (num <= 0) throw new IllegalArgumentException("La quantità di giocattoli non può essere ≤ 0.");

        return giocattoliInVendita.prezzoDaGiocattolo(giocattolo, num);
    }

}

