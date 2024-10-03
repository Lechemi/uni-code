
public class Test {
    public static void main(String[] args) {

        Riga r = new Riga((short) 15);
        r.aggiungi(TipoTeramino.daId('L').teramino('A', 1), new Coordinata(2, -1));
        r.aggiungi(TipoTeramino.daId('T').teramino('B', 1), new Coordinata(4, 0));
        r.aggiungi(TipoTeramino.daId('I').teramino('C', 4), new Coordinata(8, -1));
        r.aggiungi(TipoTeramino.daId('Z').teramino('D', 0), new Coordinata(10, -1));
        r.aggiungi(TipoTeramino.daId('J').teramino('E', 0), new Coordinata(12, -3));
        

        System.out.println(r.toString());

    }
}
