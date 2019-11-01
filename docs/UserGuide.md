# BakingHome - User Guide

[toc]

## 1. Introduction
BakingHome is a one-stop application for managers of small to medium-sized bakeries to manage their business, including orders, inventory, products and sales. BakingHome is designed for users whose workflow is primarily keyboard-based.

## 2. Using the Guide
Welcome to BakingHome User Guide! This User Guide provides a detailed documentation of BakingHome's features and commands. In addition, this User Guide also provides a quick way to set up BakingHome.

Here are the conventions this User Guide follows:

- `mark-up` text represents commands or arguments. For example, `order add`.

- > ⚠️ **WARNING** This is a warning.

- > ℹ️ **INFO** This is additional information

No prior knowledge is needed to read this User Guide. Enjoy!

## 3. Quick Start
1. Ensure you have Java 11 or above installed in your Computer.
2. Downnload the latest `BakingHome.jar` [here](https://github.com/AY1920S1-CS2113T-T12-3/main/releases).
3. Copy the file to the folder you want to use as the home folder for you BakingHome. It is advised to creat a new folder to this app as there will be a data file generated when you start to use. 
4. Double-click the file to start the app. The GUI should appear in a few seconds.
![](https://i.imgur.com/S1hwFE4.png)

5. Type the command in the command box and press Enter to execute it. Here are a few examples you can try. 

- `inv add -name flour -qty 5 -rmk kg`: adds 5kg of flour into the inventory list
- `inv edit 1 -qty 5`
- `inv clear`: Clears the whole inventory list to be an empty list.
- `product add -name Egg tart -ingt [Egg, 2] [Flour, 1] -cost 1 -price 2`
- `shop add -name flour -qty 5 -cost 5 -rmk in kg` : adds 5kg of flour into the shopping list and the unit cost is $5 per kg.
- `order add -name Jiajun -contact 12345678 -deadline tomorrow -item Croissant, 6 -item Toast, 10 -rmk Customer is allergic to nuts`: adds an order with all fields present.
- `sale add -desc blueberry pie -val 12.99 -at 30/10/2019 08:31 -rmk added chocolate toppings`
6. Refer to section 6. Basic Commands for more details.

## 4. BakingHome Overview
This section provides an overview of BakingHome.

### 4.1 Entities
BakingHome supports four types of entities: **Ingredient**, **Product**, **Order**, **Sale**. 

Here is an overview of the entities. 

- Ingredient is the most basic entity in BakingHome. It has a name and a unitless quantity. Some examples are `egg, 4` and `flour, 10`.
- Product is an item to sell in the bakery. It requires one or more ingredients to bake. Examples are `bread` and `cake`.
- Order is a collection of products requested by a customer. For example, `five bread and two cakes ordered by Mr. Yang`.
- Sale is a transaction. It can be either income or expenditure.

### 4.2 Feature Hightlights

BakingHome allows you to manage your products, orders, inventory, shopping list and sales easily. Besides adding, deleting and editing the entities, BakingHome also supports:

- One step adding of insufficient ingredients to the shopping list. 
- Automatically adding a sale entry upon completing an order or buying from the shopping list.
- Dynamically displaying inventory status in an order.
- Productivity features: Undo, Redo, Autocomplete and Shortcut features that promote your productivity.

## 5. Command Format
This section specifies the formats you need to follow when entering a command.

### 5.1 General format
- Words in UPPER_CASE are the parameters to be supplied by the user.
- Items in curly brackets are optional e.g ``-name NAME {-status STATUS}`` can be used as `-name cake -status archive` or as `-name cake`.
- Items with `…` after them can be used zero or more times
- Parameters can be in any order.
- `-` is a reserved symbol for BakingHome, so it cannot be used in parameter values. For example, product cannot have the name `cheese-cake`.

### 5.2 Selecting entries
In BakingHome, an index is a positive integer representing an entry. You can select the entries you want to manage by specifying their indices. This can be done easily in three ways:
- Select a single index: `INDEX`. For example, `1`,`2`
- Select multiple indices with `,` : `INDEX1,INDEX2,INDEX3...`. For example, `1,2,3`, `2,5,8`
- Select a range of indices with `~`: `START~END`. `START` should not be greater than `END`. Examples: `1~8`, `9~10`

## 6. Basic Commands
This section introduces the basic and most frequently used commands to get you started.

### 6.1 Inventory

#### 6.1.1 Inventory Commands Overview
BakingHome's inventory management system allows easy management of ingredients in stock. An inventory command can have various parameters as illustrated in the table below: 
| Parameter Name | Description | Remarks |
| :--- | :--- | :--- |
| `INGREDIENT` | Name of the ingredient | - Name can take any value but cannot be empty. </br> - No two ingredients can have the same name. |
| `QUANTITY` | Quantity of the ingredient in the inventory list  | The default quantity is set to 0.0 if not specified. Cannot exceed 50,000.0|
| `REMARKS` | Additional remarks regarding the ingredient |Cannot exceed 50 characters. |

#### 6.1.2 Switching to inventory interface : `inv`
Displays the inventory list.

Format: `inv`

Example: `inv`

#### 6.1.3 Adding an ingredient : `inv add`
Adds a new ingredient to the inventory list.

Format: `inv add -name INGREDIENT {-qty QUANTITY} {-rmk REMARKS}`

Examples: 
- `inv add -name egg` : adds egg into the inventory list with default quantity 0.0
- `inv add -name flour -qty 5 -rmk qty in kg` : adds 5kg of flour into the inventory list

> ℹ️ **INFO**
> The default quantity is "0.0" if there is no input parameter for it.

> ⚠️ **WARNING**
> 1. Adding of ingredients will not work if it already exists in the inventory list. To add the quantity, use the edit command which is shown below instead.
> 2. `-qty` and `-cost` only accepts numbers as valid inputs.

#### 6.1.4 Editing an ingredient : `inv edit`
Edits the details of the ingredient at `INDEX`.

Format: `inv edit INDEX {-name INGREDIENT} {-qty QUANTITY} {-rmk REMARKS}`

Examples:
- `inv edit 1 -qty 5` : edits the quantity of the 1st ingredient to be 5
- `inv edit 3 -qty 10 -rmk in kg` : edits the 3rd ingredient’s quantity to be 10 and remarks to be “in kg”

> ℹ️ **INFO**
> Use the `INDEX` shown as displayed on the shopping list 

> ⚠️ **WARNING**
> 1. Input parameters can be interchanged but `INDEX` must come after `inv edit` 
> 2. You would not be able enter the command if you try to edit the ingredient's name to one that already exists in the list.
> 
#### 6.1.5 Deleting an ingredient : `inv remove`
Deletes an ingredient at the specified `INDEX`

Format: `inv remove INDICES`

Examples:
- `inv remove 1` : Removes the ingredient at index 1 of the displayed inventory list.
- `inv remove 4,5` : Removes the ingredients at index 4 and index 5 of the displayed inventory list.
- `inv remove 2~5` : Removes the ingredients from index 2 to index 5 of the displayed inventory list.

> ℹ️ **INFO**
> Only ',' and and '~' works.

#### 6.1.6 Clearing the inventory list : `inv clear`
Clears the whole inventory list to be an empty list.

Format: `inv clear`

Example: `inv clear`

### 6.2 Product List

#### 6.2.1 Product Commands Overview
BakingHome's product management system has various commands. An product command can have various parameters as illustrated in the table below:
| Parameter Name | Description | Remarks |
| :--- | :--- | :--- |
| `name` | Name of the product | - Name can take any value but cannot be empty. </br> - No two products can have the same name. |
| `price`| Retail price of the product| The default retail price is set to $0.0 if not specified|
| `cost` | The cost of the product | The default cost is set to $0.0 if not specified|
| `ingt` | The ingredients needed to make the product ||
| `status`| Status of the product. It can be either `ACTIVE` or `ARCHIVE` | A newly added product can only have `ACTIVE` status |

#### 6.2.2 Switching to product interface : `product`
Displays a list of active products.

Format: `product`


#### 6.2.3 Adding a new product : `product add`
Adds a product to the product list. 

Format: `product add -name PRODUCT_NAME {-ingt [INGREDIENT_NAME, PORTION]}... {-cost COST} {-price RETAIL PRICE}`
Examples:
- `product add -name Cheese cake`: adds a new product named "cheese cake". Product name can take any values but **cannot be empty**. No two products can have the **same name.**
-  `product add -name Egg tart -ingt [Egg, 2] [Flour, 1] -cost 1 -price 2`: adds a product named Egg Tart that requires 2 portions of Egg and 1 portions of Flour to make. It costs $1 to make and the reatil price is $2. Portion can only take **positive number**.
-  `product add -name Bread -ingt [Flour, 1] [Sugar, 5]` Adds a Bread product which costs $4 and will be saled at a retail price of $5.5. Cost and price must be **positive numbers**.

> ℹ️ **INFO**
> Try out these highly automated features!
> 1. Not sure about the cost upon adding a product? Don't worry, the system helps you generate one based on the ingredients given for your reference. i.e. in the above examples, if both 1 Flour costs $5, and 1 Sugar costs $1, then the system will assign a ingredient cost of $10 to Bread.
> 2. If the ingredients in the product is not in the current shopping list, the system will also automatically help you add a corresponding shopping list entry.


> ⚠️ **WARNING**
Take note that an `product add` command does not take in -status parameter, i.e. a newly added product can only have *ACTIVE* status. To add a archived product, you can:
    1. Add the product.
    2. Use the `product edit` command (illustrated below) to edit the status.

#### 6.2.3 Editing a product: `product edit`

Edits the details of an existing product at `INDEX`.

Format: `product edit -i INDEX {-name PRODUCT_NAME} {-ingt [INGREDIENT_NAME, PORTION]}... {-cost COST} {-price RETAIL PRICE}`
Example:
- `product edit -i 1 -name Small Cheese cake`: edits the name of the first product in the list to be "Small Cheese cake"
-  `product edit -i 2 -name Large Egg Tart -ingt [Egg, 4] [Flour, 2]`: edits both the name and the ingredients needed. 

> ⚠️ **WARNING**
If you want to change the ingredient fields, you have to include all ingredients needed again. The system will **regenerate** the ingredients from the entry. i.e. `product edit -i 2 -name Large Egg Tart -ingt [Egg, 4]` indicates that Large Egg Tart only needs 4 portions of Egg. 


#### 6.2.4 Listing products `product list`

Lists product with specified scope. This command takes in only one parameter `-scope` which can be `all`, `active` or `archive`. 


Format: `product list -scope SCOPE`
Example: 
- `product list -scope archive` lists all archived products

#### 6.2.5 Showing ingredients needed for a product `product show`

Shows the details of a product at `INDEX`. Since the product list only shows name, cost, price and status of a product, you may use this command to check the ingredients as well as the corresponding portion needed for this product. See figure x. 

Format: `product show -i INDEX` 

![](https://i.imgur.com/em5R1g0.png)


> ⚠️ **WARNING**
If you edit the currently showing product, remember to refresh the page to see the changes.


#### 6.2.5 Deleting an product : `product remove`
Deletes products at the specified INDICES.

Format: `product remove INDICES`

Examples:
`product remove 1`
`product remove 1~4`
`product remove 1,3,4`

#### 6.2.6 Searching product `product search`
Lists products whose name includes the specified keyword.

Formate: `product search -include KEYWORD`

Example:
`product search -include Tart`
### 6.3 Shopping List

#### 6.3.1 Shopping List Commands Overview
BakingHome's shopping management system allows easy tracking of ingredients to buy and its cost. A shopping command can have various parameters as illustrated in the table below: 
| Parameter Name | Description | Remarks |
| :--- | :--- | :--- |
| `INGREDIENT` | Name of the ingredient | - Name can take any value but cannot be empty. </br> - No two ingredients can have the same name. |
| `QUANTITY` | Quantity of the ingredient in the inventory list  | The default quantity is set to 0.0 if not specified. It cannot be more than 50,000|
| `REMARKS` | Additional remarks regarding the ingredient | Cannot exceed 50 characters |
| `COST` | The unit cost of the ingredient | The default unit cost is set to $0.00 if not specified |

#### 6.3.2 Switching to shopping list interface : `shop`
Displays the shopping list.

Format: `shop`

Example: `shop`

#### 6.1.3 Adding an ingredient : `shop add`
Adds a new ingredient to the shopping list.

Format: `shop add -name INGREDIENT {-qty QUANTITY} {-cost COST} {-rmk REMARKS}`

Examples: 
- `shop add -name egg` : adds egg into the shopping list with default quantity 0.0 and default unit cost $0.00
- `shop add -name flour -qty 5 -cost 5 -rmk in kg` : adds 5kg of flour into the shopping list and the unit cost is $5 per kg.

> ℹ️ **INFO**
> The default quantity is "0.0" and default unit cost is "0.0" if there are no input parameters for it.

> ⚠️ **WARNING**
> 1. Adding of ingredients will not work if it already exists in the shopping list. To add the quantity, use the edit command which is shown below instead.
> 2. `-qty` and `-cost` only accepts numbers as valid inputs.

#### 6.1.4 Editing an ingredient : `shop edit`
Edits the details of the ingredient at `INDEX`.

Format: `shop edit INDEX {-name INGREDIENT} {-qty QUANTITY} {-cost COST} {-rmk REMARKS}`

Examples:
- `shop edit 1 -qty 5` : edits the quantity of the 1st ingredient to be 5
- `shop edit 3 -qty 10 -cost 5` : edits the 3rd ingredient’s quantity to be 10 and unit cost to be $5

> ℹ️ **INFO**
> Use the `INDEX` shown as displayed on the shopping list 

> ⚠️ **WARNING**
> 1. Input parameters can be interchanged but `INDEX` must come after `shop edit` 
> 2. You would not be able enter the command if you try to edit the ingredient's name to one that already exists in the list.

#### 6.1.5 Deleting an ingredient : `shop remove`
Deletes an ingredient at the specified `INDEX`

Format: `shop remove INDEX`

Examples:
- `shop remove 1` : removes the ingredient at index 1 of the displayed shopping list.
- `shop remove 3,7` : removes the ingredients at index 3 and index 7 of the displayed shopping list.
- `shop remove 2~5` : Removes the ingredients from index 2 to index 5 of the displayed shopping list.

> ℹ️ **INFO**
> Only ',' and and '~' works.

#### 6.1.6 Clearing the shopping list : `shop clear`
Clears the whole shopping list to be an empty list.

Format: `shop clear`

Example: `shop clear`

#### 6.1.7 Filter the shopping list : `shop list`
Only shows the shopping list with ingredients of quantity >0.

Format: `shop list`

Example: `shop list`

> ℹ️ **INFO**
> 1. To go back to the original shopping list that shows all ingredients regardless of quantity, input `shop` command.
> 2. Once you use this command, edit, remove, and buy commands will be based on the indices of this filtered list unless you go back to the original list.


#### 6.1.8 Buying ingredients : `shop buy`
Transfers ingredients and its quantity from the shopping list to the inventory list. This command also sends the total cost of the ingredients bought to the sales management system which automatically generates a sales transaction.

Format: `shop buy INDICES`

Examples:
- `shop buy 1` : sets the ingredient’s quantity at index 1 of the displayed shopping list to “0.0” and adds the ingredient to the inventory list.
- `shop buy 2,3` : sets the ingredient’s quantity at index 2 and 3 of the displayed shopping list to “0.0” and adds the ingredients to the inventory list.
- `shop buy 2~4` : sets the ingredient’s quantity from index 2 to 4 of the displayed shopping list to “0.0” and adds the ingredients to the inventory list.

> ℹ️ **INFO**
If the ingredient already exists in the inventory list, its quantity will then be added. 


### 6.4 Order List

#### 6.4.1 Order Commands Overview
BakingHome’s order management commands allow you to manage your orders easily. 

An order command can have various parameters as illustrated in the table below:
| Parameter Name | Description | Remarks |
| :---: | :--- | :--- |
| `ID` | Unique identifier of an order | Cannot be changed once order is created |
| `NAME` | Name of the customer | Non-blank and within 20 characters. Default "customer". |
| `CONTACT` | Contact of the customer| Can be a number or string. Non-blank and within 20 characters. Default "N/A".  |
| `DEADLINE` | Date of delivery or pickup | Default curent time. |
| `ITEM` | Product ordered, including product name and quantitity | Product must exist in Product List; Quantity is a double within 0 to 50,000.0. Default empty |
| `STATUS` | Status of the product. Can be either `Active`, `Completed` or `Canceled` | Once the status of a product is marked as Completed, you cannot change it. Default `Active`. |
| `TOTAL` | Total price of the order | Default value is the total of item retial prices|
| `REMARKS` | Additional notes regarding the order |Cannot exceed 50 characters |

#### 6.4.2 Showing order list : `order`
Displays a list of orders with then specifed `status` and their indices.
If `status` is not specified, all orders will be displayed.

Format: `order {-status STATUS}`
Examples: 
- `order`
- `order -status active`
#### 6.4.3 Adding an order : `order add`
Adds a new order to the order list.

Format: `order add  {-name NAME} {-contact CONTACT} {-by DEADLINE} {-rmk REMARKS} {-item ITEM_NAME, QUANTITY} {-total TOTAL}...`

Examples: 
- `order add` : adds an empty order.
- `order add -item bread, 2` : adds an order with one item
- `order add -name Jiajun -contact 12345678 -deadline tomorrow -item Croissant, 6 -item Toast, 10 -rmk Customer is allergic to nuts` : adds an order with all fields present
> ⚠️ **WARNING**
Once added, an order will not change even if the details of the products it contains change in Product List. This helps prevent accidental change to order information.
For example, if the retail price of a product is raised, the total price of the orders that contains this product will not be affected by the change.

> ℹ️ **INFO**
`total` is calculated according to to the retail prices of all products if not specified. In this way, you do not have the hassle to calculate total prices by yourself everytime.

#### 6.4.4 Editing an order : `order edit`
Edits the details of orders at `INDEX`.

Format: `order edit INDEX {-name NAME} {-contact CONTACT} {-by DEADLINE} {-rmk REMARKS} {-item PRODUCT_NAME, QUANTITY}...`

Examples:
- `order edit 1 -name jj`
- `order edit 2 -item milk, 3`


#### 6.4.5 Deleting an order : `order remove`
Deletes orders at the specified `INDICES`

Format: `order remove INDICES`

Examples:
- `order remove 1`
- `order remove 1~9`
- `order remove 1,3,4`

#### 6.4.6 Completing an order : `order done`
Marks orders at `ORDER_INDICES` as `Completed`.

Format: `order done INDICES`
Examples:
- `order done 1`
- `order done 1~9`
- `order done 1,3,4`

> ⚠️ **WARNING**
Once marked as `Completed`, an order cannot be changed using `order edit`. 

> ℹ️ **INFO**
Upon completing an order, the ingredient used by this order will be deducted from the inventory. If ingredients in inventory are not enough, the corresponding ingredients will be deducted to zero.

### 6.4.7 Checking Order Status
//TODO


### 6.5 Sale List

#### 6.5.1 Sale Commands Overview
Baking Home's Sale Management System allows for easy access and viewing of the bakery's finances. Simple commands allow the user to make quick edits on the details of any sales entry.

Some of the various parameters are detailed in the table below:
| Parameter Name | Description | Remarks |
|-|-|-|
| `ID` | Unique identifier of a sale |
| `DESCRIPTION` | Details of Transaction |
| `VALUE` | Total value of the sale | Positive number to 2 decimal points |
| `IS-SPEND` | Whether transaction is expenditure | Only true or false |
| `SALE-DATE` | Date of transaction |
| `REMARKS` | Additional comments |

#### 6.5.2 Showing sale list : `sale`
Brings the user to Sale Page from anywhere on the application.
Also resets to show all sale entries when in filter mode.

Format: `sale`
Examples:
- `sale`

#### 6.5.3 Adding a sale : `sale add`

Format: `sale add  {-desc DESCRIPTION} {-val VALUE} {-spend IS-SPEND} {-at SALE-DATE} {-rmk REMARKS}` (-spend is used to mark expenditures)
Examples:
- `sale add -desc blueberry pie -val 12.99 -at 30/10/2019 08:31 -rmk added chocolate toppings`
- `sale add -desc 7 cartons of soy milk purchased -val 14.49 -spend true -at 29/05/2018 19:97 -rmk to replace milk on new menu`

#### 6.5.4 Editing a sale: `sale edit`
Format: `sale edit INDEX {-desc DESCRIPTION} {-val VALUE} {-at SALEDATE} {-rmk REMARKS}`
Examples:
- `sale edit 1 -val 9.99 -rmk TGIF discount`
- `sale edit 2 -desc 5 cartons of soy milk -val 10.35 -rmk revised quantity from 7`

#### 6.5.5 Removing a sale : `sale remove`
Format: `sale remove INDEX`
Examples:
- `sale remove 1`
- `sale remove 4`
- `sale remove 9`

#### 6.5.6 Filtering sale (between dates): `sale filter`
Format: `sale filter -from START-DATE -to END-DATE`
Examples:
- `sale filter -from 05/09/2014 -to 13/10/2014`
- `sale filter -from 23/10/2014 19:00 -to 13/10/2014 19:00`

## 7. Advanced Features
This section introduces some advanced features that promote your productivity.

### 7.1 Redo and Undo

#### 7.1.1 Undo a command : `undo`
Undo a previous undoable command
Format: `undo`

#### 7.1.2 Redo a command : `redo`
Redo a previous undone command
Format: `undo`

> ℹ️ **INFO** Undoable commands are commands that change BakingHome's data, such as `add`, `remove`, and `edit`. Commands that do not change data, such as `list` are not undoable.
### 7.2 AutoCompelete
The AutoComplete feature saves you from the trouble of remembering all the command parameters by automatically predicting what they want to type.

#### Invoking AutoComplete:
Press `Tab` key to invoke AutoComplete. AutoComplete will automatically fill the parameter or command word you want to enter.
If there are mutiple suggestions available, you can navigate among them by repeatedly pressing the `Tab` key.

### 7.3 Shortcut

Shortcut provides a quick way to get tasks done without the hassle of entering commands one by one. It allows you to execute a series of commands by entering only one command.  You can create your own shortcuts in BakingHome.

#### 7.3.1 Setting a shortcut: `short`
Creates or deletes a shortcut.

Format: `short SHORTCUT_NAME COMMAND_STRING`
- `SHORTCUT_NAME` is the identifier of the shorcut. It should not contain spaces or dashes.
- `COMMAND_STRING` can take three forms:
    - Empty
    - Single command. For example, `order add`
    - Multiple commands separated by `;`. For example, `order add; order show`
> ⚠️ **WARNING**
To prevent infinity loops, `COMMAND_STRING` cannot contain `do` command.

> ℹ️ **INFO**
>- If `SHORTCUT_NAME` has already been set and `COMMAND_STRING` is not empty, the new `COMMAND_STRING` will overwrite the old one.
>- If `SHORTCUT_NAME` has already been set and `COMMAND_STRING` is empty, the corresponding shortcut will be removed.


Examples:
- `short demo order add` : adds a new shortcut `demo` with one command.
- `short demo order add -name jj; order remove 1` : overwrites `demo` with two commands.
- `short demo` : removes the `demo` shortcut.

#### 7.3.2 Executing a shortcut: `do`
Execute a shortcut. The commands in a shortcut will be executed in the order they were added.

Format: `do SHORTCUT_NAME`
Examples:
```
//First, create a shortcut with 2 commands
short demo product add -name cake; order add -item cake, 1

//Then, executes the shortcut.
//A new order and a new product will appear in BakingHome
do demo
```

#### 7.3.3 Viewing shortcuts:
You cannot view your shortcut list directly from the BakingHome app. However, you can check them in the data file.


### 8. Saving the data

BakingHome data are saved automatically after any command that changes the data. The data file is located at `data/baking.json`

There is no need to save manually.
> ⚠️ **WARNING**
> Edit data file with caution. Although you can edit the data file directly, it could damage the data file. Whenever BakingHome detects a damaged file, it will override that file and you may lose you data.
> 
### 9. FAQ
**Q:** How do I transfer my data to another Computer?
**A:** Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous BakingHome folder.


