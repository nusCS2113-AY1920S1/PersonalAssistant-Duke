import java.io.*;
import java.util.ArrayList;

public class Storage {
    private File dataFile;
    private InputStream is;

    public Storage(String filePath) {
        dataFile = new File(filePath);
    }

    /**
     * read the data stored in hard disk to taskList.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        try {
            is = new FileInputStream(dataFile);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while ((line = br.readLine()) != null) {
                tasks.add(Parser.dataLine(line));
            }
            br.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }

    /**
     * save the tasks data to the hard disk.
     * @param taskList the array list of tasks to be saved.
     */
    public boolean store(ArrayList<Task> taskList) {
        String output = "";
        for (int i = 0; i < taskList.size();i++) {
            output += taskList.get(i).dataString() + "\n";
        }
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(dataFile));
            bw.write(output);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
