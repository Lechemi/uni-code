package e05;

import java.util.function.Predicate;

public class IsEven implements Predicate <Integer> {
    
    @Override
    public boolean test(Integer n) {
        return n%2 == 0;
    }
}
