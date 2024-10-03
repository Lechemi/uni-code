import java.util.Set;

/* 
 * Nota: non tocco questa interfaccia perché di base è solo una traccia per implementare la classe concreta.
*/

public interface Teramino {
    /* 
     * Interfaccia che rappresenta un teramino.
    */

    /* 
     * EFFECTS: Restituisce il nome di questo teramino.
    */
    char nome();

    /* 
     * EFFECTS: Restituisce l'insieme delle coordinate che descrivono questo teramino.
    */
    Set<Coordinata> coordinate();

    /* 
     * EFFECTS: Restituisce un nuovo teramino, ottenuto dalla rotazione di 90° verso destra 
     *          di questo teramino.
    */
    Teramino ruota();

    /* 
     * EFFECTS: Restituisce il bounding box di questo teramino.
    */
    Rettangolo boundingBox();
}
