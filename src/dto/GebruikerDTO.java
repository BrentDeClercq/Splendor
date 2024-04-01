package dto;

import java.util.Objects;

public record GebruikerDTO(String gebruikersnaam, int geboortejaar) {

    public int gebruikersnaamLengte() {
	return gebruikersnaam.length();
    }

    @Override
    public int hashCode() {
	return Objects.hash(this.gebruikersnaam, this.geboortejaar);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	GebruikerDTO other = (GebruikerDTO) obj;
	return Objects.equals(this.gebruikersnaam, other.gebruikersnaam()) && this.geboortejaar == other.geboortejaar();
    }
}
