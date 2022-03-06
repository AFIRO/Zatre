package domein;

import java.util.ArrayList;
import java.util.List;

public class DomeinController {
	private final SpelerRepository spelerRepository;

	public DomeinController() {
		this.spelerRepository = new SpelerRepository();
	}

	public void registreer(String gebruikernaam, int geboortejaar) {
		spelerRepository.voegSpelerToe(gebruikernaam, geboortejaar);

	}

	/*
	 * public Speler haalSpelerOp(String gebruikernaam, int geboortejaar) { return
	 * spelerRepository.getSpeler(gebruikernaam, geboortejaar); }
	 */

	public void meldAan(String gebruikersnaam, int geboortejaar) {
		spelerRepository.vraagSpelerOp(gebruikersnaam, geboortejaar);
	}

	public String geefSpeler(String gebruikersnaam, int geboortejaar) {
		String naam = spelerRepository.geefSpeler(gebruikersnaam, geboortejaar).getGebruikersnaam();
		int kansen = spelerRepository.geefSpeler(gebruikersnaam, geboortejaar).getSpeelkansen();

		return String.format("Gebruikersnaam: %s%nSpeelkansen: %d%n%n", naam, kansen);
	}

	public List<String> geefSpelers() {
		List<Speler> spelers = new ArrayList<>(spelerRepository.geefSpelers());
		List<String> aangemeldeSpelers = new ArrayList<>();

		int index = 1;

		for (Speler speler : spelers) {
			String naam = speler.getGebruikersnaam();
			int geboortejaar = speler.getGeboortejaar();
			int speelkansen = speler.getSpeelkansen();
			aangemeldeSpelers.add(String.format("Speler%d%nGebruikersnaam: %s%nGeboortejaar: %d%nSpeelkansen: %d%n%n",
					index, naam, geboortejaar, speelkansen));
			index++;
		}
		return aangemeldeSpelers;

	}

}