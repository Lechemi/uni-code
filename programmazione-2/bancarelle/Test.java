package bancarelle;
import java.util.List;

import bancarelle.listino.PoliticaMoltiplicativa;

public class Test {
    public static void main(String[] args) {

        Giocattolo canepezza = new Giocattolo("cane", "pezza");
        Giocattolo soldatino = new Giocattolo("soldatino", "legno");
        Giocattolo bambola = new Giocattolo("bambola", "cacca");

        Bancarella bancarella1 = new Bancarella("Michele");
        bancarella1.mettiInVendita(canepezza, 10, new PoliticaMoltiplicativa(2));
        bancarella1.mettiInVendita(soldatino, 5, new PoliticaMoltiplicativa(3));
        bancarella1.mettiInVendita(bambola, 5, new PoliticaMoltiplicativa(4));

        Bancarella bancarella2 = new Bancarella("Martino");
        bancarella2.mettiInVendita(canepezza, 1, new PoliticaMoltiplicativa(9));
        bancarella2.mettiInVendita(soldatino, 2, new PoliticaMoltiplicativa(7));
        bancarella2.mettiInVendita(bambola, 10, new PoliticaMoltiplicativa(5));

        Bancarella bancarella3 = new Bancarella("Alessandro");
        bancarella3.mettiInVendita(canepezza, 20, new PoliticaMoltiplicativa(9));
        bancarella3.mettiInVendita(soldatino, 3, new PoliticaMoltiplicativa(1));

        List<Bancarella> bancarelle = List.of(bancarella1, bancarella2, bancarella3);

        Compratore compratore = new CompratoreConcreto("Simone", 1000, bancarelle);

        try {
            Acquisto a = compratore.compra(canepezza, 31);
            System.out.println(a.toString());
            a.effettua();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println(compratore.getBudget());

    }
}
