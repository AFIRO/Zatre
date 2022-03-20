package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUp extends Application
{
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new HoofdPaneel(), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String... args)
    { Application.launch(StartUp.class, args);
    }
}
