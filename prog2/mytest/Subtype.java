package mytest;

public class Subtype extends Supertype {

    public static void ciaociao(String s) {
        if (s == null) throw new IllegalArgumentException("s non può essere null");

        ciao(s);
    }
}
