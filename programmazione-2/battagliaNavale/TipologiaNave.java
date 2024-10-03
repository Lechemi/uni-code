public enum TipologiaNave {
    Portaerei('P'){
        @Override
        public int lunghezza() {
            return 8;
        }
    },
    Fregata('F'){
        @Override
        public int lunghezza() {
            return 7;
        }
    },
    Sottomarino('S'){
        @Override
        public int lunghezza() {
            return 5;
        }
    },
    Cacciamine('C'){
        @Override
        public int lunghezza() {
            return 2;
        }
    };

    public final char id;

    private TipologiaNave(final char id) {
        this.id =id;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    public static TipologiaNave daLettera(final char l) {
        for (TipologiaNave t : values()) if (l == t.id) return t;

        throw new IllegalArgumentException("La lettera non corrisponde a nessuna tipologia di nave.");
    }

    public abstract int lunghezza();
}
