package UserCode.Tasks;

import org.json.simple.JSONArray;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task>{

    public TaskList() {
    }

    /**
    public TaskList(JSONArray array) throws FarmioException {
        for (JSONObject jsonObject : (Iterable<JSONObject>) array) {
            this.add(new Task(jsonObject));
        }
    }**/


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

    public ArrayList<String> toStringArray() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < this.size(); i++) {
            list.add(Integer.toString(i + 1) + ". " + this.get(i).toString());
        }
        return list;
    }

    public String removeTask (int taskID) {
        Task t = this.remove(taskID - 1);
        return t.toString();
    }
}
