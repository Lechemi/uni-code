import java.util.LinkedList;
import java.util.List;

public class Extra extends Robot {

    public Extra(Magazzino m) {
        super(m);
    }

    @Override
    public boolean sposta(int da, int a, int n) {
        if (n < 0) throw new IllegalArgumentException("Il numero di pacchi da spostare dev'essere positivo.");
        if (n > magazzino.numeroPacchiDi(da)) return false;

        List<Pacco> prelevati = new LinkedList<>();
        for (int i = 0; i < n; i++) prelevati.add(magazzino.prelevaDa(da));
        for (int j = prelevati.size(); j >= 0; j--) magazzino.depositaIn(a, prelevati.get(j));

        return true;
    }
    
}
