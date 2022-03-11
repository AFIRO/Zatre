package cui;

import domein.DomeinController;
import util.Taal;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UC1 {
	private final Scanner scanner;
	private final DomeinController domeinController;
	private Taal taal;

	public UC1(DomeinController domeinController) {
		scanner = new Scanner(System.in);
		this.domeinController = domeinController;
		taal = domeinController.getTaal();
		registreerSpelerInputMenu();
	}

	private void registreerSpelerInputMenu() {
		boolean loopflag = true;
		boolean inputLoopflag = true;
		int geboortejaar = 0;
		String gebruikersnaam = "";

		while (loopflag) {
			while (inputLoopflag)
				try {
					System.out.println(taal.getLocalisatie("GEWENSTE_NAAM"));
					gebruikersnaam = scanner.next();
					System.out.println(taal.getLocalisatie("GEWENSTE_GEBOORTEDATUM"));
					geboortejaar = scanner.nextInt();
					inputLoopflag = false;
				} catch (InputMismatchException e) {
					System.out.println(taal.getLocalisatie("CORRECTE_KEUZE"));
					scanner.next();
				}
			try {
				domeinController.registreer(gebruikersnaam, geboortejaar);
				System.out.println(taal.getLocalisatie("CORRECT_GEREGISTREERD"));

				System.out.print(domeinController.geefSpeler(gebruikersnaam, geboortejaar));
				// methode geefSpeler dient aangepast te worden om de geregistreerde speler
				// terug te geven want die speler staat niet in de lijst van aangemelde spelers.

				// System.out.println(taal.getLocalisatie("GEBRUIKERSNAAM") + gebruikersnaam);
				// System.out.println(taal.getLocalisatie("GEBOORTEJAAR") + geboortejaar);
				// System.out.println();
				loopflag = false;
			} catch (IllegalArgumentException e) {
				System.out.println(taal.getLocalisatie(e.getMessage()));
				System.out.println(taal.getLocalisatie("PROBEER_OPNIEUW"));
				registreerSpelerInputMenu();
			}
		}
	}
}
