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
            FileReader readFile= new FileReader(this.file);
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
            System.out.println("     Invalid file name/file path. File not found." +
                    "Will make a new file ...");
            return new ArrayList<>();
        } catch (IOException obj) {
            System.out.println("     Error while reading data from the file");
            System.err.println(obj.getMessage());
            return new ArrayList<>();
        } catch (ArrayIndexOutOfBoundsException obj) {
            System.out.println("     Index out of bounds. Probably due to invalid format of storing" +
                    "Todo/Deadline/Event data");
            return new ArrayList<>();
        }
    }

    public void saveData(ArrayList<Task> storeDataInFile) {

        try {
            FileWriter writin = new FileWriter(this.file);
            BufferedWriter outData = new BufferedWriter(writin);
            for (int i = 0; i < storeDataInFile.size(); i++) {
                outData.write(storeDataInFile.get(i).fileOutFormat());
                outData.newLine();
            }
            outData.close();
        } catch (IOException obj) {
            System.out.println("     Error with input output operations while handling the file.");
            System.err.println(obj.getMessage());
        }
    }
}

