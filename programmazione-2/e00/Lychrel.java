package e00;


public class Lychrel {

    /* 
     * EFFECTS: Restituisce una stringa contenente la rappresentazione di n
     */
    static String numToString(long n) {
        return n + "";
    }

    /* 
     * REQUIRES: s deve contenere solo caratteri numerici
     * EFFECTS: Restituisce un long che rappresenta il numero s
     */
    static long stringToNum(String s) {
        long n = 0;
        for (int i=0; i < s.length(); i++) {
            n *= 10;
            n += s.charAt(1) - '0';
        }
        return n;
    }

    /* 
     * EFFECTS: Restituisce true se s è palindroma
     */
    static boolean isPalindrome(String s) {
        // Stabilisce se una stringa è palindroma
        return true;
    }

    /* 
     * EFFECTS: Restituisce la stringa ottenuta invertendo l'ordine dei caratteri in s
     */
    static String reverseString(String s) {
        if (s.length() <= 1) return s;
        return s.charAt(s.length()-1) + reverseString(s.substring(1, s.length()-1)) + s.charAt(0);
    }

    /* 
     * REQUIRES: n deve essere positivo e non deve essere un numero di Lychrel
     * MODIFIES: System.out
     * EFFECTS: Stampa su System.out la sequenza di Lychrel a partire da n
     */
    static void printLychrelSequence(long n) {
        while (!isPalindrome(numToString(n))) {
            n += stringToNum(reverseString(numToString(n)));
            System.out.println(n);
        }
    } 

    public static void main(String[] args) {
        printLychrelSequence(59);
    }

}
