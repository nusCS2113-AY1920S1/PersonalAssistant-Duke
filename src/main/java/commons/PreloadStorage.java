package commons;

import dukeexceptions.DukeIOException;
import tasks.TaskList;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * Deals with pre-loading the jar file with tasks.
 */
public class PreloadStorage extends Storage {

    private final Logger logger = DukeLogger.getLogger(PreloadStorage.class);

    /**
     * Reads and populates the TaskList of events from preloadevent.txt.
     * @param list TaskList of events
     * @throws DukeIOException when preloadevent.txt is not found
     */
    @Override
    public void readEventList(TaskList list) throws DukeIOException {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("documents/"
                    + "preloadevent.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.addTask(super.stringToTask(line));
            }
        } catch (IOException e) {
            logger.severe("There is no event.txt to read from.");
            throw new DukeIOException(DukeConstants.NO_PRELOAD_EVENT_TXT);
        }
    }

    /**
     * Reads and populates the TaskList of deadlines from preloaddeadline.txt.
     * @param list TaskList of deadlines
     * @throws DukeIOException when preloaddeadline.txt is not found
     */
    @Override
    public void readDeadlineList(TaskList list) throws DukeIOException {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("documents/"
                    + "preloaddeadline.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.addTask(super.stringToTask(line));
            }
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
        } catch (IOException e) {
            logger.severe("There is no event.txt to read from.");
            throw new DukeIOException(DukeConstants.NO_PRELOAD_DEADLINE_TXT);
        }
    }
}
