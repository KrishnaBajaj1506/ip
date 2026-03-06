import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles loading and saving of tasks to a persistent file.
 * <p>
 * Reads and writes the task data file at the specified file path,
 * allowing tasks to be persisted across application sessions.
 * The file format for each task is:
 * {@code TYPE | DONE | DESCRIPTION [| EXTRA...]}
 * </p>
 */
public class Storage {
    private final String filePath;

    /**
     * Constructs a {@code Storage} instance for the given file path.
     * <p>
     * The file and any required parent directories are created automatically
     * when {@link #save(ArrayList)} is first called.
     * </p>
     *
     * @param filePath the path to the data file (e.g.,
     *                 {@code "./data/mexicola.txt"})
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the data file and returns them as an {@link ArrayList}.
     * <p>
     * If the file does not exist, an empty list is returned. Lines that cannot
     * be parsed are silently skipped.
     * </p>
     *
     * @return list of {@link Task} objects read from the file
     * @throws MexicolaException if the file exists but cannot be read
     */
    public ArrayList<Task> load() throws MexicolaException {
        ArrayList<Task> tasks = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) {
            return tasks; // No saved data yet — start fresh
        }

        try {
            Scanner s = new Scanner(f);
            while (s.hasNext()) {
                String line = s.nextLine().trim();
                if (line.isEmpty())
                    continue;

                String[] parts = line.split(" \\| ");
                if (parts.length < 3)
                    continue; // Malformed line — skip

                String type = parts[0];
                boolean isDone = parts[1].equals("1");
                String description = parts[2];

                switch (type) {
                    case "T":
                        Todo t = new Todo(description);
                        if (isDone)
                            t.markAsDone();
                        tasks.add(t);
                        break;
                    case "D":
                        if (parts.length < 4)
                            continue;
                        try {
                            Deadline d = Deadline.of(description, parts[3]);
                            if (isDone)
                                d.markAsDone();
                            tasks.add(d);
                        } catch (MexicolaException ex) {
                            // Malformed or unrecognised date — skip this entry
                        }
                        break;
                    case "E":
                        if (parts.length < 5)
                            continue;
                        Event e = new Event(description, parts[3], parts[4]);
                        if (isDone)
                            e.markAsDone();
                        tasks.add(e);
                        break;
                    default:
                        // Unknown task type — skip
                        break;
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            throw new MexicolaException("Could not read the data file: " + e.getMessage());
        }
        return tasks;
    }

    /**
     * Saves all tasks to the data file, overwriting any existing content.
     * <p>
     * Each task is written on its own line using the format returned by
     * {@link Task#toFileFormat()}. Parent directories are created if they
     * do not already exist.
     * </p>
     *
     * @param tasks the list of tasks to persist
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        File file = new File(filePath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        FileWriter fw = new FileWriter(filePath);
        for (Task t : tasks) {
            fw.write(t.toFileFormat() + System.lineSeparator());
        }
        fw.close();
    }
}
