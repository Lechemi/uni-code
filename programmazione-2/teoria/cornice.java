package teoria;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class cornice {
    public static void main(String[] args) {
        List<String> parole = new ArrayList<>();
        Scanner s = new Scanner(System.in);

        int maxLength = 0;

        while (s.hasNext()) {
            String parola = s.next();
            if (parola.length() > maxLength) maxLength = parola.length();
            parole.add(parola);
        }

        System.out.println();

        for (int i=0; i < maxLength+4; i++) {
            System.out.print('*');
        }
        System.out.println();

        for (String parola: parole) {
            System.out.print("* " + parola);
            for (int i=0; i<=(maxLength-parola.length()); i++) {
                System.out.print(" ");
            }
            System.out.print("*");
            System.out.println();
        }

        for (int i=0; i < maxLength+4; i++) {
            System.out.print('*');
        }

        // A destra

        System.out.println();
        System.out.println();
        System.out.println();

        for (int i=0; i < maxLength+4; i++) {
            System.out.print('*');
        }
        System.out.println();

        for (String parola: parole) {
            System.out.print("*");
            for (int i=0; i<=(maxLength-parola.length()); i++) {
                System.out.print(" ");
            }
            System.out.print(parola + " *");
            System.out.println();
        }

        for (int i=0; i < maxLength+4; i++) {
            System.out.print('*');
        }

        // Centrato

        System.out.println();
        System.out.println();
        System.out.println();

        for (int i=0; i < maxLength+4; i++) {
            System.out.print('*');
        }
        System.out.println();

        for (String parola: parole) {
            System.out.print("*");
            for (int i=0; i<=((maxLength-parola.length())/2); i++) {
                System.out.print(" ");
            }
            System.out.print(parola);
            for (int i=0; i<=((maxLength-parola.length())/2); i++) {
                System.out.print(" ");
            }
            System.out.print("*");
            System.out.println();
        }

        for (int i=0; i < maxLength+4; i++) {
            System.out.print('*');
        }
        
        System.out.println();

        s.close();

    }
}
