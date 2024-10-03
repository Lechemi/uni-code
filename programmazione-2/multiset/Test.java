public class Test {
    public static void main(String[] args) {
        MultiSet<String> set = new MapMultset<>();

        set.add("a");
        set.add("a");
        set.add("a");
        set.add("b");
        set.remove(5);

        System.out.println(set.add("c"));

        MultiSet<String> other = new ListMultiSet<>();
        other.add("a");
        other.add("a");
        other.add("b");

        set.union(other).forEach(e -> {System.out.println(e.toString());});
    }
}
