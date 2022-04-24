import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import managers.GUIManager;
import utility.Utilities;
import views.SplashScreenView;

import javax.swing.text.html.StyleSheet;
import java.util.concurrent.TimeUnit;

public class Preloader extends javafx.application.Preloader {
    private static Stage stage;
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Scene sc = new Scene(new SplashScreenView().getView());
        stage.setScene(sc);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        Utilities.centerStage(stage);
    }

    public static Stage getStage() {
        return stage;
    }
}
