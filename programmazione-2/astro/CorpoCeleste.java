package astro;

abstract class CorpoCeleste {

    /* 
     * Overview: Rappresenta un corpo celeste, caratterizzato da un nome e da una posizione,  
     *           data da un punto tridimensionale. Un tipico corpo celeste è: Nome, posizione: (x, y, z).
    */

    // REP
    final String nome;
    protected Punto pos;

    /* 
     * AF(nome, pos) = nome, posizione: (pos.x, pos.y, pos.z)
     * IR(nome, pos): nome ≠ ""
     *                      nome ≠ null
     *                      pos ≠ null
    */

    // EFFECTS: Restituisce un corpo celeste chiamato nome e con posizione (x, y, z).
    //          Se nome è vuota o null, solleva un'eccezione di tipo IllegalArgumentException.
    CorpoCeleste(String nome, int x, int y, int z) {
        if (nome == "" || nome == null) throw new IllegalArgumentException();

        this.nome = nome;
        this.pos = new Punto(x, y, z);
    }

    // EFFECTS: Restituisce l'energia del corpo celeste.
    public abstract int calcolaEnergia();

    // EFFECTS: Restituisce il nome di this.
    public String getNome() {
        return nome;
    }

    // EFFECTS: Restituisce la posizione di this.
    public Punto getPosizione() {
        return pos;
    }

    @Override
    public String toString() {
        return nome + ", pos: " + pos.toString();
    }
    
}
