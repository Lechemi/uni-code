package e05;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class FilterIterator<T> implements Iterator<T> {

    /* 
     * stiamo prendendo un iteratore normale source che fa scorrere gli elementi
     * di una collezione (di tipo base T) in modo sequenziale, e lo siamo facendo passare attraverso un filtro
     * per creare un nuovo iteratore FilterIterator che restituisce solo gli elementi restituiti da source
     * che rispettano un certo criterio stabilito da filter.
     */

    private final Iterator<T> source;
    private final Predicate<T> filter;

    private T next = null;
    private boolean hasNext = false;

    public FilterIterator(final Iterator<T> source, Predicate<T> filter) {
      this.source = source;
      this.filter = filter;
    }

    @Override
    public boolean hasNext() {
        // dobbiamo verificare che ci sia un prossimo elemento e che 
        // questo soddisfi il predicato filter
        while (!hasNext && source.hasNext()) {
            next = source.next();
            hasNext = filter.test(next);
        }
        return hasNext;
    }

    @Override
    public T next() {
        // In questo caso next() deve solo restituire quello che ha trovato hasNext(), cioè next
        if (!hasNext()) throw new NoSuchElementException();
        hasNext = false; // In questo modo posso rientrare nel while di hasNext() per passare al successivo
        return next;
    }

}