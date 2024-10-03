package teoria;
class HelloWorld {

    public static void main(String[] args) {

        // TIPO LIST

        /* String[] paroleComeArray = new String[2];
        List<String> parole = new ArrayList<>();

        parole.add("Pippo");
        parole.add("Pluto");

        paroleComeArray[0] = "Pippo";
        paroleComeArray[1] = "Paperino";

        System.out.println(parole);
        System.out.println(paroleComeArray);

        for(String parola: parole){
            System.out.println(parola);
        } */

        // BOXING e UNBOXING

        /* List<Integer> numeri = new ArrayList<>();
        numeri.add(Integer.valueOf(3));
        numeri.add(Integer.valueOf(4));

        // Qua 5, 6 e intero vengono "inscatolati" (boxing) in tipo Integer
        int intero = 4;
        numeri.add(5);
        numeri.add(6);
        numeri.add(intero);

        int somma = 0;
        for(Integer n: numeri){
            somma += n;
        }
        System.out.println(somma); */

        // INPUT

        /* List <Integer> numeri = new ArrayList<>();
        Scanner s = new Scanner(System.in);
        while (s.hasNextInt()) {
            numeri.add(s.nextInt());
        }
        for (int i=numeri.size()-1; i>= 0; i--) {
            System.out.println(numeri.get(i));
        }
        s.close(); */
    
        // ARGS

        /* for (int i=0; i < args.length; i++) {
            System.out.println(args[i]);
        } */
    
    }
    
}

