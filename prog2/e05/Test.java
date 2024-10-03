package e05;

import java.util.Iterator;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        Iterator<Integer> filter = new FilterIterator<>(
            List.of(22, 1, 2, 3, 4, 5, 6, 7, 8, 9).iterator(), 
            new IsEven());

        while (true) System.out.println(filter.next());
    }
}
