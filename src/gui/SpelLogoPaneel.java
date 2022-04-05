package gui;

import domein.DomeinController;
import javafx.scene.layout.HBox;

public class SpelLogoPaneel extends HBox {

	private final DomeinController domeinController;
	private final SpelPaneel spelPaneel;

	public SpelLogoPaneel(SpelPaneel spelPaneel, DomeinController domeinController) {
		this.domeinController = domeinController;
		this.spelPaneel = spelPaneel;  
		voegComponentenToe();
	}

	private void voegComponentenToe() {
		// TODO Auto-generated method stub
		
	}
}
