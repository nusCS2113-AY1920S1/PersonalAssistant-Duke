//@@author maxxyx96-unused
/**
 * Code snippet from Ui that was used for AddBudgetCommand.
 */
//    /**
//     * Shows the user the amount that is added/subtracted to the existing budget.
//     *
//     * @param amount The amount that is to be added/subtracted.
//     * @param budget The existing budget of the user.
//     */
//    public void showAddBudget(float amount, float budget) {
//        if (amount > 0) {
//            out.println("     You are adding $" + amount + " to your current budget of $" + budget);
//        } else {
//            out.println("     You are subtracting $" + -amount + " from your current budget of $" + budget);
//        }
//    }

/**
 * Code snippet from Ui that was used for AddBudgetCommand, ResetBudgetCommand and ViewBudgetCommand.
 */
//    /**
//     * Shows the current budget of the user.
//     *
//     * @param amount the budget the user currently has.
//     */
//    public void showBudget(float amount) {
//        out.println("     Your budget is : $" + amount);
//    }
//

//@@author talesrune-unused
/**
 * Code snippet from Ui that was used for AddCommand.
 */
//    /**
//     * Outputs task that is added to the user.
//     *
//     * @param items The task list that contains a list of tasks.
//     */
//    public void showAdd(TaskList items) {
//        out.println("     Got it. I've added this task:");
//        out.println("       " + items.get(items.size() - Numbers.ONE.value).toString());
//        out.println("     Now you have " + items.size() + " tasks in the list.");
//    }

//@@author e0318465-unused
/**
 * Code snippet from Ui that was used for AddContactsCommand.
 */
//    /**
//     * Outputs the contact details that are most recently added.
//     *
//     * @param contactList The list of contacts.
//     */
//    public void showAddedContact(ContactList contactList) {
//        if (contactList.size() == Numbers.ZERO.value) {
//            out.println("     You have no contacts!");
//        } else {
//            out.println("     Got it, now you have " + contactList.size() + " contacts. Contact added.");
//            out.println(contactList.get(contactList.size() - Numbers.ONE.value));
//        }
//    }

//@@author talesrune-unused
/**
 * Code snippet from Ui that was used for AddNotesCommand.
 */
//    /**
//     * Outputs notes of the task that is added or updated to the user.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param index The index of the task.
//     */
//    public void showAddNotes(TaskList items, int index) {
//        out.println("     Nice! Added/Updated notes of this task ^^:");
//        out.println("       " + (index + Numbers.ONE.value) + "." + items.get(index).toString()
//        + " | Added Notes: " + items.get(index).getNotes());
//    }

//@@author talesrune-unused
/**
 * Code snippet from Ui that was used for DeleteCommand and DeleteNotesCommand.
 */
//    /**
//     * Outputs notes of the task that is added or updated to the user.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param index The index of the task.
//     * @param deletedNotes The deleted notes of the task.
//     */
//    public void showDeleteNotes(TaskList items, int index, String deletedNotes) {
//        out.println("     Deleted notes of this task ^^:");
//        out.println("       " + (index + Numbers.ONE.value) + "." + items.get(index).toString()
//        + " | Deleted notes: " + deletedNotes);
//    }

//@@author e0318465-unused
/**
 * Code snippet from Ui that was used for DeleteContactCommand.
 */
//    /**
//     * Outputs contact that is deleted, to the user through CLI.
//     *
//     * @param contactList The contact list that contains a list of contacts.
//     * @param deletedContact The contact that is deleted.
//     */
//    public void showContactDeleted(ContactList contactList, String deletedContact) {
//        out.println("     Now you have " + contactList.size() + " contact(s). I've removed this contact:");
//        out.println(deletedContact);
//    }

//@@author talesrune-unused
/**
 * Code snippet from Ui that was used for DoneCommand.
 */
//    /**
//     * Outputs task that is completed to the user.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param index The index of the task.
//     */
//    public void showDone(TaskList items, int index) {
//        out.println("     Nice! I've marked this task as done:");
//        out.println("       " + items.get(index).toString());
//    }

//@@author e0318465-unused
/**
 * Code snippet from Ui that was used for DuplicateFoundCommand.
 */
//    /**
//     * Outputs an alert when a duplicated inout is detected.
//     */
//    public void showDuplicateMsg() {
//        out.println("     The same task is already in the list!");
//    }

//@@author talesrune-unused
/**
 * Code snippet from Ui that was used for FilterCommand.
 */
