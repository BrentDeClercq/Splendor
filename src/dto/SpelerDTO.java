package dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import domein.Edele;
import domein.Edelsteen;
import domein.Ontwikkelingskaart;
import domein.Speler;

//public record SpelerDTO(String gebruikersnaam,int prestigepunten, EdeleDTO edeleDTO, OntwikkelingskaartDTO ontwikelingskaartDTO, EdelsteenDTO edelsteenDTO) {

//}
public record SpelerDTO(String gebruikersnaam, int geboortejaar, ArrayList<EdeleDTO> edelen, ArrayList<OntwikkelingskaartDTO> ontwikkelingskaarten, ArrayList<Integer> edelsteenFiches, ArrayList<Integer> bonussen, int prestige) {

    public SpelerDTO(Speler speler) {
	this(speler.getGebruikersnaam(), speler.getGeboortejaar(), edelenDTO(speler.getEdelen()), ontwikkelingskaartenDTO(speler.getOntwikkelingskaarten()), edelsteenHashMapDTO(speler.getEdelsteenFiches()), edelsteenHashMapDTO(speler.getBonussen()), speler.getPrestige());
    }

    // filler voor error te corrigeren in DomeinController:74, weet niet of die arraylists juist gaan werken
    public SpelerDTO(String gebruikersnaam) {
	this(gebruikersnaam, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0);
    }

    private static ArrayList<EdeleDTO> edelenDTO(ArrayList<Edele> edelenInp) {
	// edelen -> DTO
	ArrayList<EdeleDTO> edelenDTO = new ArrayList<>();
	for (Edele edele : edelenInp) {
	    edelenDTO.add(new EdeleDTO(edele));
	}
	return edelenDTO;
    }

    private static ArrayList<OntwikkelingskaartDTO> ontwikkelingskaartenDTO(ArrayList<Ontwikkelingskaart> ontwikkelingskaartenInp) {
	// ontwikkelingskaarten -> DTO
	ArrayList<OntwikkelingskaartDTO> ontwikkelingskaartenDTO = new ArrayList<>();
	for (Ontwikkelingskaart kaart : ontwikkelingskaartenInp) {
	    ontwikkelingskaartenDTO.add(new OntwikkelingskaartDTO(kaart));
	}
	return ontwikkelingskaartenDTO;
    }

    private static ArrayList<Integer> edelsteenHashMapDTO(HashMap<Edelsteen, Integer> edelsteenHashMapInp) {
	// edelsteenFiches -> DTOs
	ArrayList<Integer> edelsteenHashMapDTO = new ArrayList<>();
	for (int i = 0; i < edelsteenHashMapInp.keySet().size(); i++) {
	    edelsteenHashMapDTO.add(edelsteenHashMapInp.get(Edelsteen.toSoort(i)));
	}
	return edelsteenHashMapDTO;
    }

    @Override
    public String toString() {
	return String.format("%s (%d)", this.gebruikersnaam, this.geboortejaar);
    }
}