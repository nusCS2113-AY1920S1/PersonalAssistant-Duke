package list;
import exception.DukeException;
import parser.Parser;
import task.Task;
import task.TaskList;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


public class DegreeList implements Serializable, Cloneable {
    private static ArrayList<String> list = new ArrayList<>();
    static int i = 0;
    public static ArrayList<String> getDegrees(){
        return list;
    }

    private static final String filename = "../data/savedegree.txt";


    public DegreeList(){}
    /**
     * Fetches current size of ArrayList.
     *
     * @return long size of ArrayList
     */
    public long size() {
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



    /**
     * Method to add a degree to degree list.
     * User-friendly method to display the degree added.
     *
     * @param input The degree as specified by the user.
     * @throws DukeException The degree does not exist?
     */
    public void add_custom(String input) throws DukeException {

            BufferedWriter bw = null;
            FileWriter fw = null;
            try {
                File file = new File(filename);
                if(!file.exists()) {
                    file.createNewFile();
                }

                fw = new FileWriter(file.getAbsoluteFile(), true);
                bw = new BufferedWriter(fw);
                list.add(input);
                System.out.print("Added ");
                System.out.print(input);
                System.out.println(" to your choices of degrees");
                String data = "degree-"+input + "-" + list.indexOf(input) + "\n";
                bw.write(data);
                i = i + 1;
        }
        catch (Exception e)
        {
            throw new DukeException(e.getLocalizedMessage());
        } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                    if (fw != null) {
                        fw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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
                dd.processing(imp);
                this.list.remove(request);
                System.out.println("Now you have " + this.list.size() + " degrees you are interested in.");

            }
        } catch (DukeException e) {
            throw new DukeException(e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new DukeException("That is NOT a valid integer");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to print out the entire list of degrees for the user.
     */
    public void print() {
        if (list.size() == 0) {
            System.out.println("Whoops, there doesn't seem to be anything here at the moment");
        } else {
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
        String[] split = input.split(Parser.swap);
        if(split.length < 3) {
            throw new DukeException("Please mention both the indices to swap the degrees");
        } else if(split.length > 3) {
            throw new DukeException("Too many arguments");
        } else if (split.length == 3){
                String first_index = split[0];
                String second_index = split[2];
                int request = Integer.parseInt(first_index);
                String degree = list.get(request - 1);
                int request1 = Integer.parseInt(second_index);
                String degree1 = list.get(request1 - 1);
                Collections.swap(list, request - 1, request1 - 1);
                System.out.println("Swap complete!");
            try {
                dd.work(degree, Integer.toString(request1 - 1));
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                dd.work(degree1, Integer.toString(request - 1));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    /**
     * Deletes the entire degree list.
     */
    public void clear() {
        list.clear();
    }
}
