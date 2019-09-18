# User Guide

## Introduction
Our project aims to develope a task tracking application that monitors and encourages productivity. The application will be easy to use, with intuitive ways of adding, finding and tracking tasks that have been added. This easy convenience is definitely helpful in our often busy lives, so give this app a chance to help you!

This user guide aims to help you learn your way around our app, making the learning process smooth and effortless. So what are you waiting for? Let's go!

## Features 

Command Format
- Words in `UPPER_CASE` are the parameters to be supplied by the user e.g. in `todo DESCRIPTION`,
  `DESCRIPTION` is a parameter which can be used as `todo read book`.

### 3.1 Adding a task : `todo/deadline/event`
Adds a todo/deadline/event to the tasklist.

Format: `todo DESCRIPTION`  
        `deadline DESCRIPTION /by dd/mm/yyyy hhmm`  
        `event DESCRIPTION /at dd/mm/yyyy hhmm`  
        
  - `DESCRIPTION` is the description of the task.
  - `dd/mm/yyyy hhmm` is the day/month/year and time (24hrs) of the task e.g. `9/9/2019 1200`
  
Examples:
  - `todo read book`
  - `deadline homework /by 8/8/2019 2359`
  - `event team meeting /at 20/8/2019 1500`

### 3.2 Deleting a task : `delete`
Deletes the specified task from the task list.

Format: `delete INDEX`
  - Deletes the task at the spcified `INDEX`.
  - The index refers to the index shown in the displayed task list.
  - The index must be a positive integer 1, 2, 3, ...
  
Examples:
  - `list`  
    `delete 2`  
    Deletes the 2nd task in the task list.
  
### 3.3 Finding tasks : `find`
Finds tasks whose description contains the given keyword.

Format: `find KEYWORD`
  - The search is case sensitive. e.g. `Book` will not match `book`
  - Only the description is searched.
  - Only full words will be matched e.g. `books` will not match `book`
  
Examples:
  - `find book`  
  Returns any task with the descriptions containing the keyword.


### 3.4 Marking a task as done : `done`
Marks a task in the task list as done.

Format: `done INDEX`
  - Marks the task as done at the specified `INDEX`
  - The index refers to the index shown in the displayed task list.
  
Examples:
  - `done 1`  
  Marks the 1st task in the task list as done.

### 3.5 Listing all the tasks : `list`
Shows a list of all the task in the task list.

Format: `list`

### 3.6 Snoozing a task : `snooze`
Provides a way to easily snooze/postpone/reschedule a task.

Format: `snooze INDEX`  
        `NUMBER min/hour/day/week/month`
  - Snoozes the task as done at the specified `INDEX`
  - The index refers to the index shown in the displayed task list.
  - `NUMBER` refers to the quantity of the `min/hour/day/week/month`
  
Examples:  
  - `snooze 1`  
    `1 week`  
    Snoozes the 1st task in the task list by 1 week.
  
  
### 3.7 Exiting the program : `bye`
Exits the program.

Format: `bye`

### 3.8 Saving the data
Save the tasks in the hard disk automatically whenever the task list changes.
There is no need to save manually.

## Usage

### `Keyword` - Describe action

Describe action and its outcome.

Example of usage: 

`keyword (optional arguments)`

Expected outcome:

`outcome`
