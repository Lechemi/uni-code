import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> titoli = List.of("ylang ylang", "ciao", "come");
        List<Durata> durate = List.of(new Durata(120), new Durata(130), new Durata(144));

        Album nuovo = new Album("mio album", titoli, durate);

        List<String> ts = List.of("mamam", "h", "him");
        List<Durata> ds = List.of(new Durata(1000), new Durata(134), new Durata(114));

        Album a = new Album("heeem", ts, ds);

        Playlist miaP = new Playlist("indie");
        miaP.aggiungi(a.branoDaPosizione(2));
        miaP.aggiungi(a.branoDaPosizione(3));
        miaP.aggiungi(nuovo.branoDaPosizione(1));
        System.out.println(miaP.toString());

        Playlist altra = new Playlist("altra");
        altra.aggiungi(nuovo.branoDaTitolo("ciao"));
        altra.aggiungi(nuovo.branoDaTitolo("come"));
        altra.aggiungi(a.branoDaTitolo("h"));
        altra.aggiungi(nuovo.branoDaTitolo("ylang ylang"));
        altra.aggiungi(nuovo.branoDaTitolo("ylang ylang"));
        System.out.println(altra.toString());

        Playlist fusa = altra.fondiCon(miaP, "fusioneeee");
        System.out.println(fusa.toString());

        System.out.println(fusa.contieneBranoIntitolato("h"));
        
    }
}
