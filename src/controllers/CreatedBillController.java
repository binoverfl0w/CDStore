package controllers;

import db.Database;
import interfaces.ControllerInterface;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import models.Bill;
import utility.Logger;
import views.CreatedBillView;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CreatedBillController implements ControllerInterface<CreatedBillView> {

    private Bill bill;

    public CreatedBillController(CreatedBillView view, Bill bill) {
        this.bill = bill;
        setView(view);
    }

    @Override
    public void setView(CreatedBillView view) {
        view.getScrollPane().setContent(view.getContainer());
        for (int i = 0; i < bill.getProducts().size(); i++) {
            if (bill.getProducts().get(i) != null) {
                view.getContainer().getChildren().add(view.createRow(bill.getProducts().get(i), bill.getQuantities().get(i)));
            }
        }
        view.getTotalCost().setText("Total cost: " + bill.getTotalCost() + "$");
        view.getSave().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try(FileWriter fw = new FileWriter("res/bills/Bill" + Database.Bills.getObjects().size() + ".txt");
                    PrintWriter pw = new PrintWriter(fw)) {
                    pw.println(" ----------------------------------------------------");
                    pw.println("|                                                    |");
                    pw.println("|                        Bill                        |");
                    pw.println("|                                                    |");
                    for (int i = 0; i < bill.getProducts().size(); i++) {
                        if (bill.getProducts().get(i) != null) {
                            pw.println(bill.getProducts().get(i).getName() + "      " + bill.getProducts().get(i).getPrice()+"x"+bill.getQuantities().get(i));
                        }
                    }
                    pw.println("               Total: " + bill.getTotalCost() + "$");
                    pw.println("|                                                    |");
                    pw.println("|                                                    |");
                    pw.println(" ----------------------------------------------------");
                    Logger.log("Created bill no: " + Database.Bills.getObjects().size()+".");
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Bill saved!");
                    alert.setHeaderText("Bill no: " + Database.Bills.getObjects().size() +" saved successfully");
                    alert.showAndWait();
                    Database.Products.save();
                    Database.Bills.addObject(bill);
                    Database.Bills.save();
                    view.getSave().setDisable(true);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
