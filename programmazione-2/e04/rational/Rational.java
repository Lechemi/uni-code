package e04.rational;

public class Rational {
    /* 
     * OVERVIEW: Le istanze di questa classe non sono mutabili. 
     *           Ogni istanza rappresenta un numero razionale. Un tipico numero razionale è x/y.
    */
    private int num, den;

    /* 
     * AF(num, den) = num/den
     * IR(num, den) = den > 0
    */


    // COSTRUTTORE

    /* 
     * EFFECTS: Solleva un'eccezione di tipo NegativeDenominatorException se den ≤ 0.
     *          Altrimenti restituisce il numero razionale num/den.
    */
    public Rational(int num, int den) {

        if (den<=0) throw new NegativeDenominatorException();

        this.num = num;
        this.den = den;
        riduci();
    }


    // METODI

    /* 
     * MODIFIES: this
     * EFFECTS: modifica this riducendolo ai minimi termini.
    */
    private void riduci() {
        int mcd = mcd();
        while (mcd != 1) {
            num /= mcd;
            den /= mcd;
            mcd = mcd();
        }

        assert repOK();
    }

    //EFFECTS: Restituisce il massimo comune divisore tra this.num e this.den. 
    private int mcd() {
        int tempMcd;

        int tempNum = (num >= 0) ? num : -num;
        tempMcd = (tempNum > den) ? den : tempNum;

        while (num % tempMcd != 0 || den % tempMcd != 0) tempMcd --;

        return tempMcd;
    }

    // EFFECTS: restituisce this.num.
    public int getNum() {
        return num;
    }

    // EFFECTS: restituisce this.den.
    public int getDen() {
        return den;
    }

    /* 
     * EFFECTS: restituisce un razionale equivalente al risultato della somma this + other.
    */
    public Rational sum(Rational other) {
        Rational sum = new Rational(num, den);

        sum.den = den * other.den;
        sum.num = (sum.den / den) * num + (sum.den / other.den) * other.num;
        sum.riduci();

        return sum;
    }

    /* 
     * EFFECTS: restituisce un razionale equivalente al risultato della sottrazione this - other.
    */
    public Rational subtractBy(Rational other) {
        Rational sub = new Rational(num, den);

        sub.den = den * other.den;
        sub.num = (sub.den / den) * num - (sub.den / other.den) * other.num;
        sub.riduci();

        return sub;
    }

    /* 
     * EFFECTS: restituisce un razionale equivalente al risultato della moltiplicazione this * other.
    */
    public Rational multiplyBy(Rational other) {
        return new Rational(num*other.num, den*other.den);
    }

    /* 
     * EFFECTS: Solleva un'eccezione del tipo ZeroDivisorException se other è uguale a 0.
     *          Altrimenti, restituisce un razionale equivalente al risultato della divisione this/other.
    */
    public Rational divideBy(Rational other) {
        if (other.num == 0) throw new ZeroDivisorException();

        if (other.num < 0) {

        }

        return new Rational(num*(-other.den), den*(-other.num));
    }
    
    // METODI ADDIZIONALI

    private boolean repOK(){
        return den > 0;
    }

    @Override
    public String toString() {
        return (num + "/" + den);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Rational)) return false;

        Rational other = (Rational) o;

        return (other.num == num && other.den == den);
    } 

    @Override
    public int hashCode() {
        return Integer.hashCode(num/den);
    }

}
