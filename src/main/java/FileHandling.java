import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileHandling {
    private String file;

    public FileHandling(String file) {
        this.file = file;
    }

    public ArrayList<Task> retrieveData() throws DukeException {

        try {
            FileReader readFile = new FileReader(this.file);
            BufferedReader read = new BufferedReader(readFile);
            ArrayList<Task> initialData = new ArrayList<>();
            String input;

            while ((input = read.readLine()) != null) {
                int k = 0;
                List<String> columns = Arrays.asList(input.split("\\|"));
                switch (columns.get(0)) {
                case "T":
                    initialData.add(new Todo(columns.get(2)));
                    break;
                case "E":
                    initialData.add(new Event(columns.get(2), columns.get(3)));
                    break;
                case "D":
                    initialData.add(new Deadline(columns.get(2), columns.get(3)));
                    break;
                default:
                    System.out.println("\n     There is an invalid entry in the file. This entry will "
                            + "not be copied to the list:");
                    System.out.println("     " + input);
                    k = 1;
                }
                if (k == 0) {
                    if (columns.get(1).equals("1")) {
                        initialData.get(initialData.size() - 1).markAsDone();
                    }
                }
            }
            return initialData;
        } catch (FileNotFoundException obj) {
            throw new DukeException(" Invalid file name/file path. File not found."
                    + "Will make a new file ...");
        } catch (IOException obj) {
            throw new DukeException(" Error while reading data from the file. Will continue "
                   + "with empty list");
        } catch (ArrayIndexOutOfBoundsException obj) {
            throw new DukeException(" Index out of bounds. Probably due to invalid format of storing"
                    + "Todo/Deadline/Event data");
        }
    }

    public void saveData(ArrayList<Task> storeDataInFile) throws DukeException {

        try {
            FileWriter writin = new FileWriter(this.file);
            BufferedWriter outData = new BufferedWriter(writin);
            for (int i = 0; i < storeDataInFile.size(); i++) {
                outData.write(storeDataInFile.get(i).fileOutFormat());
                outData.newLine();
            }
            outData.close();
        } catch (IOException obj) {
            throw new DukeException(" Error occurred while writing data to the file " + obj);
        }
    }
}

