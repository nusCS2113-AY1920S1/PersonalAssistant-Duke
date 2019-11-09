package chronologer.storage;

import chronologer.task.Deadline;
import chronologer.task.Event;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.task.Todo;
import chronologer.ui.UiTemporary;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Adapter used to parse JsonObjects and translate them into task list objects.
 *
 * @author Tan Yi Xiang
 * @version V1.0
 */
public class TaskListAdapter implements JsonDeserializer<TaskList> {

    private static final String EVENT = "EVENT";
    private static final String DEADLINE = "DEADLINE";
    private static final String TODO = "TODO";
    private static final String TODO_DURATION = "TODO DURATION";
    private static final String TODO_PERIOD = "TODO PERIOD";

    private static final String JSON_ERROR = "OOPS!! There's an invalid task type detected in the save file" + ""
        +  "This task will be ignored.";

    @Override
    public TaskList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
        throws JsonParseException {

        TaskList taskList;
        ArrayList<Task> listOfTasks = new ArrayList<>();
        JsonArray jsonArray = json.getAsJsonArray();

        for (JsonElement element : jsonArray) {
            JsonObject object = element.getAsJsonObject();
            String type = object.get("type").toString().replaceAll("\"", "");
            ;
            if (DEADLINE.equals(type)) {
                listOfTasks.add(new Gson().fromJson(object, Deadline.class));
            } else if (EVENT.equals(type)) {
                listOfTasks.add(new Gson().fromJson(object, Event.class));
            } else if (isTodo(type)) {
                listOfTasks.add(new Gson().fromJson(object, Todo.class));
            } else {
                UiTemporary.printOutput(JSON_ERROR);
            }
        }
        taskList = new TaskList(listOfTasks);
        return taskList;
    }

    /**
     * Determine if Json type parameter is of Todo task type.
     *
     * @param type The Json parameter type
     * @return True if todo false otherwise
     */
    private boolean isTodo(String type) {
        return TODO.equals(type) || TODO_DURATION.equals(type) || TODO_PERIOD.equals(type);
    }
}
