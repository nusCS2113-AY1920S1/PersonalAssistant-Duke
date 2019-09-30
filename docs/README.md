# Restaurant Manager - User Guide

1.  Introduction
2. Quick Start
3. Features
   - bye: Exit the program. [Level-1]
   - list: Show all the tasks. [Level-2]
   - done: Mark a task as done. [Level-3]
   - todo: Create a new todo task. [Level-4]
   - deadline: Create a new deadline task. [Level-4]
   - event: Create a new event task. [Level-4]
   - Load and save tasks to hard disk. [Level-7]
   - Identify dates and times. [Level-8]
   - delete: Delete a task. [Level-6]
   - find: Find a task by searching for a keyword. [Level-9]
4. Command Summary

## 1. Introduction

Duke is targeted towards restaurant chefs who wants to be able to consolidate most of the things happening in their kitchen such as recipes, ingredients, expiry dates etc. By using this product, you are able to order all the ingredients needed for your kitchen. Additionally, you can try out new recipes and save it in Duke. you can also view customer feedback.

## 2. Quick Start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest Duke.jar [here](https://github.com/AY1920S1-CS2113-T14-2/main/releases).

3. Copy the file to the folder you want to use as the home folder for your Duke application.

4. Double-click the file to start the app. The GUI should appear in a few seconds.

   ![UI](https://github.com/9hafidz6/main/blob/master/docs/images/UI.png)

5. Type into the INPUT box some commands and press ENTER to execute

6. Some example commands

   1. **list**: lists out all the tasks
   2. **deadline** prepare new recipe **/by** 1/1/2019: adds a deadline task "prepare new recipe" into your list by "1/1/2019"
   3. **bye**: exits the program

7. Refer to Section 4 for the full list of features

## 3. Features

**command format**

- commands are in `UPPER_CASE` are to be provided by the user eg. `todo d/DESC`, `DESC` is a parameter which can be used as `todo d/buy groceries`
-

### 3.1 bye: Exit the program. [Level-1]

outputs an exit message to the user and terminates the program

Format: `bye` 

### 3.2 list: Show all the tasks. [Level-2]

Shows all the task marked done/undone to the user

Format: `list`

shows the user all the tasks that is stored in Duke

	 Here are the tasks in your list:
	 1.[D][✓] return book(by: Monday)
	 2.[E][✘] meeting(at: U-Town)
	 3.[P][✓] lecture(from: 1600 to: 1800)
### 3.3 done: Mark a task as done. [Level-3]

marks a task in list as done.

Format: `done` <indx>

eg. `done 1`, outputs: [ T ] [ ✓ ] return book

	 Nice! I've marked this task as done:
	 [D][✓] return book(by: Monday)
### 3.4 todo: Create a new todo task. [Level-4]

creates  a new task, todo where user enters the description of the task that needs to be done

Format: `todo` <desc>

eg. `todo order eggs`, stores T|0|order eggs, outputs [ T ] [ ✘ ] order eggs

	 Got it. I've added this task:
	 [T][✘] order eggs
	 Now you have 6 tasks in the list.
### 3.5 deadline: Create a new deadline task. [Level-4]

creates a new task, deadline, where user enters their tasks as well the date that the task needs to be done

Format: `deadline` <desc> `/by` <desc>

eg. `deadline submit review /by 1/1/2019`, stores D|0|submit review|1/1/2019 , outputs [ D ] [ ✘ ] submit review (/by: 1/1/2019)

	 Got it. I've added this task:
	 [D][✘ ] submit review(by: 1/1/2019)
	 Now you have 5 tasks in the list.
### 3.6 event: Create a new event task. [Level-4]

creates a new task, event, where user enters their tasks as well the date that the task needs to be done

Format: `event`<desc> `/at` <desc>

eg. `event birthday /at multi purpose hall`, stores E|0|submit review|multi purpose hall , outputs       [ED ] [ ✘ ] birthday (/at: multi purpose hall)

	 Got it. I've added this task:
	 [E][✘] birthday(at: multi purpose hall)
	 Now you have 4 tasks in the list.
### 3.8 Load and save tasks to hard disk. [Level-7]

### 3.9 Identify dates and times. [Level-8]

### 3.10 delete: Delete a task. [Level-6]

deletes a tasks that was stored in Duke regardless of it being marked as done or undone.

Format: `delete` <indx>

eg. `delete 1`, deletes the first task that is stored in Duke, if task does not exist, output an error message to user `Enter a valid task number after done, between 1 and x` (x is the total number of tasks in Duke)

	 Noted. I've removed this task:
	 [T][✓] read book
	 Now you have 3 tasks in the list.
### 3.12 find: Find a task by searching for a keyword. [Level-9]

finds a task in Duke which contains a specific word or description

Format: `find` <desc>

eg. `find eggs`, iterates through all the task in Duke and if any of the tasks contains the description, output the task to the user

	 	Here are the matching tasks in your list:
	 1.[T][✘] stock up on eggs
	 2.[D][✘] buy eggs(by: Monday)
	 3.[T][✘] create a new egg sandwich
if there are no matches, outputs `No matching tasks found!`

## 4. Command Summary

Index | Keyword  | Usage
----- | -------- | ----------
1     | bye      | bye
2     | list     | list
3     | done     | done \<indx\>
4     | todo     | todo \<desc\>
5     | deadline | deadline \<desc\> /by \<date\>
6     | event    | event \<desc\> /at \<date\>
7     | delete   | delete \<indx\>
8     | find     | find \<desc\>
9 | remind | remind
