# Restaurant Manager - User Guide

1.  Introduction
2. Quick Start
3. Features
   - bye: Exit the program
   - list: Show all the tasks
   - done: Mark a task as done
   - todo: Create a new todo task
   - deadline: Create a new deadline task
   - event: Create a new event task
   - Load and save tasks to hard disk
   - Identify dates and times
   - delete: Delete a task
   - find: Find a task by searching for a keyword. 
   - Error Handling 
4. Command Summary
5.  FAQ

## 1. Introduction

Duke is targeted towards restaurant chefs who wants to be able to consolidate most of the things happening in their kitchen such as recipes, ingredients, expiry dates etc. By using this product, you are able to order all the ingredients needed for your kitchen. Additionally, this application takes in customers order/preorder of the restaurants dishes. The customers are able to give their feedback/rating of the dishes. the average rating of all the dishes can be viewed by the restaurant chef. Proceed to the Developer Guide [here]( https://github.com/AY1920S1-CS2113-T14-2/main/blob/master/docs/DeveloperGuide.md ) to learn more about this application. 



## 2. Quick Start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest Duke.jar [here](https://github.com/AY1920S1-CS2113-T14-2/main/releases).

3. Copy the file to the folder you want to use as the home folder for your Duke application.

4. Double-click the file to start the app. The GUI should appear in a few seconds.

   ![UI]( https://github.com/AY1920S1-CS2113-T14-2/main1/blob/master/docs/images/Ui.png )

5. Type into the INPUT box some commands and press ENTER to execute

6. Some example commands

   1. **list**: lists out all the tasks
   2. **deadline** prepare new recipe **/by** 1/1/2019: adds a deadline task "prepare new recipe" into your list by "1/1/2019"
   3. **bye**: exits the program

7. Refer to Section 4 for the full list of commands

## 3. Features

**command format**

- commands are in `UPPER_CASE` are to be provided by the user eg. `todo d/DESC`, `DESC` is a parameter which can be used as `todo d/buy groceries`
-

### 3.1 bye: Exits the program

outputs an exit message to the user and terminates the program

Format: `bye` 

### 3.2 list: Show all the tasks

Shows all the task marked done/undone to the user

Format: `list`

shows the user all the tasks that is stored in Duke

	 Here are the tasks in your list:
	 1.[D][✓] return book(by: Monday)
	 2.[E][✘] meeting(at: U-Town)
	 3.[P][✓] lecture(from: 1600 to: 1800)
### 3.3 done: Mark a task as done

marks a task in list as done.

Format: `done` <indx>

eg. `done 1`

	 Nice! I've marked this task as done:
	 [D][✓] return book(by: Monday)
### 3.4 todo: Create a new todo task

creates  a new task, todo where user enters the description of the task that needs to be done

Format: `todo` <desc>

eg. `todo order eggs`

	 Got it. I've added this task:
	 [T][✘] order eggs
	 Now you have 6 tasks in the list.
### 3.5 deadline: Create a new deadline task

creates a new task, deadline, where user enters their tasks as well the date that the task needs to be done

Format: `deadline` <desc> `/by` <desc>

eg. `deadline submit review /by 1/1/2019`, stores D|0|submit review|1/1/2019 

output:

	 Got it. I've added this task:
	 [D][✘ ] submit review(by: 1/1/2019)
	 Now you have 5 tasks in the list.
### 3.6 event: Create a new event task

creates a new task, event, where user enters their tasks as well the date that the task needs to be done

Format: `event`<desc> `/at` <desc>

eg. `event birthday /at multi purpose hall`, stores E|0|submit review|multi purpose hall 

output:

	 Got it. I've added this task:
	 [E][✘] birthday(at: multi purpose hall)
	 Now you have 4 tasks in the list.


### 3.10 delete: Delete a task

deletes a tasks that was stored in Duke regardless of it being marked as done or undone.

Format: `delete` <indx>

eg. `delete 1`, deletes the first task that is stored in Duke

output:

	 Noted. I've removed this task:
	 [T][✓] read book
	 Now you have 3 tasks in the list.
### 3.12 find: Find a task by searching for a keyword

finds a task in Duke which contains a specific word or description

Format: `find` <desc>

eg. `find eggs`, iterates through all the task in Duke and if any of the tasks contains the description,

output:

	 	Here are the matching tasks in your list:
	 1.[T][✘] stock up on eggs
	 2.[D][✘] buy eggs(by: Monday)
	 3.[T][✘] create a new egg sandwich
### 3.13 Error Handling 

handles unexpected commands from the user such as unknown/incomplete command. if user enters an invalid command, the application will output a message that corresponds to what the user entered wrongly.

eg. `deadline` <empty desc>, `deadline` <desc> `by` <empty desc>, `delete` <empty indx>

outputs:

```
	 ____________________________________________________________
	 ☹ OOPS!!! The description cannot be empty.
	 ____________________________________________________________

```

### 3.14 stats: gives the statistics of the Dish

### 3.15 order: creates a new order 

### 3.16 preorder: 

### 3.17 help: shows a list of commands to the user 

things to include in version 2:

...

## 4. Command Summary

Index | Keyword  | Usage
----- | -------- | ----------
1     | bye      | bye
2     | list     | list
3     | done     | done <indx> 
4     | todo     | todo <desc> 
5     | deadline | deadline <desc> /by <date> 
6     | event    | event <desc> /at <date> 
7     | delete   | delete <desc> 
8     | find     | find <desc> 
9 | remind | remind
10 | stats | stats 
11 | order | order <desc> <index> 
12 | preorder | preorder <desc> <indx> 
13 | cancel | cancel <indx> 
14 | help | help 

# 5. FAQ

Q: how do I transfer data to another computer 

A: install the application on the other computer and an empty recipe.txt will be created under the data folder. Replace this file with the same file found in your previous computer. therefore your data will be transferred  

Q:

A: