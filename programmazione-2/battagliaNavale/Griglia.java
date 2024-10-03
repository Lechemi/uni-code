public interface Griglia {
    /* 
     * Interfaccia che rappresenta la griglia del gioco battaglia navale.
    */

    /* 
     * EFFECTS: Restituisce il simbolo corrispondente alla posizione pos su questa griglia.
     *          Solleva NullPointerException se pos è nulla.
    */
    public abstract char get(final Posizione pos);
    
    /* 
     * EFFECTS: Restituisce una stringa rappresentante questa griglia.
    */
    public default String asString() {
        StringBuilder sb = new StringBuilder();
        for (int riga = 0; riga < 10; riga ++) {
            for (char colonna = 65; colonna < 75; colonna++) {
                sb.append(get(new Posizione(colonna, riga)));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
