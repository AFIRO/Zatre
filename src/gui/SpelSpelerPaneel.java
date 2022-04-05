package gui;

import domein.DomeinController;
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
		// TODO Auto-generated method stub
		
	}
}