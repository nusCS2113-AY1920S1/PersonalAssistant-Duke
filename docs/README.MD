# Chronologer User Guide

## 1. Introduction
Chronologer is a task manager designed for students to handle convoluted and unclear management of module information frequently faced throughout the semester. It is optimized for those who are familiar with Command Line Interfaces(CLI) and even has a GUI that displays clear and intuitive information.
With Chronologer, students no longer have to fret about lagging behind in their work and continue to be on top of their taskings.

## 2. Quickstart
1. Ensure you have Java 11 installed on your computer.

2. Download the latest Chronologer.jar file here.

3. Copy the file to a home folder you want to use as the main directory.

4. Double-click the file to start the app. (The GUI should load in a few seconds.) 

5. Type the command in the command box and press the ENTER key to execute that command.

6. Refer to the features section for details of each command.

## 3. Features 
#### Command Format

Command format:
- Words enclosed by <> brackets are the parameters to be provided by the user.
- Inputs enclosed by these [ ] are optional fields.
- Each command is not case sensitive.


### 3.0 Listing all tasks : `list`

Shows a list of all tasks in the task scheduler. <br />
Format: `list`


## 3.1 Adding and deleting task commands
### 3.1.1) Adding a deadline task : `deadline`
Adds a deadline task to the task manager on specific date and time.<br />
Format: `<deadline> <description> </by> <date> <time>`
- Time must be in 24 hour format (dd/mm/yyyy)
- /by flag is required to separate the date-time components from the deadline description
- Chronologer will detect clashes with another deadline at the same time slot.

Examples:<br />
`deadline CS2113 homework /by 29/9/2019 1900`<br />
`deadline pay bills /by 05/06/2019 0800`

### 3.1.2) Adding an event task: `event`
Adds an event task to the task manager on specific date and time.<br />
Format: `<event> <description></at> <start_datetime - end_datetime>`
- Time must be in 24 hour format (dd/mm/yyyy)
- /at flag is required to separate the date-time components from the event description
- Chronologer will detect clashes with another event around the same time range.

Examples:<br />
`event carnival /at 29/9/2019 1900 - 30/9/2019 1600`<br />
`event graduation /at 08/09/2019 1500 - 09/09/2019 1700`

### 3.1.3) Adding a dateless todo task: todo
Adds a todo task to the task manager.<br />
Format: `<todo> <description>`
- No task will clash with a todo due to its less strict nature in real life.

Examples:<br />
`Todo homework`

### 3.1.4) Adding a todo task with duration: todo
Adds a todo task with duration to the task manager.<br />
Format: `<todo> <description></for> <duration>`
- Time duration is in hours.

Examples:<br />
`Todo homework /for 4`

### 3.1.5) Adding a todo task with period: todo
Adds a todo task with period to the task manager.<br />
Format: `<todo> <description></between> <start_datetime - end_datetime>`
- Time must be in 24 hour format (dd/mm/yyyy)
- /between flag is required to separate the date-time components from the Todo description.

Examples:<br />
`Todo homework /between 29/9/2019 1900 - 30/9/2019 1600`
 
### 3.1.6) Adding an assignment: assignment
Adds an assignment task to the task manager list.<br />
Format: `assignment </m> <module code of assignment> </by> <end_datetime>`
- Time must be in 24 hour format (dd/mm/yyyy HHmm)
- Module code must be given in one word

Example:<br />
`Assignment /m 2040c /by 28/10/2019 2000`


### 3.1.7) Adding an examination: exam
Adds an examination task to the task manager list.<br />
Format: `exam </m> <module code of examination> </at> <start_datetime - end_datetime>`
- Time must be in 24 hour format (dd/mm/yyyy HHmm)
- Module code must be given in one word

Example:<br />
`exam /m 2040c /by 28/10/2019 1000 - 28/10/2019 1200`

