package duke.user;

import duke.exceptions.DukeException;
import duke.user.gender;

import java.util.ArrayList;
import java.util.Scanner;

public class User {
    private ArrayList<Integer> weight = new ArrayList();
    private int height = 0;
    private gender sex;
    private boolean isSetup;
    private String name;

    public User() {
        this.isSetup = false;
    }

    public User(String name, int weight, int height, gender sex) {
        this.name = name;
        this.weight.add(weight);
        this.height = height;
        this.sex = sex;
        this.isSetup = true;
    }

    public void setup() throws DukeException {
        Scanner in = new Scanner(System.in);
        String name;
        int weight = 0;
        int height = 0;
        System.out.println("Input name");
        name = in.nextLine();
        try {
            System.out.println("Input weight");
            weight = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
        try {
            System.out.println("Input height");
            height = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
        System.out.println("Input gender(Male/Female)");
        String sex = in.nextLine();
        this.name = name;
        this.weight.add(weight);
        this.height = height;
        if (sex.charAt(0) == 'M') {
            this.sex = gender.MALE;
        } else {
            this.sex = gender.FEMALE;
        }
        this.isSetup = true;
    }

    public void setWeight(int weight) {
        this.weight.add(weight);
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getName() {
        return this.name;
    }

    public int getWeight() {
        return this.weight.get(this.weight.size() - 1);
    }

    public ArrayList<Integer> getAllWeight() {
        return this.weight;
    }

    public int getHeight() {
        return this.height;
    }

    public gender getSex() {
        return this.sex;
    }

    public boolean getIsSetup() {
        return this.isSetup;
    }
}