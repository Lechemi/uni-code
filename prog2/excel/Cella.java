public interface Cella {
    // Interfaccia che rappresenta la cella di un foglio di calcolo.

    /* 
     * EFFECTS: Restituisce il valore del contenuto di questa cella.
     *          Solleva RiferimentoVuotoException se non è possibile calcolare il valore del contenuto 
     *          della cella, nel caso in cui questo dipenda da celle vuote.
    */
    int contenuto() throws RiferimentoVuotoException;
}