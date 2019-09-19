package duke.task;

import duke.worker.Parser;

import java.util.Date;
import java.util.HashMap;

public class Task {
    public String taskName;
    public String taskDetails;
    public String detailDesc;
    public TaskType taskType;
    public Boolean isDone = false;
    protected Date datetime = null;

    /**
     * Constructor for the 'Task' Class.
     *
     * @param name Name of the task as inputted by the user
     */
    public Task(String name) {
        this.taskType = TaskType.PARENT;
        this.taskName = name.replace(this.taskType.name(), "").trim();
    }

    /**
     * Get a 'tick' or 'cross' depending on .isDone
     *
     * @return A special string that represents a tick for true or a cross for false
     */
    public String getStatusIcon() {
        if (this.isDone) {
            return "✓"; // / u2713
        } else {
            return "✘"; // / u2718
        }
    }

    /**
     * Sets a Task object's .isDone property to true.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Sets a Task object's .isDone property to false.
     */
    public void markNotDone() {
        this.isDone = false;
    }

    /**
     * Generates a String Describing the Task Object.
     * Optimized for user's reading.
     *
     * @return String detailing the Task Object, including type, isDone, taskName and taskDetails
     */
    public String genTaskDesc() {
        String generatedStr = "";
        if (!this.taskName.isEmpty()) {
            generatedStr += "["
                    + this.getStatusIcon()
                    + "]"
                    + "["
                    + this.genTypeAcronym()
                    + "] "
                    + this.taskName;
        }
        if (this.detailDesc != null || this.taskDetails != null) {
            generatedStr += " (";
            if (this.detailDesc != null && !this.detailDesc.isEmpty()) {
                generatedStr += this.detailDesc + ": ";
            }
            if (this.taskDetails != null && !this.taskDetails.isEmpty()) {
                generatedStr += this.taskDetails;
            }
            generatedStr += ")";
        }
        return generatedStr;
    }

    public String genTypeAcronym() {
        return this.taskType.name().substring(0, 1);
    }

    /**
     * Records the details from the user input into this Task object.
     * @param userInput The input taken from the user in CLI
     */
    public void recordTaskDetails(String userInput) {
        userInput = Parser.removeStr(this.taskType.name(), userInput);
        this.taskName = userInput;
        int indexBackslash = userInput.indexOf('/');
        //Check if '/' exists
        if (indexBackslash >= 0) {
            int indexMsg = userInput.indexOf(' ', indexBackslash);
            if (indexMsg >= 0) {
                this.detailDesc = userInput.substring(indexBackslash + 1, indexMsg);
            }
            String[] splitDetails = userInput.split('/' + this.detailDesc, 2);
            this.taskName = splitDetails[0].trim();
            if (splitDetails.length > 1) {
                this.taskDetails = splitDetails[1].trim();
            }
        }
    }

    public void setDatetime(Date datetime){
        this.datetime = datetime;
    }

    public Date getDatetime(){
        return this.datetime;
    }





}



