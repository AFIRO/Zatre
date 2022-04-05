package gui;

import domein.DomeinController;
import javafx.scene.layout.GridPane;

public class SpelBordPaneel extends GridPane{
	 private final DomeinController domeinController;
	 private final SpelPaneel spelPaneel;

	public SpelBordPaneel(SpelPaneel spelPaneel, DomeinController domeinController) {
		this.domeinController = domeinController;
		this.spelPaneel = spelPaneel;
		  voegComponentenToe();
		
	}

	private void voegComponentenToe() {
		// TODO Auto-generated method stub
		
	}
	
}
