package rims.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import rims.resource.Reservation;
import rims.resource.ReservationList;
import rims.resource.Resource;

public class Ui {
    protected Scanner inputScanner;
    protected String input;
    protected int intInput;
    public static String line = "____________________________________________________________________________________________________________________________________________";
    public static String hash = "********************************************************************************************************************************************";
    public static String tab = "\t";

    public Ui() {
        inputScanner = new Scanner(System.in);
        Home();
    }

    public String getInput() {
        input = inputScanner.nextLine();
        return input;
    }

    public int getIntegerInput() {

        intInput = inputScanner.nextInt();
        return intInput;
    }

    public void printLine() {
        System.out.println(tab + line);
    }

    public void ErrorPrint(String Error) {
        System.out.println(tab + hash);
        System.out.println(tab + Error);
        System.out.println(tab + hash);
    }

    public void print(String input) {
        System.out.println(tab + input);
    }

    public void printArray(ArrayList<String> inputs) {
        for (int i = 0; i < inputs.size(); i++) {
            System.out.println("\t" + inputs.get(i));
        }
    }

    public void printResourceArray(ResourceList resources) {

        for (int i = 0; i < resources.size(); i++) {
            System.out.println("\t" + resources.getResourceList().get(i).toString());
        }
    }

    /**
     * This method loops through all the resources first, print out the resource.
     * Then, it goes into each resource and print out all the reservation under this
     * resource.
     * 
     * @param resources
     */
    public void printResourceArrayWithReservations(ArrayList<Resource> resources) {
        printLine();
        for (int i = 0; i < resources.size(); i++) {
            print(resources.get(i).toString());
            printReservationArray(resources.get(i).getReservations());
        }
        printLine();
    }

    public void printReservationArray(ReservationList reservations) {
        for (int i = 0; i < reservations.size(); i++) {
            System.out.println("\t" + reservations.getReservationByIndex(i).toString());
        }
    }

    /**
     * This method takes in an arraylist<resource> because the java remove method
     * re-allocates an memory address, so passing ResourceList results in a null
     * pointer exception
     */
    public void printRooms(ArrayList<Resource> resources) {
        printLine();
        print("List of all rooms:\n");
        for (int i = 0; i < resources.size(); i++) {
            if (resources.get(i).getType().equals("R")) {
                System.out.println("\t" + resources.get(i).toString());
            }
        }
        printLine();
    }

    /**
     * This method takes in an arraylist<resource> because the java remove method
     * re-allocates an memory address, so passing ResourceList results in a null
     * pointer exception
     */
    public void printItem(ArrayList<Resource> resources) {
        printLine();
        print("List of all items:\n");
        for (int i = 0; i < resources.size(); i++) {
            if (resources.get(i).getType().equals("I")) {
                System.out.println("\t" + resources.get(i).toString());
            }
        }
        printLine();
    }

    public void printReservations(int resource_id, ResourceList resources) {
        printLine();

        Resource thisResource = resources.getResourceByResourceId(resource_id);
        ReservationList reservations = thisResource.getReservations();

        if (reservations.size() > 0) {
            print("List of all reservations under this resource:\n");
            printReservationArray(reservations);
        } else {
            print("There is currently no reservations under this resource");
        }
        printLine();
    }

    /**
     * asks the user to enter an resource id, and returns it as an integer
     * 
     * @return input i
     */
    public int getResourceId() {
        int i;
        printLine();
        print("Enter the resource ID");
        printLine();
        i = Integer.parseInt(getInput());
        return i;
    }

    /**
     * asks the user to enter his user id, and returns it as an integer
     * 
     * @return input i
     */
    public int getUserId() {
        int i;
        printLine();
        print("Enter your user ID");
        printLine();
        i = Integer.parseInt(getInput());
        return i;
    }

