package list;
import exception.DukeException;
import parser.Parser;
import storage.Storage;
import task.TaskList;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class DegreeList implements Serializable, Cloneable {
    private static ArrayList<String> list = new ArrayList<>();
    public static ArrayList<String> getDegrees(){
        return list;
    }

    //private static final String filename = "../data/savedegree.txt";

    public DegreeList(){}
    /**
     * Fetches current size of ArrayList.
     *
     * @return long size of ArrayList
     */
    public int size() {
        return list.size();
    }

    /**
     * Method to facilitate the deep cloning of this taskList.
     * Returns a copy of this taskList, but with different references.
     * This is to avoid shallow copying, which will also modify the saved state of the taskList.
     *
     * @return A copy of this taskList with different references to objects.
     */
    public DegreeList deepClone() {
        try {
            //Serialization of object
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
            objectOutputStream.writeObject(this);

            //De-serialization of object
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteOutputStream.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
            return (DegreeList) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * Out of Bounds checker.
     *
     * @param request int The index to be checked if it exists
     * @return boolean true if within range, false if not
     */
    private boolean isOutOfRange(int request) {
        return ((request < 0) || (request >= this.size()));
    }

    /**
     * Method to add a degree to degree list.
     * Does not output anything to the user, used mostly for backend processes.
     *
     * @param input The degree to be added to the degree list.
     */
    public void add(String input) {
        list.add(input); //Straightforward and quiet method to add degrees, for backend stuffs
    }

    private int check_for_duplicates(String input) throws DukeException {
        int flag = 0;
        if (input.matches("Computer Engineering|CEG|Com E")) {
            if (list.contains("Computer Engineering") || list.contains("CEG") || list.contains("Com E")) {
                //throw new DukeException("The degree already exists in your choices");
                flag = 1;
            }
        }
        if (input.matches("Material Science Engineering|MSE")) {
            if (list.contains("Material Science Engineering") || list.contains("MSE")) {
                //throw new DukeException("The degree already exists in your choices");
                flag = 1;
            }
        }

        if (input.matches("Biomedical Engineering|BME|BioMed|Bio Eng|BM")) {
            if (list.contains("Biomedical Engineering") || list.contains("BME") || list.contains("BioMed") || list.contains("Bio Eng") || list.contains("BM")) {
                //throw new DukeException("The degree already exists in your choices");
                flag = 1;
            }
        }
        if (input.matches("Chemical Engineering|Chem Eng|ChE")) {
            if (list.contains("Chemical Engineering") || list.contains("Chem Eng") || list.contains("ChE")) {
                //throw new DukeException("The degree already exists in your choices");
                flag = 1;
            }
        }
        if (input.matches("Civil Engineering|Civil E|CivE|Civil")) {
            if (list.contains("Civil Engineering") || list.contains("Civil E") || list.contains("CivE") || list.contains("Civil")) {
                //throw new DukeException("The degree already exists in your choices");
                flag = 1;
            }
        }
        if (input.matches("Electrical Engineering|EE|ElecE")) {
            if (list.contains("Electrical Engineering") || list.contains("EE") || list.contains("ElecE")) {
                //throw new DukeException("The degree already exists in your choices");
                flag = 1;
            }
        }
        if (input.matches("Environmental Engineering|ENVE")) {
            if (list.contains("Environmental Engineering") || list.contains("ENVE")) {
                //throw new DukeException("The degree already exists in your choices");
                flag = 1;
            }
        }

        if (input.matches("Industrial Systems Engineering|ISE|IE")) {
            if (list.contains("Industrial Systems Engineering") || list.contains("ISE") || list.contains("IE")) {
                //throw new DukeException("The degree already exists in your choices");
                flag = 1;
            }
        }

        if (input.matches("Mechanical Engineering|Mech Eng|ME")) {
            if (list.contains("Mechanical Engineering") || list.contains("Mech Eng") || list.contains("ME")) {
                //throw new DukeException("The degree already exists in your choices");
                flag = 1;
            }
        }
        return flag;
    }



    /**
     * Displays the degree specified by the user.
     *
     * @param index The position of the degree in the degree list.
     * @return The degree in the degree list.
     * @throws DukeException Throws when degree is not found within the degree list.
     */
    public String get(int index) throws DukeException {
        if (!this.isOutOfRange(index)) {
            return this.list.get(index);
        } else {
            throw new DukeException("Requested Degree not found within degree list");
        }
    }

    public String conversion(int request) {
        String imp = list.get(request);
        return imp;
    }

    /**
     * Method to add a degree to degree list.
     * User-friendly method to display the degree added.
     *
     * @param input The degree as specified by the user.
     * @throws DukeException The degree does not exist?
     */
    public void add_custom(String input, Storage storage) throws DukeException {
        int flag = check_for_duplicates(input);
        if(flag == 0) {
            list.add(input);
            System.out.print("Added " + input + " to your choice of degrees.\n");
        }

        else {
            throw new DukeException("The degree already exists in your choices");

        }

    }

    /**
     * Method to delete a particular degree from the degree list.
     *
     * @param input The degree to be deleted
     * @throws DukeException Throws an error if the degree does not exist.
     */
    public void delete(String input, DegreeListStorage dd) throws DukeException{
        try {
            int request = Integer.parseInt(input);
            request -= 1;
            if (isOutOfRange(request)) {
                throw new DukeException("The index was not found within range");
            } else {
                String imp = conversion(request);
                System.out.println("Noted. I've removed this degree:\n"
                        + "  " + list.get(request));
                //dd.processing(imp);
                this.list.remove(request);
                System.out.println("Now you have " + this.list.size() + " degrees you are interested in.");

            }
        } catch (DukeException e) {
            throw new DukeException(e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new DukeException("That is NOT a valid integer");
        }
    }

    /**
     * Method to print out the entire list of degrees for the user.
     */
    public void print() {
        if (list.size() == 0) {
            System.out.println("Whoops, there doesn't seem to be anything here at the moment");
        } else {
            System.out.println("Here are your degree choices:");
            for(int i = 0; i < list.size(); i++) {
                System.out.println((i+1) + ". " + list.get(i));
            }
        }
    }

    /**
     * Method to swap two degrees.
     * The user can input 2 indices to rank 2 degrees based on his order of preference.
     *
     * @param input
     * @param dd DegreeList Storage makes changes in the text file
     * @throws DukeException
     */
    public void swap(String input, DegreeListStorage dd) throws DukeException {
        String[] split = input.split(" ");
        if(split.length < 2) {
            throw new DukeException("Please mention both the indices to swap the degrees");
        } else if(split.length > 2) {
            throw new DukeException("Too many arguments");
        } else if (split.length == 2){
                String first_index = split[0];
                String second_index = split[1];
                int request = Integer.parseInt(first_index);
                //String degree = list.get(request - 1);
                int request1 = Integer.parseInt(second_index);
                //String degree1 = list.get(request1 - 1);
                Collections.swap(list, request - 1, request1 - 1);
                System.out.println("Swap complete!");
//            try {
//                //dd.work(degree, Integer.toString(request1 - 1));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            try {
//               // dd.work(degree1, Integer.toString(request - 1));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }



    /**
     * Deletes the entire degree list.
     */
    public void clear() {
        list.clear();
    }
}
