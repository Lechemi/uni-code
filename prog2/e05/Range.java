package e05;
import java.util.Iterator;

/* 
 * AF(from, to, step) = [from, from+step, from+2step, ... to]
 * 
 * RI(from, to, step): 
 * - step ≠ 0
 */

public class Range {

    private final int from;
    private final int to;
    private final int step;

    public Range(int from, int to, int step) {
        if (step == 0) throw new IllegalArgumentException();
        this.from = from;
        this.to = to;
        this.step = step;
    }

    public Range(int to, int step) {
        if (step == 0) throw new IllegalArgumentException();
        this.from = 0;
        this.to = to;
        this.step = step;
    }

    public Range(int to) {
        this(to, to > 0 ? +1 : -1);
    }

    public Iterator<Integer> iterator() {
        return new Iterator<Integer>() {

            int next = from;

            @Override
            public boolean hasNext() {
                return false;
            }

            @Override
            public Integer next() {
                return null;
            }

        };
    }
    
}
