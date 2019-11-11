package eggventory.ui;

//@@author Raghav-B
public abstract class Ui {

    public abstract void initialize(Runnable runMethod);

    public abstract String read();

    public abstract String print(String printString);

    /**
     * Formats the a String of input into the appropriate output Format with
     * indentation and lines.
     * @param printString String to format.
     * @return Formatted String.
     */
    String printFormatter(String printString) {
        String output = addIndent() + addLine() + "\n";

        String[] linesToPrint = printString.split("\n", 0);
        for (int i = 0; i < linesToPrint.length; i++) {
            output += (addIndent() + linesToPrint[i]) + "\n";
        }
        output += addIndent() + addLine() + "\n";
        return output;
    }

    /**
     * Prints eggventory introduction message.
     */
    void printIntro() {
        String logo = "  _      __    __                     __         ____         _   __         __               \n"
                + " | | /| / /__ / /______  __ _  ___   / /____    / __/__ ____ | | / /__ ___  / /____  ______ __\n"
                + " | |/ |/ / -_) / __/ _ \\/  ' \\/ -_) / __/ _ \\  / _// _ `/ _ `/ |/ / -_) _ \\/ __/ _ \\/"
                + " __/ // /\n"
                + " |__/|__/\\__/_/\\__/\\___/_/_/_/\\__/  \\__/\\___/ /___/\\_, /\\_, /|___/\\__/_//_/\\__/\\___/_/"
                + "  \\_, / \n"
                + "                                                  /___//___/                           /___/  \n";

        print(logo);
        print("Hello! I'm Humpty Dumpty\n" + "What can I do for you?");
    }

    /**
     * Prints error message to CLI.
     */
    public void printError(Exception e) {
        print("Parser error: \n" + e.getMessage());
    }

    /**
     * Prints the EggVentory exit message.
     */
    public void printExitMessage() {
        print("Bye! Your stonks are safe with me!");
    }

    /**
     * Adds indent for formatting Eggventory text output.
     * @return Returns String with indentation.
     */
    String addIndent() {
        return "        ";
    }

    /**
     * Prints the standard newline.
     * @return Returns String with newline.
     */
    String addLine() {
        return "____________________________________________________________";
    }



    public void clearTable() {

    }

    public void drawTable(TableStruct tableStruct) {
    }
}
