package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class LoginPaneel extends VBox {
	    private final MenuPaneel menuPaneel;
	    private final DomeinController domeinController;

	    public LoginPaneel(MenuPaneel menuPaneel, DomeinController domeinController) {
	        this.menuPaneel = menuPaneel;
	        this.domeinController = domeinController;
	        voegComponentenToe();
	    }

	    private void voegComponentenToe()
	    {
	    	 Button btnQuit = new Button(domeinController.getTaal().getLocalisatie("GUI_STARTMENU_5"));
	    	 btnQuit.setOnAction(this::quit);
	    }
	    
	    
	    public void quit(ActionEvent actionEvent) {
	        System.exit(0);

	    }
}
