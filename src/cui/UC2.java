package cui;

import domein.DomeinController;
import util.Taal;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

public class UC2 {
	private final Scanner scanner;
	private final DomeinController domeinController;
	private Taal taal;

	public UC2(DomeinController domeinController) {
		scanner = new Scanner(System.in);
		this.domeinController = domeinController;
		taal = domeinController.getTaal();
		meldAanInputMenu();
	}

	private void meldAanInputMenu() {
		boolean loopflag = true;
		boolean inputLoopflag = true;
		boolean nextUser = true;
		String nogAanmelden = "";
		int geboortejaar = 0;
		String gebruikersnaam = "";

		while (loopflag) {
			while (inputLoopflag)
				while (nextUser) {
					try {
						System.out.println(taal.getLocalisatie("GEKENDE_NAAM"));
						gebruikersnaam = scanner.next();
						System.out.println(taal.getLocalisatie("GEWENSTE_GEBOORTEDATUM"));
						geboortejaar = scanner.nextInt();
						inputLoopflag = false;
					} catch (InputMismatchException e) {
						System.out.println(taal.getLocalisatie("CORRECTE_KEUZE"));
						scanner.next();
					}
					try {
						domeinController.meldAan(gebruikersnaam, geboortejaar);
						System.out.println(taal.getLocalisatie("CORRECT_AANGEMELD"));
						System.out.printf("%s", domeinController.geefSpeler(gebruikersnaam, geboortejaar));
						System.out.println();

						if (domeinController.geefSpelers().size() == 4) {
							nextUser = false;
							loopflag = false;
							break;
						}
						System.out.println(taal.getLocalisatie("NOG_AANMELDEN"));
						nogAanmelden = scanner.next().toLowerCase();
						if (!(nogAanmelden.charAt(0) == 'y')) {
							nextUser = false;
							loopflag = false;
						}
					} catch (IllegalArgumentException e) {
						System.out.println(taal.getLocalisatie(e.getMessage()));
						System.out.println(taal.getLocalisatie("PROBEER_OPNIEUW"));
					}
				}
		}
		//Vertaling voor output moet nog moet nog aan vettaling passeren
		for (String speler : domeinController.geefSpelers()) {
			System.out.print(speler);
		}
	}
}
