import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        Shell myShell = new Shell();

        BufferedReader reader;

        System.out.println();
        System.out.println("--- INIZIO ---");

		try {
			reader = new BufferedReader(new FileReader("/Users/micheleceroni/Code/Programmazione 2/filesystem/input.txt"));
			String line = reader.readLine();

			while (line != null) {
                System.out.println(line);
				myShell.parseCommand(line);
				line = reader.readLine();
			}

			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
    }
}
