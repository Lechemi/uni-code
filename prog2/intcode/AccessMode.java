package intcode;
public enum AccessMode {

    /* Uso questa enum solo per "dare un nome" alle varie modalità d'accesso
    in questo modo poi, al posto di scrivere if (a == 0) ...
    potrò fare if (accessmode == POSITION) ...
    Si in questo caso è un po' "superfluo" */
    
    // Questi sono i valori che può assumere una variabile di tipo AccessMode
    POSITION(0), // indirizzamento diretto
    IMMEDIATE(1), // valore
    RELATIVE(2); // indirizzamento indiretto

    private final int code;

    private AccessMode(int code) {
        this.code = code;
    }

    // EFFECTS: Resituisce l'AccessMode con codice code. 
    //          Solleva IllegalArgumentExceptio se code non è associato a nessuna modalità d'accesso.
    // Itero su tutti i codici d'accesso e restituisco la costante giusta (poco efficiente tbh)
    // values() restituisce un array riempito con le costanti dichiarate (è una funzione dei tipi enum)
    public static AccessMode fromCode(int code) {
        for (AccessMode m : values()) if (m.code == code) return m;

        throw new IllegalArgumentException("Invalid Mode: " + code);
    }

}