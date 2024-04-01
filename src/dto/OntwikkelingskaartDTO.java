package dto;

import java.util.ArrayList;
import java.util.HashMap;

import domein.Edelsteen;
import domein.Ontwikkelingskaart;

public record OntwikkelingskaartDTO(int niveau, int prestige, int bonusSoort, ArrayList<Integer> vereisteFiches, String img) {

    public OntwikkelingskaartDTO(Ontwikkelingskaart kaart) {
	this(kaart.getNiveau(), kaart.getPrestige(), Edelsteen.toInt(kaart.getBonus()), Edelsteen.edelsteenHashMapDTO(kaart.getVereisteFiches()), kaart.getImg());
    }
}
