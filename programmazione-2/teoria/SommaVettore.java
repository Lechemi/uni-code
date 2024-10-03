package teoria;
public class SommaVettore {

    /* 
     * SPECIFICA STILE LISKOV
     * Breve desc: funzione che restituisce la somma dei valori avuti per argomento
     * REQUIRES: valori != null
     * MODIFIES: niente
     * EFFECTS: Restituisce un intero s uguale alla somma di tutti gli elementi del vettore
     */
    static int sommaVettore(int[] valori) { // Astrazione per parametrizzazione
        // Astrazione per specificazione
        int somma = 0;
        for (int i=0; i<valori.length; i++) {
            somma += valori[i];
        }
        return somma;
    }

    public static void main(String[] args) {
        int[] peso = new int[] {50, 60, 90};
        int[] telefoni = new int[] {1, 4, 2, 5, 2};

        int pesoTotale = sommaVettore(peso);
        int telefoniTotale = sommaVettore(telefoni);

        System.out.println("Il peso totale è " + pesoTotale);
        System.out.println("Il numero totale di telefoni è " + telefoniTotale);

    }
}
