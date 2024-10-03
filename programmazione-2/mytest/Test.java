package mytest;

/* 
 * da testare:
 * - posso chiamare sull'oggetto x solo i metodi del tipo apparente di x; VERO
 * 
*/

public class Test {
    public static void main(String[] args) {
        Mutabie m = new Mutabie("WHERE THE FUCK");
        System.out.println(m.nome.toString());
        System.out.println("HWIORGFPAWIRUGBANWORIBF");
    }
}
