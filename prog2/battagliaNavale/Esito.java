public enum Esito {
    Colpito(){
        @Override
        public String toString() {
            return "*";
        }
    },
    ColpitoEAffondato(){
        @Override
        public String toString() {
            return "#";
        }
    },
    Mancato(){
        @Override
        public String toString() {
            return "•";
        }
    };

    public abstract String toString();
}
