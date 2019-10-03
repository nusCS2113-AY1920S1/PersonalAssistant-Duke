package storage;

import task.Task;
import task.Event;
import task.Todo;
import task.Recurring;
import task.Deadline;
import task.FixedDuration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Represents the object that reads and writes to the text files where data is stored.
 */
public class Storage {

    private static String FILE_PATH = "src/main/data/duke.txt";

    public Storage() {
    }

    public Storage(String filePath) {
        FILE_PATH = filePath;
    }

    public ArrayList<Task> loadFile() {
        File file = new File(FILE_PATH);
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            ArrayList<Task> listContent = new ArrayList<>();
            String line = br.readLine();

            while (line != null) {
                String[] data = line.split("\\| ");
                if (data[0].equals("T ")) {
                    Todo t = new Todo(data[2], data[1].contains("1"));
                    listContent.add(t);
                } else if (data[0].equals("D ")) {
                    Deadline t = new Deadline(data[2], data[3], data[1].contains("1"));
                    listContent.add(t);
                } else if (data[0].equals("E ")) {
                    Event t = new Event(data[2], data[3], data[1].contains("1"));
                    listContent.add(t);
                } else if (data[0].equals("R ")) {
                    Recurring t = new Recurring(data[2], data[3], data[1].contains("1"));
                    listContent.add(t);
                } else if (data[0].equals("F ")) {
                    FixedDuration t = new FixedDuration(data[2], data[3], data[1].contains("1"));
                    listContent.add(t);
                }
                line = br.readLine();
            }
            return listContent;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String[]> loadReminderFile(String filePath) {
        File file = new File(filePath);
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            ArrayList<String[]> listContent = new ArrayList<>();
            String line = br.readLine();

            while (line != null) {
                String[] data = line.split("\\| ");
                listContent.add(data);
                line = br.readLine();
            }
            return listContent;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void writeFile(String s, boolean append, String filePath) {
        File file = new File(filePath);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file, append);
            bw = new BufferedWriter(fw);
            bw.write(s);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeReminderFile(String s, String filePath) {
        File file = new File(filePath);
        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            fw = new FileWriter(file, true);
            bw = new BufferedWriter(fw);
            bw.write(s);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteItemFromFile(String oldString, String filePath) {
        File file = new File(filePath);
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            String oldContent = "";
            String line = br.readLine();

            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();
                line = br.readLine();
            }
            oldContent = oldContent.substring(0, oldContent.length() - 1);
            String newContent = oldContent.replace(oldString + System.lineSeparator(), "");
            Storage writer = new Storage();
            writer.writeFile(newContent,false, filePath);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
