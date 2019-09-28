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
   - Handle errors and invalid inputs. [Level-5]
   - Load and save tasks to hard disk. [Level-7]
   - Identify dates and times. [Level-8]
   - delete: Delete a task. [Level-6]
   - Perform text UI testing. [A-TestUiTesting]
   - find: Find a task by searching for a keyword. [Level-9]
   - Object-oriented. [A-MoreOOP]
   - Perform JUnit testing. [A-JUnit]
   - Have comments. [A-JavaDoc]
4. Command Summary

## 1. Introduction

Duke is targeted towards restaurant chefs who wants to be able to consolidate most of the things happening in their kitchen such as recipes, ingredients, expiry dates etc.

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

### 3.1 bye: Exit the program. [Level-1]

Saves data onto local storage and exits the program 

Format: `bye`

### 3.2 list: Show all the tasks. [Level-2]

Shows all the task marked done/undone to the user 

Format: `list`

### 3.3 done: Mark a task as done. [Level-3]

marks a task in list as done.

Format

### 3.4 todo: Create a new todo task. [Level-4]

### 3.5 deadline: Create a new deadline task. [Level-4]

### 3.6 event: Create a new event task. [Level-4]

### 3.7 Handle errors and invalid inputs. [Level-5]

### 3.8 Load and save tasks to hard disk. [Level-7]

### 3.9 Identify dates and times. [Level-8]

### 3.10 delete: Delete a task. [Level-6]

### 3.11 Perform text UI testing. [A-TestUiTesting]

### 3.12 find: Find a task by searching for a keyword. [Level-9]

### 3.13 Object-oriented. [A-MoreOOP]

### 3.14 Perform JUnit testing. [A-JUnit]

### 3.15 Have comments. [A-JavaDoc]

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