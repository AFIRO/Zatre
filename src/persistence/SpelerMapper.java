package persistence;

import domein.Speler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class SpelerMapper {
	private static final String INSERT_SPELER = "INSERT INTO ID372560_SDProjectG101.Speler (gebruikersnaam, geboortejaar, speelkansen) VALUES (?, ?, ?)";
	private static final String GET_SPELERS = "SELECT * FROM ID372560_SDProjectG101.Speler";
	private static final String GET_SPELER = "SELECT * FROM ID372560_SDProjectG101.Speler WHERE gebruikersnaam = ? AND geboortejaar = ?";
	private static final String UPDATE_SPELER = "UPDATE ID372560_SDProjectG101.Speler SET speelkansen = ? WHERE gebruikersnaam = ? AND geboortejaar = ?";
    private static final String VERWIJDER_SPELER = "UPDATE ID372560_SDProjectG101.Speler SET speelkansen = ? WHERE gebruikersnaam = ? AND geboortejaar = ?";

	public void voegSpelerToe(Speler speler)  {

		try (
				Connection connection = DriverManager.getConnection(Connectie.JDBC_URL, Connectie.userName, Connectie.password);
				PreparedStatement query = connection.prepareStatement(INSERT_SPELER)) {

			query.setString(1, speler.getGebruikersnaam());
			query.setInt(2, speler.getGeboortejaar());
            query.setInt(3, speler.getSpeelkansen());
            query.executeUpdate();

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public List<Speler> geefSpelers() {
        List<Speler> spelers = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(Connectie.JDBC_URL, Connectie.userName, Connectie.password);
                PreparedStatement query = connection.prepareStatement(GET_SPELERS);
                ResultSet gevondenSpelers = query.executeQuery()) {


            while (gevondenSpelers.next()) {
                String gebruikersnaam = gevondenSpelers.getString("gebruikersnaam");
                int geboortejaar = gevondenSpelers.getInt("geboortejaar");
                int speelkansen = gevondenSpelers.getInt("speelkansen");

                spelers.add(new Speler(gebruikersnaam, geboortejaar, speelkansen));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return spelers;
    }

    public Speler geefSpeler(String gebruikersnaam, int geboortejaar) {

        Speler speler;

        try (
				Connection connection = DriverManager.getConnection(Connectie.JDBC_URL, Connectie.userName, Connectie.password);
				PreparedStatement query = connection.prepareStatement(GET_SPELER)) {

			query.setString(1, gebruikersnaam);
            query.setInt(2, geboortejaar);

            try (ResultSet gevondenSpeler = query.executeQuery()) {
                if (gevondenSpeler.next()) {
                    String gevondenGebruikersnaam = gevondenSpeler.getString("gebruikersnaam");
                    int gevondenGeboortejaar = gevondenSpeler.getInt("geboortejaar");
                    int gevondenSpeelkansen = gevondenSpeler.getInt("speelkansen");

                    speler = new Speler(gevondenGebruikersnaam, gevondenGeboortejaar, gevondenSpeelkansen);
                }
				else
					throw new IllegalArgumentException(ResourceBundle.getBundle("dictionary", Locale.getDefault()).getString("SPELER_BESTAAT_NIET"));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return speler;
    }

	public void updateSpeler(Speler speler) {
		try (
				Connection connection = DriverManager.getConnection(Connectie.JDBC_URL, Connectie.userName, Connectie.password);
				PreparedStatement query = connection.prepareStatement(UPDATE_SPELER)) {
			query.setInt(1, speler.getSpeelkansen());
			query.setString(2, speler.getGebruikersnaam());
			query.setInt(3, speler.getGeboortejaar());
			query.executeUpdate();

		} catch (SQLException throwables) {
			throwables.printStackTrace();
		}
	}
	}
