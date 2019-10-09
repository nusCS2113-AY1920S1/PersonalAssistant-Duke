package commands;

import Storage.Storage;
import Tasks.Task;
import UI.Ui;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class ContactsCommand extends Command {
    /**
     * This method is the list of all the contact numbers and you got add/find/delete contacts.
     * @param list
     * @param ui
     * @param storage
     * @throws ParseException
     * @throws IOException
     * @throws NullPointerException
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws ParseException, IOException, NullPointerException {
        System.out.print("CONTACTS PAGE\n\n");
        HashMap<String, String> map = storage.Contact(); //Read the file
        Map<String, String> contact = new TreeMap<String, String>(map);

        System.out.print("Name:                         | Number:\n------------------------------------------\n");
        for (String key : contact.keySet()) {
            if (!key.contains("NUS")) {
                System.out.print(key);
                int l = 30 - key.length();
                for (int i = 0; i < l; i++) {
                    System.out.print(" ");
                }
                System.out.print("| ");
                System.out.print(contact.get(key)+ "\n");
                System.out.print("------------------------------------------\n");
            }
        }
        System.out.print("\nNUS CONTACTS:\n");
        for (String key : contact.keySet()) {
            if (key.contains("NUS")) {
                System.out.print(key);
                int l = 30 - key.length();
                for (int i = 0; i < l; i++) {
                    System.out.print(" ");
                }
                System.out.print("| ");
                System.out.print(contact.get(key) + "\n");
                System.out.print("------------------------------------------\n");
            }
        }
        ui.ReadCommand();
        while (!ui.FullCommand.equals("esc")) {
            if (ui.FullCommand.equals("add")) {
                System.out.println("Input in this format: Name,Number");
                ui.ReadCommand();
                String[] split_info = ui.FullCommand.split(",");
                String name = split_info[0];
                String number = split_info[1];
                contact.put(name, number);
                System.out.println("Okay we have successfully added your new contact :) " + ui.FullCommand);
                String toStore = "";
                for (String key : contact.keySet()) {
                    toStore = toStore.concat(key + "|" + contact.get(key) + "\n");
                }
                storage.Storages_Contact(toStore);
            } else if (ui.FullCommand.split(" ")[0].equals("find")) {
                int a = ui.FullCommand.split(" ")[1].toCharArray()[0];
                String command = ui.FullCommand.split(" ")[0];
                String name_to_find = "";
                for (int i = 1; i < ui.FullCommand.split(" ").length; i++) {
                    if (i != ui.FullCommand.split(" ").length - 1) {
                        name_to_find = name_to_find.concat(ui.FullCommand.split(" ")[i] + " ");
                    } else {
                        name_to_find = name_to_find.concat(ui.FullCommand.split(" ")[i]);
                    }
                }
                if (a <= 9) {
                    System.out.println("Name not found.");
                } else {
                    System.out.println("Name:                         | Number:\n------------------------------------------");
                    for(String keys:contact.keySet()) {
                        if(keys.contains(name_to_find)) {
                            System.out.print(keys);
                            int l = 30 - keys.length();
                            for (int i = 0; i < l; i++) {
                                System.out.print(" ");
                            }
                            System.out.print("| ");
                            System.out.println(contact.get(keys));
                            System.out.println("------------------------------------------");
                        }
                    }
                }
            }
            else if (ui.FullCommand.equals("c_list")) {
                System.out.println("Name:                         | Number:\n------------------------------------------");
                for (String key : contact.keySet()) {
                    if (!key.contains("NUS")) {
                        System.out.print(key);
                        int l = 30 - key.length();
                        for (int i = 0; i < l; i++) {
                            System.out.print(" ");
                        }
                        System.out.print("| ");
                        System.out.println(contact.get(key));
                        System.out.println("------------------------------------------");
                    }
                }
                System.out.println("\nNUS CONTACTS:");
                for (String key : contact.keySet()) {
                    if (key.contains("NUS")) {
                        System.out.print(key);
                        int l = 30 - key.length();
                        for (int i = 0; i < l; i++) {
                            System.out.print(" ");
                        }
                        System.out.print("| ");
                        System.out.println(contact.get(key));
                        System.out.println("------------------------------------------");
                    }
                }

            } else if (ui.FullCommand.contains("delete")) {
                String command = ui.FullCommand.split(" ")[0];
                String name_to_delete = "";
                for (int i = 1; i < ui.FullCommand.split(" ").length; i++) {
                    if (i != ui.FullCommand.split(" ").length - 1) {
                        name_to_delete = name_to_delete.concat(ui.FullCommand.split(" ")[i] + " ");
                    } else {
                        name_to_delete = name_to_delete.concat(ui.FullCommand.split(" ")[i]);
                    }
                }
                if (ui.FullCommand.equals("delete")) {
                    System.out.println("You need to indicate what you want to delete, Format: delete name");
                } else if (contact.containsKey(name_to_delete)) {
                    contact.remove(name_to_delete);
                    System.out.println(name_to_delete + " has been removed.");
                } else {
                    System.out.println(name_to_delete + " is not in the list.");
                }
                String toStore = "";
                for (String key : contact.keySet()) {
                    toStore = toStore.concat(key + "|" + contact.get(key) + "\n");
                }
                storage.Storages_Contact(toStore);
            }
            ui.ReadCommand();
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }

}
