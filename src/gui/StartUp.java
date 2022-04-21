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
        Scene scene = new Scene(new HoofdPaneel(domeinController), 1000, 1000); //breedte x hoogte
        stage.setScene(scene);
        stage.setTitle("Zatre Application");
        stage.show();
    }

    public static void main(String... args)
    {
    	Application.launch(StartUp.class, args);
    }
}
