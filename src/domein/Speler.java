package domein;

public class Speler {

	private final String gebruikersnaam;
	private final int geboortejaar;
	private int speelkansen;
	
	public Speler(String gebruikersnaam, int geboortejaar) {
		this.gebruikersnaam = gebruikersnaam;
		this.geboortejaar = geboortejaar;
		setSpeelkansen(5);
		
	}
	
	

	public int getSpeelkansen() {
		return speelkansen;
	}



	public final void setSpeelkansen(int speelkansen) {
		
		//moet deze methode final zijn
		this.speelkansen = speelkansen;
	}



	public String getGebruikersnaam() {
		return this.gebruikersnaam;
	}

	public int getGeboortejaar() {
		return this.geboortejaar;
	}

	
	

}