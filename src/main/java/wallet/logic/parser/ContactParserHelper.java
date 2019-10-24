//@@author Xdecosee

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
     * @param info   Array of user input arguments.
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
        String details;
        String phone;

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> detailsList = new ArrayList<>();
        ArrayList<String> phoneList = new ArrayList<>();

        detailsIndex = findIndexOf(info, "/d");
        phoneIndex = findIndexOf(info, "/p");

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

        name = concatList(nameList);
        if (name.trim().isEmpty()) {
            return null;
        }

        details = concatList(detailsList);
        if (details.trim().isEmpty()) {
            details = null;
        }

        phone = concatList(phoneList);
        if (phone.trim().isEmpty()) {
            phone = null;
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
        int detailsIndex;
        int phoneIndex;
        int nameIndex;

        String name;
        String details = null;
        String phone = null;

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> detailsList = new ArrayList<>();
        ArrayList<String> phoneList = new ArrayList<>();

        detailsIndex = findIndexOf(info, "/d");
        phoneIndex = findIndexOf(info, "/p");
        nameIndex = findIndexOf(info, "/n");

        for (int i = 0; i < info.length; i++) {
            if (i == detailsIndex) {
                section = 1;
            } else if (i == phoneIndex) {
                section = 2;
            } else if (i == nameIndex) {
                section = 0;
            } else if (section == 1) {
                detailsList.add(info[i]);
            } else if (section == 2) {
                phoneList.add(info[i]);
            } else if (section == 0) {
                nameList.add(info[i]);
            } else {
                continue;
            }
        }

        name = concatList(nameList);
        if (name.trim().isEmpty()) {
            name = null;
        }

        if (detailsIndex >= 0) {
            details = concatList(detailsList);
            if (details.trim().isEmpty()) {
                details = "";
            }
        }

        if (phoneIndex >= 0) {
            phone = concatList(phoneList);
            if (phone.trim().isEmpty()) {
                phone = "";
            }
        }

        if (nameIndex >= 0 && phoneIndex >= 0 && detailsIndex >= 0) {
            return null;
        }

        Contact processed = new Contact(name, details, phone);

        return processed;
    }
}
