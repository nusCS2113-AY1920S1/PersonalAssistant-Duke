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
        String details = "[no details]";
        String phone = "[no phone]";

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> detailsList = new ArrayList<>();
        ArrayList<String> phoneList = new ArrayList<>();

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

            if (name.trim().isEmpty()) {
                return null;
            }
        }

        if (!detailsList.isEmpty()) {
            details = concatList(detailsList);
            if (details.trim().isEmpty()) {
                details = "[no details]";
            }
        }

        if (!phoneList.isEmpty()) {
            phone = concatList(phoneList);
            if (phone.trim().isEmpty()) {
                phone = "[no phone]";
            }
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

        String name = null;
        String details = null;
        String phone = null;

        ArrayList<String> nameList = new ArrayList<>();
        ArrayList<String> detailsList = new ArrayList<>();
        ArrayList<String> phoneList = new ArrayList<>();

        detailsIndex = findIndexOf(info, "/d");
        phoneIndex = findIndexOf(info, "/p");
        nameIndex = findIndexOf(info, "/n");


        //Process input into three arraylist
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

        if (!nameList.isEmpty()) {
            name = concatList(nameList);
            if (name.trim().isEmpty()) {
                name = null;
            }
        }

        if (!detailsList.isEmpty()) {
            details = concatList(detailsList);
            if (details.trim().isEmpty()) {
                details = "[no details]";
            }
        } else if (detailsIndex != -1 && detailsList.isEmpty()) {
            details = "[no details]";
        }

        if (!phoneList.isEmpty()) {
            phone = concatList(phoneList);
            if (phone.trim().isEmpty()) {
                phone = "[no phone]";
            }
        } else if (phoneIndex != -1 && phoneList.isEmpty()) {
            phone = "[no phone]";
        }

        Contact processed = new Contact(name, details, phone);

        return processed;
    }

}
