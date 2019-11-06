package usercode.tasks;

import exceptions.FarmioException;
import frontend.GameConsole;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class TaskList extends ArrayList<Task>{

    public TaskList() {
    }

    public TaskList(JSONArray array) throws FarmioException {
        for (JSONObject object : (Iterable<JSONObject>) array) {
            this.add(Task.toTask(object));
        }
    }


    public void addTask(Task task) throws FarmioException {
        if (this.size() < GameConsole.FRAME_SECTION_HEIGHT) {
            this.add(task);
        } else {
            throw new FarmioException("Unable to add more task! Please optimize your code!");
        }
    }

    public void editTask(int taskID, Task task) throws FarmioException {
        try {
            this.set(taskID - 1, task);
        } catch (IndexOutOfBoundsException e) {
            throw new FarmioException("Invalid TaskID!");
        }
    }

    public void insertTask (int taskID, Task task) throws FarmioException {
        if (this.size() >= GameConsole.FRAME_SECTION_HEIGHT) {
            throw new FarmioException("Unable to add more task! Please optimize your code!");
        }
        try {
            this.add(taskID - 1, task);
        } catch (IndexOutOfBoundsException e) {
            throw new FarmioException("Invalid Insert Position!");
        }
    }

    public Task popTask() {
        return this.remove(0);
    }

    public JSONArray toJson(){
        JSONArray array = new JSONArray();
        for(Task task: this){
            array.add(task.toJSON());
        }
        return array;
    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < this.size(); i++) {
            list.add((i + 1) + ". " + this.get(i).toString());
        }
        return list;
    }

    public String deleteTask(int taskID) throws FarmioException {
        try {
            Task t = this.remove(taskID - 1);
            return t.toString();
        } catch (IndexOutOfBoundsException e) {
            throw new FarmioException("Invalid TaskID!");
        }
    }

    public void deleteAll() {
        this.clear();
    }
}
