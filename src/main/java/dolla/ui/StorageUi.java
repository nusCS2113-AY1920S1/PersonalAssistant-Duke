package dolla.ui;

public class StorageUi extends Ui {
    /**
     * This method will print the message for successfully loading the save.
     */
    public static void printStorageLoadMessage() {
        System.out.println(line);
        System.out.println("\tYour save data has been loaded :)");
        System.out.println(line);
    }

    /**
     * This method will print the message after creating a folder if the folder needed is not found.
     */
    public static void printCreateFolderMessage() {
        System.out.println(line);
        System.out.println("\tLooks like it's your first time, let me create a save file for you :)");
    }

    /**
     * This method will print the error message if reading the save file fail.
     */
    public static void printErrorReadingSaveMessage() {
        System.out.println(line);
        System.out.println("*** there was an error reading dolla.txt ***");
    }

    /**
     * This method will print the error message of writing to save file fail.
     */
    public static void printErrorWritingSaveMessage() {
        System.out.println(line);
        System.out.println("***Error writing to dolla.txt***");
        System.out.println(line);
    }


}
