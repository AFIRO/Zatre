package domein;

import util.Taal;

import java.util.ArrayList;
import java.util.List;

public class DomeinController {
	private final SpelerRepository spelerRepository;
	private final Taal taal;

	// alle methodes die niet op DCD staan moeten private staan

	public DomeinController(Taal taal) {
		this.taal = taal;
		this.spelerRepository = new SpelerRepository();

	}

	public Taal getTaal() {
		return taal;
	}

	public void registreer(String gebruikernaam, int geboortejaar) {
		spelerRepository.voegSpelerToe(gebruikernaam, geboortejaar);
	}

	public void meldAan(String gebruikersnaam, int geboortejaar) {
		spelerRepository.vraagSpelerOp(gebruikersnaam, geboortejaar);
	}

	public String geefSpeler(String gebruikersnaam, int geboortejaar) { // opmaak string in UC
		Speler speler = spelerRepository.geefSpeler(gebruikersnaam, geboortejaar);

		return String.format("%s: %s%n%s: %d%n", "GEBRUIKERSNAAM", speler.getGebruikersnaam(), "SPEELKANSEN",
				speler.getSpeelkansen());
	}

	public List<String> geefSpelers() { // opmaak string in UC
		List<Speler> spelers = spelerRepository.geefSpelers();

		List<String> players = new ArrayList<>();

		String output;
		for (Speler speler : spelers) {
			output = String.format("%s: %d%n", "SPELER", spelers.indexOf(speler));
			output += speler.toString();
			players.add(output);
		}
		return players;
	}
	// aan te passen - List<Strings> door te geven. To String gebruiken in Speler ->
	// door Lorenz

	// mag weggehaald worden
	private String geefVertaling(String key) {
		return taal.getLocalisatie(key);
	}

	// te verwijderen van zodra geefSpelers list doorgeeft
	public int geefAantalSpelersInSpel() {
		return spelerRepository.geefSpelers().size();
	}

}