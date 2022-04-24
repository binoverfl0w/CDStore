# CDStore
A GUI application that uses JavaFX and implements the MVC architecture. It has a three-level user system: Cashier, Manager and Administrator.

## What is used in this application?
1. Inheritance, polymorphism, Abstract Classes and Interfaces
2. File Handling - Text and Binary IO (both of them)
3. Validation of Input
4. Usage of String Functions and/or Regular Expressions for validating the input
5. Exception Handling
6. JavaFX UI and Graphics
7. JavaFX Events 

## What different users can do?
**Cashier** - Has the right to check out the tech-items that a customer may need from the CD-Store. This means that
the cashier should create a bill and enter the data of the bought item, such as the title of the CD and its quantity. If
the CD does not exist or is out of stock, it gives him an alert about it. The software provides him the total amount of 
the bill and it should be it is in a printable format [BillNo.txt]. The updates in the
software file are done automatically by adding the data into the respective files.

**Manager** - The manager has the right to supply the CD-Store with the needed items. So he may enter in the stock
the new genre and/or add CDs of the same genre to the stock of the CD-Store. He is informed when
entered in the system if there are few (usually less than 5) items of a category in the CD-Store stock, so he may add
them. The same user may also check the performance of the cashiers by checking their total number of bills, the
CDs sold and the total amount of money made for a certain date or between a certain period of time. Also the
statistics about the CDs sold and bought are provided to them whenever requested from them daily, monthly
and/or total. The manager keeps a list of suppliers and the CDs they offer.

**Administrator** -> The administrator has the right to manage almost everything that the Cashier and Manager do.
Beside them he has the right to manage the employees (Cashier and Manager), by registering, modifying and
deleting them including their access to the software. He may keep data about them such as: name, birthday, phone,
email, salary, access level, and other information about them. The software provides him also data about
total incomes (total of items sold) and total cost (total of items bought and staff salaries) during a period of time.

## How to run this application?
1. Clone this repository
2. Open the folder as a project in IntelliJ
3. Make sure to include the JavaFX library as a dependency
4. Add the following VM argument in the run configuration: --module-path \path\to\javafx\lib --add-modules=javafx.controls,javafx.graphics
5. Run CDStore.java
(Note: Default credentials: admin/admin)

JavaFX and Java SDK version: 17
