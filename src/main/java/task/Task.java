package task;
import process.*;
/**
 * Represents a Task recorded by Duke task manager
 */
public class Task {
        protected String description;
        protected boolean isDone;
        protected String tt;
        protected String extra;
        /**
         * Creates a Task object with the given description and whether it is done
         * @param description of the task
         * @param b if a task is checked
         * @throws DukeException if an error has occured in constructing the task
         */
        public Task(String description, boolean b)  throws DukeException {
            this.tt = "";
            this.extra = "";
            this.description = description;
            this.isDone = b;
            if (description.isBlank()) {
                throw new DukeException("empty task");
            }
        }
        /**
         * Pack the Task in the form of a string with the given separator for writing to a file
         * @param sep the separator used
         * @return the save form of a task
         */
        public String save_as(String sep) {
            return tt + sep + isDone + sep + description + sep + extra + "\n";
        }
        /**
         * Mark a Task as b
         * @param b checked or not checked
         */
        public void done(boolean b) {
            this.isDone = b;
        }
        /**
         * Represents the object of this class as a string
         * @return that string
         */
        public String toString() {
            return (isDone ? "[Done] " : "[X] ") + this.description;
        }

}