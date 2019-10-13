package wallet.logic.parser;

import java.util.ArrayList;

import wallet.model.contact.Contact;

public class ContactParserHelper {


    public String concatList(ArrayList<String> toJoin) {

        String result = "";
        for (String s : toJoin) {
            result += s + " ";
        }
        return result;
    }

    public int findIndexOf(String[] info, String toFind) {

        for (int i = 0; i < info.length; i += 1) {
            if (info[i].equals(toFind)) {
                return i;
            }
        }

        return -1;

    }

    public Contact processInput(String[] info) {
        int section = 0;
        int details_index;
        int phone_index;
        String name;
        String details = "[no details]";
        String phone = "[no phone]";

        ArrayList<String> name_list = new ArrayList<>();
        ArrayList<String> details_list = new ArrayList<>();
        ArrayList<String> phone_list = new ArrayList<>();

        details_index = findIndexOf(info, "/d");
        phone_index = findIndexOf(info, "/p");

        //Process input into three arraylist
        for (int i = 0; i < info.length; i += 1) {
            if (i == details_index) {
                section = 1;
            } else if (i == phone_index) {
                section = 2;
            } else if (section == 1) {
                details_list.add(info[i]);
            } else if (section == 2) {
                phone_list.add(info[i]);
            } else {
                name_list.add(info[i]);
            }
        }

        if (name_list.isEmpty()) {
            return null;
        } else {
            name = concatList(name_list);
        }

        if (!details_list.isEmpty()) {
            details = concatList(details_list);
        }

        if (!phone_list.isEmpty()) {
            phone = concatList(phone_list);
        }

        Contact processed = new Contact(name, details, phone);

        return processed;
    }


}
