# User Guide

* [1. Introduction](#1-introduction)
* [2. Features](#2-features)
  + [Creating a task `add task`](#creating-a-task--add-task-)
  + [Creating a patient profile `add patient`](#creating-a-patient-profile--add-patient-)
  + [Assigning a patient to a deadline task: `assign deadline task`](#assigning-a-patient-to-a-deadline-task---assign-deadline-task-)
  + [Assigning a patient to a period task: `assign period task`](#assigning-a-patient-to-a-period-task---assign-period-task-)
  + [Viewing a list of patients: `list patients`](#viewing-a-list-of-patients---list-patients-)
  + [Viewing a list of tasks: `list tasks`](#viewing-a-list-of-tasks---list-tasks-)
  + [Removing a patient from the patient list: `delete patient`](#removing-a-patient-from-the-patient-list---delete-patient-)
  + [Removing a task from the task list: `delete task`](#removing-a-task-from-the-task-list---delete-task-)
  + [Removing an assigned task from a patient: `delete assigned task`](#removing-an-assigned-task-from-a-patient---delete-assigned-task-)
  + [Finding a patient from the patient list: `find patient`](#finding-a-patient-from-the-patient-list---find-patient-)
  + [Finding a task from the task list: `find task`](#finding-a-task-from-the-task-list---find-task-)
  + [Finding a list of assigned tasks for a patient: `find assigned tasks`](#finding-a-list-of-assigned-tasks-for-a-patient---find-assigned-tasks-)
  + [Updating a patient’s information: `update patient`](#updating-a-patient-s-information---update-patient-)
  + [Updating a task’s description: `update task`](#updating-a-task-s-description---update-task-)
  + [Undo the previous action: `undo`](#undo-the-previous-action---undo-)
  + [Filtering data](#filtering-data)
  + [Show upcoming tasks for the week: `show upcoming tasks`](#show-upcoming-tasks-for-the-week---show-upcoming-tasks-)
  + [View help guide: `help`](#view-help-guide---help-)
  + [Typo-correction](#typo-correction)
  + [Display pie chart or bar chart](#display-pie-chart-or-bar-chart)
  + [Saving the data](#saving-the-data)
  + [Exiting the program: `bye`](#exiting-the-program---bye-)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>

## 1. Introduction

Dukpital is intended for nurses who wish to keep their workload organized and monitored through an easy-to-access application. When handling many tasks serving a wide variety of patients, 

Dukpital is valuable in providing support in tracking tasks specific to each of the patients a nurse may be caring for. It is best optimized through the CLI (Command Line Interface), though users have the benefit of access to a GUI (Graphical User Interface) as well, if they prefer it.

## 2. Features

### Creating a task `add task`

Creates and adds a task to the user’s task list with a user-specified description.

Format: `add task :TASK_DESCRIPTION`

### Creating a patient profile `add patient`

Creates and adds a patient profile to the user’s patient list with user-specified name, NRIC, remark, and room fields.

Format: `add patient :PATIENT_NAME :NRIC :ROOM :REMARK`

### Assigning a patient to a deadline task: `assign deadline task`

Designates patients that are relevant to a deadline task with a user-specified time.

Format: `assign deadline task :#TASK_ID :#PATIENT_ID :<dd/MM/yyyy HHmm> `

### Assigning a patient to a period task: `assign period task`

Designates patients that are relevant to a period task with a user-specified start and end time.

Format: `assign period task :#TASK_ID :#PATIENT_ID :<dd/MM/yyyy HHmm> :<dd/MM/yyyy HHmm>`

### Viewing a list of patients: `list patients` 

Generates a list of patients currently hospitalized and under the user’s care.

Format: `list patients`

### Viewing a list of tasks: `list tasks`

Generates a list of tasks created by the user.

Format: `list tasks`

### Removing a patient from the patient list: `delete patient`

Removes a patient from the user’s list of patients. Can input either the patient’s ID number, or their name.

Format: `delete patient :#PATIENT_ID`

### Removing a task from the task list: `delete task`

Removes a task from the user’s list of tasks. Can input either the task’s ID number, or its description.

Format: `delete task :#TASK_ID`

### Removing an assigned task from a patient: `delete assigned task`

Removes an assigned task from a specific patient’s list of assigned tasks. User provides the to-be-deleted assigned task’s unique ID.

Format: `delete assigned task :#ASSIGNED_TASK_UNIQUE_ID`

### Finding a patient from the patient list: `find patient`

Finds a patient within the user’s patient list by using the user’s input. Can input either the patient’s ID number, or their name.

Format: `find patient :#PATIENT_ID` or `find patient :PATIENT_NAME`

### Finding a task from the task list: `find task`

Finds a task within the user’s task list by using the user’s input. Can input either the task’s ID number, or its description.

Format: `find task :#TASK_ID` or `find task :TASK_NAME`

### Finding a list of assigned tasks for a patient: `find assigned tasks`

Finds list of tasks associated with a specific patient within the user’s task list by using the user’s input. Can only input the patient’s ID number.

Format: `find assigned tasks :#PATIENT_ID` (Note that the Patient ID is required here, rather than the assigned task Unique ID, or Task ID.)

### Updating a patient’s information: `update patient`

Changes a user-specified field of data within a saved patient profile.

Format: `update patient :#PATIENT_ID :name/NRIC/room/remark :NEW_INFO`

### Updating a task’s description: `update task`

Changes the description of a saved task.Format: `update task :#TASK_ID :description :NEW_DESCRIPTION`

(GUI will be implemented in v2.0.)

### Undo the previous action: `undo`

Regresses the data will roll back to its state prior to the ‘undone’ action.

Format: `undo`

### Filtering data

It supports filtering of row in table. It will display the rows of data with respective keyword in columns.

Format:  `filter: <content> `	

Format: `clear filter `

### Show upcoming tasks for the week: `show upcoming tasks`

Displays the upcoming week’s assigned tasks. If an assigned task has been scheduled for a date within the upcoming week, it will be displayed underneath its scheduled date.
The user can view the list of the week’s upcoming tasks in the CLI.

Format: `show upcoming tasks`
The user can switch to the GUI display of the assigned tasks for today’s date.

Format: `show today`
The user can switch to the GUI display of the assigned tasks for tomorrow’s date.

Format: `show tomorrow`

### View help guide: `help`

Displays a comprehensive list of the commands and their respective CLI formats to the user. 

The user can view the list of available commands and their CLI formats in the CLI.Format: `help`

The user can switch to the GUI display of the Help Guide to view Dukepital’s list of commands.Format: `show help guide`

### Typo-correction

Duke will check for typo in command keyword. Note that only typo in keyword, such as `list tasks`, `bye`, `add patient` will be checked. Not all invalid command with typo will be corrected. Only invalid command with less than 20% variance of a valid command will be considered as typo.

For example, command “b ye” will be detected as “bye”, as user can enter Y to proceed with corrected typo. Invalid command such as`listttt taskkk` will not be corrected to `list task`. This invalid command will be executed, and receive an error response with a 

### Display pie chart or bar chart

Displays the command statistics in pie chart or bar chart.

**Note:** bye, undo, help, piechart, barchart command will not be recorded.

Format: `piechart` or `barchart`

### Saving the data

After the user submits commands that change their task list, patient list, or relation between tasks and patients, data is saved onto the hard disk automatically. This data can be accessed during later sessions.

### Exiting the program: `bye`

Closes the program when the user submits the proper command.

Format: `bye`

