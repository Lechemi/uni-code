public class Test {
    public static void main(String[] args) throws InvalidFormatException, AutoRiferimentoException {
        FoglioDiCalcolo f = new FoglioDiCalcolo();
        f.scrivi("A1", 5);

        f.scrivi("C2", 6);
        f.scrivi("C3", 10);
        f.scrivi("C4", -2);

        f.scrivi("B7", "*", "C2:C4");

        System.out.println(f.leggi("B7"));

        f.scrivi("C3", "MAX", "B7;A1");
    }
}
