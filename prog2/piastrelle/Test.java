import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Piastrella q = new PiastrellaQuadrata(10, 5);
        Piastrella r = new PiastrellaRettangolare(12, 5, 4);

        Pavimentazione.Componente cucina = new Pavimentazione.Componente(r, 10);
        Pavimentazione.Componente bagno = new Pavimentazione.Componente(q, 15);

        List<Pavimentazione.Componente> comps = new ArrayList<>();
        comps.add(bagno);
        comps.add(cucina);

        Pavimentazione casa = new Pavimentazione(comps);

        casa.forEach(rivestimento -> {
            System.out.println(rivestimento.costo());
        });

    }
}
