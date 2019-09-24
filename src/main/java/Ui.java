import java.util.Scanner;

public class Ui {
    Scanner scanner = new Scanner(System.in);

    public Ui() {}

    public void showWelcome() {
        System.out.println(
                " _____               __            _     \n" +
                "|   __|___ ___ _____|  |   ___ ___|_|___ \n" +
                "|   __| .'|  _|     |  |__| . | . | |  _|\n" +
                "|__|  |__,|_| |_|_|_|_____|___|_  |_|___|\n" +
                "                              |___|      \n" +
                "\n");
    }

    public String getCommand() {
        System.out.println("Listening for command: ");
        String s = scanner.nextLine();
        return s;
    }

    public String getSaveFile() {
        return null;
    }
}
