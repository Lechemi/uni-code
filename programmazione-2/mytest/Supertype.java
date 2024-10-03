package mytest;

public class Supertype {

    public static void ciao(String s) {
        if (s == null) throw new IllegalArgumentException("s non può essere null");

        System.out.println(s);
    }
}
