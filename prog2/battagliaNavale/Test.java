public class Test {
    public static void main(String[] args) {
        GrigliaFlotta g = new GrigliaFlotta();
        GrigliaStrategica st = new GrigliaStrategica();

        g.posiziona(new Nave(Posizione.daStringa("C3"), false, TipologiaNave.daLettera('F')));
        g.posiziona(new Nave(Posizione.daStringa("G2"), true, TipologiaNave.daLettera('C')));
        g.posiziona(new Nave(Posizione.daStringa("A0"), true, TipologiaNave.daLettera('P')));

        st.aggiorna(Posizione.daStringa("C3"),  g.riceviAttacco(Posizione.daStringa("C3")));
        st.aggiorna(Posizione.daStringa("H2"),  g.riceviAttacco(Posizione.daStringa("H2")));
        st.aggiorna(Posizione.daStringa("G2"),  g.riceviAttacco(Posizione.daStringa("G2")));
        st.aggiorna(Posizione.daStringa("J9"),  g.riceviAttacco(Posizione.daStringa("J9")));

        System.out.println(g.asString() + "\n" +  st.asString());
    }
}
