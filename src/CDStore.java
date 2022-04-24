import db.Database;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import managers.AccountManager;
import managers.GUIManager;
import models.Account;
import models.Bill;
import models.Employee;
import models.Stock;
import utility.Logger;
import utility.Session;
import utility.Utilities;

public class CDStore extends Application {
    BooleanProperty ready = new SimpleBooleanProperty(false);

    private void longStart() {
        //simulate long init in background
        Task task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(1000);
                ready.setValue(Boolean.TRUE);
                return null;
            }
        };
        new Thread(task).start();
    }
    @Override
    public void start(Stage stage) {
        longStart();

        // After the app is ready, show the stage
        ready.addListener(new ChangeListener<Boolean>(){
            public void changed(
                    ObservableValue<? extends Boolean> ov, Boolean t, Boolean t1) {
                if (Boolean.TRUE.equals(t1)) {
                    Platform.runLater(new Runnable() {
                        public void run() {
                            if (Database.Accounts.getObjects().size() == 0) {
                                AccountManager.createAdminAccount();
                            }
                            if (Database.Categories.getObjects().size() == 0) {
                                Stock.createDefaultCategory();
                            }
                            if(Database.Suppliers.getObjects().size() == 0) {
                                Stock.createDefaultSupplier();
                            }
                            // Hide the preloader
                            Preloader.getStage().hide();
                            // Keep track of the application's stage
                            GUIManager.stage = stage;
                            if (AccountManager.isUserLoggedIn()) {
                                Logger.log("Successfully logged in!");
                                //If the user checked the remember me box and a session is valid
                                GUIManager.displayMainView();
                            } else {
                                GUIManager.displayLoginView();
                            }
                        }
                    });
                }
            }
        });
    }

    public static void main(String[] args) {
        Font.loadFont( GUIManager.class.getClassLoader().getResourceAsStream( "css/fonts/bariolbold.ttf"), 10);
        Font.loadFont( GUIManager.class.getClassLoader().getResourceAsStream( "css/fonts/moon.otf"), 10);

        System.setProperty("javafx.preloader", Preloader.class.getCanonicalName());
        Application.launch(CDStore.class, args);
    }
}
