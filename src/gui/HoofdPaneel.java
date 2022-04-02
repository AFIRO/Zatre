package gui;

import domein.DomeinController;
import javafx.scene.layout.BorderPane;


public class HoofdPaneel extends BorderPane
{
    private DomeinController domeinController;
    private final TaalPaneel taalPaneel;
    private MenuPaneel menuPaneel;


    public HoofdPaneel(DomeinController dc)
    {
    	domeinController = dc;
    	taalPaneel = new TaalPaneel(domeinController, this);
    	
        voegComponentenToe();
    } //Sofie: aangepast volgens feedback maar er komt rode lijn onder
    
    private void voegComponentenToe()
    {
        setCenter(taalPaneel);

    }

    public void taalGekozen(){
        this.menuPaneel = new MenuPaneel(this,domeinController);
        setCenter(menuPaneel);
    }

    public void setDomeincontroller(DomeinController domeinController) {
        this.domeinController = domeinController;
    }
}
