package gui;




import domein.DomeinController;
import domein.Scoreblad;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SpelScorebladPaneel extends VBox {

	private final DomeinController domeinController;
	private Label lblTitel;
	private Label lblActieveSpeler;
	private Text txfScoreblad;
	private TableView scoreTable;

	/**
	 * UC3: constructor voor paneel
	 *
	 * @param domeinController de dc voor gebruik
	 */

	public SpelScorebladPaneel(DomeinController domeinController) {
		this.domeinController = domeinController;
		voegComponentenToe();
	}

	/**
	 * UC3: initaliseert de elementen, geeft hen de correcte styling en plaatst hen
	 * op de juiste plaats.
	 */

	private void voegComponentenToe() {
		// instantie elementen
		lblTitel = new Label(domeinController.getTaal().getLocalisatie("HUIDIGE_SPELER"));
		lblActieveSpeler = new Label();
		//txfScoreblad = new Text();
		// maak hier een TableView

		scoreTable = new TableView<Scoreblad>();

		TableColumn<Scoreblad, String> col1 = new TableColumn<>("DT");
		// column1.setCellValueFactory(new PropertyValueFactory<>("DT"));

		TableColumn<Scoreblad, String> col2 = new TableColumn<>("10");
		// column2.setCellValueFactory(new PropertyValueFactory<>("10"));

		TableColumn<Scoreblad, String> col3 = new TableColumn<>("11");
		// column3.setCellValueFactory(new PropertyValueFactory<>("11"));

		TableColumn<Scoreblad, String> col4 = new TableColumn<>("12");
		// column4.setCellValueFactory(new PropertyValueFactory<>("12"));

		TableColumn<Scoreblad, String> col5 = new TableColumn<>("Bonus");
		// column5.setCellValueFactory(new PropertyValueFactory<>("Bonus"));

		TableColumn<Scoreblad, String> col6 = new TableColumn<>("Total");
		// column6.setCellValueFactory(new PropertyValueFactory<>("Total"));

		scoreTable.getColumns().addAll(col1, col2, col3, col4, col5, col6);
		
		//scoreTable.getColumns().add(col1);
		//scoreTable.getColumns().add(col2);
	//	scoreTable.getColumns().add(col3);
		//scoreTable.getColumns().add(col4);
		//scoreTable.getColumns().add(col5);
		//scoreTable.getColumns().add(col6);

		col1.prefWidthProperty().bind(scoreTable.widthProperty().multiply(0.15));
		col2.prefWidthProperty().bind(scoreTable.widthProperty().multiply(0.15));
		col3.prefWidthProperty().bind(scoreTable.widthProperty().multiply(0.15));
		col4.prefWidthProperty().bind(scoreTable.widthProperty().multiply(0.15));
		col5.prefWidthProperty().bind(scoreTable.widthProperty().multiply(0.2));
		col6.prefWidthProperty().bind(scoreTable.widthProperty().multiply(0.2));

		col1.setResizable(false);
		col2.setResizable(false);
		col3.setResizable(false);
		col4.setResizable(false);
		col5.setResizable(false);
		col6.setResizable(false);

		// styling
		lblTitel.setStyle("-fx-font-size: 2em");
		lblActieveSpeler.setStyle("-fx-font-size: 1.5em");
		this.setPrefHeight(1000);
		this.setPrefWidth(350);
		this.setAlignment(Pos.TOP_LEFT);
		scoreTable.setMaxWidth(325);
		scoreTable.setMinHeight(500);
	
		
		// insert in GUI
		this.getChildren().addAll(lblTitel, lblActieveSpeler, scoreTable);

	}

	/**
	 * UC4: eventhandler voor update van info in dit scherm
	 */

	public void updateInfo() {
		this.lblActieveSpeler.setText(domeinController.geefActieveSpeler().get(0));

		for (String Regel : domeinController.geefScoreBladVanActieveSpeler()) {
			String[] score = Regel.split(" ");
			for(String cell : score) {
				scoreTable.getItems().add(cell.toString());
			}
		
			// voeg row aan table toe
			//for (String item : score) {
				// voeg toe aan row
			//}
		}
		// this.txfScoreblad.setText(domeinController.geefScoreBladVanActieveSpeler());
	}
}