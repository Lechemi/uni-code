package e00;
import java.util.Scanner;

public class Disegna_Scacchiera {

    public static char switchSymbol(char currentSymbol) {
        if (currentSymbol == '-') return '#';
        return '-';
    }

    public static String fromCharToStringOfChars(char symbol, int length) {
        String line = "";
        for (int i=0; i<length; i++) line += symbol;
        return line;
    }

    public static void main(String[] args) {

        // Prendo un singolo intero dall'utente
        Scanner s = new Scanner(System.in); 
        System.out.print("Enter square size: ");  
        int n = s.nextInt();  
        s.close();
        
        char symbolToPrint = '-';
        String lineToPrint = fromCharToStringOfChars(symbolToPrint, n);

        for (int i=0; i<8; i++) {
            for (int k=0; k<n; k++) {
                for (int j=0; j<8; j++) {
                    System.out.print(lineToPrint);
                    symbolToPrint = switchSymbol(symbolToPrint);
                    lineToPrint = fromCharToStringOfChars(symbolToPrint, n);
                }
                System.out.println();
            }
            symbolToPrint = switchSymbol(symbolToPrint);
            lineToPrint = fromCharToStringOfChars(symbolToPrint, n);
        }

    }
}
