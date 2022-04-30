package gui;

import domein.DomeinController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUp extends Application {

    /**
     * UC3: main entrypoint
     */

    public static void main(String... args) {
        Application.launch(StartUp.class, args);
    }

    /**
     * UC3: startup voor GUI
     *
     * @param stage stage voor app
     */

    @Override
    public void start(Stage stage) {
        DomeinController domeinController = new DomeinController();
        Scene scene = new Scene(new HoofdPaneel(domeinController), 500, 500); // breedte x hoogte
        stage.setScene(scene);
        stage.setTitle("Zatre");
        stage.show();
    }
}
