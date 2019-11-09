import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class DataForTest {

    private final String userInput;

    public DataForTest(String userInput) {
        this.userInput = userInput;
    }

    public ArrayList<String> execute(String userInput) {
        ArrayList<String> output = new ArrayList<>();
        
        switch (userInput) {
            case "bookings":
                try {
                    output.addAll(getStringFromFile("/data/bookingsTest.txt"));
                } catch (IOException e) {
                    output.add("Error in reading bookingsTest.txt");
                }
                break;
            case "recipes":
                try {
                    output.addAll(getStringFromFile("/data/recipesTest.txt"));
                } catch (IOException e) {
                    output.add("Error in reading recipesTest.txt");
                }
                break;
            case "inventories":
                try {
                    output.addAll(getStringFromFile("/data/inventoriesTest.txt"));
                } catch (IOException e) {
                    output.add("Error in reading inventoriesTest.txt");
                }
                break;
            default:
                output.add("No such text");
        }
        return output;
    }

    /**
     * Reads help files from the resource folder of the JAR and returns the files as
     * a String.
     * @param resourcePath Path to file in resources folder.
     * @return String format of entire file.
     * @throws IOException when file cannot be read for some unknown error.
     */
    private ArrayList<String> getStringFromFile(String resourcePath) throws IOException {
        InputStream inputStream = getClass().getResourceAsStream(resourcePath);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        
        ArrayList<String> input = new ArrayList<>();
        
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                break;
            }
            input.add(line);
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();

        return input;
    }
}
