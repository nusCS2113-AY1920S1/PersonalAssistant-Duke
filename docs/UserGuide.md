# BakingHome - User Guide

- [BakingHome - User Guide](#bakinghome---user-guide)
  - [1. Introduction](#1-introduction)
  - [2. Using the Guide](#2-using-the-guide)
  - [3. Quick Start](#3-quick-start)
  - [4. BakingHome Overview](#4-bakinghome-overview)
    - [4.1 Entities](#41-entities)
    - [4.2 Feature Highlights](#42-feature-highlights)
  - [5. Command Format](#5-command-format)
    - [5.1 General Format](#51-general-format)
    - [5.2 Selecting Entries](#52-selecting-entries)
    - [5.3 Date and Time](#53-date-and-time)
  - [6. Basic Commands](#6-basic-commands)
    - [6.1 Inventory](#61-inventory)
      - [6.1.1 Inventory Overview](#611-inventory-overview)
      - [6.1.2 Switching to inventory interface : `inv`](#612-switching-to-inventory-interface--inv)
      - [6.1.3 Adding an ingredient : `inv add`](#613-adding-an-ingredient--inv-add)
      - [6.1.4 Editing an ingredient : `inv edit`](#614-editing-an-ingredient--inv-edit)
      - [6.1.5 Deleting an ingredient : `inv remove`](#615-deleting-an-ingredient--inv-remove)
      - [6.1.6 Clearing the inventory list : `inv clear`](#616-clearing-the-inventory-list--inv-clear)
    - [6.2 Product List](#62-product-list)
      - [6.2.1 Product List Overview](#621-product-list-overview)
      - [6.2.2 Switching to product list interface : `product`](#622-switching-to-product-list-interface--product)
      - [6.2.3 Adding a new product : `product add`](#623-adding-a-new-product--product-add)
      - [6.2.4 Editing a product: `product edit`](#624-editing-a-product-product-edit)
      - [6.2.5 Listing products `product filter`](#625-listing-products-product-filter)
      - [6.2.6 Showing ingredients needed for a product `product show`](#626-showing-ingredients-needed-for-a-product-product-show)
      - [6.2.7 Deleting an product : `product remove`](#627-deleting-an-product--product-remove)
      - [6.2.8 Searching products `product search`](#628-searching-products-product-search)
      - [6.2.9 Sorting products `product sort`](#629-sorting-products-product-sort)
    - [6.3 Shopping List](#63-shopping-list)
      - [6.3.1 Shopping List Overview](#631-shopping-list-overview)
      - [6.3.2 Switching to shopping list interface : `shop`](#632-switching-to-shopping-list-interface--shop)
      - [6.3.3 Adding an ingredient : `shop add`](#633-adding-an-ingredient--shop-add)
      - [6.3.4 Editing an ingredient : `shop edit`](#634-editing-an-ingredient--shop-edit)
      - [6.3.5 Deleting an ingredient : `shop remove`](#635-deleting-an-ingredient--shop-remove)
      - [6.3.6 Clearing the shopping list : `shop clear`](#636-clearing-the-shopping-list--shop-clear)
      - [6.3.7 Filter the shopping list : `shop list`](#637-filter-the-shopping-list--shop-list)
      - [6.3.8 Buying ingredients : `shop buy`](#638-buying-ingredients--shop-buy)
    - [6.4 Order List](#64-order-list)
      - [6.4.1 Order List Overview](#641-order-list-overview)
      - [6.4.2 Showing order list : `order`](#642-showing-order-list--order)
      - [6.4.3 Adding an order : `order add`](#643-adding-an-order--order-add)
      - [6.4.4 Editing an order : `order edit`](#644-editing-an-order--order-edit)
      - [6.4.5 Deleting an order : `order remove`](#645-deleting-an-order--order-remove)
      - [6.4.6 Completing an order : `order done`](#646-completing-an-order--order-done)
      - [6.4.7 Sorting orders: `order sort`](#647-sorting-orders-order-sort)
      - [6.4.8 Checking Order Status](#648-checking-order-status)
        - [Inventory Status](#inventory-status)
        - [Deadline Status](#deadline-status)
        - [Order Statistics](#order-statistics)
    - [6.5 Sale List](#65-sale-list)
      - [6.5.1 Sale Commands Overview](#651-sale-commands-overview)
      - [6.5.2 Showing sale list : `sale`](#652-showing-sale-list--sale)
      - [6.5.3 Adding a sale : `sale add`](#653-adding-a-sale--sale-add)
      - [6.5.4 Editing a sale: `sale edit`](#654-editing-a-sale-sale-edit)
      - [6.5.5 Removing a sale : `sale remove`](#655-removing-a-sale--sale-remove)
      - [6.5.6 Filtering sale (between dates): `sale filter`](#656-filtering-sale-between-dates-sale-filter)
    - [6.6 Exiting BakingHome: `exit`](#66-exiting-bakinghome-exit)
  - [7. Advanced Features](#7-advanced-features)
    - [7.1 Redo and Undo](#71-redo-and-undo)
      - [7.1.1 Undo a command : `undo`](#711-undo-a-command--undo)
      - [7.1.2 Redo a command : `redo`](#712-redo-a-command--redo)
    - [7.2 AutoComplete](#72-autocomplete)
      - [Invoking AutoComplete:](#invoking-autocomplete)
    - [7.3 Shortcut](#73-shortcut)
      - [7.3.1 Setting a shortcut: `short`](#731-setting-a-shortcut-short)
      - [7.3.2 Executing a shortcut: `do`](#732-executing-a-shortcut-do)
      - [7.3.3 Viewing shortcuts:](#733-viewing-shortcuts)
    - [7.4 Hot Keys](#74-hot-keys)
  - [8. Managing your data](#8-managing-your-data)
  - [9. FAQ](#9-faq)

<div style="page-break-after: always;"></div>

# BakingHome - User Guide

## 1. Introduction
BakingHome is a one-stop application for managers of home bakeries to manage their business efficiently. It includes features for products, orders, inventory, shopping list, and sales. BakingHome is designed for users whose workflow is primarily keyboard-based.

## 2. Using the Guide
Welcome to BakingHome User Guide! This User Guide provides detailed documentation of BakingHome's features and commands. Besides, this User Guide also provides a quick way to set up BakingHome.

Here are the conventions this User Guide follows:

- `mark-up` text represents commands or arguments. For example, `order add`.

- > ⚠️ **WARNING** This box represents a warning.

- > ℹ️ **INFO** This box represents additional information.
- > 🍭 **HIGHLIGHT** This box represents a highlighted feature.

No prior knowledge is needed to read this User Guide. Enjoy!

## 3. Quick Start
1. Ensure you have Java 11 installed on your computer. 
> ⚠️ **WARNING** BakingHome does not support Java 12.
3. Download the latest `BakingHome.jar` [here](https://github.com/AY1920S1-CS2113T-T12-3/main/releases).
4. Copy the file to the folder you want to use as the home folder for you BakingHome. It is advised to create a new folder for this app as there will be a data file generated when you start to use it. 
5. Run the app in the command line by entering `java -jar PATH_OF_BAKINGHOME`. The Graphic User Interface (GUI) should appear in a few seconds.
![](https://i.imgur.com/S1hwFE4.png)

6. Type the command in the command box and press Enter to execute it. Here are a few examples you can try. 

- `inv add -name flour -qty 5 -rmk kg`: adds 5kg of flour into the inventory list
- `inv edit 1 -qty 10`: edits the quantity of the first ingredient to be 10
- `inv clear`: clears the whole inventory list to be an empty list.
- `product add -name Egg tart -ingt [Egg, 2] [Flour, 1] -cost 1 -price 2`
- `shop add -name flour -qty 5 -cost 5 -rmk in kg` : adds 5kg of flour into the shopping list and the unit cost is $5 per kg.
- `sale add -desc blueberry pie -val 12.99 -at 30/10/2019 08:31 -rmk added chocolate toppings`
7. Refer to Section 6. Basic Commands for more details.

## 4. BakingHome Overview
This section provides an overview of BakingHome.

### 4.1 Entities
BakingHome supports four types of entities: **Ingredient**, **Product**, **Order**, **Sale**. 

Here is an overview of the entities. 

- Ingredient is the most basic entity in BakingHome. It has a name and a unitless quantity. Some examples are `egg, 4` and `flour, 10`.
- Product is an item to sell in the bakery. It requires one or more ingredients to bake. Examples are `bread` and `cake`.
- Order is a collection of products requested by a customer. For example, `five bread and two cakes ordered by Mr. Yang`.
- Sale is a transaction. It can be either income or expenditure.

### 4.2 Feature Highlights

BakingHome allows you to manage your products, orders, inventory, shopping list, and sales easily. Besides adding, deleting and editing the entities, BakingHome also supports:

- One step adding insufficient ingredients to the shopping list. 
- Automatically adding a sale entry upon completing an order or buying from the shopping list.
- Dynamically displaying inventory status (sufficient/insufficient ingredients) in an order.
- Productivity features: Undo, Redo, Autocomplete and Shortcut features that boost your productivity.

## 5. Command Format
This section specifies the formats you need to follow when entering a command.

### 5.1 General Format
- Words in UPPER_CASE are the parameters to be supplied by the user.
- Items in curly brackets are optional e.g ``-name NAME {-status STATUS}`` can be used as `-name cake -status archive` or as `-name cake`.
- Items with `...` after them can be used zero or more times
- If items without `...` after them are used more than one time, only the last one will be taken. 
- Parameters can be in any order.
- `-` is a reserved symbol for BakingHome, so it cannot be used in parameter values. For example, a product cannot have the name `cheese-cake`.
- BakingHome does not allow exact duplicates of names. For example, if "egg" is already in the inventory list, "egg" cannot be added to the list but its plural form "eggs" can (as they have different naming). It is up to your discretion to name your objects.

### 5.2 Selecting Entries
In BakingHome, an `INDEX` is a positive integer representing an entry. You can select the entries you want to manage by specifying their indices. This can be done easily in three ways:
- Select a single `INDEX`: For example, `1`,`2`
- Select multiple `INDICES` with `,` : `INDEX1,INDEX2,INDEX3...`. For example, `1,2,3`, `2,5,8`
- Select a range of `INDICES` with `~`: `START~END`. `START` should not be greater than `END`. Examples: `1~8`, `9~10`

Note that some commands only allow you to select a single `INDEX`, while others allow you to select multiple `INDICES`

### 5.3 Date and Time
BakingHome uses your local time zone. A date should be entered in the format `dd/MM/yyyy HH:mm`. For example, `31/09/2019 18:00`.


## 6. Basic Commands
This section introduces the basic and most frequently used commands to get you started.

### 6.1 Inventory

#### 6.1.1 Inventory Overview
BakingHome's inventory management system allows easy management of ingredients in stock. An inventory command can have various parameters as illustrated in the table below: 
| Parameter Name | Description | Remarks |
| :--- | :--- | :--- |
| `INGREDIENT` | Name of the ingredient | - Name can take any value but cannot be empty. </br> - No two ingredients can have the same exact name. </br> - Cannot exceed 20 characters. |
| `QUANTITY` | Quantity of the ingredient in the inventory list  | - The default quantity is set to 0.0 if not specified. </br> - Cannot exceed 50,000.0|
| `REMARKS` | Additional remarks regarding the ingredient |Cannot exceed 50 characters. |

#### 6.1.2 Switching to inventory interface : `inv`
Displays the inventory list.

Format: `inv`

Example: `inv`

#### 6.1.3 Adding an ingredient : `inv add`
Adds a new ingredient to the inventory list.

Attribute | Corresponding Parameter | Default value | 
:--: | :---: | :---: |  
`-name` | `INGREDIENT`| Compulsory input | Cannot exceed 20 characters. |
`-qty` | `QUANTITY` | 0.0 | 
`-rmk` | `REMARKS` | No default value |

Format: `inv add -name INGREDIENT {-qty QUANTITY} {-rmk REMARKS}`

Examples: 
- `inv add -name egg` : adds egg into the inventory list with default quantity 0.0
- `inv add -name flour -qty 5 -rmk qty in kg` : adds 5kg of flour into the inventory list

> ℹ️ **INFO**
> The default quantity is "0.0" if there is no input parameter for it.

> ⚠️ **WARNING**
> 1. Adding ingredients will not work if it already exists in the inventory list. To add the quantity, use the edit command which is shown below instead.
> 2. `-qty` only accepts numbers as valid inputs.

#### 6.1.4 Editing an ingredient : `inv edit`
Edits the details of the ingredient at `INDEX`.

Format: `inv edit INDEX {-name INGREDIENT} {-qty QUANTITY} {-rmk REMARKS}`

Examples:
- `inv edit 1 -qty 5` : edits the quantity of the 1st ingredient to be 5
- `inv edit 3 -qty 10 -rmk in kg` : edits the 3rd ingredient’s quantity to be 10 and remarks to be “in kg”

> ℹ️ **INFO**
> Use the `INDEX` shown as displayed on the inventory list.

> ⚠️ **WARNING**
 Input parameters can be interchanged but `INDEX` must come after `inv edit` 
> 

#### 6.1.5 Deleting an ingredient : `inv remove`
Deletes an ingredient at the specified `INDICES`.

Format: `inv remove INDICES`

Examples:
- `inv remove 1` : Removes the ingredient at index 1 of the displayed inventory list.
- `inv remove 4,5` : Removes the ingredients at index 4 and index 5 of the displayed inventory list.
- `inv remove 2~5` : Removes the ingredients from index 2 to index 5 of the displayed inventory list.

> ℹ️ **INFO**
> Only ',' and '~' work.

#### 6.1.6 Clearing the inventory list : `inv clear`
Clears the whole inventory list to be an empty list.

Format: `inv clear`

Example: `inv clear`

> ⚠️ **WARNING**
> If you accidentally cleared the inventory list and want to recover the data back, you can execute the `undo` command by inputting "undo" into the command line.  

### 6.2 Product List

#### 6.2.1 Product List Overview
BakingHome's Product List records the selling products. It has various parameters for you to record the details of a product. Here is a summary:
| Parameter Name | Description | Remarks |
| :--- | :--- | :--- |:---|
| `NAME`| Name of the product | - Name cannot be empty. <br> - Cannot exceed 50 characters</br> - No two products can have the exact same name.<br> - The first letter will be capitalized and all remaining will be set to lower case. |
| `PRICE`| Retail price of the product| Default $0.0 if not specified|
| `INGREDIENTS` | A list of ingredients needed and their portion to make the product. | - `INGREDIENT` has two sub-parameters, input in the format:[`INGREDIENT_NAME`,`PORTION_NUMBER`] where `PORTION_NUMBER` is the protion needed for the ingredient.
| `COST` | The cost of the product | Calculated based on the cost of the ingredients as specified in the Shopping List. $0.0 if not specified and no ingredients included.|
| `STATUS`| Status of the product. It can be either `ACTIVE` or `ARCHIVE` | A newly added product can only have `ACTIVE` status. |



#### 6.2.2 Switching to product list interface : `product`

Format: `product`


#### 6.2.3 Adding a new product : `product add`
Adds a product to the product list. 

|Attribute|Corresponding Parameter|Default Value|
|:---|:---|:---|
|`-name`|`NAME`|Compulsory input|
|`-price`|`PRICE`|0.0|
|`-ingt`| `INGREDIENTS`|Default empty <br> - For input format`[INGREDIENT_NAME]` `PORTION_NUMBER` is set to 0.0 by default|
|`-cost`|`COST`|Calculated ingredient cost|
Format: `product add -name PRODUCT_NAME {-ingt [INGREDIENT_NAME, PORTION_NUMBER]...} {-cost COST} {-price RETAIL_PRICE}`

Examples:
-  `product add -name cake` : adds a "cake" product. The name "cake" will be changed to "Cake" by the system. Both cost and price will be set to $0.0.
-  `product add -name Bread -ingt [Flour, 1] [Sugar, 5]` : adds a Bread product which costs $4 and will be sold at a retail price of $5.5.
-  `product add -name Egg tart -ingt [Egg, 2] [Flour, 1] -cost 1 -price 2`: adds a product named Egg tart that requires 2 portions of Egg and 1 portion of Flour to make. It costs $1 to make and the retail price is $2.

-  `product add -name Cheese cake -ing [Cream cheese, 3] [Sugar, 3] [Butter milk, 1] -cost 3.0 -price 5.9`: adds a new product with all attributes present. 

   

> 🍭 **HIGHLIGHT**
> Try out these **highly automated features！**
> 1. Not sure about the cost upon adding a product? Don't worry, the system helps you generate one based on the ingredients given for your reference. i.e. in the above examples, if both 1 Flour costs $5, and 1 Sugar costs $1, then the system will assign an ingredient cost of $10 to Bread.
> 2. If the ingredients in the product are not in the current shopping list, the system will also automatically help you add a corresponding shopping list entry.


> ⚠️ **WARNING**
>1. Ingredients with the same name can be input multiple times but only the last one will be recorded. For example, for `[Butter, 3] [Butter, 2]` or `[Butter,3] [Sugar, 5] [Butter, 2]`, then quantity recorded will be 2.
>2. Once added, ingredient list in a product will not change even if the ingredients it contains are changed in Shopping List. This helps to retain the original product information. For example, changing the ingredient name of `Sugar` to `Brown sugar` will not affects the ingredients stored in `Cheese cake`.

> ℹ️ **INFO**
Take note that a `product add` command does not take in -status parameter, i.e. a newly added product can only have *ACTIVE* status. To add an archived product, you can:
    1. Add the product.
    2. Use the `product edit` command (illustrated below) to edit the status.
#### 6.2.4 Editing a product: `product edit`

Edits the details of an existing product at `INDEX`.

|Attribute|Corresponding Parameter|
|:---|:---|:---|
|`-name`|`NAME`|
|`-price`|`PRICE`|
|`-ingt`| `INGREDIENTS`|
|`-cost`|`COST`|
|`-status`|`STATUS`

Format: `product edit INDEX {-name PRODUCT_NAME} {-ingt [INGREDIENT_NAME, PORTION_NUMBER]...} {-cost COST} {-price RETAIL PRICE}`

Example:
- `product edit 1 -name Small cheese cake` : edits the name of the first product in the list to be "Small cheese cake".
-  `product edit 2 -name Large egg tart -ingt [Egg, 4] [Flour, 2]` : edits both the name and the ingredients needed. 

> ⚠️ **WARNING**
If you want to change the ingredient fields, you have to include all ingredients needed again. The system will **regenerate** the ingredients from the entry. i.e. `product edit -i 2 -name Large egg tart -ingt [Egg, 4]` indicates that Large egg tart only needs 4 portions of Egg. 


#### 6.2.5 Listing products `product filter`
Filters product with specified scope. This command takes in only one parameter `-scope` which can be `all`, `active` or `archive`. 

|Attribute|Possible Values| Default Value| 
|:---|:---|:---|
|`-scope`|`all`, `active`, `archive` <br>- case insensitive|`active`|

Format: `product filter -scope SCOPE`
Example: 
- `product filter -scope archive` : lists all archived products.

#### 6.2.6 Showing ingredients needed for a product `product show`

Shows the ingredients needed of a product at `INDEX`. The interface is shown below.
Format: `product show INDEX` 
Example: `product show 6`

![](https://i.imgur.com/U13pS6M.png)





> ⚠️ **WARNING**
If you edit the currently showing product, remember to refresh the page to see the changes.


#### 6.2.7 Deleting an product : `product remove`
Deletes products at the specified INDICES.

Format: `product remove INDICES`

Examples:
`product remove 1`
`product remove 1~4`
`product remove 1,3,4`

#### 6.2.8 Searching products `product search`
Lists products whose name includes the specified keyword.

|Attribute|Default Value| 
|:---|:---|
|`-include`|Compulsory input|


Formate: `product search -include KEYWORD`

Example:
`product search -include Tart`

#### 6.2.9 Sorting products `product sort`
Sorts products of different scope according to categories, in either ascending or descending order.

Category is specified by `-by` parameter.
Default sorting order is ascending. Specify `-re` in the command if you want to sort in acsending order.

|Attribute|Possible Values| Defult Value
|:---|:---|:---
|`-by`|`name`, `price`, `cost`,`profit` <br>- profit = (price - cost)|Compulsory input|
|`-scope`|`all`, `active`, `archive` <br> - case insensitive<br> |`active`|
|`-re`|Do not input any value for this parameter|Descending order

Format: `product sort -by CATEGORY {-scope SCOPE} {-re}`
Example: 
- `product sort -by name` sorts active products by name in descending order.
- `product sort -by profit -scope active` sorts active products by profit in descending order.
- `product sort -by cost -scope all -re` sorts all products by cost in ascending order.

### 6.3 Shopping List

#### 6.3.1 Shopping List Overview
BakingHome's shopping management system allows easy tracking of ingredients to buy and its cost. A shopping command can have various parameters as illustrated in the table below: 
| Parameter Name | Description | Remarks |
| :--- | :--- | :--- |
| `INGREDIENT` | Name of the ingredient | - Name can take any value but cannot be empty. </br> - No two ingredients can have the same exact name. </br> - Cannot exceed 20 characters. |
| `QUANTITY` | Quantity of the ingredient in the inventory list  | - The default quantity is set to 0.0 if not specified. </br> - Cannot exceed 50,000.0 |
| `REMARKS` | Additional remarks regarding the ingredient | Cannot exceed 50 characters |
| `COST` | The unit cost of the ingredient | - The default unit cost is set to $0.0 if not specified. </br> - Cannot exceed 10,000.0 |

#### 6.3.2 Switching to shopping list interface : `shop`
Displays the shopping list.

Format: `shop`

Example: `shop`

#### 6.3.3 Adding an ingredient : `shop add`
Adds a new ingredient to the shopping list.

Attribute | Corresponding Parameter | Default value | 
:--: | :---: | :---: | 
`-name` | `INGREDIENT`| Compulsory input |
`-qty` | `QUANTITY` | 0.0 | 
`-cost` | `COST` | 0.0 |
`-rmk` | `REMARKS` | No default value |

Format: `shop add -name INGREDIENT {-qty QUANTITY} {-cost COST} {-rmk REMARKS}`

Examples: 
- `shop add -name egg` : adds egg into the shopping list with default quantity 0.0 and default unit cost $0.0
- `shop add -name flour -qty 5 -cost 5 -rmk in kg` : adds 5kg of flour into the shopping list and the unit cost is $5 per kg.

> ℹ️ **INFO**
> The default quantity is "0.0" and the default unit cost is "0.0" if there are no input parameters for it.

> ⚠️ **WARNING**
> 1. Adding ingredients will not work if it already exists on the shopping list. To add the quantity, use the edit command which is shown below instead.
> 2. `-qty` and `-cost` only accepts numbers as valid inputs.

> 🍭 **HIGHLIGHT**
> The total cost of the shopping list will be calculated by BakingHome and shown automatically on the UI.

#### 6.3.4 Editing an ingredient : `shop edit`
Edits the details of the ingredient at `INDEX`.

Format: `shop edit INDEX {-name INGREDIENT} {-qty QUANTITY} {-cost COST} {-rmk REMARKS}`

Examples:
- `shop edit 1 -qty 5` : edits the quantity of the 1st ingredient to be 5
- `shop edit 3 -qty 10 -cost 5` : edits the 3rd ingredient’s quantity to be 10 and unit cost to be $5

> ℹ️ **INFO**
> Use the `INDEX` shown as displayed on the shopping list 

> ⚠️ **WARNING**
> Input parameters can be interchanged but `INDEX` must come after `shop edit` 


#### 6.3.5 Deleting an ingredient : `shop remove`
Deletes an ingredient at the specified `INDICES`

Format: `shop remove INDICES`

Examples:
- `shop remove 1` : removes the ingredient at index 1 of the displayed shopping list.
- `shop remove 3,7` : removes the ingredients at index 3 and index 7 of the displayed shopping list.
- `shop remove 2~5` : Removes the ingredients from index 2 to index 5 of the displayed shopping list.

> ℹ️ **INFO**
> Only ',' and and '~' works.

#### 6.3.6 Clearing the shopping list : `shop clear`
Clears the whole shopping list to be an empty list.

Format: `shop clear`

Example: `shop clear`

> ⚠️ **WARNING**
> If you accidentally cleared the shopping list and want to recover the data back, you can execute the `undo` command by inputting "undo" into the command line.  

#### 6.3.7 Filter the shopping list : `shop list`
Only shows the shopping list with ingredients of quantity > 0.

Format: `shop list`

Example: `shop list`

> ℹ️ **INFO**
> 1. To go back to the original shopping list that shows all ingredients regardless of quantity, input `shop` command.
> 2. Once you use this command, edit, remove, and buy commands will be based on the indices of this filtered list unless you go back to the original list.


#### 6.3.8 Buying ingredients : `shop buy`
Transfers ingredients and their quantity from the shopping list to the inventory list. This command also sends the total cost of the ingredients bought to the sales management system which automatically generates a sales transaction.

Format: `shop buy INDICES`

Examples:
- `shop buy 1` : sets the ingredient’s quantity at index 1 of the displayed shopping list to “0.0” and adds the ingredient to the inventory list.
- `shop buy 2,3` : sets the ingredient’s quantity at index 2 and 3 of the displayed shopping list to “0.0” and adds the ingredients to the inventory list.
- `shop buy 2~4` : sets the ingredient’s quantity from index 2 to 4 of the displayed shopping list to “0.0” and adds the ingredients to the inventory list.

> ℹ️ **INFO**
If the ingredient already exists in the inventory list, its quantity will then be added. 
> 🍭 **HIGHLIGHT**
> Check out the Sales page to find that a sales transaction has already been generated for you by BakingHome automatically!

### 6.4 Order List

#### 6.4.1 Order List Overview
BakingHome’s order management feature allows you to manage your orders easily.

An order has various attributes as illustrated in the table below:

Attributes | Description | Remarks|
:--: | :---: | :--- | :---: | :--: |
| `ID` | Unique identifier of an order| Created automatically upon adding an order. Immutable. |
| `CREATION` | Date of creation of the order| Created automatically upon adding an order. Immutable. |
| `NAME` | Customer name | String. Non-blank and within 20 characters. |
| `CONTACT` | Customer contact | String. Non-blank and within 20 characters. |
| `DEADLINE` | Date of delivery or pickup | Past dates are allowed as well |
| `ITEM` | A product ordered | Including `ITEM_NAME` and `QUANTITY`. `ITEM_NAME` must exist in Product List; `QUANTITY` is a double between 0 and 50,000.0.
| `STATUS` | Status of the order | Can be either `Active`, `Completed` or `Canceled` (case-insensitive). Once the status of an order is marked as `Completed`, you cannot change that order (except possibly with `undo`). |
| `TOTAL` | Total price of the order | Double between 0 and 500,000 |
| `REMARKS` | Additional notes | Cannot exceed 50 characters |

> ℹ️ **INFO**
Currently, `QUANTITY` is unitless. You can specify unit in `REMARKS` to avoid confusion.

#### 6.4.2 Showing order list : `order`
Displays a list of orders with specified `STATUS` and their indices.
If `STATUS` is not specified, all orders will be displayed.

Format: `order {-status STATUS}`
Examples: 
- `order`
- `order -status active`
#### 6.4.3 Adding an order : `order add`
Adds a new order to the order list.

Format: `order add  {-name NAME} {-contact CONTACT} {-by DEADLINE} {-rmk REMARKS} {-status STATUS} {-item ITEM_NAME, QUANTITY} {-total TOTAL}...`

Attributes | Corresponding parameters | Default values 
:--: | :---: | :---: | :---: 
`-name` | `NAME`| "customer" | String. Non-blank and within 20 characters.|
`-contact` | `CONTACT` | "N/A"| String, non-blank and within 20 characters. |
`-by` | `DEADLINE` | current date | Past dates are allowed as well |
`-rmk` | `REMARKS` | "N/A" | Cannot exceed 50 characters |
`-item` | `ITEM`| Not applicable. Both `ITEM_NAME` and `QUANTITY` need to be specified | `ITEM_NAME` must exist in Product List; `QUANTITY` is a double between 0 and 50,000.0. |
`-total` | `TOTAL` | The total retail prices of items ordered | Double between 0 and 500,000 
`-status`| `STATUS`| `Active` | 

Examples: 
- `order add`
- `order add -item Cookie, 2`
- `order add -name Jiajun -contact 12345678 -by 01/01/2019 18:00 -item Cookie, 6 -item Waffle, 10 -rmk Customer is allergic to nuts`

> ⚠️ **WARNING**
Once added, an order will not change even if the details of the products it contains change in Product List. This helps prevent accidental change to order information.
For example, if the retail price of a product is raised, the total prices of the orders that contain this product will not be affected by the change.
Similarly, if a product's ingredient use changes in Product List, the ingredient use of already-added products in Order List will not change.

> ⚠️ **WARNING**
Items should exist in Product list and has a status `ACTIVE` in Product List to be added into an order. Since product names in Product List are capitalized (first letter in upper case and other letters in lower case), order list will convert `ITEM_NAME` accordingly.

> ℹ️ **INFO**
You can add an order with no items or an item with a quantity zero.

> 🍭 **HIGHLIGHT**
`TOTAL` is calculated according to the retail prices of all products if not specified. In this way, you do not have to go through the hassle to calculate total prices by yourself every time.

#### 6.4.4 Editing an order : `order edit`
Edits the details of orders at `INDEX`.

Format: `order edit INDEX {-name NAME} {-contact CONTACT} {-by DEADLINE} {-rmk REMARKS} {-status STATUS} {-total TOTAL} {-item PRODUCT_NAME, QUANTITY}...`

Attributes | Corresponding Parameters 
:--: | :---: |
`-name` | `NAME`| 
`-contact` | `CONTACT` | 
`-by` | `DEADLINE` | 
`-rmk` | `REMARKS` |
`-item` | `ITEM`| 
`-total` | `TOTAL` | 
`-status`| `STATUS`|

Examples:
- `order edit 1 -name jj`
- `order edit 2 -item Cookie, 3`
- 
> ⚠️ **WARNING**
If you want to change the item fields, you have to include all items needed again. BakingHome will **regenerate** the items from the entry. i.e. `order edit 1 -item Cake, 1 -item Cookie, 2` indicates that this order only contains 1 serving of Cake and 2 servings of Cookie. 



#### 6.4.5 Deleting an order : `order remove`
Deletes orders at the specified `INDICES`

Format: `order remove INDICES`

Examples:
- `order remove 1`
- `order remove 1~9`
- `order remove 1,3,4`

#### 6.4.6 Completing an order : `order done`
Marks orders at `INDICES` as `Completed`.

Format: `order done INDICES`
Examples:
- `order done 1`
- `order done 1~9`
- `order done 1,3,4`

> ⚠️ **WARNING**
> - Once marked as `Completed`, an order cannot be changed using `order edit`. 
> - Only orders with status `Active` or `Canceled` can be completed. You cannot complete an order which has the status `Completed`. In other words, an order cannot be completed twice.

> 🍭 **HIGHLIGHT**
> Try out these highly-automated features!
> 
> Upon completing an order,
> - the ingredients used by this order will automatically be deducted from the inventory.
>     - If some ingredients are not enough in inventory, they will be deducted to zero. 
>     - If some ingredients are not available in inventory, they will not be deducted.
> - a sales entry corresponding to this order will be added automatically. 

#### 6.4.7 Sorting orders: `order sort`
Sorts orders by `CRITERON`. If `-re` is present, sorts in reversed order. Otherwise, sorts in default order.

Format: `order sort CRITERON {-re}`
- CRITERON should be one of the following: `status`, `deadline`, `total`, `creation` (case-insensitive).

| `CRITERON` | Descriptions | Default order |
| -------- | -------- | -------- |
| `status`     | sort by order statuses | `Active` comes before `Completed`. `Completed` comes before `Canceled`|
| `total` | sort by total prices | ascending prices
| `creation` | sort by order creation dates | the oldest to the newest creation date|
| `deadline` | sort by deadlines (dates of delivery or pick up)| First, sort by `status`. Then, the oldest to the newest deadline. |


#### 6.4.8 Checking Order Status
##### Inventory Status
If the inventory does not have enough ingredients to make the products in an order, a red `Insufficient Ingredients` label will be displayed on the corresponding order.

> ℹ️ **INFO**
> - The label is only displayed on `Active` orders.
> - Inventory status is computed separately for different orders. For example, if two orders require some same ingredients, and the ingredients in inventory are enough for either of them, but not both, the badge will not be displayed in any of the orders. 

##### Deadline Status
The deadline for an order, which is displayed near the clock icon, turns red if the deadline is before the current time.

##### Order Statistics 
A statistics bar is displayed on top of the order list showing the number of active, canceled, and completed orders in the order list.
> ℹ️ **INFO**
> If you have filtered the order list, for example, using `order show -status Active`, the statistics bar shows statistics for the filtered list.
> 
### 6.5 Sale List

#### 6.5.1 Sale Commands Overview
Baking Home's Sale Management System allows for easy access and viewing of the bakery's finances. Simple commands allow the user to make quick edits on the details of any sales entry.

Some of the various parameters are detailed in the table below:
| Attributes | Corresponding Parameter name | Default value | Remarks
|-|-|-|-|
|`-desc`| DESCRIPTION | "N/A" | No more than 50 characters. |
|`-val`| VALUE | 0.0 | Positive number no more than 50000. |
|`-spend`| ISSPEND | false| Only true or false | |
|`-at`| SALEDATE | Current system date and time | |
|`-rmk`| REMARKS | N/A | No more than 50 characters.
|`-from`| STARTDATE | | Filtered results do not include this date. |
|`-to`| ENDDATE | | Filtered results do not include this date. |

#### 6.5.2 Showing sale list : `sale`
Brings the user to Sale Page from anywhere on the application.
Also resets to show all sale entries when in filter mode.

Format: `sale`

Examples:`sale`

#### 6.5.3 Adding a sale : `sale add`
Adds a sale entry with the given parameters. Any parameters that are not given will be assigned default value.

Format: `sale add  {-desc DESCRIPTION} {-val VALUE} {-spend ISSPEND} {-at SALEDATE} {-rmk REMARKS}` (-spend is used to mark expenditures).

Examples:
- `sale add -desc blueberry pie -val 12.99 -at 30/10/2019 08:31 -rmk added chocolate toppings`
- `sale add -desc 7 cartons of soy milk purchased -val 14.49 -spend true -at 29/05/2018 19:97 -rmk to replace milk on new menu`

#### 6.5.4 Editing a sale: `sale edit`
Edits the sale entry at a given `INDEX` with given parameters.

Format: `sale edit INDEX {-desc DESCRIPTION} {-val VALUE} {-spend ISSPEND} {-at SALEDATE} {-rmk REMARKS}`

Examples:
- `sale edit 1 -val 9.99 -rmk TGIF discount`
- `sale edit 2 -desc 5 cartons of soy milk -val 10.35 -rmk revised quantity from 7`

#### 6.5.5 Removing a sale : `sale remove`
Removes the sale entry at a given `INDEX`.

Format: `sale remove INDEX`

Examples:
- `sale remove 1`
- `sale remove 4`
- `sale remove 9`
- `sale remove 1~3`

#### 6.5.6 Filtering sale (between dates): `sale filter`
Shows a list of sale entries between a given `STARTDATE` and `ENDDATE`, not inclusive of these two dates.

Format: `sale filter -from STARTDATE -to ENDDATE`

Examples:
- `sale filter -from 09/05/2014 -to 13/10/2014`
- `sale filter -from 23/10/2014 19:00 -to 13/10/2014 19:00`

### 6.6 Exiting BakingHome: `exit`
This command is used to quit BakingHome.

Format: `exit`
Examples: `exit`
## 7. Advanced Features
This section introduces some advanced features that boost your productivity.

### 7.1 Redo and Undo

#### 7.1.1 Undo a command : `undo`
Undo a previous undoable command
Format: `undo`

#### 7.1.2 Redo a command : `redo`
Redo a previous undone command
Format: `undo`

> ℹ️ **INFO** 
> Undoable commands are commands that change BakingHome's data, such as `add`, `remove`, and `edit`. Commands that do not change data, such as `list` are not undoable.

### 7.2 AutoComplete
The AutoComplete feature saves you from the trouble of remembering all the command parameters by automatically predicting what they want to type.

#### Invoking AutoComplete:
Press `Tab` key to invoke AutoComplete. AutoComplete will automatically fill the prefixes or command word you want to enter where possible.

If there are multiple suggestions available, you can navigate among them by repeatedly pressing the `Tab` key.
> ⚠️ **WARNING**
> Some suggestions may not fit the context. For example, the input `order d` has two suggestions `order do` and `order done`. The former is not a valid command.

### 7.3 Shortcut

Shortcut provides a quick way to get tasks done without the hassle of entering commands one by one. It allows you to execute a series of commands by entering only one command.  You can create your own shortcuts in BakingHome.

#### 7.3.1 Setting a shortcut: `short`
Creates or deletes a shortcut.

Format: `short [SHORTCUT_NAME] [COMMAND_STRING]`
- `SHORTCUT_NAME` is the identifier of the shortcut. It should be a word.
    - A word cannot contain characters except for a-z, A-Z, 0-9, and the underscore character.
- `COMMAND_STRING` can take three forms:
    - Blank
    - A single command. For example, `order add`
        - The command should be non-blank and cannot contain semicolon or square brackets
    - Multiple commands. For example, `order add; order done 1`
        - The commands should be separated by `;`. Each command cannot contain semicolons or square brackets.   
        - **Invalid** examples:
            - `order add; order done 1;`
            - `;order add`
            - `;order add;`
            - `order add -name [invalid brackets] -rmk a;b ; order remove`
> ⚠️ **WARNING**
> - To prevent infinite loops, `COMMAND_STRING` cannot contain `do` commands.
> - `COMMAND_STRING` cannot contain square brackets.

> ℹ️ **INFO**
>- If `SHORTCUT_NAME` has already been set and `COMMAND_STRING` is valid, the new `COMMAND_STRING` will overwrite the old one.
>- If `SHORTCUT_NAME` has already been set and `COMMAND_STRING` is blank, the corresponding shortcut will be removed.
>- If `SHORTCUT_NAME` is not set and `COMMAND_STRING` is valid, the corresponding shortcut will be created.

> ℹ️ **INFO**
> Commands in command string are not verified at the time they are added. In other words, invalid commands (except for `do` commands) may be added into a shortcut using `short`. Commands are only verified when a shortcut is executed using `do` command.

Examples:
- `short [demo] [order add]` : adds a new shortcut `demo` with one command.
- `short [demo] [order add -name jj; order remove 1]` : overwrites `demo` with two commands.
- `short [demo] [ ]` : removes the `demo` shortcut.

#### 7.3.2 Executing a shortcut: `do`
Execute a shortcut. The commands in a shortcut will be executed in the order they were added.

Format: `do SHORTCUT_NAME`
Examples:
```
//First, create a shortcut with 2 commands
short [demo] [product add -name cake; order add -item cake, 1]

//Then, executes the shortcut.
//A new order and product will appear in BakingHome
do demo
```

#### 7.3.3 Viewing shortcuts:
You cannot view your shortcut list directly from the BakingHome app. However, you can check them in the data file.


### 7.4 Hot Keys 
If your cursor is in the command box, there are a few hot keys you can use.
Press `UP` and `DOWN` keys to navigate among input history. Press `ESC` key to close the popup.


## 8. Managing your data

BakingHome's data are saved automatically after any command that changes the data. The data file is located at `data/baking.json`

If the data file is missing or damaged, BakingHome will load the demo data.

> ⚠️ **WARNING**
> Edit data file with caution. Although you can edit the data file directly, it could damage the data file. 
> 
## 9. FAQ
**Q:** How do I transfer my data to another computer?

**A:** Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous BakingHome folder.

