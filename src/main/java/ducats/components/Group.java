package ducats.components;


import java.util.ArrayList;

import ducats.DucatsException;

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

    public Group copy(Group object) throws DucatsException, IOException,ClassNotFoundException {
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

    /**
     * Returns a String representation of the group to be used in persistent storage.
     *
     * @return a storage-friendly String representation of the group
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(name).append(" ");
        for (Bar bar: bars) {
            result.append(bar).append(" ");
        }
        return result.toString();
    }
}
