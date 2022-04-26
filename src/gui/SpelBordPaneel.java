package gui;

import domein.DomeinController;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.List;

public class SpelBordPaneel extends GridPane{
	private final DomeinController domeinController;
	private final SpelSpelerPaneel spelSpelerPaneel;
	private static final int BOARD_SIZE = 16;
	private static final int VAK_SIZE = 50;

	public SpelBordPaneel(DomeinController domeinController, SpelSpelerPaneel spelSpelerPaneel) {
		this.domeinController = domeinController;
		this.spelSpelerPaneel = spelSpelerPaneel;
		this.setAlignment(Pos.CENTER);
		this.setWidth(1000);
		this.setHeight(1000);
		voegComponentenToe();
		
	}

	private void voegComponentenToe() {
		genereerBord();
	}

	/**
	 * UC3: genereert de vakken voor het spelbord, zet ze op de juiste plaatsen in GUI en koppelt de juiste eventhandler eraan.
	 */

	private void genereerBord() {
		//zelfde lijst als uit domein voor invalide vakjes
		List<Integer> controleLijst = List.of(1,2, 3, 4, 8, 12, 13, 14,15);
		//lijst met coordinaten van witte vakjes
		List<String> witteLijst = List.of("7.1","9.1", "7.15", "9.15", "1.7", "1.9", "15.7", "15.9");

		//itereer langs rij
		for (int rij = 1; rij < BOARD_SIZE; rij++) {
			//itereer langs kolom
			for (int kolom = 1; kolom < BOARD_SIZE; kolom++) {
				//Skip vakjes die niet mogen bestaan
				//case vakje uit bovenste rij bestaat niet
				if (rij == 1 && controleLijst.contains(kolom)) {
					continue;
				}
				//case vakje uit onderste rij bestaat niet
				if (rij == 15 && controleLijst.contains(kolom)) {
					continue;
				}
				//case vakje uit linker rij bestaat niet
				if (kolom == 1 && controleLijst.contains(rij)){
					continue;
				}
				//case vakje uit rechter rij bestaat niet
				if (kolom == 15 && controleLijst.contains(rij)){
					continue;
				}

				//maak vakje met correcte grootte en coordinates
				VakGUI vak = new VakGUI(VAK_SIZE,rij,kolom);
				//default waarden
				vak.setFill(Color.BLACK);
				vak.setStroke(Color.WHITE);

				//zet juiste vakjes wit
				if (vak.getKolom() == vak.getRij())
					vak.setFill(Color.WHITE);
				if (16-vak.getRij() == vak.getKolom())
					vak.setFill(Color.WHITE);
				if (witteLijst.contains(String.format("%d.%d",rij,kolom)))
					vak.setFill(Color.WHITE);
				//maak startvakje 8.8 rood
				if (rij == 8 && kolom == 8)
					vak.setFill(Color.RED);
				//plaats vakje op correcte plaats in gridpane
				setRowIndex(vak, rij);
				setColumnIndex(vak, kolom);
				//voeg eventhandler toe voor extra van coordinaten
				vak.setOnMousePressed(event -> clickVak(vak));
				//voeg vakje toe aan gridpane
				this.getChildren().add(vak);
			}
		}

	}

	/**
	 * UC3: eventhandler die indentiteit van vak doorstuurd naar correct paneel.
	 */

	private void clickVak(VakGUI vak) {
		this.spelSpelerPaneel.setGekliktVak(vak);
	}

}
