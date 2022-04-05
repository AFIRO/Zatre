package gui;

import domein.DomeinController;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class SpelSpelerPaneel extends VBox {

	private final DomeinController domeinController;
	private final SpelPaneel spelPaneel;
	private final MenuPaneel menuPaneel;

	public SpelSpelerPaneel(SpelPaneel spelPaneel, MenuPaneel menuPaneel, DomeinController domeinController) {
		this.domeinController = domeinController;
		this.spelPaneel = spelPaneel;
		this.menuPaneel = menuPaneel;
		  voegComponentenToe();
	}

	private void voegComponentenToe() {
		Button btnVraagSteentjes = new Button(domeinController.getTaal().getLocalisatie("VRAAG_STEENTJES"));
		Button btnCancelSpel = new Button(domeinController.getTaal().getLocalisatie("CANCEL_SPEL"));
		
		btnVraagSteentjes.setOnAction(this::vraagSteentjes);
		btnCancelSpel.setOnAction(this::cancelSpel);
		
		this.getChildren().addAll(btnVraagSteentjes, btnCancelSpel);
	}
	
	
	private void vraagSteentjes() {
		
	}
	private void cancelSpel() {
		
	}
}