package e04;

/* import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader; */
import java.io.IOException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Test {
    public static void main(String[] args) throws IOException {

        // TESTING UNBOUNDEDINTQUEUE
        /* UnboundedIntQueue q = new UnboundedIntQueue();

        File file = new File("/Users/micheleceroni/Code/Programmazione 2/e04/input.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
 
        Integer num = 0;

        String line;
        while ((line = br.readLine()) != null) {
            if (line.equals("+1")) {
                q.enqueue(++num);
            }else{
                try {
                    System.out.println(q.dequeue());
                } catch (EmptyQueueException e) {
                    System.out.println("Tentativo di dequeue da coda vuota.");
                    System.out.println(q.toString());
                    break;
                }
            }
        }

        System.out.println(q.toString());

        br.close(); */

        // TESTING STRINGTOINTMAP
        StringToIntMap map = new StringToIntMap();
        StringToIntMap othermap = new StringToIntMap();

        map.put("primo elemento", 12);
        map.put("secondo elemento", 2);
        map.put("terzo elemento", 4);
        map.put("quarto elemento", 7);

        othermap.put("secondo elemento", 2);
        othermap.put("terzo elemento", 4);
        othermap.put("quarto elemento", 7);
        othermap.put("primo elemento", 12);

        System.out.println(map.equals(othermap));
    }
    
}
