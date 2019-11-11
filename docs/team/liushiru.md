# Liu Shiru - Project Portfolio for BakingHome

### About BakingHome
My team of 4 software engineering students and I were tasked to developed a basic command line interface desktop application for our Software Engineering project. We choose to developed a integrated Bakery Management System called BakingHome. BakingHome enables a family bakery manager to effectively manage all fundamental aspects of his business such as the products, inventory, shopping list, custoumers' order and sales. 

My role was to design and implement the Product feature from end to end. The following sections illustrate this feature in more detail, as well as the relevant documents.

This is what the product section looks like. 

![](https://i.imgur.com/U13pS6M.png)
*Figure 1. BakingHome User Interface*

<br>

The following are icons and format used in the portfolio.

- `mark-up` text represents code snippet, commands or arguments. For example, `product add -name Cheese cake`
- > ⚠️ **WARNING** This box represents a warning.
- > ℹ️ **INFO** This box represents additional information.
- > 🍭 **HIGHLIGHT** This box represents a highlighted feature.

### Summary of contributions
This section shows a summary of my coding, documentation, and other helpful contributions to the team project.


- **Code contributed:** Please click these links to see my [Functional code and Test code](https://nuscs2113-ay1920s1.github.io/dashboard/#search=liushiru&sort=groupTitle&sortWithin=title&since=2019-09-21&timeframe=commit&mergegroup=false&groupSelect=groupByRepos&breakdown=false&tabOpen=true&tabType=authorship&tabAuthor=liushiru&tabRepo=AY1920S1-CS2113T-T12-3%2Fmain%5Bmaster%5D)
  - Credits: the coding style follows an sample project [addressbook](https://github.com/se-edu/addressbook-level3).

- **Feature added:** I implemented the product section from end to end.

  - **Function:** The product section allows the user to document the product business with various details, such as the cost, retail price, ingredients needed and so on. 
  - **Justifications:** Products is one of the most important aspect of a business as it directly impacts on the profit. It is also the basis of other sections such as, the ingredients needed and the orders could be made. Therefore, a clean and easy way to manage the product sale is the first step to the success of a business.
  - **Highlights:** This feature is highly **automated**. When adding a new product entry, the system will provide a reference cost based on the ingredients needed. It will also generate new entries in the shopping list when there are new ingredients needed mentioned. 
 

**Other contibutions:**
- Report bugs and start discussion:  [1](https://github.com/AY1920S1-CS2113T-T12-3/main/issues/204), [2](https://github.com/AY1920S1-CS2113T-T12-3/main/issues/183).
-  Offer insights and [suggestions](https://github.com/AY1920S1-CS2113T-T12-3/main/issues/215) to group mate.
-  Respond to discussion with careful consideration. [See here](https://github.com/AY1920S1-CS2113T-T12-3/main/issues/143).
- Offer solutions to group mate's coding problem. 
  - When group mates seek help for coding problem, I always offer solutons. This [github issue](https://github.com/AY1920S1-CS2113T-T12-3/main/issues/218) is one example.

## _Contribution to the User Guide_
I wrote the summary table for the Product feature for the user to have a overall understanding about what parameters a product can take. It is reproduced as below:
### 6.2 Product List

#### 6.2.1 Product List Overview
BakingHome's Product List records the selling products. It has various attributes for you to record the details of a product.  
An product has various attributes as illustrated in the table below:
| Parameter Name | Description | Remarks |
| :--- | :--- | :--- |:---|
| `NAME`| Name of the product | - Name cannot be empty. <br> - Cannot exceed 50 characters</br> - No two products can have the exact same name. Note that `Egg tart` and `Egg tarts` are considered different products. You are advised to use singular.<br> - The first letter will be capitalized and all remaining will be set to lower case |
| `PRICE`| Retail price of the product| Default $0.0 if not specified|
| `INGREDIENTS` | A list of ingredients needed and their portion to make the product. | - `INGREDIENT` has two sub Parameter, input in the format:[`INGREDIENT_NAME`,`PORTION_NUMBER`]
| `COST` | The cost of the product | Calculated based on the cost of the ingredients as specified in the [Shopping List](). $0.0 if not specified and no ingredients included.|
| `STATUS`| Status of the product. It can be either `ACTIVE` or `ARCHIVE` | A newly added product can only have `ACTIVE` status. |

_______________________________________________________________


I also make the User Guide more interactive and engaging by addressing the user directly and using second pronoun. This is reflected in the INFORMATION, WARNING and HIGHLIGHT boxes in the product add section: 


#### 6.2.3 Adding a new product : `product add`
Adds a product to the product list. 

|Attribute|Corresponding Parameter|Default Value|
|:---|:---|:---|
|`-name`|`NAME`|Compulsory input|
|`-price`|`PRICE`|0.0|
|`-ingt`| `INGREDIENTS`|- Default empty <br> - For input`[INGREDIENT_NAME]` `PORTION_NUMBER` is set to 0.0 by default|
|`-cost`|`COST`|Calculated ingredient cost|
Format: `product add -name PRODUCT_NAME {-ingt [INGREDIENT_NAME, PORTION]}... {-cost COST} {-price RETAIL_PRICE}`

Examples:
-  `product add -name cake` : adds a "cake" product. The name "cake" will be changed to "Cake" by the system. Both cost and price will be set to $0.0.
-  `product add -name Bread -ingt [Flour, 1] [Sugar, 5]` : adds a Bread product which costs $4 and will be sold at a retail price of $5.5.
-  `product add -name Egg tart -ingt [Egg, 2] [Flour, 1] -cost 1 -price 2`: adds a product named Egg Tart that requires 2 portions of Egg and 1 portions of Flour to make. It costs $1 to make and the retail price is $2.

-  `product add -name Cheese cake -ing [Cream cheese, 3] [Sugar, 3] [Butter milk, 1] -cost 3.0 -price 5.9`: adds a new product with all parameter present. 

   

> 🍭 **HIGHLIGHT**
> Try out these **highly automated features！**
> 1. Not sure about the cost upon adding a product? Don't worry, the system helps you generate one based on the ingredients given for your reference. i.e. in the above examples, if both 1 Flour costs $5, and 1 Sugar costs $1, then the system will assign an ingredient cost of $10 to Bread.
> 2. If the ingredients in the product are not in the current shopping list, the system will also automatically help you add a corresponding shopping list entry.


> ⚠️ **WARNING**
>1. Each parameter can be input multiple times, but only the last parameter will take effect. For example, `product add -name name_1 -name name_2` add a new product with name "name_2".
>2. Ingredients with the same name can be input multiple times but only the last one will be recorded. For example, for `[Butter, 3] [Butter, 2]` or `[Butter,3] [Sugar, 5] [Butter, 2]`, then quantity recorded will be 2.
>3. Once added, ingredient list in a product will not change even if the ingredients it contains are changed in Shopping List. This helps to retain the original product information. For example, changing the ingredient name of `Sugar` to `Brown sugar` will not affects the ingredients stored in `Cheese cake`.

> ℹ️ **INFO**
Take note that a `product add` command does not take in -status parameter, i.e. a newly added product can only have *ACTIVE* status. To add an archived product, you can:
    1. Add the product.
    2. Use the `product edit` command (illustrated below) to edit the status.
__________________________________________________________________

I also make sure the readers can get the most important information from the User Guide by bolding keywords.

#### 6.2.3 Editing a product: `product edit`

Edits the details of an existing product at `INDEX`.


Format: `product edit INDEX {-name PRODUCT_NAME} {-ingt [INGREDIENT_NAME, PORTION]}... {-cost COST} {-price RETAIL PRICE}`

Example:
- `product edit 1 -name Small cheese cake` : edits the name of the first product in the list to be "Small cheese cake"
-  `product edit 2 -name Large egg tart -ingt [Egg, 4] [Flour, 2]` : edits both the name and the ingredients needed. 

> ⚠️ **WARNING**
If you want to change the ingredient fields, you have to include all ingredients needed again. The system will **regenerate** the ingredients from the entry. i.e. `product edit -i 2 -name Large egg tart -ingt [Egg, 4]` indicates that Large egg tart only needs 4 portions of Egg. 
__________________________________________________________________
I proposed to add a summary table for each command for clearer presentation. Our group agreed and they followed. An example of the summary table can be seen in the `SortProductCommand`:

#### 6.2.7 Sorting products `product sort`
Sorts products of different scope according to categories, in either ascending or descending order.

Category is specified by `-by` parameter.
Default sorting scope is active products.
Default sorting order is ascending. If you want to sort in acsending order, you can specify `-re` parameter.

|Parameter|Default Value| Remarks
|:---|:---|:---
|`-by`|Compulsory input|Values can be `name`, `price`, `cost`,`profit` <br>- profit = (price - cost)|
|`-scope`|`active`|Values can be `all`, `active`, `archive` <br> - case insensitive<br> -Default `active`|
|`-re`|Do not input any value for this parameter|

Format: `product sort -by CATEGORY {-scope SCOPE} {-re}`
Example: 
- `product sort -by name` sorts active products by name in descending order.
- `product sort -by profit -scope active` sorts active products by profit in descending order
- `product sort -by cost -scope all -re` sorts all product by cost in ascending order

_________________________________________________________________
I keep sections about small features short to make the User Guide concise but clear at the same time.

#### 6.2.4 Listing products `product filter`
Filters product with specified scope. This command takes in only one parameter `-scope` which can be `all`, `active` or `archive`. 

|Attribute|Value|
|:---|:---|
|*`-scope`|`all`, `active`, `archive` <br>- case insensitive|

Format: `product filter -scope SCOPE`
Example: `product filter -scope archive`: lists all archived products

#### 6.2.5 Showing ingredients needed for a product `product show`

Shows the details of a product at `INDEX`. Since the product list only shows name, cost, price and status of a product, you may use this command to check the ingredients as well as the corresponding portion needed. See Figure x.
Format: `product show INDEX` 
Example: `product show 6`

> ⚠️ **WARNING**
If you edit the currently showing product, remember to refresh the page to see the changes.


#### 6.2.5 Deleting an product : `product remove`
Deletes products at the specified INDICES.

Format: `product remove INDICES`

Examples:
`product remove 1`
`product remove 1~4`
`product remove 1,3,4`

#### 6.2.6 Searching products `product search`
Lists products whose name includes the specified keyword.

|Parameter|Value|
|:---|:---|
|*`-include`|Any value|


Formate: `product search -include KEYWORD`

Example:
`product search -include Tart`

__________________________________________________________________

### Contribution to the Developer Guide

The following section shows my contribution to the Developer Guide.

#### 3.2 Filter Product Feature
This feature shows only products with a certain status, i.e. shows only products with an ```ARCHIVE``` status.
The list mechanism in product is facilitated by FilteredList which wraps a ObservableList and filters using the provided Predicate.  
```A FilteredList<Product>```  filteredProducts is stored in the ModelManager. In BakingHome, there is a ```ObservableList<Product>``` products which contains all products, regardless of the status. filteredProducts in the ModelManager is initialized with this ObservableList.  

Since a FilteredList needs a Predicate, which matches the elements in the source list that should be visible, the list mechanism implements the following operation to support the filtering: 
```FilteredProductList#updateFilteredProductList(Predicate<Product> predicate)``` -- Sets the value of the property Predicate in the filteredProducts. The ListProductCommand will use this method to change the visibility of products with different status by passing in corresponding predicate. 

An example usage scenario and how the list mechanism behaves at each step is shown below. 
- Step1. The user launched the application for the first time. 
UniqueProductList will be initialized with the list of products in BakingHome. This list contains two products A and B. Product A has an ACTIVE status and Product B has an ARCHIVE status. 
- Step 2. The user executes product list -status archive command to list all archived products. 

The Sequence Diagram below shows how the components interact with each other for list product feature. 
 
![](https://i.imgur.com/LtxnCfV.png)
*Figure 1: Sequence Diagram for List Product Feature*

#### 3.2.1 Design Considerations
**Alternative 1** (current choice): Save all products in a ObservableList in BakingHome, and keeps a FilteredList in the ModleManager. ProductCommandParser parse the user input and get the Predicate to update the FilteredList. 
- Pros: Implementation is clearer and code is more human readable. 
- Cons: Memory is wasted in keeping two sets of the list. Structure becomes complicated
**Alternative 2:** Keep only one List of of products. Loop through the list to get the products with desired status. 
- Pros: Structure is simpler.  
- Cons: Time complexity is very high, resulting in slow response of the application when product list gets long. 
**Alternative 3:** Keep two separate product lists, one for archived product one for active product. 
- Pros: Fast access of both status. 
- Cons: Implementation will become complicated. It also makes it very expensive when user wants to sort the product according to the product name.  
