package domein;

import java.util.ArrayList;
import java.util.List;

import exceptions.LoginException;
import persistentie.SpelerMapper;

public class SpelerRepository {

    private List<Speler> spelersUitDatabank;

    public SpelerRepository() {
	try {
	    spelersUitDatabank = new ArrayList<Speler>(SpelerMapper.geefSpelersUitDatabank());
	} catch (LoginException le) {
	    throw le;
	}
    }

    public boolean controleerSpeler(Speler speler) {
	for (Speler s : spelersUitDatabank) {
	    if (speler.getGebruikersnaam().equals(s.getGebruikersnaam()) && speler.getGeboortejaar() == s.getGeboortejaar()) {
		return true;
	    }
	}
	return false;
    }
}
