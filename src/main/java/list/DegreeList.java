package list;
import exception.DukeException;
import parser.Parser;
import task.Task;

import java.util.ArrayList;


public class DegreeList {
    private ArrayList<String> list = new ArrayList<>();


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
     * Out of Bounds checker.
     *
     * @param request int The index to be checked if it exists
     * @return boolean true if within range, false if not
     */
    private boolean isOutOfRange(int request) {
        return ((request < 0) || (request >= this.size()));
    }

    public void add_custom(String input) throws DukeException {
        try{
        list.add(input);
        System.out.print("Added ");
        System.out.print(input);
        System.out.println(" to your choices of degrees");
        }
        catch (Exception e)
        {
            throw new DukeException(e.getLocalizedMessage());
        }
    }

    public void delete(String input) throws DukeException{
        try {
            int request = Integer.parseInt(input);
            request -= 1;
            if (isOutOfRange(request)) {
                throw new DukeException("The index was not found within range");
            } else {
                System.out.println("Noted. I've removed this degree:\n"
                        + "  " + list.get(request));
                this.list.remove(request);
                System.out.println("Now you have " + this.list.size() + " degrees you are interested in.");
            }
        } catch (DukeException e) {
            throw new DukeException(e.getLocalizedMessage());
        } catch (NumberFormatException e) {
            throw new DukeException("That is NOT a valid integer");
        }
    }

    public void print() {
        if (list.size() == 0) {
            System.out.println("Whoops, there doesn't seem to be anything here at the moment");
        } else {
            for(int i = 0; i < list.size(); i++) {
                System.out.println((i+1) + ". " + list.get(i));
            }
        }
    }
}
