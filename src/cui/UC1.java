package cui;

import domein.DomeinController;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UC1 {
	private final Scanner scanner = new Scanner(System.in);
	private final DomeinController domeinController;

	public UC1(DomeinController domeinController) {
		this.domeinController = domeinController;
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
					System.out.println(domeinController.getTaal().getLocalisatie("GEWENSTE_NAAM"));
					gebruikersnaam = scanner.next();
					System.out.println(domeinController.getTaal().getLocalisatie("GEWENSTE_GEBOORTEDATUM"));
					geboortejaar = scanner.nextInt();
					inputLoopflag = false;
				} catch (InputMismatchException e) {
					System.out.println(domeinController.getTaal().getLocalisatie("CORRECTE_KEUZE"));
					scanner.next();
				}
			try {
				domeinController.registreer(gebruikersnaam, geboortejaar);
				System.out.println(domeinController.getTaal().getLocalisatie("CORRECT_GEREGISTREERD"));

				System.out.print(domeinController.geefSpeler(gebruikersnaam, geboortejaar));
				loopflag = false;
			} catch (IllegalArgumentException e) {
				System.out.println(domeinController.getTaal().getLocalisatie(e.getMessage()));
				System.out.println(domeinController.getTaal().getLocalisatie("PROBEER_OPNIEUW"));
				registreerSpelerInputMenu();
			}
		}
	}
}
