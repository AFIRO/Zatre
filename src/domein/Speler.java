package domein;

import java.util.Calendar;

public class Speler {

	private final String gebruikersnaam;
	private final int geboortejaar;
	private int speelkansen;
	
	public Speler(String gebruikersnaam, int geboortejaar) 
	{
		this(gebruikersnaam, geboortejaar, 5);
	}
	
	public Speler (String gebruikersnaam, int geboortejaar, int speelkansen)
	{	
		controleerGebruikersnaam (gebruikersnaam);
		this.gebruikersnaam = gebruikersnaam;
		controleerGeboortejaar (geboortejaar);
		this.geboortejaar = geboortejaar;
		setSpeelkansen(speelkansen);
	}
	
	private void controleerGebruikersnaam (String gebruikersnaam) {
		if (gebruikersnaam.length() < 5)
			throw new IllegalArgumentException ("gebruikersnaam moet langer zijn dan 5 tekens.");
	}
	
	private void controleerGeboortejaar (int geboortejaar) {
		if((Calendar.getInstance().get(Calendar.YEAR) - geboortejaar) < 6)
			throw new IllegalArgumentException ("speler is jonger dan 6 jaar");
	}

	public int getSpeelkansen() 
	{
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