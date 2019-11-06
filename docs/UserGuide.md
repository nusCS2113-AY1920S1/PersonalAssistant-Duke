# Chef Duke - User Guide

1.  Introduction
2. Quick Start
3. Features
   - Main Menu
   - Recipe Book Management
     - Adding Dish
     - Adding Ingredient to Dish
     - List all Dish
     - Initialize Dish
     - Removing Dish
   - Ingredient Management by Fridge Storage
     - Add Ingredient
     - Remove Ingredient
     - Finding Ingredient
     - Using an Ingredient
     - Listing all Expired Ingredients Today
     - Removing all Expired Ingredients 
   - Order Management
   - Todo List
   - Statistics: Popularity of Dishes
   - (the below related to tasks should be deleted)
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

**Command Format**

- Command parameter in `UPPER_CASE` is needed to be specified by the user. E.g., `add DESC` can be specified as `add noodle`.
- Command parameter followed by `-(...)` is to inform the user of the specifying format. E.g., `ORDER_DATE-(dd/mm/yyyy)` indicates only the format such as `31/12/2019` is accepted. 
- Some command parameter is followed by `-(option: a (default) | b | c)`, indicating it has `a`, `b`, `c` three options, with the default set to `a`. E.g., for `-l LIST_TYPE-(option: all (default) | undone)`, the user can enter `-l` without further specification as it is equivalent to `-l all`, or specify as `-l undone`.
- Command parameter can be optional. If it is wrapped by `[...]`, the specification can be dropped and the value is set as `NULL` or default. Otherwise, the parameter must be specified. E.g.,  `add [-d ORDER_DATE-(dd/mm/yyyy)] -n DISH1_NAME[*DISH_AMOUNT], DISH2_NAME[*DISH_AMOUNT]` supports command  `add chicken rice*1, cake*2, laksa`, where the order date is set to `date of today` if not specified, and the dish amount is set to `1` if not specified.



### 3.1 Main Menu

The user is greeted depending on the time of day, `good morning` , `good evening` etc. Upon start up, if there are any expired ingredients in the fridge, the application will prompt the user to clear the expired ingredients. In the Main menu, the user is given various options to enter:

- `a`, remove the **expired** ingredients 
- `b` , proceed to **fridge** commands
- `c`, proceeds to **order** commands
- `d`, proceeds to **dish** commands
- `t`, view today's **todo** list
- `q` , **exits** the application



### 3.2 Recipe Book Management

To begin with, the user needs to enter command `d` in main menu, so as to step into Recipe Book management menu. The below commands are all executed in the Recipe Book management menu. 

#### 3.2.1 Adding Dish: `add`

To add a dish to the DishList, the user needs to execute command below:

Format: `add DISH_NAME`

If the dish already exist in the list, message is output:

```
         dish already exist in list
```

Examples: 

- `add chicken rice`
- `add tom yum noodles`

```
         _________________________________________________________________________________________
         you have added the following dish:
         chicken rice
         _________________________________________________________________________________________
```



#### 3.2.2 Add Ingredient to Dish: `ingredient`

Executing this command associates an `Ingredient` to an existing `Dish`. The user needs to execute the following command with the below format:

Format: `ingredient INGREDIENT_NAME INGREDIENT_AMOUNT DISH_INDEX`

Examples:

- `ingredient rice 50 1`, ingredient rice (50g) is now associated to 1st dish
- `ingredient noodle 50 2` ingredient noodle (50g) is now associated to 2nd dish

```
         _________________________________________________________________________________________
         ingredient: rice
         added to: chicken rice
         _________________________________________________________________________________________
```



#### 3.2.3 Listing all Dishes: `list`

The user needs to enter the command below:

Format: `list`

If there are no dishes in list, the program will output the following message:

```
        OOPS!!! No Dishes yet!.
        You can type:
        'template' to see the format of the commands,
        'back' to see all your options,
        'q' to exit
```

If user enters a valid command and the dish list is not empty, the result will be as follows.

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



#### 3.2.4 Initializing the Dish List: `initialize`

The user needs to enter the command below:

user needs to enter the command below:

Format: `initialize`

The user is then asked to confirm as this command deletes all the entries in the dish.

If user enters `y`,

```
         are you sure you want to clear list? (yes or no)
y
         LIST IS CLEARED
```

If user enters `n`,

```
         are you sure you want to clear list? (yes or no)
n
         LIST IS NOT CLEARED
```



#### 3.2.5 Removing Dish: `remove`

The user needs to enter the command below:

Format: `remove DISH_INDEX`

Examples:

- `remove 1`
- `remove 2`

If user enters an invalid index, an error message will appear.

```
        OOPS!!! dish does not exist.
        You can type:
        'template' to see the format of the commands,
        'back' to see all your options,
        'q' to exit
         _________________________________________________________________________________________
```

