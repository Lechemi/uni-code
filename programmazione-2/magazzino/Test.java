public class Test {
    public static void main(String[] args) {
        Magazzino m = new Magazzino(3);

        m.depositaIn(0, new Pacco("A", 2));
        m.depositaIn(0, new Pacco("B", 2));
        m.depositaIn(2, new Pacco("C", 2));
        m.depositaIn(1, new Pacco("D", 2));
        m.depositaIn(1, new Pacco("E", 2));
        m.depositaIn(1, new Pacco("F", 2));

        System.out.println(m.toString());

        Robot micro = new Micro(m);
        micro.sposta(2, 1, 2);
        System.out.println("DOPO");
        System.out.println(m.toString());
    
    }
}
