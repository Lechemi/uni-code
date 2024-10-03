import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.InvalidPathException;
import java.util.Iterator;
import java.util.Objects;

import entry.Directory;
import entry.Entry;
import entry.File;

public class FileSystem {
    /* 
     * Rappresenta un filesystem.
     * Le istanze di questa classe sono immutabili.
    */

    // REP
    private final Directory root = new Directory("root", null);

    /* 
     * AF(c) = Directory radice del filesystem: c.root
     * RI(c) : c.root ≠ null
    */

    /* 
     * EFFECTS: Crea un filesystem vuoto, avente una directory alla radice di nome "root".
    */
    public FileSystem() {}

    /* 
     * EFFECTS: Restituisce la directory radice di this.
    */
    Directory root() { return root; }

    /* 
     * EFFECTS: Restituisce l'ultima directory di absolutePath.
     *          Solleva IllegalArgumentException se absolutePath non è assoluto.
     *          Solleva NullPointerException se absolutePath è nullo.
     *          Solleva InvalidPathException se absolutePath non è valido per this.
    */
    public Directory resolveAbsolutePath(final Path absolutePath, final boolean withLast) throws InvalidPathException {
        if (!Objects.requireNonNull(absolutePath, "Il percorso assoluto non può essere nullo.").isAbsolute) {
            throw new IllegalArgumentException("Il path dev'essere assoluto");
        }

        Directory current = root;
        Iterator<String> entries = !withLast ? absolutePath.entryNamesWithoutLast() : absolutePath.entryNames();
        while (entries.hasNext()) {
            try {
                Entry next = current.entryFromName(entries.next());
                if (!(next instanceof Directory)) throw new InvalidPathException(absolutePath.toString(), "path non valido");
                current = (Directory) next;
            } catch (FileNotFoundException e) {
                throw new InvalidPathException(absolutePath.toString(), "path non valido");
            }
        }

        return current;
    }

    /* 
     * EFFECTS: Restituisce la entry avente percorso assoluto absolutePath.
     *          Solleva IllegalArgumentException se absolutePath non è assoluto.
     *          Solleva NullPointerException se absolutePath è nullo.
    */
    public Entry entryFromAbsolutePath(final Path absolutePath) throws InvalidPathException { 
        if (!Objects.requireNonNull(absolutePath, "Il percorso assoluto non può essere nullo.").isAbsolute) {
            throw new IllegalArgumentException("Il path dev'essere assoluto");
        }

        if (absolutePath.rootPath) return root;

        Directory lastDir = resolveAbsolutePath(absolutePath, false);

        try {
            return lastDir.entryFromName(absolutePath.lastEntryName());
        } catch (FileNotFoundException e) {
            throw new InvalidPathException(absolutePath.toString(), "path non valido");
        }
    }

    /* 
     * EFFECTS: Crea la directory di nome n, avente percorso assoluto absolutePath.
     *          Solleva NullPointerException se absolutePath è nullo.
     *          Solleva IllegalArgumentException se absolutePath non è assoluto.
    */
    public void createDirectoryAt(final Path absolutePath) throws InvalidPathException, FileAlreadyExistsException {
        if (!Objects.requireNonNull(absolutePath, "Il percorso assoluto non può essere nullo.").isAbsolute) {
            throw new IllegalArgumentException("Il path dev'essere assoluto");
        }

        Directory lastDir = resolveAbsolutePath(absolutePath, false);
        Directory newDir = new Directory(absolutePath.lastEntryName(), lastDir);
        lastDir.addEntry(newDir);
    }

    /* 
     * EFFECTS: Crea il file di nome n, di dimensione d, avente percorso assoluto absolutePath.
     *          Solleva NullPointerException se absolutePath è nullo.
     *          Solleva IllegalArgumentException se d ≤ 0.
     *          Solleva IllegalArgumentException se absolutePath non è assoluto.
    */
    public void createFileAt(final Path absolutePath, final int d) throws FileAlreadyExistsException, InvalidPathException {
        if (!Objects.requireNonNull(absolutePath, "Il percorso assoluto non può essere nullo.").isAbsolute) {
            throw new IllegalArgumentException("Il path dev'essere assoluto");
        }

        if (d <= 0) throw new IllegalArgumentException("La dimensione del file dev'essere maggiore di 0.");

        Directory lastDir = resolveAbsolutePath(absolutePath, false);
        File newFile = new File(absolutePath.lastEntryName(), d);
        lastDir.addEntry(newFile);
    }

    /* 
     * EFFECTS: Restituisce il contenuto della directory avente percorso assoluto absolutePath,
     *          una entry alla volta.
     *          Solleva NullPointerException se absolutePath è nullo.
     *          Solleva IllegalArgumentException se absolutePath non è assoluto.
    */
    public Iterator<Entry> dirContent(final Path absolutePath) throws InvalidPathException {
        if (!Objects.requireNonNull(absolutePath, "Il percorso assoluto non può essere nullo.").isAbsolute) {
            throw new IllegalArgumentException("Il path dev'essere assoluto");
        }
        
        if (absolutePath.rootPath) return root.children();

        Directory lastDir = resolveAbsolutePath(absolutePath, true);
        return lastDir.children();
    }

    /* 
     * EFFECTS: Restituisce la dimensione della entry avente percorso assoluto absolutePath.
     *          Solleva NullPointerException se absolutePath è nullo.
     *          Solleva IllegalArgumentException se absolutePath non è assoluto.
    */
    public int sizeFromPath(final Path absolutePath) throws InvalidPathException { 
        if (!Objects.requireNonNull(absolutePath, "Il percorso assoluto non può essere nullo.").isAbsolute) {
            throw new IllegalArgumentException("Il path dev'essere assoluto");
        }

        if (absolutePath.rootPath) return root.size();

        Directory lastDir = resolveAbsolutePath(absolutePath, false);
        try {
            Entry lastEntry = lastDir.entryFromName(absolutePath.lastEntryName());
            return lastEntry.size();
        } catch (FileNotFoundException e) {
            throw new InvalidPathException(absolutePath.toString(), "path non valido");
        }
    }
}



