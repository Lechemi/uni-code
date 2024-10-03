package astro;

class Stella extends CorpoCeleste {

    /* 
     * Overview: Rappresenta una stella, ossia un corpo celeste di posizione fissa.
     *           Le istanze di questo tipo sono immutabili.
     *           Una tipica stella è: Nome, posizione: (x, y, z)
    */

    /* 
     * AF_Stella(nome, pos) = AF_CorpoCeleste(nome, pos)
     * IR_Stella(nome, pos) = AF_CorpoCeleste(nome, pos)
    */

    // EFFECTS: Restituisce una stella chiamata nome, e con posizione (x, y, z).
    //          Se nome è vuota o null, solleva un'eccezione di tipo IllegalArgumentException.
    Stella(String nome, int x, int y, int z) {
        super(nome, x, y, z);
    }

    // Effects: Restituisce l'energia di this, data dal prodotto della sua energia potenziale
    //          per la sua energia cinetica. Essendo le stelle ferme, tale prodotto vale 0.
    @Override
    public int calcolaEnergia() {
        return 0;
    }
    
}