Otherwise, it will print out the information of the removed dishes.

```
         _________________________________________________________________________________________
         The following dish have been removed:
         chicken rice
         _________________________________________________________________________________________
```



### 3.3 Ingredient Management by Fridge Storage

To begin with, the user needs to enter command `'a'- remove all expired` or `'b' - add/remove/use an ingredient` in the main menu. In mode `a`, all expired ingredients would be removed after execution of the command `a`. While the execution of command  `b`, directs the user into Ingredient management menu. The below commands, except for removing all expired, are all executed in the Ingredient management menu.

#### 3.3.1 Adding Ingredient: `add`

To add an ingredient to the IngredientsList, the user needs to execute command below:

Format: `add `  `INGREDIENT_NAME` `INGREDIENT_AMOUNT` `INGREDIENT_EXPIRY_DATE-(dd/mm/yyyy)`

If the ingredient already exist in the list, but **do not have the same expiry date**, another entry of the ingredient will be created in the ingredient list. **However**, if the ingredient also **have the same expiry date as the existing ingredient in the ingredient list**, the amount of the existing ingredient amount will be added on and no new entry will be created in the ingredient list.

Examples: 

- `add ` `salt` `50` `31/10/2019` 
- `add` `chilli` `70` `29/04/2019`  

```
         _________________________________________________________________________________________
         you have added the following ingredient:
         salt 50 31/10/2019
         _________________________________________________________________________________________
```



#### 3.3.2 Removing Ingredient: `remove`

To remove an ingredient from the IngredientsList, the user needs to execute command below:

Format: `remove ` `ingredient index`

If the ingredient index does not exist in the list, message is output:

```
	 ☹ OOPS!!! Enter a valid ingredient index number after delete, between 1 and 14.
	 You can type: 
	'template' to see the format of the commands, 
	'back' to see all your options, 
	'q' to exit
```

This example was done when the size of the ingredient list is 14.

Examples: 

- `remove ` `7`
- `remove` `3 `

```
         _________________________________________________________________________________________
         Noted. I've removed this ingredient:
	 salt, amount is: 50 expiring on 31st of Octoboer 2019
	 Now you have 14 ingredients in the list.
         _________________________________________________________________________________________
```



#### 3.3.3 Finding Ingredient: `find`

To find an ingredient from the IngredientsList, user needs to execute command below:

Format: `find` `INGREDIENT_NAME`

If the ingredient index does not exist in the list, message is output:

```
	 No such ingredient found!
```

Examples: 

- `find ` `salt`
- `find` ` chilli `

```
These are the ingredients you searched for!
	 5. salt 50 31/10/2019.
	 9. salt 60 21/07/2021
```



#### 3.3.4 Using an ingredient 

To use an ingredient from the ingredient list, the user needs to execute command below:

Format: `use`  `INGREDIENT_NAME` `INGREDIENT_AMOUNT` 

If there are not enough required ingredients, message is output:

```
	 There is not a sufficient amount of rice that is not expired, maybe you could buy some first? 
```

Examples: 

- `use ` `salt` `50` 
- `use` ` chilli` `40`  

```
	 Great you used salt amount: 50
```

Also, once amount reaches 0, the ingredient will be deleted off from the IngredientsList. 



#### 3.3.5 Listing all expired ingredients on the date itself: `list`

To list all expired ingredient from the IngredientsList on the date itself, user needs to execute command below:

Format: `listtoday`

if there are no expired ingredients for the date itself, message is output:

```
	 No expired ingredients for today!
```

Otherwise,

```
	 Here are the expired ingredients for today
	 6. salt, amount is: 50 expired on 31st of October 2019.
	 7. chilli, amount is: 60 expired on 31st of October 2019.
```



#### 3.3.6 Removing all expired ingredients 

*Note that*: this removing all expired command is executed by the user entering `a` in the **main menu**.

If there are no expired ingredients for the date itself, message is output:

```
	 ☹ OOPS!!! Seems like you don't have any expired ingredients in the fridge!. 
```

Otherwise,

```
	Removed:  ingredients: 
	salt, amount is: 50 expired on 31st of October 2019
```

### 

### 3.4 Order Management

3.4.1 



### 3.5 Error Handling 

handles unexpected commands from the user such as unknown/incomplete command. if user enters an invalid command, the application will output a message that corresponds to what the user entered wrongly.

eg. `deadline` <empty desc>, `deadline` <desc> `by` <empty desc>, `delete` <empty indx>

outputs:

```
	 ____________________________________________________________
	 ☹ OOPS!!! The description cannot be empty.
	 ____________________________________________________________

```



### 3.6 stats: gives the statistics of the Dish

### 3.7 order: creates a new order 

### 3.8 preorder: 

### 3.9 help: shows a list of commands to the user 

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
