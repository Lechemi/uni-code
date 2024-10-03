package e00;
public class Kaprekar {

    /* 
     * REQUIRES: array di 4 interi non negativi
     * EFFECTS: riordina in maniera decrescente gli elementi di digits
     */
    static short[] reorderDown(short[] digits) {
        if (digits[0] <= digits[1]) {
            short box = digits[0];
            digits[0] = digits[1];
            digits[1] = box;
        }

        if (digits[2] <= digits[3]) {
            short box = digits[2];
            digits[2] = digits[3];
            digits[3] = box;
        }

        if (digits[0] <= digits[2]) {
            short box = digits[0];
            digits[0] = digits[2];
            digits[2] = box;
        }

        if (digits[1] <= digits[3]) {
            short box = digits[1];
            digits[1] = digits[3];
            digits[3] = box;
        }

        if (digits[1] <= digits[2]) {
            short box = digits[1];
            digits[1] = digits[2];
            digits[2] = box;
        }

        return digits;
    }

    /* 
     * REQUIRES: array di 4 interi non negativi
     * EFFECTS: riordina in maniera crescente gli elementi di digits
     */
    static short[] reorderUp(short[] digits) {
        if (digits[0] >= digits[1]) {
            short box = digits[0];
            digits[0] = digits[1];
            digits[1] = box;
        }

        if (digits[2] >= digits[3]) {
            short box = digits[2];
            digits[2] = digits[3];
            digits[3] = box;
        }

        if (digits[0] >= digits[2]) {
            short box = digits[0];
            digits[0] = digits[2];
            digits[2] = box;
        }

        if (digits[1] >= digits[3]) {
            short box = digits[1];
            digits[1] = digits[3];
            digits[3] = box;
        }

        if (digits[1] >= digits[2]) {
            short box = digits[1];
            digits[1] = digits[2];
            digits[2] = box;
        }

        return digits;
    }

    /* 
     * REQUIRES: intero positivo di 4 cifre
     * EFFECTS: ritorna il riferimento ad un array i cui elementi sono 
     * le cifre decimali di n, preservandone l'ordine
     */
    static short[] numToArrayOfDigits(short n) {
        short[] arrayOfDigits = new short[4];
        for (short i=0; i<4; i++, n/=10) arrayOfDigits[i] = (short) (n%10);
        return arrayOfDigits;
    }

    /* 
     * REQUIRES: array != null, ciascun elemento di array deve essere compreso tra 0 e 9, len(array) = 4
     * EFFECTS: ritorna uno short composto dalle cifre decimali contenute in array, preservandone l'ordine
     */
    static short arrayOfDigitsToNum(short[] array) {
        short num = 0;
        short k = 1000;
        for (short i=0; i<4; i++, k/=10) num += array[i] * k;
        return num;
    }
    
    /* 
     * REQUIRES: n intero positivo di 4 cifre
     * EFFECTS: ritorna il numero successivo della sequenza di Kaprekar
     */
    static short kaprekarStep(short n) {
        if (n<0 || n > 9999) throw new IllegalArgumentException("Numero inserito non valido. Inserito: " + n);
        short[] array = numToArrayOfDigits(n);
        return (short) (arrayOfDigitsToNum(reorderDown(array)) - arrayOfDigitsToNum(reorderUp(array)));
    }

    public static void main(String[] args) {
        short n = 11122;
        int cont = 0;

        System.out.println(n);

        while (n!=6174) {
            n = kaprekarStep(n);
            cont ++;
            System.out.println(n);
        } 

        System.out.println("Numero di passi per arrivare a 6174: " + cont);

    }
}
