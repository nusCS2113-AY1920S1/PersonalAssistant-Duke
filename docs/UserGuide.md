# Chef Duke - User Guide

By: `Team CS213-T14-2`       Since: `Oct 2019`



[TOC]

## 1. Introduction

Chef Duke is targeted towards restaurant chefs who wants to be able to consolidate most of the things happening in their kitchen such as recipes, ingredients, expiry dates etc. By using this product, you are able to order all the ingredients needed for your kitchen. Additionally, this application takes in customers order/preorder of the restaurants dishes, and the chef is able to view his/her today's todo list according to order update. Proceed to the Developer Guide [here]( https://github.com/AY1920S1-CS2113-T14-2/main/blob/master/docs/DeveloperGuide.md ) to learn more about this application. 
## 2. Quick Start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest Duke.jar [here](https://github.com/AY1920S1-CS2113-T14-2/main/releases).

3. Copy the file to the folder you want to use as the home folder for your Duke application.

4. Use the command prompt and navigate to the path where the application is downloaded `cd ../FILEPATH`

5. run the command `java -jar v1.3` , application will then be executed 

   ![](https://github.com/AY1920S1-CS2113-T14-2/main/blob/master/docs/images/Ui.png)

6. Type some commands and press ENTER to execute

7. Some example commands

   1. given various options in menu, user can type these commands `options` ,`q`, `t`, `a`, `b`, `c`, `d`.
   2. user enters `d` then `add chicken rice`, dish is then added to the list
   3. user enters `d` then `list`, outputs the the dishes and the ingredients associated to the dish in table form

8. Refer to Section 4 for the full list of commands

## 3. Features

**Command Format**

- Command parameter in `UPPER_CASE` is needed to be specified by the user. E.g., `add DESC` can be specified as `add noodle`.
- Command parameter followed by `-(...)` is to inform of the specifying format. E.g., `ORDER_DATE-(dd/mm/yyyy)` indicates the acceptable format is like `31/12/2019` . 
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

the user needs to enter command `d` from the Main menu to enter the Dish template. this template allows the user to modify the recipe book.

#### 3.2.1 Adding Dish

user needs to enter `d` in main menu first.

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

#### 3.2.2 Adding Ingredient to Dish

user needs to enter `d` in main menu first.

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


#### 3.2.3 List all Dishes
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
_________________________________________________________________________________________
    Here are the dishes in your list:
	1. Recipe for chicken rice
	2. Recipe for chicken noodle
	3. Recipe for pizza
________________________________________________________________________________________
```

```
	 Here are the dishes in your list:
	1. Recipe for chicken rice
	 rice, 10
	 salt, 20
	2. Recipe for chicken noodle
	 noolde, 100
	 chicken, 50
	3. Recipe for pizza
	 _________________________________________________________________________________________
```

#### 3.2.4 Initializing the Dish List
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

#### 3.2.5 Removing Dish
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

Otherwise, it will print out the information of the dishes.

```
         _________________________________________________________________________________________
         The following dish have been removed:
         chicken rice
         _________________________________________________________________________________________
```

#### 3.2.6 Finding Dish

user needs to enter the command below:

Format: `find DESCRIPTION`

examples:

- `find chicken`
- `find rice`

```
________________________________________________________________________________________
dishes that contain, chicken:

	 index:1	 dish:chicken rice
	 Recipe for chicken rice
	 index:2	 dish:chicken noodle
	 Recipe for chicken noodle
	 _________________________________________________________________________________________
```

```
_________________________________________________________________________________________
dishes that contain, rice:

	 index:1	 dish:chicken rice
	 Recipe for chicken rice
	 _________________________________________________________________________________________
```

if there are no dishes containing the keyword, program will output below:

```
________________________________________________________________________________________
dishes that contain, test:

	 No dishes found
	 _________________________________________________________________________________________
```

#### 3.2.7 Changing name of Dish

user needs to enter the command below:

Format: `change INDEX DESCRIPTION`

examples:

- `change 1 pizza`
- `change 2 spaghetti`

```
________________________________________________________________________________________
	 dish: chicken rice
	 changed to: pizza
	 _________________________________________________________________________________________
```

```
_________________________________________________________________________________________
	 dish: chicken noodle
	 changed to: spaghetti
	 _________________________________________________________________________________________
```

other wise, if user enters an invalid command, 

	_________________________________________________________________________________________OOPS!!! enter a valid index/description 
	 You can type: 
	 'template' to see the format of the commands, 
	 'back' to see all your options, 
	 'q' to exit
	 _________________________________________________________________________________________
### 3.3 Ingredient Management by Fridge Storage

To begin with, the user needs to enter command `'a'- remove all expired` or `'b' - add/remove/use an ingredient` in the main menu. In mode `a`, all expired ingredients would be removed after execution of the command `a`. While the execution of command  `b`, redirects the user into Ingredients management menu. The below commands, except for removing all expired, are all executed in the Ingredient management menu, upon choosing option `b`.

#### 3.3.1 Adding Ingredient

To add an ingredient to the `Fridge`, the user needs to execute the command below:

Format: `add `  `INGREDIENT_NAME` `INGREDIENT_AMOUNT` `INGREDIENT_EXPIRY_DATE-(dd/mm/yyyy)`

*Note:*  `INGREDIENT_NAME` must not contain spaces, use underscores or Camel case, eg. blueCheese or blue_cheese

If the ingredient already exist in the `IngredientList` of the `Fridge`, but **does not have the same expiry date as the ingredient to be added**, another entry of the ingredient will be created in the ingredient list, differing from the existing one by the expiry date. **However**, if the ingredient to be added also **has the same expiry date as an existing ingredient in the ingredient list with the same name**, the amount of the ingredient to be added will be added onto the amount of the existing ingredient and no new entry will be created in the `IngredientList` of the `Fridge`.

Examples: 

- `add ` `chicken` `3  ` `1/2/2020`
- `add` `chilli` `70` `29/04/2019`  

```
         _________________________________________________________________________________________
add chicken 3 1/2/2020
         _________________________________________________________________________________________
     Got it. I've added chicken to the fridge, you currently have:
	 chicken, amount is: 3 expiring on 1st of February 2020          _________________________________________________________________________________________

add chicken 2 1/2/2020        ______________________________________________________________________________________

	Got it. I've added chicken to the fridge, you currently have:
	chicken, amount is: 5 expiring on 1st of February 2020        _________________________________________________________________________________________
	
add chicken 4 2/3/2020         _________________________________________________________________________________________

	Got it. I've added chicken to the fridge, you currently have:
	chicken, amount is: 4 expiring on 2nd of March 2020
         _________________________________________________________________________________________
```

Furthermore, if the user adds an expired ingredient, he gets a `WARNING!!! Adding an expired ingredient in the fridge, do you want to proceed adding it? ` 

```
         _________________________________________________________________________________________
add milk 250 9/11/2019       _________________________________________________________________________________________

 	WARNING!!! Adding an expired ingredient in the fridge, do you want to proceed adding     it? 
    Type yes to confirm, typing anything else will result in not adding the ingredient
           _________________________________________________________________________________________
  
yes        _________________________________________________________________________________________

		Got it. I've added milk to the fridge, you currently have:
		WARNING! expired ingredient: milk, amount is: 250 expired on 9th of November 2019
	 _________________________________________________________________________________________
```

#### 3.3.2 Removing Ingredient

To remove an ingredient from the `Fridge`, user needs to execute command below:

Format: `remove ` `ingredient index`

If the ingredient index does not exist in the list, the following message is printed :

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
	 salt, amount is: 50 expiring on 31st of October 2019
	 Now you have 14 ingredients in the list.
   
         _________________________________________________________________________________________
```

#### 3.3.3 Finding Ingredient

To find an ingredient from the `Fridge`, user needs to execute command below:

Format: `find` `INGREDIENT_NAME`

If the ingredient name does not exist in the list, message is output:

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

To use an ingredient from the `Fridge`, the user needs to execute the command below:

Format: `use`  `INGREDIENT_NAME` `INGREDIENT_AMOUNT` 

*Note:*  `INGREDIENT_NAME` must not contain spaces, use underscores or Camel case, eg. blueCheese or blue_cheese

A warning:  `*always use most recently expiring ingredients first, to prevent food waste!*` is printed when showing the example use of this command. This notifies the chef that he should be using the ingredients with the most recent expiry date first. This command will look through all the ingredients matching the ingredient name, sorted by ascending expiry date, and continue removing them (considering the amount of the ingredient they contain ) until the total  `INGREDIENT_AMOUNT` is used/reached. Furthermore, if the `Fridge` contains expired ingredients matching the  `INGREDIENT_NAME`, they are not taken into consideration, since the Chef should not be able to use an expired ingredient in his dishes!

If there are not enough required ingredients, the following message is printed:

```
   There is not a sufficient amount of rice that is not expired, maybe you could buy some first? 
```

Examples: 

- `use ` `salt` `50` 
- `use` ` chilli` `40`  

```
   Great you used salt amount: 50
```

Also, once amount reaches 0, the ingredient will be deleted off from the `IngredientsList` of the `Fridge` .

#### 3.3.5 Listing all expired ingredients on the date itself

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

To be able to use this feature, the user needs to enter `a` in the menu, and it will remove all of the expired ingredients currently in the `Fridge`.

*Note that*: removing all expired command is executed by the user entering `a` in the **main menu**.

If there are no expired ingredients in the `Fridge`, the user gets the following output upon trying to use this feature.

```
	 ☹ OOPS!!! Seems like you don't have any expired ingredients in the fridge!
```

Otherwise,

```
	Removed ingredients: 
salt, amount is: 50, expired on 31st of October 2019
milk, amount is: 150, expired on 11th of October 2019
```

#### 3.3.7 Changing the amount of an ingredient

To change the amount of an ingredient, the user will have to first type show to know the index of the ingredient. 

Format: `changeamount` `ingredient index` `new amount` 

If the user enters an invalid number for the ingredient index, he will be prompted with error response.

For example, for a list of 

```
1: WARNING! expired ingredient: cockroach, amount is: 50 expired on 11th of November 2019
2: chichken, amount is: 2 expiring on 12th of December 2019
3: rice, amount is: 1476 expiring on 2nd of March 2020
4: chicken, amount is: 6 expiring on 2nd of March 2020
5: rice, amount is: 500 expiring on 12th of December 2020
6: chicken, amount is: 2147483646 expiring on 11th of November 2022
```

Examples: 

- `changeamount` `7` `22`   

```
	OOPS!!! Enter a valid ingredient index number after change, between 1 and <number of     ingredients in the fridge>  
```

Otherwise,

- `changeamount` `4` `22`   

```
	 You've changed index number 4's amount to 22
	 4.chicken, amount is: 22 expiring on 2nd of March 2020
```

#### 3.3.8 Changing the name of an ingredient

To change the name of an ingredient, the user will have to first type show to know the index of the ingredient. 

Format: `changeamount` `ingredient index` `new name` 

If the user enters an invalid number for the ingredient index, he will be prompted with error response.

For example, for a list of 

```
1: WARNING! expired ingredient: cockroach, amount is: 50 expired on 11th of November 2019
2: chichken, amount is: 2 expiring on 12th of December 2019
3: rice, amount is: 1476 expiring on 2nd of March 2020
4: chicken, amount is: 22 expiring on 2nd of March 2020
5: rice, amount is: 500 expiring on 12th of December 2020
6: chicken, amount is: 2147483646 expiring on 11th of November 2022
```

Examples: 

- `changename` `7` `beef`   

```
	OOPS!!! Enter a valid ingredient index number after change, between 1 and <number of     ingredients in the fridge>  
```

Otherwise,

- `changename` `4` `beef`   

```
	You've changed index number 4's name to beef
	4.beef, amount is: 22 expiring on 2nd of March 2020
```

### 3.4 Order Management

To begin with, the user needs to enter command `c` in **main menu**, so as to step into Order Management menu. The below commands are all executed in the Order management menu. 

#### 3.4.1 Adding Order Today or Pre-Order

To add a new order to the order list, the user needs to execute the command following the below format.

General Format: 

`add [-d ORDER_DATE-(dd/mm/yyyy)] -n DISH1_NAME[*DISH1_AMOUNT], DISH2_NAME[*DISH2_AMOUNT], ...`

Simplified Format (applicable for **today's order**):

`add -n DISH1_NAME[*DISH1_AMOUNT], DISH2_NAME[*DISH2_AMOUNT], ...`

<u>Requirement:</u>

- The serving date of the order `ORDER_DATE` should be valid and **cannot be before today**. 
  - If the date is today, the user can simply enter command in simplified format.
  - Otherwise, the order is treated as *pre-order*. `ORDER_DATE` must be specified.
- The ordered dishes **cannot be empty**.

Examples: 

- `add -d 31/12/2019 -n beef noodle*3`: there can be only one ordered dishes.
- `add -d 12/11/2019 -n chicken rice*1, cake*2, laksa`: the amount of laksa is 1, as default.
- `add -n pasta, mushroom soup` : the serving date of this order is date of today, as default.

Sample output message:

```
_____________________________________________________________________
   Got it. New order added: 
   [✘] Order on 31/12/2019 
      (1) beef noodle 3
   Now you have 1 orders in the order list. Type 'list' to see all the orders.
_____________________________________________________________________
```

```
_____________________________________________________________________
   Got it. New order added: 
   [✘] Order today 
      (1) pasta 1
      (2) mushroom soup 1
   Now you have 2 orders in the order list. Type 'list' to see all the orders.
_____________________________________________________________________
```

However, these cases can raise exceptions:

- `add -d 31/02/2020 -n laksa*3`

  ```
  	 OOPS!!! Must enter a valid date
  ```

- `add -d 12/11/1998 -n chicken rice`

  ```
  	 OOPS!!! Must set date equal or after today 
  ```

- `add -d 12/11/2020 -n`  or `add -d -n fried rice`

  ```
  	 OOPS!!! must enter a valid order date or specify dishes 
  ```

#### 3.4.2 Altering Order Serving Date

In pre-order management, it is very common that the serving date vary. To update the serving date of a coming/future order in the order list, the user needs to execute the command following the below format.

Genearl Format: `alter ORDER_INDEX ORDER_DATE-(dd/mm/yyyy)`

Simplified Format (applicable for **today's order**): `alter ORDER_INDEX`

<u>Requirement:</u>

- Order is going to be altered should be **today's undone order** or **pre-order**.
  - If the order has been done, then altering it does not make sense. It will do nothing and reminds you of its done status.
- `ORDER_INDEX` ranges from 1 to the size of the current order list. 
- The newly set date should be in valid format and **cannot be before today**. 
  - If the date is today, the user can simply enter command in simplified format. 
  - Otherwise, the order is treated as *pre-order*. `ORDER_DATE` must be specified.

Examples: 

- `alter 2`: changes the serving date of 2nd order in the order list to today.
- `alter 1 03/01/2020`: changes the serving date of 2nd order in the order list to `03/01/2020`.

The sample output message would be like: (If the program is run on `03/11/2019`)

```
_____________________________________________________________
   Nice! You've changed the order to the date 03/11/2019:
   [✘] Order today 
      (1) fish 1
      (2) chili crab 1
_____________________________________________________________
```

```
_____________________________________________________________
   Nice! You've changed the order to the date 03/12/2019:
   [✘] Order on 03/12/2019 
      (1) fish 1
      (2) chili crab 1
_____________________________________________________________
```

If `ORDER_INDEX` were refers to an undone order, then it would reminds you that: 

```
	 OOPS!!! Order done already. Date alteration is not expected.
```

If the order list were empty at current stage, the output message would be:

```
   OOPS!!! No order in the list! No order can be altered!
```

If `ORDER_INDEX` were out of range, it would reminds you the below, if the size of order list is 4:

```
   OOPS!!! Must enter a valid order index number between 1 and 4
```

Or, would reminds you the below, if the size of order list is 1:

```
   OOPS!!! Got only 1 order in the order list. Enter '1' as order index
```

#### 3.4.3 Cancelling Order

To cancel an existing undone order from the order list, the user needs to execute the command following the below format.

Format: `cancel ORDER_INDEX`

<u>Requirement:</u>

- `ORDER_INDEX` ranges from 1 to the size of the current order list. 
- Only **today's undone order** or **pre-order** can be cancelled. 

Examples: 

- `cancel 3`: remove 3rd order in the order list, if exists.

The sample output message would be like:

```
_____________________________________________________________________
   Noted. You've cancelled this order:
   [✘] Order on 03/12/2019 
      (1) fish 1
      (2) chili crab 1
   Now you have 3 orders in the order list. Type 'list' to see all the orders.
_____________________________________________________________________
```

If the order to be cancelled has been done, it will reminds you that: 

```
	 OOPS!!! Order 4 has already been done! Cancellation does not make sense! 
```

If the order list is empty at current stage, the output message would be:

```
   OOPS!!! No order in the list! No order can be cancelled!
```

If `ORDER_INDEX` were out of range, it would reminds you the below, if the size of order list is 4:

```
   OOPS!!! Must enter a valid order index number between 1 and 4
```

Or, would reminds you the below, if the size of order list is 1:

```
   OOPS!!! Got only 1 order in the order list. Enter '1' as order index
```

#### 3.4.4 Marking Order as Done

To mark an existing undone order of the date today as done, the user needs to execute the command following the below format.

Format: `done ORDER_INDEX`

<u>Requirement:</u>

- `ORDER_INDEX` ranges from 1 to the size of the current order list. 
- Only **today's undone order** can be done, while Pre-order cannot. Pre-order supports cancellation and date alteration.

Examples: 

- `done 2`: Mark 2nd order in the order list as done, if there exists among today's orders and it is still undone. 

Otherwise, the sample output message would be like:

```
______________________________________________________________________
   Nice! You've marked this order as done:
   [✓] Order on 12/09/2020 
      (1) beef noodle 1
      (2) chili crab 1
______________________________________________________________________
```

If the order has already been done, the output message would be:

```
	 OOPS!!! Order 1 has already been done! 
```

If the order is a pre-order, the output message would be:

```
	 OOPS!!! Order 4 is not serving today!
```

If the order list is empty, the output message would be:

```
   OOPS!!! No order in the list! No order can be done!
```

If `ORDER_INDEX` were out of range, it would reminds you the below, if the size of order list is 4:

```
   OOPS!!! Must enter a valid order index number between 1 and 4
```

Or, would reminds you the below, if the size of order list is 1:

```
   OOPS!!! Got only 1 order in the order list. Enter '1' as order index
```

#### 3.4.5 Initializing Order List

To clear all the orders in the order list, the user needs to execute the command following the below format.

Format: `init`

Since initialization is very dangerous, the data in all orders would be lost if entering this command. Hence, the program will asks the user to confirm the initialization operation: `Are you sure you want to clear all orders in the order list? [y/n] `

If the user answers  `y` or `Y`, the output message will be: (Note that *<u>today todo list</u>* is cleared also.) Then it will provide the user with add order command format.

```
_________________________________________________________________________
   ORDER LIST CLEARED
   TODAY TODO LIST CLEARED

   Continue by adding order. Template:
   add [-d ORDER_DATE-(dd/mm/yyyy)] -n DISH1_NAME[*DISH_AMOUNT], DISH2_NAME[*DISH_AMOUNT]
_________________________________________________________________________
```

If the user answers  `n`  or `N ` or any other input string, the output message will be:

```
_________________________________________________________________________
   ORDER LIST NOT CLEARED

   Continue by adding, removing, altering, listing order.
   Type 'template' to see the format of the commands
_________________________________________________________________________
```

#### 3.4.6 Listing Order by Different Filtering Keywords

To list orders in the order list, the user needs to execute the command following the below format.

*** Remark:  If not specify, the order list refers to the whole order list, including orders on every possible date and including those done orders.* 

<u>**Format 1:**</u>

​	 `list [-l LIST_TYPE-(option: all (default) | undone | today | undoneToday)]`

- Examples:
  - `list` (i.e., `list -l all`): list all orders in the order list.
  - `list -l undone`: list all the undone orders in the order list. 
  - `list -l today`: list all orders of today in the order list.
  -  `list -l undoneToday`: list all undone orders of today in the order list.

<u>**Format 2:**</u> 

​	 `list -n DISH_NAME`

- Example
  -  `list -n chicken rice`: list all undone orders of the date today that contains the dish `chicken rice`.

-  *Remark*
   -  The command only supports querying for one dish, as considering the chef may look for orders that have some dish and then plan to cook at the same time.
   -  The command only supports for today's order, since others do not have applicable senarios.

<u>**Format 3:</u>

​	 `list -d ORDER_DATE-(dd/mm/yyyy) [-l LIST_TYPE-(option: all (default) | undone)]`

- Example: 
  - `list -d 31/12/2019 -l undone`: list all undone orders on `31/12/2019`. 
  - `list -d 31/12/2019` (i.e., `list -d 31/12/2019 -l all`): list all orders on `31/12/2019`. 
- *Remark*: 
  - If you want to find ***today's orders***, we recommend you to use command `list -l today` or `list -l undoneToday`.

The sample output message is like:

```
_______________________________________________________________________
   Here is(are) the order(s) in the order list:
   1.[✘] Order today 
      (1) laksa 1
   2.[✘] Order today 
      (1) chicken rice 1
   3.[✘] Order on 12/12/2019 
      (1) beef noodle 2
_______________________________________________________________________
```

If there is no order in the order list at current stage, the output message is: 

```
   OOPS!!! No orders in the order list! 
```

If there is no order satisfying your requirement, the output message is:

```
   OOPS!!! No orders found!
```

If the specified parameter is not in the options or is omitted, then you would get messages like below:

```
	 OOPS!!! Only support find fixed date orders among all/undone orders
```

```
	 OOPS!!! Must follow the format of list command template
```

```
   OOPS!!! Must enter a list type, dishes name, or order date.
```

### 3.5 ToDo List Today

Chef needs to check his/her remaining tasks of the day. The ToDo list keeps in accordance with the update of the orders in the order list. To view the ToDo List of today, the user needs to enter `t` in the **main menu**.

The sample output message as follows. The Date in the parenthesis is the current date time.

```
______________________________________________________________________
   Today Task list (Thu Nov 07 13:00:00 SGT 2019)
   1. chicken rice (amount: 3) 
   2. cake (amount: 2) 
   3. beef noodles (amount: 4)
______________________________________________________________________
```

<u>Update Principle:</u>

1. add undone dishes from pre-orders when the restaurant opens;
2. add undone dishes when today's new order comes;
3. delete finished dishes from the ToDo list when an order of today is done;
4. delete undone dishes from the ToDo list when an order of today is cancelled.
5. add undone dishes when a pre-order alters its date to become today's undone order.

### 3.6 Statistics of Most Popular Dishes `[Coming in v2.0]`

Order component in the program tracks all the records of done orders. By sorting the ordered number of each dishes, the chef can get the statistics of most popular dishes, in a week, or a month, or a season. The statistics lead to the food reference of the majority of the customers. If the chef wants to explore a new dishes, the common characters of those top popular dishes may be a good reference.

### 3.7 Checking Ingredients with Orders `[Coming in v2.0]`

When a new order of today comes, the todo list will update. In the meantime, the progarm will automatically check if the fridge has enough ingredient for that order. If not, the program will remind you to buy ingredients. Also, at the moment the restaurant opens, the program will check if there is enough ingredient for today's orders (which were pre-orders yesterday). If not, the program will also remind you to buy ingredients. 

### 3.8 Draft Recipe `[Coming in v2.0]`

The recipebook of a restaurant is not fixed. The chef may need to explore new recipe and dishes. For instance, the chef want to explore a dish, we call it as dishA for simplicity. The program can track all historical trials of this dishA. Normally a dish cannot be created overnight or within one or two trials. The chef intends to use different ingredients and different proportions to make better taste than the taset in previous trials. 

Therefore, the program will record information of every trial, such as ingredient name, amount. The feedback that what the chef think of the trial, will also be included in the trial information. Providing trial information well-organized and the chef can see each upon entering some command, the chef would consider put forward a new dish much easier.

Upon the draft recipe is regarded as fixed and no longer changes, it will be added into recipebook.

### 3.9 Changing ingredients of a dish `[Coming in V2.0]`

The DishList contains all the dishes which also contains a list of ingredients each. However, there may be a case where there is a large number of ingredients for a dish. Thus there may be be a typo and the chef needs to amend the error. additionally, the ingredients for a certain dish may need changes as the chef may want to change the recipe, hence with this feature, he may be able to do so. 

## 4. Command Summary

<u>Main Menu</u>

Index | Keyword  | Usage | Description 
----- | -------- | ---------- | ---------- 
1 | options | options | show options 
2 | q       | q | exit program 
 3 | t | t | view chef's today todo list 
 4 | a | a | remove all expired ingredients 
 5 | b | b | go into ingredient template 
 6 | c | c | go into order template 
 7 | d | d | go into dish template 

<u>Ingredient Template</u>

| Index | Keyword      | Usage                         | Description                                          |
| ----- | ------------ | ----------------------------- | ---------------------------------------------------- |
| 1     | add          | add DESC AMOUNT DATE          | add an Ingredient to the fridge                      |
| 2     | remove       | remove INDEX                  | remove an ingredient from fridge                     |
| 3     | find         | find DESC                     | find an ingredient in the fridge                     |
| 4     | listtoday    | listtoday                     | list all expired ingredient                          |
| 5     | a            | a                             | removes all expired ingredient                       |
| 6     | use          | use DESC AMOUNT               | use an ingredient from the fridge                    |
| 7     | changeamount | changeamount INDEX NEW_AMOUNT | changes the amount of the ingredient using its index |
| 8     | changename   | changename INDEX NEW_NAME     | changes the name of the ingredient using its index   |
| 9     | show         | show                          | lists all the ingredients in the fridge              |

<u>Order Template</u>

| Index | Keyword | Usage                                          | Description                           |
| ----- | ------- | ---------------------------------------------- | ------------------------------------- |
| 1     | add     | add [-d ORDER_DATE] -n DISH_NAME[*DISH_AMOUNT] | add a new order                       |
| 2     | alter   | alter ORDER_INDEX ORDER_DATE                   | alter serving date                    |
| 3     | cancel  | cancel ORDER_INDEX                             | cancel undone order                   |
| 4     | done    | done ORDER_INDEX                               | mark order done                       |
| 5     | init    | init                                           | initialize                            |
| 6     | list    | list [-l LIST_TYPE]                            | list orders                           |
| 7     | list    | list -n DISH_NAME                              | ind the orders containing that dishes |
| 8     | list    | list -d ORDER_DATE [-l LIST_TYPE]              | list orders on querying date          |

** For more details, please go for 3.4 Order.

<u>Dish Template</u>

| Index | Keyword    | Usage                        | Description               |
| ----- | ---------- | ---------------------------- | ------------------------- |
| 1     | add        | add DESC                     | adds a dish to the list   |
| 2     | find       | find DESC                    | find dish by keyword      |
| 3     | change     | change INDEX DESC            | change name of dish       |
| 4     | remove     | remove INDEX                 | removes a dish from list  |
| 5     | list       | list                         | list all dishes           |
| 6     | initialize | initialize                   | clears the dish list      |
| 7     | ingredient | ingredient DESC AMOUNT INDEX | add an ingredient to dish |

<u>Common commands in template</u>

| Index | Keyword  | Usage    | Description                   |
| ----- | -------- | -------- | ----------------------------- |
| 1     | template | template | shows the template of current |
| 2     | back     | back     | goes to main menu             |
| 3     | q        | q        | exits program                 |



## 5. FAQ

**Q: how do I transfer data to another computer?** 

A: install the application on the other computer and an empty recipe.txt will be created under the data folder. Replace this file with the same txt file found in your previous computer. therefore your data will be transferred. 

**Q: Who should I assign found bugs to?**

A: Work distribution is as follows: 

- Dishes Component @[9hafidz6](https://github.com/9hafidz6);
- Ingredient Component @[x3chillax](https://github.com/x3chillax); 
- Order Component @[VirginiaYu](https://github.com/VirginiaYu);           
- Fridge Component @3[saradj](https://github.com/saradj); 
- Recipebook @[CEGLincoln](https://github.com/CEGLincoln).                     

​		Thank you for testing our product! : )

