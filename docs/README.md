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


### Listing all tasks : `list`

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


### Postponing a task : `postpone`

Edits an existing task in the task scheduler to a different date. <br />
Format: `postpone INDEX DATE_TIME`

- Edits the task at the specified `INDEX`. The index refers to the index number shown in the task list. `INDEX` must be a positive integer.


Examples:

* `postpone 1 25/09/2019 1500`
* `postpone 5 27/09/2019 0900 - 03/10/2019 2359`

### Searching for a free timeslot: 'search'

Finds a timeslot that spans the specified period.
Format: `search PERIOD UNIT_OF_TIME`

- `PERIOD` must be a positive integer.
- `UNIT_OF_TIME` can be `minutes`, `hours` or `days`.

Examples:

* `search 20 hours`
* `search 2 days`

Expected outcome:

`You can schedule something after [E][X] Dinner (at: 24/09/2019 1800 - 24/09/2019 1930)`
