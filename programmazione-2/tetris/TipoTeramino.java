import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum TipoTeramino {
    /* 
     * Enum le cui istanze (immutabili) rappresentano uno dei tipi convenzionali di teramino.
    */

    I('I'){
        @Override
        public TeraminoConcreto teramino(final char n, final int r) {
            if (r < 0) throw new IllegalArgumentException("Il numero di rotazioni dev'essere positivo.");
            Set<Coordinata> set = new HashSet<>(Arrays.asList(
                new Coordinata(0, 0),
                new Coordinata(0, 1),
                new Coordinata(0, 2),
                new Coordinata(0, 3)
            ));
            TeraminoConcreto t = new TeraminoConcreto(set, n);
            for (int i = 0; i < r%4; i++) t = t.ruota();
            return t;
        }
    },
    J('J'){
        @Override
        public TeraminoConcreto teramino(final char n, final int r) {
            if (r < 0) throw new IllegalArgumentException("Il numero di rotazioni dev'essere positivo.");
            Set<Coordinata> set = new HashSet<>(Arrays.asList(
                new Coordinata(0, 0),
                new Coordinata(1, 0),
                new Coordinata(1, 1),
                new Coordinata(1, 2)
            ));
            TeraminoConcreto t = new TeraminoConcreto(set, n);
            for (int i = 0; i < r%4; i++) t = t.ruota();
            return t;
        }
    },
    L('L'){
        @Override
        public TeraminoConcreto teramino(final char n, final int r) {
            if (r < 0) throw new IllegalArgumentException("Il numero di rotazioni dev'essere positivo.");
            Set<Coordinata> set = new HashSet<>(Arrays.asList(
                new Coordinata(0, 0),
                new Coordinata(0, 1),
                new Coordinata(0, 2),
                new Coordinata(1, 0)
            ));
            TeraminoConcreto t = new TeraminoConcreto(set, n);
            for (int i = 0; i < r%4; i++) t = t.ruota();
            return t;
        }
    },
    O('O'){
        @Override
        public TeraminoConcreto teramino(final char n, final int r) {
            if (r < 0) throw new IllegalArgumentException("Il numero di rotazioni dev'essere positivo.");
            Set<Coordinata> set = new HashSet<>(Arrays.asList(
                new Coordinata(0, 0),
                new Coordinata(0, 1),
                new Coordinata(1, 0),
                new Coordinata(1, 1)
            ));
            TeraminoConcreto t = new TeraminoConcreto(set, n);
            for (int i = 0; i < r%4; i++) t = t.ruota();
            return t;
        }
    },
    S('S'){
        @Override
        public TeraminoConcreto teramino(final char n, final int r) {
            if (r < 0) throw new IllegalArgumentException("Il numero di rotazioni dev'essere positivo.");
            Set<Coordinata> set = new HashSet<>(Arrays.asList(
                new Coordinata(0, 0),
                new Coordinata(1, 0),
                new Coordinata(1, 1),
                new Coordinata(2, 1)
            ));
            TeraminoConcreto t = new TeraminoConcreto(set, n);
            for (int i = 0; i < r%4; i++) t = t.ruota();
            return t;
        }
    },
    T('T'){
        @Override
        public TeraminoConcreto teramino(final char n, final int r) {
            if (r < 0) throw new IllegalArgumentException("Il numero di rotazioni dev'essere positivo.");
            Set<Coordinata> set = new HashSet<>(Arrays.asList(
                new Coordinata(0, 0),
                new Coordinata(0, 1),
                new Coordinata(1, 1),
                new Coordinata(-1, 1)
            ));
            TeraminoConcreto t = new TeraminoConcreto(set, n);
            for (int i = 0; i < r%4; i++) t = t.ruota();
            return t;
        }
    },
    Z('Z'){
        @Override
        public TeraminoConcreto teramino(final char n, final int r) {
            if (r < 0) throw new IllegalArgumentException("Il numero di rotazioni dev'essere positivo.");
            Set<Coordinata> set = new HashSet<>(Arrays.asList(
                new Coordinata(0, 0),
                new Coordinata(0, 1),
                new Coordinata(1, 0),
                new Coordinata(-1, 1)
            ));
            TeraminoConcreto t = new TeraminoConcreto(set, n);
            for (int i = 0; i < r%4; i++) t = t.ruota();
            return t;
        }
    };

    private final char id;

    /* 
     * AF(c) = Il tipo di teramino è rappresentato da una lettera: c.id
     * RI(c) : c.id dev'essere una delle lettere: I, J, L, O, S, T, Z (non so quanto ha senso la RI)
    */
    
    /* 
     * EFFECTS: Costruisce il tipo di teramino identificato da id.
    */
    private TipoTeramino(final char id) {
        this.id = id;
    }

    /* 
     * EFFECTS: Restituisce il tipo di tearmino identficato da id.
     *          Solleva IllegalArgumentException se id non identifica nessun tipo di teramino.
    */
    public static TipoTeramino daId(final char id) {
        for (TipoTeramino tipo : values()) if (tipo.id == id) return tipo;

        throw new IllegalArgumentException("Tipo di teramino non valido.");
    }

    /* 
     * EFFECTS: Restituisce il teramino di nome n corrispondente a this, ruotato r volte.
     *          Solleva IllegalArgumentException se r è negativo.
    */
    public abstract TeraminoConcreto teramino(final char n, final int r);
}
