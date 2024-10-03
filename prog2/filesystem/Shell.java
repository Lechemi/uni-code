import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import entry.Directory;
import entry.Entry;

public class Shell {
    /* 
     * Classe che rappresenta una shell.
     * Le istanze di questa classe sono mutabili.
    */

    // REP
    private final FileSystem fs = new FileSystem();
    private Directory currentDirectory = fs.root();

    /* 
     * AF(c) = Filesystem: c.fs
     *         Directory corrente: c.currentDirectory
     * RI(c) : c.fs non nullo && c.currentDirectory non nulla
    */

    /* 
     * Crea una shell avente un filesystem composto solo da una directory di nome "root".
    */
    public Shell() {}

    /* 
     * EFFECTS: Metodo di utilità che converte un percorso relativo in uno assoluto.
     *          Solleva NullPointerException se relativePath è nullo.
     *          Solleva IllegalArgumentException se relativePath è assoluto.
    */
    private Path absoluteFromRelative(final Path relativePath) {
        if (Objects.requireNonNull(relativePath, "Il percorso assoluto non può essere nullo.").isAbsolute) {
            throw new IllegalArgumentException("Il path dev'essere relativo");
        }

        List<Directory> parentList = new ArrayList<>();
        Directory curr = currentDirectory;
    
        parentList.add(curr);

        while (curr.getName() != "root") {
            curr = curr.parent();
            parentList.add(curr);
        }

        String path = "";
        for (int i = parentList.size()-2; i >= 0; i--) path += (Path.SEPARATOR + parentList.get(i).getName());

        path += relativePath.toString() != "root" ? Path.SEPARATOR + relativePath.toString() : Path.SEPARATOR;

        return new Path(path);
    }

    /* 
     * EFFECTS: Metodo di utilità che restituisce il Path associato ad s, che può rappresentare
     *          un percorso assoluto o relativo.
     *          Se s è vuota o nulla, restituisce il percorso relativo che termina in this.currentDirectory.
    */
    private Path getAbsolutePath(final String s) throws InvalidPathException {
        Path p;

        if (s == null || s == "") {
            if (currentDirectory.getName() == "root") {
                p = new Path(Path.SEPARATOR);
            } else {
                p = absoluteFromRelative(new Path(currentDirectory.getName()));
            }
        } else {
            p = new Path(s);
            if (!p.isAbsolute) p = absoluteFromRelative(p);
        }

        return p;
    }

    /* 
     * EFFECTS: Stampa il contenuto della directory che si trova a path.
     *          Solleva InvalidPathException se la entry associata a path non è una directory.
    */
    private void ls(final String path) throws InvalidPathException {

        if (path == null || path == "") {
            Iterator<Entry> dirLs = currentDirectory.children();
            while (dirLs.hasNext()) System.out.println(">>> " + dirLs.next().toString());
            return;
        }

        Path p = getAbsolutePath(path);

        Entry e = fs.entryFromAbsolutePath(p);

        if (!(e instanceof Directory)) throw new InvalidPathException(p.toString(), "path non conduce ad una directory");

        Directory dir = (Directory) e;
        Iterator<Entry> dirLs = dir.children();
        while (dirLs.hasNext()) System.out.println(">>> " + dirLs.next().toString());
    }

    /* 
     * EFFECTS: Stampa la dimensione della entry che si trova a path.
    */
    private void size(final String path) throws InvalidPathException {
        if (path == null || path == "") {
            System.out.println(">>> " + currentDirectory.size());
            return;
        }
        Path p = getAbsolutePath(path);
        System.out.println(">>> " + fs.sizeFromPath(p));
    }

    /* 
     * EFFECTS: Crea la directory vuota avente percorso path.
    */
    private void mkdir(final String path) throws InvalidPathException, FileAlreadyExistsException {
        Path p = getAbsolutePath(path);
        fs.createDirectoryAt(p);
    }

    /* 
     * EFFECTS: Crea il file avente percorso path e dimensione corrispondente all'intero rappresentato
     *          da d. Se d non è convertibile ad intero, stampa un messaggio d'errore.
    */
    private void mkfile(final String path, final String d) throws InvalidPathException, FileAlreadyExistsException {
        Path p = getAbsolutePath(path);
        int size;

        try {
            size = Integer.valueOf(d);
            fs.createFileAt(p, size);
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }

    /* 
     * EFFECTS: Fa in modo che this.currentDirectory sia la directory associata a path.
     *          Se path è nulla o vuota, la radice di this.fs diventa this.currentDirectory.
     *          Solleva InvalidPathException se la entry associata a path non è una directory.
    */
    private void cd(final String path) {

        if (path == null || path == "") {
            currentDirectory = fs.root();
            return;
        }

        Path p = getAbsolutePath(path);
        Entry e = fs.entryFromAbsolutePath(p);
        if (!(e instanceof Directory)) throw new InvalidPathException(p.toString(), "path non conduce ad una directory");
        currentDirectory = (Directory) e;
    }

    /* 
     * EFFECTS: Esegue il comando rappresentato da command, se esso rappresenta un comando valido.
     *          In caso contrario, stampa un messaggio d'errore.
    */
    public void parseCommand(final String command) {
        if (command == null || command == "") return;

        String[] parts = command.split(" ", 0);
        if (parts.length < 1 || parts.length > 3) {
            System.out.println("Comando non valido (numero sbagliato di argomenti): " + command);
            return;
        }

        try {
            switch (parts[0]) {
                case "ls":
                if (parts.length == 1) {
                    ls("");
                } else {
                    ls(parts[1]);
                }
                break;
    
                case "size":
                if (parts.length == 1) {
                    size("");
                } else {
                    size(parts[1]);
                }
                break;
    
                case "mkdir":
                if (parts.length == 2) {
                    mkdir(parts[1]);
                } else {
                    System.out.println("Comando non valido (numero sbagliato di argomenti): " + command);
                    return;
                }
                break;
    
                case "mkfile":
                if (parts.length == 3) {
                    mkfile(parts[1], parts[2]);
                } else {
                    System.out.println("Comando non valido (numero sbagliato di argomenti): " + command);
                    return;
                }
                break;
    
                case "cd":
                if (parts.length == 1) {
                    cd("");
                } else {
                    cd(parts[1]);
                }
                break;
    
                case "pwd":
                System.out.println(">>> " + currentDirectory.getName());
                break;
    
                default:
                System.out.println("Comando non valido (comando inesistente): " + command);
                break;
            }
        } catch (Exception e) {
            System.out.println("errore: " + e.getMessage());
        }
        
    }
}



