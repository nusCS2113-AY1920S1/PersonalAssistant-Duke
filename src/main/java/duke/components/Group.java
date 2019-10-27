package duke.components;


import java.util.ArrayList;
import duke.components.Bar;
import java.io.ByteArrayOutputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.IOException;

public class Group implements Serializable {

    private String name;
    private ArrayList<Bar> bars;

    //@@author rohan-av

    public Group(String name, ArrayList<Bar> bars) {
        this.name = name;
        this.bars = bars;
    }
    /**
     * the method that allows this item to be copied.
     *
     * @param object the object to be copied, which in this case is group.
     */

    public Group copy(Group object) throws duke.DukeException, IOException,ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(object);
        // And then deserializing it from memory using ByteArrayOutputStream instead of FileInputStream,
        // Deserialization process will create a new object with the same state as in the serialized object.
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);
        return (Group) in.readObject();
    }

    public ArrayList<Bar> getBars() {
        return bars;
    }

    public String getName() {
        return this.name;
    }

    public int size() {
        return bars.size();
    }

    public Bar get(int i) {
        return bars.get(i);
    }

}
