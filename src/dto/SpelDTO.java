package dto;

import java.util.ArrayList;
import java.util.List;

import domein.Edele;
import domein.Edelsteen;
import domein.Ontwikkelingskaart;
import domein.Spel;
import domein.Speler;

public record SpelDTO(ArrayList<SpelerDTO> spelers, ArrayList<Integer> edelsteenFiches, ArrayList<ArrayList<OntwikkelingskaartDTO>> ontwikkelingskaartVeld, ArrayList<EdeleDTO> edelen) {

    public SpelDTO(Spel spel) {
	this(spelersDTO(spel.getSpelers()), Edelsteen.edelsteenHashMapDTO(spel.getEdelsteenFiches()), ontwikkelingskaartVeldDTO(spel.getOntwikkelingskaartVeld()), edelenDTO(spel.getEdelen()));
    }

    private static ArrayList<SpelerDTO> spelersDTO(ArrayList<Speler> spelersInp) {
	// spelers -> DTO
	ArrayList<SpelerDTO> spelersDTO = new ArrayList<>();
	for (Speler speler : spelersInp) {
	    spelersDTO.add(new SpelerDTO(speler));
	}
	return spelersDTO;
    }

    private static ArrayList<ArrayList<OntwikkelingskaartDTO>> ontwikkelingskaartVeldDTO(ArrayList<ArrayList<Ontwikkelingskaart>> veldInp) {
	// veld -> DTO
	ArrayList<ArrayList<OntwikkelingskaartDTO>> ontwikkelingskaartVeldDTO = new ArrayList<>();
	for (int i = 0; i < 3; i++) {
	    ontwikkelingskaartVeldDTO.add(new ArrayList<>());
	    for (Ontwikkelingskaart kaart : veldInp.get(i)) {
		ontwikkelingskaartVeldDTO.get(i).add(new OntwikkelingskaartDTO(kaart));
	    }
	}
	return ontwikkelingskaartVeldDTO;
    }

    private static ArrayList<EdeleDTO> edelenDTO(List<Edele> edelenInp) {
	// edelen -> DTO
	ArrayList<EdeleDTO> edelenDTO = new ArrayList<>();
	for (Edele edele : edelenInp) {
	    edelenDTO.add(new EdeleDTO(edele));
	}
	return edelenDTO;
    }

    public SpelerDTO spelerAanBeurt() {
	return spelers.get(0);
    }
}
