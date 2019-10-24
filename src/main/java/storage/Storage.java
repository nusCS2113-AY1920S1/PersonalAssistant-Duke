package storage;

import members.Member;
import tasks.Task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import utils.Parser;

//UNEDITED PARSER

/**
 * deals with loading tasks from the file and saving tasks in the file
 */
public class Storage {

    /**
     * file that store the data
     */
    private File taskDataFile;

    private File memberDataFile;

    /**
     * input stream to get the file input
     */
    private InputStream is;

    /**
     * constructor of Storage, pass the data to dataFile by file path
     *
     * @param taskFilePath   path of the file that store the task list
     * @param memberFilePath path of the file that store the member list
     */
    public Storage(String taskFilePath, String memberFilePath) {
        taskDataFile = new File(taskFilePath);
        memberDataFile = new File(memberFilePath);

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

    /**
     * Read the data stored in hard disk to taskList.
     *
     * @return an ArrayList of Task, which is the task list
     */
    public ArrayList<Task> loadTaskList() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        try {
            is = new FileInputStream(taskDataFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                tasks.add(Parser.taskDataLine(line));
            }
            Task.tasks = tasks;
            br.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * Save the tasks data to the hard disk.
     *
     * @param taskList the array list of tasks to be saved.
     * @return if this method executed successfully
     */
    public boolean storeTaskList(ArrayList<Task> taskList) {
        String output = "";
        for (int i = 0; i < taskList.size(); i++) {
            output += taskList.get(i).dataString() + "\n";
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(taskDataFile));
            bw.write(output);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * Read the data stored in hard disk to memberList.
     *
     * @return an ArrayList of Member, which is the member list
     */
    public ArrayList<Member> loadMemberList(ArrayList<Task> tasks) {
        ArrayList<Member> members = new ArrayList<Member>();
        try {
            is = new FileInputStream(memberDataFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                members.add(Parser.memberDataLine(line));
            }
            br.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return members;
    }

    /**
     * Save the members data to the hard disk.
     *
     * @param memberList the array list of members to be saved.
     * @return if this method executed successfully
     */
    public boolean storeMemberList(ArrayList<Member> memberList) {
        String output = "";
        for (int i = 0; i < memberList.size(); i++) {
            output += memberList.get(i).dataString() + "\n";
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(memberDataFile));
            bw.write(output);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
