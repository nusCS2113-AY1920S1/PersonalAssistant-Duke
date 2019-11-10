package Commons;

import DukeExceptions.DukeIOException;
import Tasks.TaskList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

public class PreloadStorage extends Storage {

    private final Logger LOGGER = DukeLogger.getLogger(PreloadStorage.class);

    @Override
    public void readEventList(TaskList list) throws DukeIOException {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("documents/preloadevent.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.addTask(super.stringToTask(line));
            }
        } catch (IOException e) {
            LOGGER.severe("There is no event.txt to read from.");
            throw new DukeIOException(DukeConstants.NO_PRELOAD_EVENT_TXT);
        }
    }

    @Override
    public void readDeadlineList(TaskList list) throws DukeIOException {
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("documents/preloaddeadline.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                list.addTask(super.stringToTask(line));
            }
        } catch (IOException e) {
            LOGGER.severe("There is no event.txt to read from.");
            throw new DukeIOException(DukeConstants.NO_PRELOAD_DEADLINE_TXT);
        }
    }
}
