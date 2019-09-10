import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Ui {
    protected String FullCommand;

    protected String ReadCommand() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        FullCommand = reader.readLine();
        return FullCommand;
    }

    protected String showWelcome() {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println("Hello from\n" + logo);
        return logo;
    }
    protected void showDateFormatError (){
        System.err.println("Date Time has to be in YYYY-MM-DD HH:mm:ss format");
    }
    protected  void showErrorMessage(Exception e) {
        System.err.println(e.getMessage());
    }
}
