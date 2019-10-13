package wallet.logic.parser;

import java.util.ArrayList;

import wallet.model.contact.Contact;

public class ContactParserHelper {

    /**
     * Concat an array of strings.
     *
     * @param toJoin Array of strings needed to join together.
     * @return A string with words joined together.
     */
    public String concatList(ArrayList<String> toJoin) {

        String result = "";
        for (String s : toJoin) {
            result += s + " ";
        }
        return result;
    }

    /**
     * Find index of command line option in user input.
     *
     * @param info Array of user input arguments.
     * @param toFind Command Line option to find.
     * @return Index of command line option.
     */
    public int findIndexOf(String[] info, String toFind) {

        for (int i = 0; i < info.length; i += 1) {
            if (info[i].equals(toFind)) {
                return i;
            }
        }

        return -1;

    }

    /**
     * Process user input for a new Contact entry.
     *
     * @param info Array of user input arguments.
     * @return Contact object with user input.
     */
    public Contact newInput(String[] info) {
        int section = 0;
        int detailsIndex;
        int phoneIndex;
        String name;
        String details = "[no details]";
        String phone = "[no phone]";

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> detailsList = new ArrayList<>();
        ArrayList<String> phoneList= new ArrayList<>();

        detailsIndex = findIndexOf(info, "/d");
        phoneIndex = findIndexOf(info, "/p");

        //Process input into three array list
        for (int i = 0; i < info.length; i += 1) {
            if (i == detailsIndex) {
                section = 1;
            } else if (i == phoneIndex) {
                section = 2;
            } else if (section == 1) {
                detailsList.add(info[i]);
            } else if (section == 2) {
                phoneList.add(info[i]);
            } else {
                nameList.add(info[i]);
            }
        }

        if (nameList.isEmpty()) {
            return null;
        } else {
            name = concatList(nameList);
        }

        if (!detailsList.isEmpty()) {
            details = concatList(detailsList);
        }

        if (!phoneList.isEmpty()) {
            phone = concatList(phoneList);
        }

        Contact processed = new Contact(name, details, phone);

        return processed;
    }

    /**
     * Process user input to update Contact entry.
     *
     * @param info Array of user input arguments.
     * @return Contact object with user input.
     */
    public Contact updateInput(String[] info) {
        int section = -1;
        int details_index;
        int phone_index;
        int name_index;

        String name = null;
        String details = null;
        String phone = null;

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> detailsList = new ArrayList<>();
        ArrayList<String> phoneList = new ArrayList<>();

        details_index = findIndexOf(info, "/d");
        phone_index = findIndexOf(info, "/p");
        name_index = findIndexOf(info, "/n");



        //Process input into three arraylist
        for (int i = 0; i < info.length; i += 1) {
            if (i == details_index) {
                section = 1;
            } else if (i == phone_index) {
                section = 2;
            } else if (i == name_index) {
                section = 0;
            } else if (section == 1) {
                detailsList.add(info[i]);
            } else if (section == 2) {
                phoneList.add(info[i]);
            } else if (section == 0) {
                nameList.add(info[i]);
            }
        }

        if (!nameList.isEmpty()) {
            name = concatList(nameList);
        }

        if (!detailsList.isEmpty()) {
            details = concatList(detailsList);
        } else if (details_index != -1) {
            details = "[no details]";
        }

        if (!phoneList.isEmpty()) {
            phone = concatList(phoneList);
        } else if (phone_index != -1) {
            phone = "[no phone]";
        }

        Contact processed = new Contact(name, details, phone);

        return processed;
    }

}
