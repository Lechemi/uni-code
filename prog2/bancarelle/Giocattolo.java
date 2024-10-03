package bancarelle;

import java.util.Objects;

public class Giocattolo {
    /* 
     * Rappresenta un giocattolo immutabile.
    */

    // REP
    private final String nome;
    private final String materiale;

    /* 
     * AF(c) = Nome giocattolo: c.nome, materiale: c.materiale
     * RI(c) = c.nome ≠ null, c.nome ≠ ""
     *         c.materiale ≠ null, c.materiale ≠ ""
    */

    /* 
     * EFFECTS: Crea un giocattolo chiamato nome e fatto di materiale.
     *          Solleva NullPointerException se nome o materiale sono nulli.
     *          Solleva IllegalArgumentException se nome o materiale sono vuote.
    */
    public Giocattolo(final String nome, final String materiale) {
        if (nome == null) throw new NullPointerException("Il nome del giocattolo non può essere nullo");
        if (materiale == null) throw new NullPointerException("Il materiale del giocattolo non può essere nullo");
        if (nome == "") throw new IllegalArgumentException("Il nome del giocattolo non può essere vuoto");
        if (materiale == "") throw new IllegalArgumentException("Il materiale del giocattolo non può essere vuoto");

        this.nome = nome;
        this.materiale = materiale;

        assert repOK();
    }

    /* 
     * EFFECTS: Restituisce il nome di this.
    */
    public String getNome() { return nome; }
    
    /* 
     * EFFECTS: Restituisce il materiale di cui è fatto di this.
    */
    public String getMateriale() { return materiale; }

    @Override
    public String toString() {
        return nome + " di " + materiale;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Giocattolo)) return false;

        Giocattolo other = (Giocattolo) obj;

        return other.nome.equals(nome) && other.materiale.equals(materiale);

    }

    @Override
    public int hashCode() {
        return Objects.hash(nome,  materiale);
    }

    private boolean repOK() {
        return nome != null && nome != "" && materiale != null && materiale != "";
    }

}

