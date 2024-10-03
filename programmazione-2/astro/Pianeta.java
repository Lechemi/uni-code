package astro;

class Pianeta extends CorpoCeleste {

    /* 
     * Overview: Rappresenta un pianeta, ossia un corpo celeste avente posizione variabile.
     *           Tale posizione varia secondo un vettore velocità tridimensionale, che caratterizza il pianeta.
     *           Le istanze di questo tipo sono mutabili.
     *           Un tipico pianeta è: Nome, posizione: (x, y, z), velocità: (vx, vy, vz).
    */

    private Punto vel;

    /* 
     * AF(nome, pos, vel) = nome, posizione: (pos.x, pos.y, pos.z), velocità: (vel.x, vel.y, vel.z)
     * IR(vel): vel ≠ null
    */

    // EFFECTS: Restituisce un pianeta chiamato nome, con posizione (x, y, z) e velocità nulla.
    //          Se nome è vuota o null, solleva un'eccezione di tipo IllegalArgumentException.
    Pianeta(String nome, int x, int y, int z) {
        super(nome, x, y, z);
        vel = new Punto(0, 0, 0);
    }

    // EFFECTS: Restituisce l'energia di this, data dal prodotto della sua energia potenziale
    //          per la sua energia cinetica. 
    @Override
    public int calcolaEnergia() {
        return pos.norma() * vel.norma();
    }

    // MODIFIES: this
    // EFFECTS: Modifica la velocità di this confrontando, coordinata per coordinata, 
    //          le posizioni di this e c.
    //          Per ogni coordinata del vettore velocità:
    //              - aggiunge 1 se la relativa coordinata posizionale di this è minore di quella di c
    //              - toglie 1 se la relativa coordinata posizionale di this è maggiore di quella di c
    //              - non fa nulla la relativa coordinata posizionale di this è uguale a quella di c
    void cambiaVel(CorpoCeleste c) {

        if (c == null) throw new IllegalArgumentException();

        int[] deltas = {0, 0, 0};

        if (pos.getX() < c.getPosizione().getX()) {
            deltas[0] = 1;
        } else if (pos.getX() > c.getPosizione().getX()) {
            deltas[0] = -1;
        }

        if (pos.getY() < c.getPosizione().getY()) {
            deltas[1] = 1;
        } else if (pos.getY() > c.getPosizione().getY()) {
            deltas[1] = -1;
        }

        if (pos.getZ() < c.getPosizione().getZ()) {
            deltas[2] = 1;
        } else if (pos.getZ() > c.getPosizione().getZ()) {
            deltas[2] = -1;
        }

        vel = vel.somma(new Punto(deltas[0], deltas[1], deltas[2]));
    }

    // MODIFIES: this
    // EFFECTS: Sposta this per un'unità di tempo e secondo moto rettilineo uniforme avente velocità di this.
    void cambiaPosizione() {
        pos = pos.somma(vel);
    }
    

    @Override
    public String toString() {
        return super.toString() + ", vel: " + vel.toString();
    }

}
