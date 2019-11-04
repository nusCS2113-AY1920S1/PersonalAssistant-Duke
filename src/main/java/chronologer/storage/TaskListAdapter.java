package chronologer.storage;

import chronologer.task.Deadline;
import chronologer.task.Event;
import chronologer.task.Task;
import chronologer.task.TaskList;
import chronologer.task.Todo;
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
 * Adapter used to parse JsonObjects and translate them into tasklist objects.
 *
 * @author Tan Yi Xiang
 * @version V1.0
 */
public class TaskListAdapter implements JsonDeserializer<TaskList> {

    private static final String EVENT = "EVENT";
    private static final String DEADLINE = "DEADLINE";

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
            } else {
                listOfTasks.add(new Gson().fromJson(object, Todo.class));
            }
        }
        taskList = new TaskList(listOfTasks);
        return taskList;
    }
}
