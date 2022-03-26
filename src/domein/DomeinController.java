package domein;

import persistence.SpelerMapper;
import util.Taal;

import java.util.ArrayList;
import java.util.List;

public class DomeinController {
	private final SpelerRepository spelerRepository;
	private final Taal taal;
	private Spel spel;

	public DomeinController(Taal taal) {
		this.taal = taal;
		this.spelerRepository = new SpelerRepository(new SpelerMapper()); //Andreeas: Dependency injection nodig voor Mockito

	}

	/**
	 * Alle UC's: getter voor localisatie
	 */

	public Taal getTaal() {
		return taal;
	}

	/**
	 * UC1: speler registreren
	 * exception kan gegooid worden door Speler als Repository (zie hun JavaDoc)
	 * @param gebruikersnaam Gebruikersnaam van speler
	 * @param geboortejaar Geboortejaar van speler
	 */

	public void registreer(String gebruikersnaam, int geboortejaar) {
		spelerRepository.voegSpelerToe(gebruikersnaam, geboortejaar);
	}

	/**
	 * UC2: speler aanmelden
	 * exception kan gegooid worden door Speler als Repository (zie hun JavaDoc)
	 * @param gebruikersnaam Gebruikersnaam van speler
	 * @param geboortejaar Geboortejaar van speler
	 */

	public void meldAan(String gebruikersnaam, int geboortejaar) {
		spelerRepository.vraagSpelerOp(gebruikersnaam, geboortejaar);
	}

	/**
	 * UC1  en UC3: String representatie van een specifieke speler
	 * exception kan gegooid worden door Speler als Repository (zie hun JavaDoc)
	 * @param gebruikersnaam Gebruikersnaam van speler
	 * @param geboortejaar Geboortejaar van speler
	 * @exception IllegalArgumentException indien gebruikersnaam te kort
	 */

	public String geefSpeler(String gebruikersnaam, int geboortejaar) { // opmaak string in UC
		Speler speler = spelerRepository.geefSpeler(gebruikersnaam, geboortejaar);

		return String.format("%n%s %s%n%s %d%n%s %d%n%n", taal.getLocalisatie("GEBRUIKERSNAAM"), speler.getGebruikersnaam(),
				taal.getLocalisatie("GEBOORTEJAAR"),speler.getGeboortejaar(),
				taal.getLocalisatie("SPEELKANSEN"), speler.getSpeelkansen());
	}

	/**
	 * UC2 en UC3: String representatie van alle aangemelde spelers een specifieke speler
	 * exception kan gegooid worden door Speler als Repository (zie hun JavaDoc)
	 * @exception IllegalArgumentException indien gebruikersnaam te kort
	 */
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


	/**
	 * UC3: Spel starten.
	 * exception kan gegooid worden door Speler als Repository (zie hun JavaDoc) TESS: toch graag extra toelichting
	 */
	public void startSpel() {
		this.spel = new Spel(spelerRepository.geefSpelers());
		spel.startSpel();

	}
	
	//uit te werken:
	//public List<String> geefScorebladen() {
	//	}
	
	//na te kijken
	public String toonWinnaar() {
		return String.format("%s%n%s", taal.getLocalisatie("WINNAAR"),
				spel.toonWinnaar());
	}
	
	/**
	 * UC4: nog nader te bepalen na ontwerp UC3
	 * @param gebruikersnaam
	 * @param geboortejaar
	 * @param vak
	 * @param steen
	 */
	public void speelBeurt(String gebruikersnaam, String geboortejaar, String vak, int steen) {
		spel.speelBeurt(gebruikersnaam, geboortejaar,vak, steen);
	}
	
	


}