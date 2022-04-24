package abstracts;

import javafx.scene.Parent;

public abstract class View {

    public Parent createView() {
        return null;
    }

    public Parent getView() { return createView(); }
}
