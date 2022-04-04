package gui;

import domein.DomeinController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUp extends Application
{
    @Override
    public void start(Stage stage) 
    {
        DomeinController domeinController = new DomeinController();
        Scene scene = new Scene(new HoofdPaneel(domeinController), 600, 400);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String... args)
    {
    	Application.launch(StartUp.class, args);
    }
}
