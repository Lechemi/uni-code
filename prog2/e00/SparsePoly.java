package e00;
import java.util.LinkedList;
import java.util.List;

/* 
 * Somma di pochi monomi di grado sparso anche molto alto
 * esempio: x + 7x^42 + 50x^1000
 * 
 * idea: lista di monomi
 * dobbiamo quindi implementare un nuovo tipo: il tipo Monomio
 * Monomio(int coeff, int degree)
 * il tipo Monomio sarà immutabile, ovvero: una volta stabiliti coeff e degree, non possono più cambiare
 * 
 * AF(c) = c.coeff * (x ^ c.degree)
 * RI(c): c.degree ≥ 0; c.coeff ≠ 0
 * 
 * cosa dobbiamo creare/sovrascrivere:
 * - costruttore(i)
 * - metodi osservazionali
 * - ToString, equals, hashCode
 * 
 * usiamo i record, simili alle struct di go, in cui queste tre cose sopra sono già fornite
 */

public class SparsePoly {
    /* 
     * OVERVIEW: Le istanze di questa classe rappresentano polinomi sparsi a una variabile
     *           con grado non negativo. Un polinomio tipico è: c0x^d0 + ... + cnx^dn
     */
    
    // Monomio
    private record Term(int degree, int coeff) {

        // Sovrascrivo il costruttore perché è utile che possa lanciare un'eccezione
        public Term(int degree, int coeff) {
            if (degree < 0) throw new NegativeExponentException("Il grado di un monomio non può essere negativo");
            // Questa eccezione la definirò come unchecked perché può essere evitata se l'utilizzatore presta attenzione
            this.degree = degree;
            this.coeff = coeff;
        }

        // Sovrascrivo toString
        public String toString(){
            return coeff + "x^" + degree;
        }
    }

    // RAPPRESENTAZIONE:
    // CAMPI
    List<Term> terms;

    /* 
     * AF(terms) = (terms[0].coeff) * x ^ (terms[0].degree) + ... + (terms[n].coeff) * x ^ (terms[n].degree)  
     *             if len(terms) == 0 => 0
     * 
     * RI(terms) = terms[i].degree >= 0
     *             && tutti gli elementi di terms devono essere Monomi (Term) (non null)
     *             && terms dev'essere odinata per grado
    */


    // COSTRUTTORI

    // EFFECTS: Costruisce il polinomio zero
    public SparsePoly() {
        terms = new LinkedList<>();
    }

    // EFFECTS: Costruisce il polinomio coeff * x ^ degree
    //          Solleva NegativeExponentException se degree < 0
    public SparsePoly(int coeff, int degree) {
        this(); // Chiamo il costruttore di questa classe (ovviamente quello senza argomenti)
        if (coeff != 0) terms.add(new Term(degree, coeff));
    }




    // METODI

    // EFFECTS: Restituisce il grado del polinomio
    public int degree() {
        if (terms.size() == 0) return -1;
        return terms.get(terms.size() - 1).degree;
        // Prendo l'ultimo termine, che so essere quello con degree maggiore
    }
    
    // EFFECTS: Restituiscie l'indice all'interno di terms del monomio il cui grado è degree
    //          altrimenti - IP - 1
    public int findCoeffByDegree(int degree) {
        // Faccio ricerca dicotomica
        int min = 0, max = terms.size() - 1;

        while (min < max) {
            //int mid = (min + max) >>> 1; // shift a destra di un bit (divisione intera per 2)
        }
        return 0;
    }

    //EFFECTS: Restituisce il coefficiente relativo a x^degree
    // REQUIRES: degree ≥ 0
    public int coeffByDegree(int degree) {
        return 0;
    }

    // EFFECTS: Restituisce other + this
    // REQUIRES: other non null
    public SparsePoly sum(SparsePoly other) {
        return new SparsePoly();
    }
}
