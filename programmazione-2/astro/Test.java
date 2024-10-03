package astro;

import java.util.Arrays;
import java.util.List;

public class Test {
    public static void main(String[] args) {    
        List<Object> marte = Arrays.asList("P", "Marte", -8, -10, 0);
        List<Object> giove = Arrays.asList("P", "Giove", 5, 5, 10);
        List<Object> saturno = Arrays.asList("P", "Saturno", 2, -7, 3);
        List<Object> venere = Arrays.asList("P", "Venere", 9, -8, -3);

        List<List<Object>> data = Arrays.asList(marte, giove, saturno, venere);

        SistemaAstronomico system = new SistemaAstronomico(data);

        for (int i = 0; i<100; i++) {
            system.aggiornaVel();
            system.aggiornaPos();
        }

        System.out.println(system.toString());
        System.out.println("Energia totale = " + system.energia());

    }
}