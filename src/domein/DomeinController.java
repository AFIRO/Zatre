package domein;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class DomeinController {
	private final SpelerRepository spelerRepository;

	public DomeinController() {
		this.spelerRepository = new SpelerRepository();
	}

	public void registreer(String gebruikernaam, int geboortejaar) {
		spelerRepository.voegSpelerToe(gebruikernaam, geboortejaar);

	}


	public void meldAan(String gebruikersnaam, int geboortejaar) {
		spelerRepository.vraagSpelerOp(gebruikersnaam, geboortejaar);
	}

	public String geefSpeler(String gebruikersnaam, int geboortejaar) {
		String naam = spelerRepository.geefSpeler(gebruikersnaam, geboortejaar).getGebruikersnaam();
		int kansen = spelerRepository.geefSpeler(gebruikersnaam, geboortejaar).getSpeelkansen();

		return String.format("%s%s%n%s%d%n", ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("GEBRUIKERSNAAM"), naam, 
				ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("SPEELKANSEN"), kansen);
	}

	public String geefSpelers() {
		List<Speler> spelers = new ArrayList<>(spelerRepository.geefSpelers());

		int index = 1;
		String output = "";
		for (Speler speler : spelers) {
			String naam = speler.getGebruikersnaam();
			int geboortejaar = speler.getGeboortejaar();
			int speelkansen = speler.getSpeelkansen();
			output += String.format("%s%d%n%s%s%n%s%d%n%s%d%n", ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("SPELER"), index, 
					ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("GEBRUIKERSNAAM"), naam, 
					ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("GEBOORTEJAAR"), geboortejaar, 
					ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("SPEELKANSEN"), speelkansen);
			index++;
			output += "\n";
		}
		return output;
		
	}

}