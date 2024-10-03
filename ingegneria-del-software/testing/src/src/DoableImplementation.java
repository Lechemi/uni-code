public class DoableImplementation implements Doable{

    private String name;

    public DoableImplementation(String name) {
        this.name = name;
    }

    public void otherMethod() {
        System.out.println("bellissima");
    }

    @Override
    public void doThing() {
        System.out.println("Bella");
    }
}
