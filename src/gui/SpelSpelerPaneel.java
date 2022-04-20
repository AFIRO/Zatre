package gui;

import domein.DomeinController;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.ListIterator;

public class SpelSpelerPaneel extends VBox {
	private final DomeinController domeinController;
	private final MenuPaneel menuPaneel;
	private final HoofdPaneel hoofdPaneel;
	private boolean eersteBeurt = true;

	public SpelSpelerPaneel(HoofdPaneel hoofdPaneel, MenuPaneel menuPaneel, DomeinController domeinController) {
		this.hoofdPaneel = hoofdPaneel;
		this.domeinController = domeinController;
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

	private void vraagSteentjes(ActionEvent actionEvent) {
		verwijderStenenUitComponent();
		var stenen = domeinController.vraagSteentjes(eersteBeurt);
		if (eersteBeurt)
			eersteBeurt = false;
		for (int waarde : stenen){
			this.getChildren().addAll(new SteenGUI(waarde));
		}
	}

	private void verwijderStenenUitComponent(){
		this.getChildren().removeIf(node -> node instanceof SteenGUI);
	}

	private void cancelSpel(ActionEvent actionEvent) {
		domeinController.cancelSpel();
		hoofdPaneel.setCenter(menuPaneel);
	}
}