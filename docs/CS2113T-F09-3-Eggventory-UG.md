<h1 id="eggventory-user-guideby-team-f09-03">Eggventory User GuideBy: Team F09-03</h1>
<p>Dated:  October 2019</p>
<h2 id="table-of-contents">Table of contents</h2>
<h3 id="introduction-"><a href="#introduction">1. Introduction </a></h3>
<h3 id="quick-start-"><a href="#quick-start">2. Quick Start </a></h3>
<h3 id="features-and-command-usage-"><a href="#features-and-command-usage">3. Features and Command Usage </a></h3>
<h5 id="viewing-help-"><a href="#viewing-help-help">3.1 Viewing help </a></h5>
<h5 id="working-with-stock-types-"><a href="#working-with-stock-types">3.2 Working with Stock Types </a></h5>
<h5 id="working-with-stocks-"><a href="#working-with-stocks">3.3 Working with Stocks </a></h5>
<h5 id="managing-your-list-of-people-"><a href="#managing-your-list-of-people">3.4 Managing your list of People </a></h5>
<h5 id="managing-your-list-of-loans-"><a href="#managing-your-list-of-loans">3.5 Managing your list of Loans </a></h5>
<h5 id="loaning-using-templates"><a href="#loaning-using-templates-coming-in-v1.4">3.6 Loaning using Templates</a></h5>
<h5 id="marking-stock-as-lost-"><a href="#marking-stock-as-lost-coming-in-v2.0">3.7 Marking Stock as Lost </a></h5>
<h5 id="searching-the-inventory-"><a href="#searching-the-inventory">3.8 Searching the Inventory </a></h5>
<h5 id="using-undo-and-redo-commands-"><a href="#using-undo-and-redo-commands-coming-in-v2.0">3.9 Using Undo and Redo Commands </a></h5>
<h5 id="setting-autosave-options-"><a href="#setting-autosave-options-coming-in-v2.0">3.10 Setting autosave options </a></h5>
<h5 id="exiting-the-program-bye-"><a href="#exiting-the-program-bye">3.1 Exiting the program: bye </a></h5>
<h3 id="faq-"><a href="#faq">4. FAQ </a></h3>
<h3 id="command-summary-"><a href="#command-summary">5. Command Summary </a></h3>
<h3 id="glossary-"><a href="#glossary">6. Glossary </a></h3>
<h2 id="introduction">1. Introduction</h2>
<p>Eggventory is an inventory management system targeted towards engineering laboratory inventories. It is designed to make tracking of stock and inventory fast and painless. There are visual elements for all functionalities which makes learning the system easy, but there are also command-line equivalents that allow advanced users to do everything in one or two short commands.</p>
<h2 id="quick-start">2. Quick start</h2>
<ol>
<li>
<h4 id="download-and-install-java-11-on-your-machine.-"><a href="https://www.oracle.com/technetwork/java/javase/downloads/jdk11-downloads-5066655.html">Download and install Java 11 on your machine. </a></h4>
</li>
<li>
<h4 id="download-the-latest-eggventory.jar-the-github-repository.-"><a href="https://github.com/AY1920S1-CS2113T-F09-3/main">Download the latest eggventory.jar the github repository. </a></h4>
</li>
<li>
<h4 id="copy-the-file-to-the-folder-you-want-to-use-as-the-home-folder-for-your-inventory-management-system.">Copy the file to the folder you want to use as the home folder for your inventory management system.</h4>
</li>
<li>
<h4 id="double-click-the-file-to-start-the-app.-the-gui-should-appear-in-a-few-seconds.">Double-click the file to start the app. The GUI should appear in a few seconds.</h4>
</li>
</ol>
<p><img src="https://lh5.googleusercontent.com/DWHPvJrc_raO8tYd-py_9o2lMkOgM5YOnqhsxf33k6wg5NVkZglD7pJeWqfwSz8WYUOradsvqAqY3zNDr5Pnq9Lk2X8_9MwV3kBeBf0dFhyZX7I7_AZYNfmNfa1IFQ" alt=""></p>
<ol start="5">
<li>
<h4 id="type-the-command-in-the-command-box-and-press-enter-to-execute-it">Type the command in the command box and press Enter to execute it</h4>
</li>
</ol>
<p>E.g. typing help and pressing Enter will open the help window.</p>
<ol start="6">
<li>
<h4 id="some-example-commands-you-can-try">Some example commands you can try:</h4>
</li>
</ol>
<ul>
<li>
<p>add stocktype Resistors : Creates a category of stock named “Resistors”</p>
</li>
<li>
<p>add stock R500 200 500-ohm resistor -st Resistors: Creates a new stock item with 200 500-ohm resistors and a stock code of “R500”, under the “Resistors” Stock Type.</p>
</li>
<li>
<p>edit stock R500 quantity -50 : Reduce the stock count of item R500 (500-ohm resistor) by 50.</p>
</li>
<li>
<p>add stock C1k 75 1000pF Capacitors :  Creates a new stock item with 75 1000pF Capacitors and stock code “C1k”. The stock type will be defaulted to “Uncategorised”.</p>
</li>
<li>
<p>list stocktype Resistors : Displays all stock that is categorised as Resistors.</p>
</li>
<li>
<p>exit : exits the app</p>
</li>
</ul>
<ol start="7">
<li>
<h4 id="refer-to-section-3-for-a-more-detailed-list-of-commands-you-can-use.">Refer to Section 3 for a more detailed list of commands you can use.</h4>
</li>
</ol>
<h2 id="features-and-command-usage">3. Features and command usage</h2>
<p>The following section describes the command line interface commands that Eggventory recognises. Each command you input has to follow a specific format of words and parameters.</p>
<p>Command Format</p>
<ul>
<li>
<p>Text in  are mandatory parameter to be supplied by the user. E.g. add stocktype  ,  is a parameter which is the name of the stocktype the user wishes to add.</p>
</li>
<li>
<p>Parameters in {Braces} can be repeated multiple times in the same command, separated by a comma (,). E.g. loan add &lt;Matric. No&gt; { } , more   pairs can be added after the first pair as such: loan add A0191234A R500 20, R250 10, R100 5</p>
</li>
<li>
<p>[coming in v2.0] Some commands have optional parameters available. Optional parameters are added to the end of the corresponding command, after all mandatory parameters. E.g. add stock R500 50 “500-ohm Resistors” -st Resistors , will create a new stock, and placed under the “Resistors” Stock Type with the “-st” tag.</p>
</li>
</ul>
<h2 id="section"></h2>
<h3 id="viewing-help-help">3.1 Viewing help: <code>help</code></h3>
<p>General help: Displays a basic list of commands and their input formats.</p>
<p>Format: <code>help</code></p>
<p>Specific help: Displays a more detailed list of commands of that type, with information about each input required.</p>
<p>Format: <code>help &lt;command&gt;</code></p>
<p>eg. help edit</p>
<hr>
<h3 id="working-with-stock-types">3.2 Working with Stock Types</h3>
<p>Stock Types are the main categories of the inventory, and each Stock Type stores multiple Stocks. For example, your inventory may have Stock Types such as Resistors, Tools or Wires. You are required to add your Stock Types to the inventory before Stocks can be added. By default, Eggventory comes with an Uncategorised Stock Type, where Stocks not assigned to a specified Stock Type are added.</p>
<p>Note: Stock Type names are not allowed to have spaces in them.</p>
<h4 id="adding-new-stock-types-add-stocktype">3.2.1 Adding new Stock Types: <code>add stocktype</code></h4>
<p>This adds a new category of stock to the inventory.</p>
<p>Format: add stocktype </p>
<p>eg. add stocktype Resistor</p>
<h4 id="deleting-stock-types-delete-stocktype">3.2.2 Deleting Stock Types: <code>delete stocktype</code></h4>
<p>This removes a stocktype from the inventory, and all stock under it.</p>
<p>Format: delete stocktype </p>
<h4 id="editing-stock-types-edit-stocktype">3.2.3 Editing Stock Types: <code>edit stocktype</code></h4>
<p>This changes the name of the selected stock type.</p>
<p>Format: edit stocktype  </p>
<h4 id="listing-stock-types-list-stocktype">3.2.4 Listing Stock Types: <code>list stocktype</code></h4>
<p>This lists out all Stock Types that are present in the inventory.</p>
<p>Format: list stocktype</p>
<hr>
<h3 id="working-with-stocks">3.3 Working with Stocks</h3>
<p>Stocks are the main types of items that Eggventory helps you to manage. Every Stock belongs to a Stock Type. For example, you may have the Stocks “500 ohm resistor” and “1k ohm resistor” in the Resistor Stock Type.</p>
<p>Stocks may be Collective or Unique. Collective stocks consist of items that are not tracked individually. They generally are stocks that do not have each have their own serial number, and are considered interchangeable. Unique stocks are items that are often expensive or limited in quantity. Such stocks usually are each assigned a serial number, and are loaned out and tracked by this number. Stocks are set as Collective by default.</p>
<p>Stocks have the following properties:</p>

