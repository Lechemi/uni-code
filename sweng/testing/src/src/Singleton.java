public enum Singleton {
    INSTANCE("Marco Burza");

    private String name;

    Singleton(String name) {
        this.name = name;
    }

    void changeName(String newName) {
        INSTANCE.name = newName;
    }

    public String getName() {
        return name;
    }
}
