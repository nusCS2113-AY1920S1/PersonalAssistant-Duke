package dolla.ui;

public class StorageUi extends Ui {
    public static void printStorageLoadMessage() {
        System.out.println(line);
        System.out.println("\tYour save data has been loaded :)");
        System.out.println(line);
    }

    public static void printCreateFolderMessage() {
        System.out.println(line);
        System.out.println("Looks like it's your first time, let me create a save file for you :)");
        System.out.println(line);
    }

    public static void printErrorReadingSaveMessage() {
        System.out.println(line);
        System.out.println("*** there was an error reading dolla.txt ***");
        System.out.println(line);
    }




}
