package seedu.duke.task;

import seedu.duke.ui.Ui;

public class DoAfter extends Task {
	protected String description;
	protected String after;
	private Ui ui = new Ui();

	public DoAfter(String description, String after) {
		super(description);
		this.after = after;
	}
	
	/**
	 * Overrides the toString method in Task to display task type and date time.
	 *
	 * @return a string with the target info.
	 */
	public String toString() {
		return "[A]" + super.toString() + " (after: " + this.after + ")";
	}

	/**
	 * Overrides the toSaveFormat function to include task type and date time.
	 *
	 * @return a string with pipe separated info.
	 */
	public String toSaveFormat() {
		return "A|" + super.toSaveFormat() + "|" + this.after;
	}

        @Override
        public void markAsDone(TaskList list) {
                boolean cannotMarkAsDone = true;
                for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).description.equals(this.after)) {
                                if (list.get(i).isDone) {
                                        cannotMarkAsDone = false;
                                        break;
                                }
                        }
                }
                if (cannotMarkAsDone) {
                        System.out.println("        ________________________________________________________________________");
                        System.out.println("        Task cannot be marked as done! The pre-requisite task has not been done!");
                        System.out.println("        ________________________________________________________________________");
                        System.out.println();
                } else {
                        this.isDone = true;
                }
        }

	public boolean equals(DoAfter temp) {
	    if (this.description == temp.description && this.after == temp.after) {
		return true;
    	    }
	    else {
    		return false;
  	    }
	}
}
