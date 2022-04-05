package gui;

import domein.DomeinController;
import javafx.scene.layout.VBox;

public class SpelScorebladPaneel extends VBox {

	private final DomeinController domeinController;
	private final SpelPaneel spelPaneel;

	public SpelScorebladPaneel(SpelPaneel spelPaneel, DomeinController domeinController) {
		this.domeinController = domeinController;
		this.spelPaneel = spelPaneel;
		  voegComponentenToe();
	}

	private void voegComponentenToe() {
		// TODO Auto-generated method stub
		
	}
}