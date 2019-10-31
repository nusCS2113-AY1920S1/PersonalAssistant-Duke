# Restaurant Manager - User Guide

1.  Introduction
2. Quick Start
3. Features
   - Menu
   - Adding Dish
   - Adding Ingredient to Dish
   - List all Dish
   - Initialize Dish
   - Removing Dish
   - Load and save tasks to hard disk
   - Identify dates and times
   - delete: Delete a task
   - find: Find a task by searching for a keyword. 
   - Error Handling 
4. Command Summary
5.  FAQ

## 1. Introduction

Duke is targeted towards restaurant chefs who wants to be able to consolidate most of the things happening in their kitchen such as recipes, ingredients, expiry dates etc. By using this product, you are able to order all the ingredients needed for your kitchen. Additionally, this application takes in customers order/preorder of the restaurants dishes. Proceed to the Developer Guide [here]( https://github.com/AY1920S1-CS2113-T14-2/main/blob/master/docs/DeveloperGuide.md ) to learn more about this application. 



## 2. Quick Start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest Duke.jar [here](https://github.com/AY1920S1-CS2113-T14-2/main/releases).

3. Copy the file to the folder you want to use as the home folder for your Duke application.

4. Use the command prompt and navigate to the path where the application is downloaded

5. run the command `java -jar v1.3` , application will then be executed 

   ![UI]( https://github.com/AY1920S1-CS2113-T14-2/main1/blob/master/docs/images/Ui.png )

6. Type some commands and press ENTER to execute

7. Some example commands

   1. given various options in menu, user can type these commands`option` ,`q`, `a`, `b`, `c`, `d`.
   2. user enters `d` then `add chicken rice`, dish is then added to the list
   3. user enters `d` then `list`, outputs the the dishes and the ingredients associated to the dish in table form

8. Refer to Section 4 for the full list of commands

## 3. Features

**command format**

- commands are in `UPPER_CASE` are to be provided by the user eg. `add/DESC`, `DESC` is a parameter which can be used as `add noodle`.

### 3.1 Main Menu

the user is greeted depending on the time of day, `good morning` , `good evening` etc. Upon start up, if there are any expired ingredients in the fridge, the application will prompt the user to clear the expired ingredients. In the Main menu, the user is given various options to enter:

- `q` , **exits** the application
- `a`, remove the **expired** ingredients 
- `b` , proceed to **fridge** commands
- `c`, proceeds to **order** commands 
- `d`, proceeds to **dish** commands

### 3.2 Adding Dish: `add`

user needs to enter `d` in main menu first.

To add a dish to the DishList, user needs to execute command below:

Format: `add d/DESC`

if the dish already exist in the list, message is output:

```
         dish already exist in list
```

Examples: 

- `add chicken rice`
- ``add tom yum noodles`

```
         _________________________________________________________________________________________
         you have added the following dish:
         chicken rice
         _________________________________________________________________________________________
```



### 3.3 Adding Ingredient to Dish: `ingredient`

user needs to enter `d` in main menu first.

Executing this command associates an ingredient to a certain Dish. user needs to execute the command below:

Format: `ingredient d/DESC n/AMOUNT i/INDEX`

Examples:

- `ingredient rice 50 1`, ingredient rice (50g) is now associated to 1st dish
- `ingredient noodle 50 2` ingredient noodle (50g) is now associated to 2nd dish

```
         _________________________________________________________________________________________
         ingredient: rice
         added to: chicken rice
         _________________________________________________________________________________________
```



### 3.4 List all Dishes: `list`

user needs to enter `d` in main menu first.

user needs to enter the command below:

Format:`list`

if there are no dishes in list, output message:

```
        OOPS!!! No Dishes yet!.
        You can type:
        'template' to see the format of the commands,
        'back' to see all your options,
        'q' to exit
```

if user enters a valid command,

```
   ____________________________
   | Dish          | ingredient|
   |===========================|
1. | chicken rice  |           |
2. | chicken noodle|           |
3. | pizza         |           |
4. | aglio olio    |           |
```



```
   _______________________________
   | Dish          | ingredient   |
   |==============================|
1. | chicken rice  | rice,salt,   |
2. | chicken noodle| noodle,flour,|
```



### 3.5 Initializing the Dish List:`initialize`

user needs to enter `d` in main menu first.

user needs to enter the command below:

Format: `initialize`

user is then asked to confirm as this command deletes all the entries in the dish

if user enters yes,

```
         are you sure you want to clear list? (yes or no)
yes
         LIST IS CLEARED
```

if user enters no,

```
         are you sure you want to clear list? (yes or no)
no
         LIST IS NOT CLEARED
```

### 3.6 Removing Dish:`remove`

user needs to enter `d` in main menu first.

user needs to enter the command below:

Format: `remove i/INDEX`

Examples:

- `remove 1`
- `remove 2`

if user enters an invalid index, a error message will appear

```
        OOPS!!! dish does not exist.
        You can type:
        'template' to see the format of the commands,
        'back' to see all your options,
        'q' to exit
         _________________________________________________________________________________________
```

if user enters a valid command,

```
         _________________________________________________________________________________________
         The following dish have been removed:
         chicken rice
         _________________________________________________________________________________________
```

### 3.7 Error Handling 

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
1 | back | back 
2 | template | template 
 |  |                                    
 |            |  
15 | add | dishadd <desc> 
16 | remove | remove <indx> 
17 | list | list 
18 | ingredient | ingredient <desc> <amount> <index> 

# 5. FAQ

Q: how do I transfer data to another computer 

A: install the application on the other computer and an empty recipe.txt will be created under the data folder. Replace this file with the same file found in your previous computer. therefore your data will be transferred  
