package cui;

import domein.DomeinController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UC2 {
	private final Scanner scanner;
	private final DomeinController domeinController;

	public UC2(DomeinController domeinController) {
		scanner = new Scanner(System.in);
		this.domeinController = domeinController;
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
						System.out.println(domeinController.geefVertaling("GEKENDE_NAAM"));
						gebruikersnaam = scanner.next();
						System.out.println(domeinController.geefVertaling("GEWENSTE_GEBOORTEDATUM"));
						geboortejaar = scanner.nextInt();
						inputLoopflag = false;
					} catch (InputMismatchException e) {
						System.out.println(domeinController.geefVertaling("CORRECTE_KEUZE"));
						scanner.next();
					}
					try {
						domeinController.meldAan(gebruikersnaam, geboortejaar);
						System.out.println(domeinController.geefVertaling("CORRECT_AANGEMELD"));
						System.out.printf("%s", domeinController.geefSpeler(gebruikersnaam, geboortejaar));
						System.out.println();
						System.out.println(domeinController.geefVertaling("NOG_AANMELDEN"));
						nogAanmelden = scanner.next();
						nogAanmelden.toLowerCase();
						if(!(nogAanmelden.charAt(0) == 'y')) {
							nextUser = false;
							loopflag = false;
						}
						//Codereview Andreeas: Omdat je hiet met HasNext werkt, blijft je antwoord vasthangen in
						//je scanner. Wanneer je terug aar regel 30 gaat voor gebruikersnaam, dan pakt hij het antwoord
						//dat hier stond (dus Y of Ja) als input voor de gebruikersnaam variabele bij .next()
						//Daardoor springt hij direct naar de input voor geboortejaar
						//probeer het antwoord voor NogAanmelden via next() op te slaan (let op exceptions) en
						//gebruik dat om een boolean te setten. Scanner is zelf niet in staat om rechtstreeks een
						//input van je gebruiker om te zetten naar een boolean.
						
					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
						System.out.println(domeinController.geefVertaling("PROBEER_OPNIEUW"));
					}
				}
		}
		// eerst alle spelers aanmelden en dan pas printen
		domeinController.geefSpelers();
	}
}
