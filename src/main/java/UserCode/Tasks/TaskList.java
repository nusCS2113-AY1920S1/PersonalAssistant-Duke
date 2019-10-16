package UserCode.Tasks;

import FarmioExceptions.FarmioException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

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

//    public JSONArray toJSON(){
//        JSONArray array = new JSONArray();
//        for(Task task: this){
//            array.add(task.toJSON());
//        }
//        return array;
//    }

    public ArrayList<String> toStringArray() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < this.size(); i++) {
            list.add(this.get(i).toString());
        }
        return list;
    }
}
