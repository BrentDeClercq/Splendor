package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.Speler;
import exceptions.LoginException;

public class SpelerMapper {

    public static List<Speler> geefSpelersUitDatabank() {
	List<Speler> spelersUitDatabank = new ArrayList<>();

	try (Connection conn = DriverManager.getConnection(Connectie.JDBC_URL); PreparedStatement query = conn.prepareStatement("SELECT * FROM ID399716_g92.spelers"); ResultSet rs = query.executeQuery()) {

	    while (rs.next()) {
		String gebruikersnaam = rs.getString("gebruikersnaam");
		int geboortejaar = rs.getInt("geboortejaar");

		spelersUitDatabank.add(new Speler(gebruikersnaam, geboortejaar));
	    }
	} catch (SQLException sqle) {
	    throw new LoginException(sqle);
	}

	return spelersUitDatabank;
    }

}