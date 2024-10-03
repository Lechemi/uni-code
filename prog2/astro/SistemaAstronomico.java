package astro;

import java.util.ArrayList;
import java.util.List;

public class SistemaAstronomico {

    /* 
     * Overview: Rappresenta un sistema astronomico, ossia una collezione di corpi celesti
     *           che interagiscono tra loro.
     *           Le istanze di questa classe non sono mutabili.
     *           Un tipico sistema astronomico è: 
     *              Nome[1], posizione[1]: (x, y, z), velocità[1]: (vx, vy, vz)
     *              ...
     *              Nome[n], posizione[n]: (x, y, z), velocità[n]: (vx, vy, vz)
    */
    
    private final List<CorpoCeleste> stato;

    /* 
     * AF(c) = qua vorrei scrivere che per ogni elemento di stato, la funzione di astrazione
     *         è quella di CorpoCeleste, ma non posso perché i pianeti hanno anche la velocità.
     * IR(c) = stessa cosa di AF
    */
    
    // Costruttore
    // REQUIRES: data[i] dev'essere nel formato: "T", "Nome", x, y, z
    // EFFECTS: Restituisce il sistema astronomico composto dai corpi celesti creati a partire
    //          da ciascun elemento di data.
    public SistemaAstronomico(List<List<Object>> data) {
        List<CorpoCeleste> stato = new ArrayList<>();

        for (List<Object> quintupla : data) {
            String tipo = (String) quintupla.get(0);
            String nome = (String) quintupla.get(1);
            int x = (int) quintupla.get(2);
            int y = (int) quintupla.get(3);
            int z = (int) quintupla.get(4);

            stato.add(tipo == "P" ? new Pianeta(nome, x, y, z) : new Stella(nome, x, y, z));
        }

        this.stato = stato;
    }

    // MODIFIES: this
    // EFFECTS: Modifica il vettore velocità di ciascun pianeta di this confrontando le sue 
    //          coordinate spaziali con quelle degli altri corpi celesti.
    public void aggiornaVel() {

        for (CorpoCeleste x : stato) {
            for (CorpoCeleste y : stato) {
                if (x.nome != y.nome && x instanceof Pianeta) {
                    Pianeta p = (Pianeta) x;
                    p.cambiaVel(y);
                }
            }
        }

    }

    // MODIFIES: this
    // EFFECTS: Ogni pianeta di this viene spostato per un'unità di tempo e 
    //          secondo moto rettilineo uniforme avente velocità del pianeta stesso.
    public void aggiornaPos() {
        for (CorpoCeleste c : stato) {
            if (c instanceof Pianeta) {
                Pianeta p = (Pianeta) c;
                p.cambiaPosizione();
            }
        }
    }

    // EFFECTS: Restituisce l'energia totale del sistema astronomico, data dall'energia di ciascun corpo celeste.
    public int energia() {
        int en = 0;
        for (CorpoCeleste c : stato) en += c.calcolaEnergia();
        return en;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (CorpoCeleste c : stato) {
            if (c instanceof Pianeta) {
                sb.append("Pianeta: ");
            } else {
                sb.append("Stella: ");
            }
            sb.append(c.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
}
