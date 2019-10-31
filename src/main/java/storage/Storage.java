package storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import model.Member;
import model.Task;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;


/**
 * deals with loading tasks from the file and saving tasks in the file
 */
public class Storage {
    //TODO consider abstracting
    //TODO temporary storage file path
    private String taskFilePath = "data/tasks.txt";
    private String memberFilePath = "data/members.txt";
    
    /**
     * file that store the data
     */
    private File taskDataFile;

    private File memberDataFile;

    /**
     * input stream to get the file input
     */
    private InputStream is;

    private Gson gson;

    //@@author JustinChia1997
    /**
     * Creates storage object
     * */
    public Storage() {

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        gson = builder.create();

        //@@author chenyuheng
        taskDataFile = new File(taskFilePath);
        memberDataFile = new File(memberFilePath);

        //@@author AugGust
        //Generate folders and files if does not exist
        taskDataFile.getParentFile().mkdirs();
        memberDataFile.getParentFile().mkdirs();
        try {
            taskDataFile.createNewFile();
            memberDataFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //@@author chenyuheng
    /**
     * saves the tasks into persistent storage
     * */
    public boolean saveTasks(ArrayList<Task> taskList) {
        String toSave = convertTaskToJson(taskList);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(taskDataFile));
            bw.write(toSave);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //@@author chenyuheng
    /**
     * loads task from persistent storage
     * */
    public ArrayList<Task> loadTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        try {
            is = new FileInputStream(taskDataFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String fullJson = "";
            String line = null;
            while ((line = br.readLine()) != null) {
                fullJson += line;
            }
            br.close();
            is.close();
            tasks = convertJsonToTask(fullJson);
            return tasks;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    //@@author JustinChia1997
    /**
     * saves members list into persistent storage
     * */
    public boolean saveMembers(ArrayList<Member> memberList) {
        String toSave = convertMemberToJson(memberList);
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(memberDataFile));
            bw.write(toSave);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //@@author JustinChia1997
    /**
     * loads members list to persistent storage
     * */
    public ArrayList<Member> loadMembers() {
        ArrayList<Member> members = new ArrayList<Member>();
        try {
            is = new FileInputStream(memberDataFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String fullJson = "";
            String line = null;
            while ((line = br.readLine()) != null) {
                fullJson += line;
            }
            br.close();
            is.close();
            members = convertJsonToMember(fullJson);
            return members;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return members;
    }

    //======================= Helper functions ======================
    //@@author JustinChia1997
    /**
     * converts the arraylist of tasks to json
     * */
    private String convertTaskToJson(ArrayList<Task> taskList) {
        String json = gson.toJson(taskList);
        return json;
    }

    //@@author JustinChia1997
    /**
     * converts json object of task to java object
     * */
    private ArrayList<Task> convertJsonToTask(String json) {
        ArrayList<Task> taskList = gson.fromJson(json, new TypeToken<List<Task>>() {
        }.getType());
        return taskList;
    }

    //@@author JustinChia1997
    /**
     * converts the arraylist of members to json
     * */
    private String convertMemberToJson(ArrayList<Member> memberList) {
        String json = gson.toJson(memberList);
        return json;
    }

    //@@author JustinChia1997
    /**
     * converts json object of member to java object
     * */
    private ArrayList<Member> convertJsonToMember(String json) {
        ArrayList<Member> memberList = gson.fromJson(json, new TypeToken<List<Member>>() {
        }.getType());
        return memberList;
    }

}
