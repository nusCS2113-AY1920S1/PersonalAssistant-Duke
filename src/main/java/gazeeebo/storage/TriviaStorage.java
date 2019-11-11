package gazeeebo.storage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class TriviaStorage {
    private String relativePathTriviaResource
            = "Trivia.txt";

    /**
     * This method read Trivia.txt, get users' past inputs from the file.
     *
     * @return hash-map of keywords and inputs
     * @throws FileNotFoundException if reading fail.
     */
    public Map<String, ArrayList<String>> readFromTriviaFile() throws FileNotFoundException {
        Map<String, ArrayList<String>> commandMemory = new HashMap<>();

        File f = new File(relativePathTriviaResource);
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            String inputCommand = sc.nextLine();
            if (commandMemory.containsKey(inputCommand.split(" ")[0])) {
                ArrayList<String> oldlist = new ArrayList<String>(commandMemory.get(inputCommand.split(" ")[0]));
                if (!oldlist.contains(inputCommand)) {
                    oldlist.add(inputCommand);
                    commandMemory.put(inputCommand.split(" ")[0], oldlist);
                }
            } else {
                ArrayList<String> newlist = new ArrayList<String>();
                newlist.add(inputCommand);
                commandMemory.put(inputCommand.split(" ")[0], newlist);
            }
        }
        sc.close();
        return commandMemory;
    }

    /**
     * This method writes to Trivia.txt file, record down updates in record of user inputs.
     * @param fileContent String of content to be saved
     * @throws IOException if the saving process goes wrong.
     */
    public void writeToTriviaFile(String fileContent) throws IOException {
        File file = new File(relativePathTriviaResource);
        if (file.exists() && !file.canWrite()) {
            System.out.println("File exists and it is read only, making it writable");
            file.setWritable(true);
        }
        try {
            FileWriter fileWriter = new FileWriter(relativePathTriviaResource, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.write(fileContent);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
