package views;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class SplashScreenView {

    Parent view;

    public SplashScreenView() {
        view = createView();
    }

    public BorderPane createView() {
        BorderPane p = new BorderPane();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("res/images/splashscreen.png");
        } catch (FileNotFoundException e) {
            //TODO
            // Add error class to manage errors
            e.printStackTrace();
        }
        Image img = new Image(fis);
        ImageView imgview = new ImageView(img);
        p.setCenter(imgview);
        return p;
    }

    public Parent getView() {
        return view;
    }
}
