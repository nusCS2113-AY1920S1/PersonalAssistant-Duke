import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Ui {
    Scanner scanner = new Scanner(System.in);

    public Ui() {}

    public void showWelcome() throws IOException {
        String s = getAsciiArt("./src/main/resources/asciiArt/welcome.txt");
        System.out.println(s);
    }

    public String getCommand() {
        System.out.println("Listening for command: ");
        String s = scanner.nextLine();
        return s;
    }

    public String getSaveFile() {
        return null;
    }

    private String getAsciiArt(String filepath) throws IOException {
        String output = "";
        try {
            BufferedReader bufferreader = new BufferedReader(new FileReader(filepath));
            String line;
            while ((line = bufferreader.readLine()) != null) {
                output += line;
                output += "\n";
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return output;
    }
}
