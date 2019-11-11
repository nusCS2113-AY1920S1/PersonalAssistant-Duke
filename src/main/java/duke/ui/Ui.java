package duke.ui;

import duke.models.locker.Locker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Ui {
    private Scanner sc = new Scanner(System.in);

    public String readCommand() {
        return sc.nextLine();
    }

    /**
     * Prints a line indented with four spaces.
     */
    public void printDash() {
        String str = "";
        for (int i = 0; i < 120; i++) {
            str += "_";
        }
        printSpaces(str);
    }

    private void printSpaces(String printStr) {
        System.out.println("    " + printStr);
    }

    /**
     * Prints the welcome message every time SpongeBob is initiated.
     */
    public void showWelcome() {
        String logo = "SpongeBob";
        printSpaces(" Hello from " + logo);
        printDash();
        printSpaces(" Hello! I am SpongeBob. I am here to manage lockers for you!");
        printSpaces(" What can I do for you?");
        printSpaces(" If you need any assistance, please type 'help' to access to Help Manual.");
        printDash();
    }

    /**
     * Prints the lockers stored in the list.
     * @param printData stores the list of lockers to be printed.
     */
    public void printList(List<Locker> printData) {
        printSpaces(" Here are the lockers in your list:");
        showList(printData);
    }

    /**
     * Indicates the user that the usage has been deleted.
     */
    public void showDeleteUsage() {
        printSpaces(" I have successfully deleted the usage of the locker.");
        printDash();
    }

    /**
     * Tells the user that SpongeBob has added the locker into the list.
     * @param size stores the number of lockers in the list
     * @param lockerA stores the locker that is added to the list
     */
    public void printAddLocker(int size, String lockerA) {
        printSpaces(" Got it. I have added this locker: ");
        printSpaces(" " + lockerA);
        printSpaces(" Now, SpongeBob is managing " + size + " lockers");
        printDash();
    }

    /**
     * Indicates the user that SpongeBob has added a batch of lockers to the list.
     * @param num stores the number of lockers that are added
     */
    public void printBatch(int num) {
        printSpaces(" Got it. I have added " + num + " lockers");
        printDash();
    }

    /**
     * This function reads the stats found.
     */
    public void readStats() {
        printSpaces(" Here are the stats found:");
        printDash();
    }

    /**
     * This function prints the stats found.
     * @param mapZone is the Zone of the Locker.
     * @param mapAddress is the Address of the Locker.
     * @param mapTag is the Tag of the Locker.
     */
    public void printStats(Map<String, Integer> mapZone, Map<String, Integer> mapAddress, Map<String, Integer> mapTag) {
        printSpaces("Zone: " + mapZone);
        printSpaces("Address: " + mapAddress);
        printSpaces("Tag: " + mapTag);
        printDash();
    }

    /**
     * Notifies the user that SpongeBob has successfully assigned
     * the locker for the user.
     * @param locker stores the string to show the locker that has been assigned
     */
    public void printSuccessfulAllocation(String locker) {
        printSpaces(" I have successfully assigned a new locker to the student. "
                + "Here are the details:");
        printSpaces(" " + locker);
        printDash();
    }

    /**
     * prints the message for successful editing of lockers.
     * @param editedLocker stores the data of the edited locker
     */
    public void showSuccessfullyEdited(String editedLocker) {
        printSpaces(" I have successfully edited the locker properties.");
        printSpaces(" " + editedLocker);
        printDash();
    }

    /**
     * Indicates the user that are no available lockers at the moment.
     */
    public void showNoAvailableLockers() {
        printSpaces(" There are no available lockers at the moment. ");
        printSpaces(" Unfortunately I will have to terminate the subscription of the student");
    }

    private void showNumTasks(int size) {
        printSpaces(" Now you have " + size
                + ((size == 1) ? " locker in the list." : " lockers in "
                + "the list"));
        printDash();
    }

    /**
     * Prints the exit message every time SpongeBob is closed.
     */
    public void exitSpongeBob() {
        printSpaces(" Bye! Hope to see you again.");
        printDash();
    }

    /**
     * Prints the error message.
     * @param errorMessage stores the error message.
     */
    public void showError(String errorMessage) {
        printSpaces(errorMessage);
        printDash();
    }

    /**
     * Notifies the user that there were no available lockers in his list
     *  of preferences.
     */
    public void showNoLockersFoundInPreferences() {
        printSpaces(" Unable to find any free lockers in the preferences stated.");
        printSpaces(" Looking for free lockers in the entire system ...");
        printDash();
    }

    /**
     * Notifies the user that SpongeBob has deleted the locker from the list.
     * @param numLockers stores the number of lockers left in the list.
     * @param lockerA stores the locker that is deleted.
     */
    public void deleteMessage(int numLockers, String lockerA) {
        printSpaces(" Noted. I have removed this locker:");
        printSpaces("  " + lockerA);
        showNumTasks(numLockers);
    }

    /**
     * Notifies the user that SpongeBob has deleted all the lockers from the list.
     */
    public void showAllLockersRemoved() {
        printSpaces(" Noted. I have removed all the lockers from the list.");
        printDash();
    }

    /**
     * Notifies the user that SpongeBob has edited the locker from the list.
     * @param lockerA stores the locker that is edited
     */
    public void editMessage(String lockerA) {
        printSpaces("Noted. I have edited the locker to:");
        printSpaces(" " + lockerA);
        printDash();
    }

    /**
     * This function tells the user that SpongeBob has exported the CSV file.
     */
    public void exportMessage() {
        printSpaces("Noted. I have exported the details to 'export.csv'. ");
        printDash();
    }

    /**
     * This function tells the user that SpongeBob has exported the CSV file.
     */
    public void exportSelect() {
        printSpaces("Noted. I have exported the selected details to 'export.csv'. ");
        printDash();
    }

    /**
     * Prints the message when there is an error in loading data from the file.
     * @param message stores the error message.
     */
    public void showLoadingError(String message) {
        printDash();
        printSpaces(message);
        printDash();
    }

    /**
     * This function prints the syntax for addlocker command.
     */
    public void showAddSyntax() {
        printSpaces("To add a locker into the system:");
        printSpaces("  addlocker s/__ a/__ z/__");
        System.out.println();
    }

    /**
     * This function prints the syntax for addbatch command.
     */
    public void showAddBatchSyntax() {
        printSpaces("To add a batch of lockers into the system:");
        printSpaces("  addbatch s/__ u/__ a/__ z/__");
        System.out.println();
    }

    /**
     * This function prints the syntax for deletelocker command.
     */
    public void showDeleteSyntax() {
        printSpaces("To delete a locker from the system:");
        printSpaces("  deletelocker XXXX");
        System.out.println();
    }

    /**
     * This function prints the syntax for editlocker command.
     */
    public void showEditSyntax() {
        printSpaces("To edit the information of a locker:");
        printSpaces("  editlocker XXXX s/__ a/__ z/__ c/__");
        printSpaces("(Note: You can choose to change one or more tokens of the locker");
        System.out.println();
    }

    /**
     * This function prints the syntax for assign command.
     */
    public void showAssignSyntax() {
        printSpaces("To assign a locker for rental to a student:");
        printSpaces("  assign n/__ i/__ e/__ m/__ f/__ t/__ p/__");
        System.out.println();
    }

    /**
     * This function prints the syntax for deleteusage command.
     */
    public void showDeleteUsageSyntax() {
        printSpaces("To delete the usage of a locker:");
        printSpaces("  deleteusage XXXX");
        System.out.println();
    }

    /**
     * This function prints the syntax for editusage command.
     */
    public void showEditUsageSyntax() {
        printSpaces("To edit the usage of a locker:");
        printSpaces("  editusage XXXX n/__ i/__ e/__ m/__ f/__ t/__");
        System.out.println();
    }

    /**
     * This function prints the syntax for find command.
     */
    public void showFindSyntax() {
        printSpaces("To find/search a locker:");
        printSpaces("  find s/__ OR find a/__ OR find z/__ OR find c/__ OR find "
                + "OR find n/__ etc.");
        System.out.println();
    }

    /**
     * This function prints the syntax for find command.
     */
    public void showSortSyntax() {
        printSpaces("To sort the lockers:");
        printSpaces("  sortby asc/ for ascending OR sortby des/ for descending ");
        printSpaces("  sortby serialNumber OR address OR zone OR tags");
        System.out.println();
    }

    /**
     * This function prints the syntax for list and bye command.
     */
    public void showOtherSyntax() {
        printSpaces("To select a locker and view its information:");
        printSpaces("  selectlocker XXXX");
        System.out.println();
        printSpaces("To show the list of lockers:");
        printSpaces("  list");
        System.out.println();
        printSpaces("To clear all lockers in the list:");
        printSpaces("  clear");
        System.out.println();
        printSpaces("To show your reminders:");
        printSpaces("  reminders");
        System.out.println();
        printSpaces("To undo a command:");
        printSpaces("  undo");
        System.out.println();
        printSpaces("To redo a command:");
        printSpaces("  redo");
        System.out.println();
        printSpaces("To show the statistics of lockers:");
        printSpaces("  stats");
        System.out.println();
        printSpaces("To export the locker list as a CSV file:");
        printSpaces("  export");
        System.out.println();
        printSpaces("To view the history of commands:");
        printSpaces("  history");
        System.out.println();
        printSpaces("To exit SpongeBob:");
        printSpaces("  bye");
        System.out.println();
    }


    /**
     * This function prints the legends for help manual.
     */
    public void showSyntaxLegends() {
        printSpaces("-LEGENDS-");
        printSpaces("XXXX - serial number of selected locker");
        printSpaces("s - serial number (addlocker) OR starting serial number (addbatch)");
        printSpaces("u - size");
        printSpaces("a - area");
        printSpaces("z - zone");
        printSpaces("c - condition/tags");
        printSpaces("n - name");
        printSpaces("i - id(matric number)");
        printSpaces("e - email");
        printSpaces("m - major of study");
        printSpaces("f - rental start date");
        printSpaces("t - rental end date");
        printSpaces("p - preferences");
        System.out.println();
    }

    /**
     * This function prints the notes in help manual.
     */
    public void showSyntaxNote() {
        printSpaces("(Note: All the tokens can be assigned in any order.)");
    }

    /**
     * This function prints the help manual when requested by the user.
     */
    public void printHelp() {
        printSpaces("HELP MANUAL");
        System.out.println();
        showAddSyntax();
        showAddBatchSyntax();
        showDeleteSyntax();
        showEditSyntax();
        showAssignSyntax();
        showDeleteUsageSyntax();
        showEditUsageSyntax();
        showFindSyntax();
        showSortSyntax();
        showOtherSyntax();
        showSyntaxLegends();
        showSyntaxNote();
        printDash();
    }

    /**
     * Prints required number of empty spaces.
     * @param num represents the number of empty spaces wanted.
     */
    public void printEmptySpace(int num) {
        for (int i = 0; i < num; i++) {
            System.out.print(" ");
        }
    }

    /**
     * This function prints the borders for table of lockers.
     */
    public void printListBorder() {
        String str = "";
        for (int i = 0; i < 75; i++) {
            if (i == 0 || i == 14 || i == 28 || i == 33 || i == 74) {
                str += "+";
            } else {
                str += "-";
            }
        }
        printSpaces(str);
    }

    /**
     * This function prints the headers for table of lockers.
     */
    public void printListHeader() {
        System.out.print("|");
        System.out.print("SERIAL NUMBER");
        System.out.print("|");
        printEmptySpace(5);
        System.out.print("TAG");
        printEmptySpace(5);
        System.out.print("|");
        System.out.print("ZONE");
        System.out.print("|");
        printEmptySpace(18);
        System.out.print("AREA");
        printEmptySpace(18);
        System.out.print("|");
    }

    /**
     * This function prints the serial number of a locker for table of lockers.
     * @param str stores the serial number of a locker
     */
    public void printListSerialNumber(String str) {
        System.out.print("|");
        int trailingSpaces = (13 - str.length()) / 2;
        int endingSpaces = 13 - trailingSpaces - str.length();
        printEmptySpace(trailingSpaces);
        System.out.print(str);
        printEmptySpace(endingSpaces);

    }

    /**
     * This function prints the tag of a locker for table of lockers.
     * @param str stores the tag of a locker
     */
    public void printListTag(String str) {
        System.out.print("|");
        if (str.equalsIgnoreCase("not-in-use")) {
            printEmptySpace(2);
            System.out.print(str);
            printEmptySpace(1);
        } else if (str.equalsIgnoreCase("in-use")) {
            printEmptySpace(4);
            System.out.print(str);
            printEmptySpace(3);
        } else if (str.equalsIgnoreCase("unauthorized")) {
            printEmptySpace(1);
            System.out.print(str);
        } else {
            printEmptySpace(4);
            System.out.print(str);
            printEmptySpace(3);
        }
    }

    /**
     * This function prints the located zone of a locker for table of lockers.
     * @param str stores the located zone of a locker
     */
    public void printListZone(String str) {
        System.out.print("|");
        printEmptySpace(1);
        System.out.print(str);
        printEmptySpace(2);
    }

    /**
     * This function prints the located area of a locker for table of lockers.
     * @param str stores the located area of a locker
     */
    public void printListArea(String str) {
        System.out.print("|");
        if (str.length() < 38) {
            int numberOfSpaces = 40 - str.length();
            if (numberOfSpaces % 2 == 0) {
                printEmptySpace(numberOfSpaces / 2);
                System.out.print(str);
                printEmptySpace(numberOfSpaces / 2);
            } else {
                printEmptySpace((int) Math.floor(numberOfSpaces / 2));
                System.out.print(str);
                printEmptySpace((int) Math.ceil(numberOfSpaces / 2) + 1);
            }
        } else {
            System.out.print(str.substring(0, 37));
            System.out.print("...");
        }
        System.out.print("|");
    }

    /**
     * This function shows a table of all lockers stored in the system.
     * @param  listOfLockers stores the list of lockers
     */
    public void showList(List<Locker> listOfLockers) {

        printListBorder();
        System.out.print("    ");
        printListHeader();
        System.out.println();
        printListBorder();
        for (int i = 0; i < listOfLockers.size(); i++) {
            System.out.print("    ");
            printListSerialNumber(listOfLockers.get(i).serialNumberToString());
            printListTag(listOfLockers.get(i).tagToString());
            printListZone(listOfLockers.get(i).zoneToString());
            printListArea(listOfLockers.get(i).areaToString());
            System.out.println();
        }
        printListBorder();
        printDash();
    }

    /**
     * This function shows a table of all lockers that was searched by the user.
     * @param foundLockers stores the list of lockers that match the search parameters
     */

    public void printFoundLockers(List<Locker> foundLockers) {
        if (foundLockers.size() != 0) {
            printSpaces(" Here are lockers that match your search parameters ");
            showList(foundLockers);
        } else {
            printSpaces(" There are NO lockers that match your search parameters ");
        }

    }

    /**
     * This function shows a table of all sorted lockers based on the user input.
     * @param sortedLockers stores the list of lockers that were sorted based on the user input
     */

    public void printSortedLockers(List<Locker> sortedLockers) {

        printSpaces(" Your lockers have been sorted accordingly ");
        showList(sortedLockers);

    }

    /**
     * This function shows a table of all sorted lockers based on the user input.
     * @param unauthorizedLockers stores the list of unauthorized lockers
     * @param brokenLockers stores the list of unauthorized lockers
     */

    public void printReminders(List<Locker> expiringLockers,
                               List<Locker> unauthorizedLockers,
                               List<Locker> brokenLockers) {

        if (expiringLockers.size() != 0) {

            printSpaces(" Here are the list of expiring lockers that require attention. ");
            showList(expiringLockers);

        } else {

            printSpaces(" There are NO expiring lockers at the moment. ");

        }
        if (unauthorizedLockers.size() != 0) {

            printSpaces(" Here are the list of unauthorized lockers that require attention. ");
            showList(unauthorizedLockers);

        } else {

            printSpaces(" There are NO unauthorized usage at the moment. ");

        }

        if (unauthorizedLockers.size() != 0) {

            printSpaces(" Hare are the list of broken lockers that require repairing. ");
            showList(brokenLockers);

        } else {

            printSpaces(" There are NO broken lockers at the moment. ");

        }
        printDash();

    }

    /**
     * This function prints the message when undo operation is successful.
     */
    public void printSuccessfulUndo() {
        printSpaces("I have successfully undo the previous command!");
        printDash();
    }

    /**
     * This function prints the message when redo operation is successful.
     */
    public void printSuccessfulRedo() {
        printSpaces("I have successfully redo the previous command!");
        printDash();
    }

    /**
     * This function prints the serial number of the selected locker.
     * @param str stores the serial number of the locker
     */
    public void printSerialNumber(String str) {
        System.out.print("|");
        System.out.print("Serial Number: #" + str);
        printEmptySpace(50 - (str.length() + 16));
        System.out.print("|");
    }

    /**
     * This function prints the tag of the selected locker.
     * @param str stores the tag of the locker
     */
    public void printTag(String str) {
        System.out.print("|");
        System.out.print("Tag: " + str);
        printEmptySpace(50 - (str.length() + 5));
        System.out.print("|");
    }

    /**
     * This function prints the located zone of the selected locker.
     * @param str stores the located zone of the locker
     */
    public void printZone(String str) {
        System.out.print("|");
        System.out.print("Zone: " + str);
        printEmptySpace(50 - (str.length() + 6));
        System.out.print("|");
    }

    /**
     * This function prints the located area of the selected locker.
     * @param str stores the located area of the locker
     */
    public void printArea(String str) {
        System.out.print("|");
        System.out.print("Area: ");
        if (str.length() < 45) {
            System.out.print(str);
            printEmptySpace(50 - (str.length() + 6));
        } else {
            System.out.print(str.substring(0, 41));
            System.out.print("...");
        }
        System.out.print("|");
    }

    /**
     * This function prints the name of the student assigned (if any) to the selected locker.
     * @param str stores the name of student (if any)
     */
    public void printStudentName(String str) {
        System.out.print("Student Name: ");
        if (str.length() < 37) {
            System.out.print(str);
            printEmptySpace(50 - (str.length() + 14));
        } else {
            System.out.print(str.substring(0, 33));
            System.out.print("...");
        }
        System.out.println("|");
    }


    /**
     * This function prints the matric number of the student assigned (if any) to the selected locker.
     * @param str stores the matric number of student (if any)
     */
    public void printStudentID(String str) {
        System.out.print("Student ID: " + str);
        printEmptySpace(50 - (str.length() + 12));
        System.out.println("|");
    }

    /**
     * This function prints the email of the student assigned (if any) to the selected locker.
     * @param str stores the email of student (if any)
     */
    public void printStudentEmail(String str) {
        System.out.print("Student Email: ");
        if (str.length() < 36) {
            System.out.print(str);
            printEmptySpace(50 - (str.length() + 15));
        } else {
            System.out.print(str.substring(0, 32));
            System.out.print("...");
        }
        System.out.println("|");
    }

    /**
     * This function prints the name of the student assigned (if any) to the selected locker.
     * @param str stores the name of student (if any)
     */
    public void printStudentMajor(String str) {
        System.out.print("Student Major: ");
        if (str.length() < 36) {
            System.out.print(str);
            printEmptySpace(50 - (str.length() + 15));
        } else {
            System.out.print(str.substring(0, 32));
            System.out.print("...");
        }
        System.out.println("|");
    }

    /**
     * This function prints the start date of rental (if any) of the selected locker.
     * @param str stores the start date of rental (if any)
     */
    public void printStartDate(String str) {
        System.out.print("|");
        System.out.print("Rental Start Date: " + str);
        printEmptySpace(50 - (str.length() + 19));
        System.out.print("|");
    }

    /**
     * This function prints the end date of rental (if any) of the selected locker.
     * @param str stores the end date of rental (if any)
     */
    public void printEndDate(String str) {
        System.out.print("Rental End Date: " + str);
        printEmptySpace(50 - (str.length() + 17));
        System.out.println("|");
    }

    /**
     * This function prints the borders for user to view the information of the selected locker.
     */
    public void printBorder() {
        String str = "";
        for (int i = 0; i < 103; i++) {
            if (i == 0 || i == 51 || i == 102) {
                str += "+";
            } else {
                str += "-";
            }
        }
        printSpaces(str);
    }

    /**
     * This function shows a the information of a selected locker.
     * @param serialNumber stores the serial number of the locker
     * @param tag stores the tag of the locker
     * @param zone stores the located zone of the locker
     * @param area stores the located area of the locker
     * @param studentName stores the name of the student assigned (if any) to the locker
     * @param studentID stores the matic number of the student assigned (if any) to the locker
     * @param studentEmail stores the email of the student assigned (if any) to the locker
     * @param studentMajor stores the major of the student assigned (if any) to the locker
     * @param startDate stores the start date of rental (if any) of the locker
     * @param endDate stores the end date of rental (if any) of the locker
     */
    public void selectMessage(String serialNumber, String tag, String zone, String area, String studentName,
                              String studentID, String studentEmail, String studentMajor, String startDate,
                              String endDate) {
        printSpaces("Here are the information of the locker you have selected:");
        System.out.println();
        printBorder();
        System.out.print("    ");
        printSerialNumber(serialNumber);
        printStudentName(studentName);
        System.out.print("    ");
        printTag(tag);
        printStudentID(studentID);
        System.out.print("    ");
        printZone(zone);
        printStudentEmail(studentEmail);
        System.out.print("    ");
        printArea(area);
        printStudentMajor(studentMajor);
        System.out.print("    ");
        printStartDate(startDate);
        printEndDate(endDate);
        printBorder();
        printDash();
    }

    /**
     * This function prints the list of command history typed by the user.
     * @param historyList stores the list of command history
     */
    public void printHistory(ArrayList<String> historyList) {
        if (historyList.isEmpty()) {
            printSpaces("There are no history of commands.");
        } else {
            printSpaces("Here are the history of commands you have typed in:");
            System.out.println();
            for (int i = 0; i < historyList.size(); i++) {
                printSpaces((i + 1) + ". " + historyList.get(i));
            }
        }
        printDash();
    }
}