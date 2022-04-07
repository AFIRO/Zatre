package gui;

import domein.DomeinController;
import javafx.scene.Group;
import javafx.scene.layout.GridPane;

public class SpelPaneel extends GridPane {
    private final DomeinController domeinController;
    private final HoofdPaneel hoofdPaneel;
    private final MenuPaneel menuPaneel;
    private SpelLogoPaneel spelLogoPaneel;
    private SpelScorebladPaneel spelScorebladPaneel;
    private SpelBordPaneel spelBordPaneel;
    private SpelSpelerPaneel spelSpelerPaneel;

    public SpelPaneel(HoofdPaneel hoofdPaneel, MenuPaneel menuPaneel, DomeinController domeinController) {
        this.domeinController = domeinController;
        this.hoofdPaneel = hoofdPaneel;
        this.menuPaneel = menuPaneel;
        setSpelSchermen();
        voegComponentenToe();  
    }
    
    private void setSpelSchermen() {
    	this.spelLogoPaneel = new SpelLogoPaneel(domeinController);
        this.spelScorebladPaneel = new SpelScorebladPaneel(domeinController);
        this.spelBordPaneel = new SpelBordPaneel(domeinController);
        this.spelSpelerPaneel = new SpelSpelerPaneel(hoofdPaneel, menuPaneel, domeinController);
    }
    
    private void voegComponentenToe() {
		// TODO Auto-generated method stub
		
	}
    
    
}