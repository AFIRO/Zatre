package domein;

import util.Taal;

import java.util.ArrayList;
import java.util.List;

public class DomeinController {
	private final SpelerRepository spelerRepository;
	private final Taal taal;
	private Spel spel;

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
			output = String.format("%s %d%n", taal.getLocalisatie("SPELER"), spelers.indexOf(speler)+1);
			output += String.format("%n%s %s%n%s %d%n%s %d%n%n", taal.getLocalisatie("GEBRUIKERSNAAM"), speler.getGebruikersnaam(),
					taal.getLocalisatie("GEBOORTEJAAR"),speler.getGeboortejaar(),
					taal.getLocalisatie("SPEELKANSEN"), speler.getSpeelkansen());
			players.add(output);
		}
		return players;
	}

	public void startSpel(List<Speler> spelers) {
		this.spel = new Spel(spelerRepository.geefSpelers());
		spel.startSpel();

	}


}