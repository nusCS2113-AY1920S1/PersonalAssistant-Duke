package Task;

import FarmioExceptions.FarmioException;
import Task.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class TaskList extends ArrayList<Task>{

    public TaskList() {
    }

    public TaskList(JSONArray array) throws FarmioException {
        for (JSONObject jsonObject : (Iterable<JSONObject>) array) {
            this.add(new Task(jsonObject));
        }
    }

    public void addTask(Task task) {
        this.add(task);
    }

    public Task popTask() {
        return this.remove(0);
    }

    public JSONArray toJSON(){
        JSONArray array = new JSONArray();
        for(Task task: this){
            array.add(task.toJSON());
        }
        return array;
    }
}