### 3.1.8) Deleting a task: delete
Deletes a task from the task manager list.<br />
Format: `delete <list number where task is located>`<br />
Deletes the task at the specified list no.
- If there are no tasks on that list no, Chronologer will inform the user.
- List no must be within the range of the task manager current list.
- List no must be an Integer and positive.

Example:<br />
`delete 2`<br />
`Delete 3`


## 3.2 Postpone commands
### 3.2.1) Postpone a deadline task : `postpone`
Postpone a deadline to another timeslot.(Applies to assignments and exams)<br />
Format: `postpone <Index> <Date> <Time>
- Time must be in 24 hour format (dd/mm/yyyy)
- List no must be a positive integer not greater than the total number of tasks in the task manager.
- Chronologer will detect clashes with another deadline at the same time slot.
- Time postponed must be later than the current one.

Examples:<br />
`postpone 2 13/12/2019 1900`<br />

### 3.2.2) Postpone an event task : `postpone`
Postpone an event to another timeslot.(Applies to lectures and tutorials)<br />
Format: `postpone <Index> <start_datetime - end_datetime>
- Time must be in 24 hour format (dd/mm/yyyy)
- List no must be a positive integer not greater than the total number of tasks in the task manager.
- Chronologer will detect clashes with another event at the same time slot.
- Time postponed must be later than the current one.

Examples:<br />
`postpone 4 25/11/2019 0800 - 26/11/2019 1900`<br />

### 3.2.3) Postpone a todo with period task : `postpone`
Postpone a todo task with period to another time range<br />
Format: `postpone <Index> <start_datetime - end_datetime>
- Time must be in 24 hour format (dd/mm/yyyy)
- List no must be a positive integer not greater than the total number of tasks in the task manager.
- Todo tasks won't clash with others.
- Time postponed must be later than the current one.

Examples:<br />
`postpone 2 24/06/2019 1900 - 25/06/2019 1900`<br />

## 3.3 Searching for a free timeslot: 'search'

Finds a timeslot that spans the specified period.
Format: `search PERIOD UNIT_OF_TIME`

- `PERIOD` must be a positive integer.
- `UNIT_OF_TIME` can be `minutes`, `hours` or `days`.

Examples:

* `search 20 hours`
* `search 2 days`

Expected outcome:

`You can schedule something after [E][X] Dinner (at: 24/09/2019 1800 - 24/09/2019 1930)`

## 3.4 Undo and Redo commands
### 3.4.1) Undo: 'undo'

Any changes made to the tasks, such as adding and deleting will be undone and the task manager will revert to a previous state.
Format: `undo`
- Simply use normal short-cut of ctrl+z to perform an undo. (*v1.4)*
- If there are no more undo commands possible, the user will be notified.

Expected outcome:

`undo was successful`

### 3.4.2) Redo: 'redo'

Any changes made to the tasks by an undo command, will be reversed and reverted back to the state before the undo command was executed.
Format: `undo`
- Simply use normal short-cut of ctrl+z to perform an undo. (*v1.4)*
- If there are no more redo commands possible, the user will be notified.

Expected outcome:

`redo was successful`

### 3.4.3) History feature: 'undo'

The last 5 changes from a particular usage will be stored into persistent storage, to allow the user to undo from launch of Chronologer.
Format: `undo`
- Simply use normal short-cut of ctrl+z to perform an undo. *(v1.4)*
- If there are no more redo commands possible, the user will be notified.

Expected outcome:

`undo was successful.`

### 3.4.4) *(v1.4)* The History (version storage): 'save state'

This allows the user to store 3 versions of the task manager at any one time.
Format: `save state`
If there are no states saved, or 3 versions already saved, the user will be notified.
The last 5 changes from a particular usage will be stored into persistent storage, to allow the user to undo from launch of Chronologer.

Expected outcome:

`state was saved successfully`

### 3.4.5) *(v1.4)* The History (version storage): 'change state'

This allows the user to change to any of 3 versions of the task manager stored on the system and use it.
Format: `change to state 3`
If there are no states saved, or 3 versions already saved, the user will be notified.

Expected outcome:

`changed to state 3.`
