package seedu.hustler.task.variables;

public class Difficulty {

     enum type {
        L,
        M,
        H
    }

    private type difficulty;

    public Difficulty(String difficulty) {
         switch (difficulty.toLowerCase()) {
             case "l":
             case "low":
             this.difficulty = type.L;
             break;
             case "m":
             case "medium":
             default:
             this.difficulty = type.M;
             break;
             case "h":
             case "high":
             this.difficulty = type.H;
        }
    }

    public String toString() {
        return "[" + this.difficulty.toString() + "]";
    }

}
