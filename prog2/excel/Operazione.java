import java.util.Iterator;
import java.util.Objects;

public enum Operazione {
    
    PIÙ("+"){
        @Override
        public int esegui(final SerieIndirizzi indirizzi, final FoglioDiCalcolo f) throws RiferimentoVuotoException {
            int totale = 0;
            Iterator<Indirizzo> inds = indirizzi.iterator();
            while (inds.hasNext()) {
                Integer val = f.leggi(inds.next());
                if (val == null) throw new RiferimentoVuotoException("La formula si riferisce ad una cella vuota.");
                totale += val;
            }
            return totale;
        }
    },
    MENO("-"){
        @Override
        public int esegui(final SerieIndirizzi indirizzi, final FoglioDiCalcolo f) throws RiferimentoVuotoException {
            boolean primo = true;
            int totale = 0;
            Iterator<Indirizzo> inds = indirizzi.iterator();
            while (inds.hasNext()) {
                Integer val = f.leggi(inds.next());
                if (val == null) throw new RiferimentoVuotoException("La formula si riferisce ad una cella vuota.");
                if (primo) {
                    totale = val;
                    primo = false;
                    continue;
                }
                totale -= val;
            }
            return totale;
        }
    },
    PER("*"){
        @Override
        public int esegui(final SerieIndirizzi indirizzi, final FoglioDiCalcolo f) throws RiferimentoVuotoException {
            boolean primo = true;
            int totale = 0;
            Iterator<Indirizzo> inds = indirizzi.iterator();
            while (inds.hasNext()) {
                Integer val = f.leggi(inds.next());
                if (val == null) throw new RiferimentoVuotoException("La formula si riferisce ad una cella vuota.");
                if (primo) {
                    totale = val;
                    primo = false;
                    continue;
                }
                totale *= val;
            }
            return totale;
        }
    },
    DIVISO("/"){
        @Override
        public int esegui(final SerieIndirizzi indirizzi, final FoglioDiCalcolo f) throws RiferimentoVuotoException {
            boolean primo = true;
            int totale = 0;
            Iterator<Indirizzo> inds = indirizzi.iterator();
            while (inds.hasNext()) {
                Integer val = f.leggi(inds.next());
                if (val == null) throw new RiferimentoVuotoException("La formula si riferisce ad una cella vuota.");
                if (primo) {
                    totale = val;
                    primo = false;
                    continue;
                }
                if (val == 0) throw new RiferimentoVuotoException("Divisione per zero.");
                totale /= val;
            }
            return totale;
        }
    },
    MAX("MAX"){
        @Override
        public int esegui(final SerieIndirizzi indirizzi, final FoglioDiCalcolo f) throws RiferimentoVuotoException {
            int max = Integer.MIN_VALUE;
            Iterator<Indirizzo> inds = indirizzi.iterator();
            while (inds.hasNext()) {
                Integer val = f.leggi(inds.next());
                if (val == null) throw new RiferimentoVuotoException("La formula si riferisce ad una cella vuota.");
                if (val > max) max = val;
            }
            return max;
        }
    },
    MIN("MIN"){
        @Override
        public int esegui(final SerieIndirizzi indirizzi, final FoglioDiCalcolo f) throws RiferimentoVuotoException {
            Integer min = Integer.MAX_VALUE;
            Iterator<Indirizzo> inds = indirizzi.iterator();
            while (inds.hasNext()) {
                Integer val = f.leggi(inds.next());
                if (val == null) throw new RiferimentoVuotoException("La formula si riferisce ad una cella vuota.");
                if (val < min) min = val;
            }
            return min;
        }
    };

    private final String code;

    private Operazione(final String code) {
        this.code = code;
    }

    public static Operazione fromCode(final String code) throws InvalidFormatException {
        Objects.requireNonNull(code, "La stringa che rappresenta l'operazione non può essere nulla.");
        for (Operazione o : values()) if (o.code == code) return o;

        throw new InvalidFormatException("Operazione non valida: " + code);
    }

    public abstract int esegui(final SerieIndirizzi indirizzi, final FoglioDiCalcolo f) throws RiferimentoVuotoException;
}
