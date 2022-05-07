package domein;

import persistence.SpelerMapper;
import util.Taal;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DomeinController {
	private final SpelerRepository spelerRepository;
	private Taal taal;
	private Spel spel;

	/**
	 * Alle UC's: constructor DC
	 */

	public DomeinController() {
		this.spelerRepository = new SpelerRepository(new SpelerMapper()); // Andreeas: Dependency injection nodig voor
		// Mockito

	}

	/**
	 * Alle UC's: getter voor localisatie
	 */

	public Taal getTaal() {
		return taal;
	}

	/**
	 * Alle UC's: setter voor localisatie
	 */

	public void setTaal(Taal taal) {
		this.taal = taal;
	}

	/**
	 * UC1: speler registreren exception kan gegooid worden door Speler als
	 * Repository (zie hun JavaDoc)
	 *
	 * @param gebruikersnaam Gebruikersnaam van speler
	 * @param geboortejaar   Geboortejaar van speler
	 */

	public void registreer(String gebruikersnaam, int geboortejaar) {
		spelerRepository.voegSpelerToe(gebruikersnaam, geboortejaar);
	}

	/**
	 * UC2: speler aanmelden exception kan gegooid worden door Speler als Repository
	 * (zie hun JavaDoc)
	 *
	 * @param gebruikersnaam Gebruikersnaam van speler
	 * @param geboortejaar   Geboortejaar van speler
	 */

	public void meldAan(String gebruikersnaam, int geboortejaar) {
		spelerRepository.vraagSpelerOp(gebruikersnaam, geboortejaar);
	}

	/**
	 * UC1 en UC3: String representatie van een specifieke speler exception kan
	 * gegooid worden door Speler als Repository (zie hun JavaDoc)
	 *
	 * @param gebruikersnaam Gebruikersnaam van speler
	 * @param geboortejaar   Geboortejaar van speler
	 * @throws IllegalArgumentException indien gebruikersnaam te kort
	 */

	public String geefSpeler(String gebruikersnaam, int geboortejaar) { // opmaak string in UC
		Speler speler = spelerRepository.geefSpeler(gebruikersnaam, geboortejaar);

		return String.format("%n%s %s%n%s %d%n%s %d%n%n", taal.getLocalisatie("GEBRUIKERSNAAM"),
				speler.getGebruikersnaam(), taal.getLocalisatie("GEBOORTEJAAR"), speler.getGeboortejaar(),
				taal.getLocalisatie("SPEELKANSEN"), speler.getSpeelkansen());
	}

	/**
	 * UC2 en UC3: String representatie van alle aangemelde spelers een specifieke
	 * speler exception kan gegooid worden door Speler als Repository (zie hun
	 * JavaDoc)
	 *
	 * @throws IllegalArgumentException indien gebruikersnaam te kort
	 */
	public List<String> geefSpelers() { // opmaak string in UC
		List<Speler> spelers = spelerRepository.geefSpelers();

		List<String> players = new ArrayList<>();

		String output;
		for (Speler speler : spelers) {
			output = String.format("%s %d: %s%n", taal.getLocalisatie("SPELER"), spelers.indexOf(speler) + 1,
					speler.getGebruikersnaam());
			output += String.format("%n%s %s%n%s %d%n%s %d%n%n", taal.getLocalisatie("GEBRUIKERSNAAM"),
					speler.getGebruikersnaam(), taal.getLocalisatie("GEBOORTEJAAR"), speler.getGeboortejaar(),
					taal.getLocalisatie("SPEELKANSEN"), speler.getSpeelkansen());
			players.add(output);
		}
		return players;
	}

	/**
	 * UC2: tonen hoeveel spelers er in het spel zitten
	 *
	 * @return aantalSpelersInSpel
	 */
	public int geefAantalSpelersInSpel() {
		return spelerRepository.geefSpelers().size();
	}

	/**
	 * UC3: Spel starten. exception kan gegooid worden door Speler als Repository
	 * (zie hun JavaDoc)
	 */
	public void startSpel() {
		this.spel = new Spel(spelerRepository.geefSpelers());
		spel.startSpel();

	}

	/**
	 * UC3: Geeft string respresentatie van winnende speler
	 */

	private String geefWinnaar() {
		return String.format("%s%n%s %s%n%s %s%n%s %d%n%n", taal.getLocalisatie("WINNAAR"),
				taal.getLocalisatie("GEBRUIKERSNAAM"), spel.geefWinnaar().getGebruikersnaam(),
				taal.getLocalisatie("GEBOORTEJAAR"), spel.geefWinnaar().getGeboortejaar(),
				taal.getLocalisatie("SPEELKANSEN"), spel.geefWinnaar().getSpeelkansen());
	}

	/**
	 * UC4: laat speler toe om een beurt te spelen
	 *
	 * @param gebruikersnaam gebruikersnaam van de gebruiker
	 * @param geboortejaar   geboortejaar van de gebruiker
	 * @param zetten         de uit te voeren zetten.
	 */
	public void speelBeurt(String gebruikersnaam, String geboortejaar, List<String> zetten) {
		spel.speelBeurt(gebruikersnaam, geboortejaar, zetten);
	}

	/**
	 * UC4: vraagt aan spel voor twee of drie willekeurige stenen uit het
	 * stenenzakje
	 *
	 * @param eersteBeurt boolean die aangeeft of het gaat om de eerste beurt of
	 *                    niet.
	 * @return int[] met daarin de waarden van de twee stenen op index 0 en 1.
	 */
	public int[] vraagSteentjes(boolean eersteBeurt) {
		if (eersteBeurt)
			return spel.haalDrieStenenUitSteenzakjeEnGeefAanSpelerBijEersteBeurt();
		else
			return spel.haalTweeStenenUitSteenzakjeEnGeefAanSpeler();
	}

	/**
	 * UC4: steekt een steen die niet geplaatst kan worden terug in de zak
	 *
	 * @param waardeVanSteen waarde van de steen die terug in zak moet.
	 */
	public void steekSteentjeTerugInZak(int waardeVanSteen) {
		spel.steekSteentjeTerugInZak(waardeVanSteen);
	}

	/**
	 * UC4: genereert het laatste scoreblad waarop naam van de winnaar en diens
	 * scores staat. Vervolgens worden de spelers weggeschreven naar de databasis en
	 * wordt de repository gewiped voor volgend spel. Deze methode wordt enkel
	 * aangeroepen indien de GUI de steentjes array [0,0] krijgt. Het spel is dan
	 * gedaan.
	 *
	 * @return laatste scoreblad
	 */
	public List<String> eindigSpel() {
		List<String> laatsteScorebladenOmTeTonen = new ArrayList<>();
		laatsteScorebladenOmTeTonen.add(geefWinnaar());
		for (Speler speler : spel.getSpelers()) {
			laatsteScorebladenOmTeTonen.add(taal.getLocalisatie("SCOREBLAD_VAN") + speler.getGebruikersnaam());
			laatsteScorebladenOmTeTonen.add("DT  /  10  /  11  /  12  /  Bonus  /  Totaal");
			laatsteScorebladenOmTeTonen.addAll(speler.getScoreblad().getRegels());
			laatsteScorebladenOmTeTonen
					.add(taal.getLocalisatie("TOTALE_SCORE") + speler.getScoreblad().berekenScoreVanScoreblad());
			laatsteScorebladenOmTeTonen.add("\n");
		}
		updateSpelersInDatabaseNaSpel();
		resetRepositoryVoorNieuwSpel();
		return laatsteScorebladenOmTeTonen;
	}

	/**
	 * UC3: laat een speler toe om het spel te cancelen. Alle spelers krijgen hun
	 * gebruikte speelkans terug. Vervolgens worden de spelers geupdate in de
	 * database en wordt de repository gewiped als voorbereiding op een nieuw spel.
	 */
	public void cancelSpel() {
		spel.cancelSpel();
		updateSpelersInDatabaseNaSpel();
		resetRepositoryVoorNieuwSpel();
	}

	/**
	 * UC3: schrijft de staat van de spelers na spel terug naar de databasis wordt
	 * de repository gewiped als voorbereiding op een nieuw spel.
	 */
	private void updateSpelersInDatabaseNaSpel() {
		for (Speler speler : spelerRepository.geefSpelers())
			spelerRepository.updateSpeler(speler);
	}

	/**
	 * UC3: wiped de repository als voorbereiding op een nieuw spel.
	 */

	private void resetRepositoryVoorNieuwSpel() {
		spelerRepository.geefSpelers().removeAll(spelerRepository.geefSpelers());
	}

	/**
	 * UC3: deze methode geeft een lijst van de scorebladen terug van de spelers
	 *
	 * @return een lijst aan strings met de string representatie van de scorebladen
	 *         van elke speler.
	 */
	public List<String> geefScoreBladVanActieveSpeler() {
		return spel.getHuidigeActieveSpeler().getScoreblad().getRegels().stream().toList();
	}

	/**
	 * UC3: bepaalt wanneer het spel be�indigd is of wordt
	 */
	public boolean isEindeSpel() {
		return spel.isEindeSpel();
	}

	/**
	 * UC4: tussentijdse evaluatie van zet in domein
	 *
	 * @param vak         gespeeld vak
	 * @param eersteSteen boolean die representeert of dit de eerste steen is (die
	 *                    enkel maar op 8.8 mag)
	 * @param steen       gespeelde steen
	 * @return of de zet legaal was niet.
	 */

	public boolean checkOfZetLegaalIsTussenTijdseValidatie(boolean eersteSteen, String vak, String steen) {
		return spel.checkOfZetLegaalIsTussenTijdseValidatie(eersteSteen, vak, steen);
	}

	/**
	 * UC4: finale evaluatie van zet in domein
	 *
	 * @param zetten string representatie van de zetten
	 * @return of de zetten legaal waren
	 */

	public boolean checkOfZettenLegaalZijnEindValidatie(List<String> zetten) {
		return spel.checkOfZettenLegaalZijnEindValidatie(zetten);
	}

	/**
	 * UC4: het spel gaat naar volgende beurt.
	 */

	public void volgendeSpeler() {
		spel.volgendeSpeler();
	}

	/**
	 * UC4: geeft nuttige string representatie van de speler die aan de beurt is
	 *
	 * @return lijst string op van de actieve speler met de naam, op 1 geboortejaar
	 *         en op 2 de speelkansen
	 */

	public List<String> geefActieveSpeler() {
		List<String> actieveSpelerGegevens = new ArrayList<>();
		actieveSpelerGegevens.add(spel.getHuidigeActieveSpeler().getGebruikersnaam());
		actieveSpelerGegevens.add(String.valueOf(spel.getHuidigeActieveSpeler().getGeboortejaar()));
		actieveSpelerGegevens.add(String.valueOf(spel.getHuidigeActieveSpeler().getSpeelkansen()));
		return actieveSpelerGegevens;

	}
}