<table>
<thead>
<tr>
<th align="right">Property</th>
<th>Description</th>
</tr>
</thead>
<tbody>
<tr>
<td align="right">StockType</td>
<td>The category the stock belongs to. The StockType should have previously been added to the inventory system before being referenced.</td>
</tr>
<tr>
<td align="right">Stock Code</td>
<td>unique string of numbers and letters, used to identify the stock.</td>
</tr>
<tr>
<td align="right">QuantityDescription</td>
<td>The common name of the item.</td>
</tr>
</tbody>
</table><p>Note: Stock codes are not allowed to have spaces in them, and no two stocks can share the same stock code.</p>
<h4 id="adding-new-stocks-add-stock">3.3.1 Adding new Stocks: <code>add stock</code></h4>
<p>This adds a new stock to the inventory.</p>
<p>Format: <code>add stock &lt;StockType&gt; &lt;Stock Code&gt; &lt;Quantity&gt; &lt;Description&gt;</code></p>
<p>eg. <code>add stock Resistor R500 1000 500ohm resistor</code></p>
<p><strong>[coming in v2.0]</strong><br>
In addition to the required parameters, stocks can also be added with the following optional parameters:</p>

<table>
<thead>
<tr>
<th>Format</th>
<th>Purpose</th>
</tr>
</thead>
<tbody>
<tr>
<td>-mq &lt;Min. Qty&gt;</td>
<td>Sets the minimum quantity of stock that should be maintained in the inventory</td>
</tr>
<tr>
<td>-u</td>
<td>Sets the stock to contain items that are unique</td>
</tr>
</tbody>
</table><p>Format: <code>add stock &lt;StockType&gt; &lt;Stock Code&gt; &lt;Quantity&gt; &lt;Description&gt; {&lt;optional parameter&gt;}</code></p>
<p>eg. <code>add stock Resistor R500 1000 500ohm resistor -mq 100</code></p>
<h4 id="deleting-stocks-delete-stock">3.3.2 Deleting Stocks: <code>delete stock</code></h4>
<p>This removes a stock from the inventory, including any references to loaned out stock.</p>
<p>Format: delete stock </p>
<h4 id="editing-stock-edit-stock">3.3.3 Editing Stock: <code>edit stock</code></h4>
<p>This directly modifies the value of a property of a stock. You may modify as many properties as you wish in one command.</p>
<p>Keywords to modify each property:</p>
<ul>
<li>
<p>stockcode</p>
</li>
<li>
<p>description</p>
</li>
<li>
<p>stocktype</p>
</li>
<li>
<p>quantity</p>
</li>
<li>
<p>minquantity</p>
</li>
</ul>
<p>Format: <code>edit stock &lt;Stock Code&gt; {&lt;Property&gt; &lt;New Value&gt;}</code></p>
<p>eg. <code>edit stock R500 quantity 1000 description stockcode Res500</code></p>
<h4 id="listing-stock-list-stock">3.3.4 Listing Stock: <code>list stock</code></h4>
<p>This lists out all Stocks that are present in the inventory.</p>
<p>Format: list stock</p>
<h4 id="listing-stock-of-a-particular-stocktype-list-stocktype">3.3.5 Listing Stock of a particular StockType: <code>list &lt;StockType&gt;</code></h4>
<p>This lists out all Stock under a particular Stock Type</p>
<p>Format: <code>list &lt;Stock Type&gt;</code></p>
<p>eg. <code>list Resistor</code></p>
<hr>
<h3 id="managing-your-list-of-people">3.4 Managing your list of People</h3>
<p>People have to be added to the system before they can take loans from the inventory. Each person is identified by their matric number, as the inventory primarily loans out stock to students.</p>
<h4 id="adding-a-person-add-person">3.4.1 Adding a Person: <code>add person</code></h4>
<p>This adds a new person to keep track that will loan stock.</p>
<p>Format: <code>add person &lt;Matric No.&gt; &lt;Name&gt;</code></p>
<p>eg. <code>add person A0123456</code><br>
Note: By nature, the matric number of each Person should be unique, meaning no two individuals are allowed to share the same matric number.</p>
<p><strong>[coming in v2.0]</strong><br>
Optional Parameters:</p>

