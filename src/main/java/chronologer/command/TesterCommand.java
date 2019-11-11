package chronologer.command;

import chronologer.exception.ChronologerException;
import chronologer.storage.ChronologerStateList;
import chronologer.storage.Storage;
import chronologer.storage.TaskListAdapter;
import chronologer.task.TaskList;
import chronologer.ui.UiMessageHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class TesterCommand extends Command {

    private static final String TEST_FILE = "TestList";
    private static final String WELCOME_TESTER_MESSAGE = "Welcome tester.\n Pre-allocated schedule loaded.\n"
            + "Type 'sudo-clear' to initialise back to an empty schedule.\n"
            + " (WARNING: CANNOT BE UNDONE)\n";

    @Override
    public void execute(TaskList tasks, Storage storage, ChronologerStateList history) throws ChronologerException {
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream(TEST_FILE);
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                builder.append(line);
            }
            String json = builder.toString();
            Gson gson = new GsonBuilder().registerTypeAdapter(TaskList.class, new TaskListAdapter())
                    .create();
            TaskList taskList = gson.fromJson(json, TaskList.class);
            bufferedReader.close();
            tasks.updateListOfTasks(taskList.getTasks());
        } catch (FileNotFoundException e) {
            logger.writeLog(e.toString(), this.getClass().getName());
            throw new ChronologerException(ChronologerException.fileDoesNotExist());
        } catch (IOException e) {
            logger.writeLog(e.toString(), this.getClass().getName());
            throw new ChronologerException(ChronologerException.unableToReadFile());
        }
        tasks.updateGui(null);
        storage.saveFile(tasks.getTasks());
        UiMessageHandler.outputMessage(WELCOME_TESTER_MESSAGE);
    }
}