    /**
     * This method asks user for a pair of dates. The dates are stored in a string
     * array. The first element is the start date and the second element is the end
     * date.
     * 
     * @return ArrayList<String>
     */
    public ArrayList<String> getPairOfDates() {
        ArrayList<String> newpair = new ArrayList<String>();
        printLine();
        print("Enter Start date & time (DD/MM/YYYY HHMM)");
        printLine();
        String startdate = getInput();
        printLine();
        print("Enter End date & time (DD/MM/YYYY HHMM)");
        printLine();
        String endDate = getInput();
        newpair.add(startdate);
        newpair.add(endDate);
        return newpair;
    }

    public void printSuccessReservation(Reservation reservation) {
        printLine();
        print("The following reservation has been made\n");
        print(reservation.toString());
        printLine();
    }

    public void printEmptyLine() {
        System.out.println();
    }

    public void formattedPrintArray(ArrayList<String> inputs) {
        printLine();
        printArray(inputs);
        printLine();
    }

    public void formattedPrint(String input) {
        printLine();
        print(input);
        printLine();
    }

    public void farewell() {
        formattedPrint("Bye. Hope to see you again soon!");
    }

    /**
     * Prints a welcome message when Duke is started up.
     */
    public void welcomeWithLogo() {
        printLogo();
        formattedPrintArray(new ArrayList<String>(Arrays.asList("Hello. I am RIM.", "Resource & Inventory Management",
                "The facilities and logistics management system.", "What can I do for you?")));
    }

    public void welcome() {
        formattedPrintArray(new ArrayList<String>(Arrays.asList("Hello. I am RIM.", "Resource & Inventory Management",
                "The facilities and logistics management system.", "What can I do for you?")));
    }

    public void Home() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("add - add new resource to inventory");
        list.add("delete - delete existing resource from inventory");
        list.add("reserve - add new reservation request");
        list.add("return - return/cancel reservation");
        list.add("list - see all resources and current reservations");

        printLine();
        formattedPrintArray(list);
        printLine();
    }

    public void printLogo() {
        String logo = "\n" + "          _____                    _____                    _____          \n"
                + "         /\\    \\                  /\\    \\                  /\\    \\         \n"
                + "        /::\\    \\                /::\\    \\                /::\\____\\        \n"
                + "       /::::\\    \\               \\:::\\    \\              /::::|   |        \n"
                + "      /::::::\\    \\               \\:::\\    \\            /:::::|   |        \n"
                + "     /:::/\\:::\\    \\               \\:::\\    \\          /::::::|   |        \n"
                + "    /:::/__\\:::\\    \\               \\:::\\    \\        /:::/|::|   |        \n"
                + "   /::::\\   \\:::\\    \\              /::::\\    \\      /:::/ |::|   |        \n"
                + "  /::::::\\   \\:::\\    \\    ____    /::::::\\    \\    /:::/  |::|___|______  \n"
                + " /:::/\\:::\\   \\:::\\____\\  /\\   \\  /:::/\\:::\\    \\  /:::/   |::::::::\\    \\ \n"
                + "/:::/  \\:::\\   \\:::|    |/::\\   \\/:::/  \\:::\\____\\/:::/    |:::::::::\\____\\\n"
                + "\\::/   |::::\\  /:::|____|\\:::\\  /:::/    \\::/    /\\::/    / ~~~~~/:::/    /\n"
                + " \\/____|:::::\\/:::/    /  \\:::\\/:::/    / \\/____/  \\/____/      /:::/    / \n"
                + "       |:::::::::/    /    \\::::::/    /                       /:::/    /  \n"
                + "       |::|\\::::/    /      \\::::/____/                       /:::/    /   \n"
                + "       |::| \\::/____/        \\:::\\    \\                      /:::/    /    \n"
                + "       |::|  ~|               \\:::\\    \\                    /:::/    /     \n"
                + "       |::|   |                \\:::\\    \\                  /:::/    /      \n"
                + "       \\::|   |                 \\:::\\____\\                /:::/    /       \n"
                + "        \\:|   |                  \\::/    /                \\::/    /        \n"
                + "         \\|___|                   \\/____/                  \\/____/         \n"
                + "                                                      ";
        System.out.println(logo);
    }


}