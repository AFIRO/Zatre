package gui;

import domein.DomeinController;
import javafx.scene.layout.VBox;

public class SpelSpelerPaneel extends VBox {

	private final DomeinController domeinController;
	private final SpelPaneel spelPaneel;

	public SpelSpelerPaneel(SpelPaneel spelPaneel, DomeinController domeinController) {
		this.domeinController = domeinController;
		this.spelPaneel = spelPaneel;
	}
}