<table>
<thead>
<tr>
<th>Format</th>
<th>Purpose</th>
</tr>
</thead>
<tbody>
<tr>
<td><code>-n &lt; Name &gt;</code></td>
<td>Sets the name of the person being added</td>
</tr>
<tr>
<td><code>-c &lt; Course &gt;</code></td>
<td>Sets the course of the person being added</td>
</tr>
</tbody>
</table><p>Format: <code>add person &lt;Matric. No&gt; {&lt;flag&gt; &lt;optional parameter&gt;}</code></p>
<p>eg. <code>add person A0187654 -n Raghav -c CEG</code></p>
<h4 id="deleting-a-persondelete-person">3.4.2 Deleting a Person:<code>delete person</code></h4>
<p>This removes a person from being tracked. All outstanding loans are automatically returned.</p>
<p>Format: <code>delete person &lt;Matric. No&gt;</code></p>
<p>eg. <code>delete person A0123456</code></p>
<h4 id="editing-a-person’s-details-edit-person">3.4.3  Editing a Person’s details: <code>edit person</code></h4>
<p>This directly modifies the value of a property of a person. You may modify as many properties as you wish in one command.</p>
<p>Properties:</p>
<ul>
<li>matric</li>
<li>name</li>
</ul>
<p>Format: <code>edit person &lt;Matric No.&gt; {&lt;Property&gt; &lt;New Value&gt;}</code></p>
<p>eg. <code>edit person A0123456 name Alex</code></p>
<h4 id="listing-all-people-list-person">3.4.4 Listing all People: <code>list person</code></h4>
<p>This lists all the people in the system, along with any information recorded about them.</p>
<p>Format: <code>list person</code></p>
<hr>
<h3 id="managing-your-list-of-loans">3.5 Managing your list of Loans</h3>
<h4 id="adding-a-loan-loan-add">3.5.1 Adding a Loan: <code>loan add</code></h4>
<p>This adds a new Loan made by a Person.  is possible to add multiple stocks at once.</p>
<p>Format: <code>loan add &lt;Matric No.&gt; {&lt;Stock Code&gt; &lt;Quantity&gt;}</code></p>
<p>eg. <code>loan add A0123456 R500 1000 X123 80</code></p>
<h4 id="returning-specific-loans-loan-return-coming-in-v1.4">3.5.2 Returning specific Loans: <code>loan return</code> [coming in v1.4]</h4>
<p>This marks specific Loans of a Person as returned.</p>
<p>Format: <code>loan return &lt;Matric No.&gt; {&lt;Stock Code&gt; &lt;Quantity&gt;}</code></p>
<h4 id="returning-all-loans-loan-returnall-coming-in-v">3.5.3 Returning all Loans: <code>loan returnall</code> [coming in v</h4>
<p>This marks all Loans of a Person as returned.</p>
<p>Format: `loan returnall &lt;Matric No</p>
<h4 id="listing-all-persons-and-their-loans-list-loan-coming-in-v.141.4">3.5.4 Listing all Persons and their Loans: <code>list loan</code> [coming in v.141.4]</h4>
<p>This lists out all loans currently recorded, listed by the Person who made the loan.</p>
<p>Format: <code>list loan</code></p>
<hr>
<h3 id="loaning-using-templates-coming-in-v1.4">3.6 Loaning using Templates [coming in v1.4]</h3>
<p>To speed up the loaning process, Eggventory allows you to create loan templates. Templates allow you to define a set of stocks in advance, and repeatedly loan out the same combination of stocks to different People.</p>
<h4 id="adding-loan-templates-add-template">3.6.1 Adding loan templates: <code>add template</code></h4>
<p>This creates a template that can be substituted for { } in loan commands.</p>
<p>Format:<code>add template “&lt;Template Name&gt;” {&lt;Stock Code&gt; &lt;Quantity&gt;}</code></p>
<p>e.g. <code>add template CG1112_Alex R500 5 A123 1</code></p>
<h4 id="deleting-a-template-delete-template">3.6.2  Deleting a Template: <code>delete template</code></h4>
<p>This deletes a saved loan template.</p>
<p>Format: d<code>elete template &lt;Template Name&gt;</code></p>
<p>eg. <code>delete template CG1112_Alex</code></p>
<h4 id="making-a-loan-from-a-template-loan-add">3.6.3 Making a Loan from a template: <code>loan add</code></h4>
<p>This adds a Loan to a Person from a Template.</p>
<p>Format: <code>loan add &lt;Matric. No&gt; &lt;Template Name&gt;</code></p>
<p>eg. <code>loan add A0187654 CG1112_Alex</code></p>
<p>Note: Additional Loans can still be added on to the same Person afterwards using the loan add command.<br>
3.6.4 Listing Loan Templates: `list template</p>
<p>This lists out all the templates created in the system.</p>
<p>Format: <code>list template</code></p>
<hr>
<h3 id="marking-stock-as-lost-coming-in-v2.0">3.7 Marking Stock as lost [coming in v2.0]</h3>
<p>Marks a certain quantity of a stock as lost. Differs from deleting stock in the fact that the quantity of stock will still be saved in the inventory (eg. for administrative purposes). Lost stock will not be included in tallies of available stock.</p>
<h4 id="marking-stock-as-lost-lost">3.7.1 Marking Stock as lost: <code>lost</code></h4>
<p>Format:<code>lost &lt;Stock Code&gt; &lt;Quantity&gt;</code></p>
<p>eg. <code>lost R500 10</code></p>
<h4 id="marking-loaned-stock-as-lost">3.7.2 Marking Loaned Stock as lost:</h4>
<p>This directly marks a quantity of a Person’s Loan as lost. The items are removed from the Loan list and counted as lost within the main inventory.</p>
<p>Format: [coming in v2.0]</p>
<h3 id="searching-the-inventory">3.8 Searching the inventory</h3>
<p>It is possible to search the inventory for a Stock or StockType with the find command. It will display all Stocks or StockType that partially or fully matches the input query.</p>
<h4 id="finding-a-stock-find-stock">3.8.1 Finding a Stock: <code>find stock</code></h4>
<p>This finds all stocks that match the query.</p>
<p>Format: <code>find stock &lt;Query&gt;</code></p>
<h4 id="finding-a-stocktype-find-stocktype">3.8.2 Finding a StockType: <code>find stocktype</code></h4>
<p>This finds all StockTypes that matches the query.</p>
<p>Format: <code>find stocktype &lt;Query&gt;</code></p>
<hr>
<h3 id="using-undo-and-redo-commands-coming-in-v2.0">3.9 Using Undo and Redo commands: [coming in v2.0]</h3>
<h4 id="undoing-a-command-undo">3.9.1 Undoing a command: `undo</h4>
<p>If you accidentally entered a command by accident, the effects of any command can be reversed with the undo command.</p>
<p>Format: <code>undo</code></p>
<h4 id="reversing-an-undo-command-redo">3.9.2 Reversing an undo command: <code>redo</code></h4>
<p>Undoing a command can be reversed using the redo command.</p>
<p>Format: <code>redo</code></p>
<hr>
<h3 id="setting-autosave-options-coming-in-v2.0">3.10 Setting autosave options: [coming in v2.0]</h3>
<p>Eggventory automatically saves the current inventory to the disk every time data is added, removed, or edited. You can disable this feature with this command. Eggventory will then save only when the program exits.</p>
<p>Format: <code>autosave on</code> OR <code>autosave off</code></p>
<hr>
<h3 id="exiting-the-program-bye">3.11 Exiting the program: <code>bye</code></h3>
<p>Format: <code>bye</code></p>
<h2 id="faq">4. FAQ</h2>
<h2 id="command-summary">5. Command Summary</h2>
<h3 id="add-commands">Add Commands</h3>

<table>
<thead>
<tr>
<th>Command</th>
<th>Syntax</th>
</tr>
</thead>
<tbody>
<tr>
<td>add stock</td>
<td><code>add stock &lt;Stock Type&gt; &lt;Stock Code&gt; &lt;Quantity&gt; &lt;Description&gt;</code></td>
</tr>
<tr>
<td>add stocktype</td>
<td><code>add stocktype &lt;Stock Type&gt;</code></td>
</tr>
<tr>
<td>add template</td>
<td><code>add template &lt;Template Name&gt; {&lt;Stock Code&gt; &lt;Quantity&gt;}</code></td>
</tr>
<tr>
<td>add person</td>
<td><code>add person &lt;Matric No.&gt;</code></td>
</tr>
</tbody>
</table><h3 id="delete-commands">Delete Commands</h3>

<table>
<thead>
<tr>
<th>Command</th>
<th>Syntax</th>
</tr>
</thead>
<tbody>
<tr>
<td>stock</td>
<td><code>delete stock &lt;Stock Code&gt;</code></td>
</tr>
<tr>
<td>delete stocktype</td>
<td><code>delete stocktype &lt;Stock Type&gt;</code></td>
</tr>
<tr>
<td>delete template</td>
<td><code>delete template &lt;Template Name&gt;</code></td>
</tr>
<tr>
<td>delete person</td>
<td><code>delete person &lt;Matric No.&gt;</code></td>
</tr>
</tbody>
</table><h3 id="edit-commands">Edit Commands</h3>

<table>
<thead>
<tr>
<th>Command</th>
<th>Syntax</th>
</tr>
</thead>
<tbody>
<tr>
<td>edit stock</td>
<td><code>edit stock &lt;Property&gt; &lt;New Value&gt;</code></td>
</tr>
<tr>
<td>edit person</td>
<td><code>edit person &lt;Property&gt; &lt;New Value&gt;</code></td>
</tr>
</tbody>
</table><h3 id="list-commands">List Commands</h3>

<table>
<thead>
<tr>
<th align="right">Command</th>
<th>Syntax</th>
</tr>
</thead>
<tbody>
<tr>
<td align="right">list stocks</td>
<td><code>list stock all</code></td>
</tr>
<tr>
<td align="right">list stocktypes</td>
<td><code>list stocktypes</code></td>
</tr>
<tr>
<td align="right">list stocktype</td>
<td><code>list stocktype &lt;Stock Type&gt;</code></td>
</tr>
<tr>
<td align="right">list loan</td>
<td><code>list loan</code></td>
</tr>
<tr>
<td align="right">list template</td>
<td><code>list template</code></td>
</tr>
<tr>
<td align="right">list lost</td>
<td><code>list lost</code></td>
</tr>
</tbody>
</table><h3 id="loan-commands">Loan Commands</h3>

<table>
<thead>
<tr>
<th>Command</th>
<th>Syntax</th>
</tr>
</thead>
<tbody>
<tr>
<td>loan add</td>
<td><code>loan add &lt;Matric No.&gt; {&lt;Stock Code&gt; &lt;Quantity&gt;}</code><br> OR  <br><code>loan add &lt;Matric No.&gt; &lt;Template Name&gt;</code></td>
</tr>
<tr>
<td>loan returned</td>
<td><code>loan returned &lt;Matric No.&gt; {&lt;Stock Code&gt; &lt;Quantity&gt;}</code> <br> OR <br> <code>loan returned &lt;Matric No.&gt; &lt;Template Name&gt;</code></td>
</tr>
</tbody>
</table><h3 id="lost-commands">Lost Commands</h3>

<table>
<thead>
<tr>
<th>Command</th>
<th>Syntax</th>
</tr>
</thead>
<tbody>
<tr>
<td>lost</td>
<td><code>lost &lt;Stock Code&gt; &lt;Quantity&gt;</code></td>
</tr>
</tbody>
</table><h3 id="other-commands">Other Commands</h3>

<table>
<thead>
<tr>
<th>Command</th>
<th>Syntax</th>
</tr>
</thead>
<tbody>
<tr>
<td>undo last command</td>
<td><code>undo</code></td>
</tr>
<tr>
<td>redo previous command</td>
<td><code>redo</code></td>
</tr>
<tr>
<td>exit application</td>
<td><code>bye</code></td>
</tr>
</tbody>
</table><h2 id="glossary">6. Glossary</h2>
<p>Matric No.: Matriculation number of student to be added</p>
<p>Stock: A physical asset to be tracked by Eggventory</p>
<p>Stock Code: A unique string of characters to identify a stock</p>
<p>Stock Type: A category of stock</p>