//    /**
//     * Outputs the tasks that are filtered to the user.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param taskType The type of task.
//     */
//    public void showFilter(TaskList items, String taskType) {
//        out.println("     Here are the filtered tasks in your list:");
//        int numFound = Numbers.ZERO.value;
//        for (int i = Numbers.ZERO.value; i < items.size(); i++) {
//        if (taskType.equals("todo") && items.get(i) instanceof Todo) {
//            out.println("     " + (i + Numbers.ONE.value) + "." + items.get(i).toString());
//            numFound++;
//        } else if (taskType.equals("deadline") && items.get(i) instanceof Deadline) {
//            out.println("     " + (i + Numbers.ONE.value) + "." + items.get(i).toString());
//            numFound++;
//        } else if (taskType.equals("fixedduration") && items.get(i) instanceof FixedDuration) {
//            out.println("     " + (i + Numbers.ONE.value) + "." + items.get(i).toString());
//            numFound++;
//        }
//        }
//        if (numFound == Numbers.ZERO.value) {
//            out.println("     No matching tasks found.");
//        }
//    }

//@@author talesrune-unused
/**
 * Code snippet from Ui that was used for FindCommand.
 */
//    /**
//     * Outputs the tasks that are matched from the keyword to the user.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param keyword The keyword to match the tasks.
//     */
//    public void showFind(TaskList items, String keyword) {
//        out.println("     Here are the matching tasks in your list:");
//        int numFound = Numbers.ZERO.value;
//        for (int i = Numbers.ZERO.value; i < items.size(); i++) {
//            if (items.get(i).getDescription().contains(keyword)) {
//                out.println("     " + (i + Numbers.ONE.value) + "." + items.get(i).toString());
//                numFound++;
//            }
//        }
//        if (numFound == Numbers.ZERO.value) {
//            out.println("     No matching tasks found.");
//        }
//    }

//@@author e0318465-unused
/**
 * Code snippet from Ui that was used for FindContactCommand.
 */
//    /**
//     * Outputs the contacts that are matched from the keyword.
//     *
//     * @param contactList The contact list that contains a list of contacts.
//     * @param keyword The keyword to match the contacts.
//     */
//    public void showFoundContacts(ContactList contactList, String keyword) {
//        out.println("     Here are the matching contacts in your list:");
//        int numFound = Numbers.ZERO.value;
//        for (int i = Numbers.ZERO.value; i < contactList.size(); i++) {
//            String details = contactList.getOnlyDetails(i);
//            details = details.replaceAll(",", " ");
//            details = details.toLowerCase();
//            if (details.contains(keyword)) {
//                out.println("     " + contactList.getSpecificContactList(i));
//                numFound++;
//            }
//        }
//        if (numFound == Numbers.ZERO.value) {
//            out.println("     No matching tasks found.");
//        }
//    }

//@@author talesrune-unused
/**
 * Code snippet from Ui that was used for ListCommand.
 */
//    /**
//     * Outputs all the tasks of the task list to the user.
//     *
//     * @param items The task list that contains a list of tasks.
//     */
//    public void showTaskList(TaskList items) {
//        out.println("     Here are the tasks in your list:");
//        out.print(items.getList());
//    }

//@@author e0318465-unused
/**
 * Code snippet from Ui that was used for ListContactsCommand.
 */
//    /**
//     * Outputs all the contacts of the contact list to user through CLI.
//     *
//     * @param contactList The list of contacts.
//     */
//    public void showContactList(ContactList contactList) {
//        out.println("     Here are all your contacts:");
//        out.print(contactList.getFullContactList());
//    }

//@@author maxxyx96-unused
/**
 * Code snippet from Ui that was used for ResetBudgetCommand.
 */
//    /**
//     * Shows the budget that the user has before the changes.
//     *
//     * @param budget The budget of the user.
//     */
//    public void showResetBudget(float budget) {
//        out.println("     Your previous budget of $" + budget + " has been reset.");
//    }

//@@author talesrune-unused
/**
 * Code snippet from Ui that was used for ShowNotesCommand.
 */
//    /**
//     * Outputs notes of the task to the user.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param index The index of the task.
//     */
//    public void showNotes(TaskList items, int index) {
//        out.println("     Here is the task and its notes:");
//        out.println("       " + (index + Numbers.ONE.value) + "." + items.get(index).toString()
//        + " | Notes: " + items.get(index).getNotes());
//    }

//@@author talesrune-unused
/**
 * Code snippet from Ui that was used for UpdateCommand.
 */
//    /**
//     * Outputs task that is updated to the user.
//     *
//     * @param items The task list that contains a list of tasks.
//     * @param index The index of the task.
//     */
//    public void showUpdate(TaskList items, int index) {
//        out.println("     Nice! I've updated this task ^^:");
//        out.println("       " + (index + Numbers.ONE.value) + "." + items.get(index).toString());
//    }