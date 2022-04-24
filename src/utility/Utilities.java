package utility;

import db.Database;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.stage.Screen;
import javafx.stage.Stage;
import models.*;

import javax.xml.crypto.Data;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;

public class Utilities {

    public final static String ACCOUNTS_PATH = "res/data/accounts.data";
    public final static String EMPLOYEES_PATH = "res/data/employees.data";
    public final static String SESSION_PATH = "res/data/session.data";
    public final static String PRODUCTS_PATH = "res/data/products.data";
    public final static String CATEGORIES_PATH = "res/data/categories.data";
    public static final String SUPPLIERS_PATH = "res/data/suppliers.data";
    public static final String BILLS_PATH = "res/data/bills.data";
    public static final String LOGS_PATH = "res/data/logs.data";

    public static void centerStage(Stage stage) {
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
        stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
    }

    public static String encrypt(String str) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        String text = str.trim();

        // Change this to UTF-16 if needed
        md.update(text.getBytes(StandardCharsets.UTF_8));
        byte[] digest = md.digest();

        String hex = String.format("%064x", new BigInteger(1, digest));
        return hex;
    }

    public static String generateRandomString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
    }

    public static Employee findEmployeeById(String id) {
        for (Employee emp : Database.Employees.getObjects()) {
            if (emp.getId().equals(id))
                return emp;
        }
        return null;
    }

    public static Account findAccountByUsername(String username) {
        for (Account acc : Database.Accounts.getObjects()) {{
            if (acc.getUsername().equals(username.toLowerCase()))
                return acc;
        }}
        return null;
    }

    public static boolean validateEmployee(String name, String surname, String email, String phone, String salary) {
        String error = "";
        if (!name.matches("^[a-zA-Z]{1,16}"))
            error += "Name must be between 1 and 16 characters and contain only letters. Example: Jim\n";
        if (!surname.matches("^[a-zA-Z]{1,16}"))
            error += "Surname must be between 1 and 16 characters and contain only letters. Example: Henderson\n";
        if (!email.contains("@"))
            error += "Email is not valid. Example: jim@gmail.com\n";
        if (!phone.matches("^[0-9]{1,15}$"))
            error += "Phone number must contain only digits. Example: 355694554678\n";
        if (!salary.matches("^[0-9]+[.]{0,1}[0-9]+$"))
            error += "Salary must contain only digits and/or a point. Example: 5020.4\n";
        if (!error.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid properties!");
            alert.setContentText(error);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public static boolean validateAccount(String username, String password, boolean ...skipCheck) {
        boolean skipUsername = skipCheck.length >= 1 ? skipCheck[0] : false;
        boolean skipPassword = skipCheck.length >= 2 ? skipCheck[1] : false;
        String error = "";
        if (!username.matches("^[a-zA-Z]{1,16}[0-9]{0,5}[_]{0,1}[0-9]{0,5}[a-zA-Z]{0,16}"))
            error += "Username must begin with a letter and contain only alphanumeric characters and one underscore. Example: jim_henderson\n";
        if (findAccountByUsername(username) != null && !skipUsername)
            error += "Username already exists.\n";
        if (!(password.matches(".{6,12}")) && !skipPassword)
            error += "Password must be between 6 and 12 characters.\n";
        if (!error.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid properties!");
            alert.setContentText(error);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public static boolean validateCategory(String name) {
        String error = "";
        if (!name.matches("^[a-zA-Z]{1,15}.*[a-zA-Z]{1,15}$"))
            error += "Category cannot be empty and must start and end with a letter\n";
        if (!error.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid properties!");
            alert.setContentText(error);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public static boolean validateProduct(String name, String quantity_s, String price_s) {
        String error = "";
        if (!name.matches("^[a-zA-Z]{1,10}$"))
            error += "Product name must contain letters and be between 1 and 10 characters.\n";
        if (!quantity_s.matches("^[0-9]{1,4}$"))
            error += "Quantity must contain only numbers\n";
        if (!price_s.matches("^[0-9]+[.]{0,1}[0-9]*$"))
            error += "Price must contain only digits and/or a point. Example: 10.4\n";
        if (!error.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid properties!");
            alert.setContentText(error);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public static boolean validateItem(String itemName, Supplier supplier) {
        String error = "";
        if (!itemName.matches("^[a-zA-Z]{1,15}.*[a-zA-Z]{1,15}$"))
            error += "Item name cannot be empty and must start and end with a letter.\n";
        if (supplier == null)
            error += "Choose a supplier.\n";
        else if (supplier.getName().equals("No supplier"))
            error += "Choose a supplier.\n";
        if (!error.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid properties!");
            alert.setContentText(error);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public static boolean validateSupplier(String supplierName) {
        String error = "";
        if (!supplierName.matches("^[a-zA-Z]{1,10}.*[a-zA-Z]{1,10}$"))
            error += "Supplier name must start and end with a letter.\n";
        if (!error.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid properties!");
            alert.setContentText(error);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public static boolean validateProductToBuy(Product product, String text) {
        String error = "";
        if (!text.matches("^[0-9]{1,4}$"))
            error += "Quantity must contain only digits.\n";
        else {
            int q = Integer.parseInt(text);
            if (product != null) {
                if (product.getQuantity() < q) {
                    error += "There are " + product.getQuantity() + " items named " + product.getName() + "\n";
                }
            }
        }
        if (product == null)
            error += "Choose a product.\n";
        if (!error.equals("")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid properties!");
            alert.setContentText(error);
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public static void checkStockAvailability() {
        String info = "";
        for (Product product : Stock.getProducts()) {
            if (product.getQuantity() < 5) {
                info += "There are items that are less than 5 in quantity.\n";
                break;
            }
        }
        if (!info.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Check stock");
            alert.setContentText(info);
            alert.showAndWait();
        }
    }
}
