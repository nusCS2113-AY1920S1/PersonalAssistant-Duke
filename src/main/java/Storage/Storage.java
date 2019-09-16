package Storage;

import myTasks.TaskList;
import Exception.DukeException;
import java.io.*;

/**
 * Storage class
 * Looks for file in certain folder to be parsed,
 * then it is opened, same file is overwritten.
 * @author Kane Quah
 * @version 1.0
 * @since 08/19
 */
public class Storage {
    private File f;
    private String input = "";
    public Storage(String filePath){
        //URL path = Duke.class.getResource(filePath);
        //forDuke working directory affects!!!!
        String file = "./data/" + filePath;
        //for runTest
        //String file = "../data/" + filePath;
        this.f = new File(file);
        /*encoding is ANSI */
    }

    /**
     * This method reads the file.
     * @return String this returns the file's lines separated by "\\n" line symbols.
     */
    public String load(){
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(f))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                this.input += line + "\n";
                line = bufferedReader.readLine();
            }
        } catch (FileNotFoundException e) {
            // exception handling
            System.out.println("file not found");
        } catch (IOException e) {
            // exception handling
            System.out.println("I/O Issues");
        }
        return this.input;
    }
    /**
     * This method overwrites the file.
     * It overwrites using the data stored in TaskList currently
     */
    void store(TaskList list) throws DukeException {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(this.f))) {
            for(int i = 0; i< list.size(); i++) {
                String fileContent = list.get(i).getType() + " | " +
                        (list.get(i).checkCompletion() ? "1": "0") + " | "+ list.get(i).getDescription();
                if(list.get(i).getType().matches("D|E"))
                {
                    fileContent += " | " + list.get(i).getDueDate();
                }
                bufferedWriter.write(fileContent);
                bufferedWriter.newLine();
            }
        } catch (IOException | DukeException e) {
            // exception handling
            throw new DukeException("Storage Attempt Failed");
        }
    }

}
