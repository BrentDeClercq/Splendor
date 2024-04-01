package ui;

import java.util.ArrayList;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.HashMap;

import domein.DomeinController;
import domein.Edele;
import dto.SpelerDTO;
import exceptions.LoginException;
import exceptions.SpelRegelException;

public class SplendorConsoleApplicatie {

    private DomeinController dc;
    private Scanner s;
    private ResourceBundle bundle;

    private static final int NEEM_FICHES = 1;
    private static final int NEEM_KAART = 2;

    public SplendorConsoleApplicatie(DomeinController dc) {
	this.dc = dc;
	this.s = new Scanner(System.in);
    }

    public void start() {
	taalInstellen();
	// System.out.print(bundle.getString("Greeting")); //test voor talen
	this.mainLoop();
    }

    // main loop
    private void mainLoop() {
	this.voegSpelersToeLoop();
	while (this.wilNieuwSpelSpelen()) {
	    ArrayList<SpelerDTO> spelers = dc.nieuwSpel();
	    String spelersMelding = "";
	    for (SpelerDTO speler : spelers) {
		spelersMelding += speler.toString();
	    }
	    System.out.printf("De speelvolgorde is:%n%s%n", spelersMelding);

	    while (!dc.spelUit()) {
		System.out.println(Overzichten.geefOverzicht(dc.getSpelDTO()));
		this.speelBeurt();
	    }

	    for (SpelerDTO winnaar : dc.getWinnaars()) {
		this.spelerWint(winnaar);
	    }
	}
    }

    // beurt wordt gespeeld, static finals als input in geval dat je meer zetten
    // wilt toevoegen
    private void speelBeurt() {
	System.out.printf("Wat wil je doen?%n" + "%d: Neem fiches%n" + "%d: Neem ontwikkelingskaart%n", NEEM_FICHES, NEEM_KAART);

	try {
	    switch (s.nextInt()) {
		case NEEM_FICHES:
		    this.neemFiches();
		    break;
		case NEEM_KAART:
		    this.neemKaart();
		    break;
		default: {
		    throw new SpelRegelException("Deze zet bestaat niet");
		}
	    }
	} catch (SpelRegelException sre) {
	    System.out.printf("Deze zet is niet mogelijk: %s%n", sre.getMessage());
	    this.speelBeurt();
	} catch (InputMismatchException ime) {
	    System.out.println("Voer een integer in");
	    s.nextLine();
	    this.speelBeurt();
	}
    }

    // vragen welke kaart je wil nemen en doorgeven aan dc
    // terug naar speelBeurt in geval van ongeldige zet
    private void neemKaart() {
	try {
	    System.out.println("In welke rij staat de kaart die je wilt nemen?");
	    int rij = s.nextInt();
	    if (rij < 0 || rij > 2)
		throw new SpelRegelException("Ingegeven rij moet binnen [0, 2] zijn");
	    System.out.println("Waar staat hij in de rij?");
	    int pos = s.nextInt();
	    if (pos < 0 || pos > 3)
		throw new SpelRegelException("Ingegeven pos moet binnen [0, 3] zijn");
	    System.out.printf("%nKaart genomen: %n%s", Overzichten.overzichtOntwikkelingskaart(dc.getSpelDTO().ontwikkelingskaartVeld().get(rij).get(pos)));
	    dc.neemKaart(rij, pos);
	} catch (SpelRegelException sre) {
	    throw sre;
	} catch (InputMismatchException ime) {
	    throw ime;
	}
    }

    // vragen welke fiches je wilt nemen, automatisch genomen als de zet geldig is
    // terug naar speelBeurt in geval van ongeldige zet
    private void neemFiches() {
	List<Integer> fichesTeNemen = new ArrayList<>();
	try {
	    while (!dc.geldigeFichesTeNemen(fichesTeNemen)) {
		System.out.printf("Welk soort fiche wil je nemen?%n" + "0: %s | 1: %s | 2: %s | 3: %s | 4: %s%n", Overzichten.edelsteenString(0), Overzichten.edelsteenString(1), Overzichten.edelsteenString(2), Overzichten.edelsteenString(3), Overzichten.edelsteenString(4));
		fichesTeNemen.add(s.nextInt());
	    }

	    dc.neemFiches(fichesTeNemen);

	    String out = "";
	    HashMap<String, Integer> fichesGenomen = new HashMap<>();
	    for (int soort : fichesTeNemen) {
		String soortAlsString = Overzichten.edelsteenString(soort);
		if (fichesGenomen.keySet().contains(soortAlsString)) {
		    fichesGenomen.put(soortAlsString, fichesGenomen.get(soortAlsString) + 1);
		} else {
		    fichesGenomen.put(soortAlsString, 1);
		}
	    }
	    for (String k : fichesGenomen.keySet()) {
		int v = fichesGenomen.get(k);
		out += String.format("%s: %d, ", k, v);
	    }
	    System.out.printf("%nFiches genomen: %s%n", out.substring(0, out.length() - 2));
	} catch (SpelRegelException sre) {
	    throw sre;
	} catch (InputMismatchException ime) {
	    throw ime;
	}
    }

    // melding ingegeven speler als winnaar
    private void spelerWint(SpelerDTO winnaar) {
	System.out.printf("%n%s wint!%n%n", winnaar);
    }

    // vragen of je een nieuw spel wilt spelen
    private boolean wilNieuwSpelSpelen() {
	System.out.println("Wil je een nieuw spel spelen? (y/n)");
	switch (s.next()) {
	    case "y":
		return true;
	    case "n":
		return false;
	    default: {
		System.out.println("Ongeldige invoer");
		return wilNieuwSpelSpelen();
	    }
	}
    }

    // login-fase loop
    private void voegSpelersToeLoop() {
	try {
	    while (this.nogSpelersToevoegenVraag()) {
		this.logSpelerIn();
	    }
	} catch (LoginException le) {
	    System.out.print(bundle.getString("exceptionLE"));
	    System.out.printf(" %s%n", le.getMessage());
	    this.voegSpelersToeLoop();
	} catch (InputMismatchException ime) {
	    System.out.println(bundle.getString("exceptionIME"));
	    this.voegSpelersToeLoop();
	}
    }

    // vragen als je een nieuwe gebruiker wilt toevoegen in geval dat
    // dc.minSpelersIngelogdBereikt false is
    private boolean nogSpelersToevoegenVraag() {
	if (dc.maxSpelersIngelogdBereikt())
	    return false;

	if (dc.minSpelersIngelogdBereikt()) {
	    System.out.printf("%s %d %s%n", bundle.getString("spelersToevoegenP1"), dc.getAantalSpelersIngelogd(), bundle.getString("spelersToevoegenP2"));
	    switch (s.next()) {
		case "y":
		    return true;
		case "n":
		    return false;
		default:
		    throw new LoginException(bundle.getString("OngeldigeInvoer"));
	    }
	}
	return true;
    }

    // gegevens van in te loggen gebruiker vragen en doorgeven aan dc
    private void logSpelerIn() {
	String gebruikersnaam;
	int geboortejaar;
	System.out.println(bundle.getString("registratie"));
	System.out.println(bundle.getString("naam"));
	gebruikersnaam = s.next();
	System.out.println(bundle.getString("jaar"));
	try {
	    geboortejaar = s.nextInt();
	    dc.logSpelerIn(gebruikersnaam, geboortejaar);
	} catch (InputMismatchException ime) {
	    throw ime;
	} catch (LoginException le) {
	    throw le;
	}
    }

    private void taalInstellen() {
	// invoer = new Scanner(System.in);
	String countryCode;
	// taal kiezen
	System.out.print("In welke taal wenst u Splendor te spelen/In which language do you prefer to play Splendor?");
	do {
	    System.out.printf("%nkies/choose NL voor nederlands or US for english: ");
	    countryCode = s.next().toLowerCase();
	    s.nextLine();
	} while (!(countryCode.equals("nl") || countryCode.equals("us")));
	// default taal instellen
	if (countryCode.equals("us")) {
	    Locale currentLocale = new Locale("en", "US");
	    ResourceBundle bundel = ResourceBundle.getBundle("ui.MessageBundle", currentLocale);
	    bundle = bundel;
	} else if (countryCode.equals("nl")) {
	    Locale currentLocale = new Locale("nl", "NL");
	    ResourceBundle bundel = ResourceBundle.getBundle("ui.MessageBundle", currentLocale);
	    bundle = bundel;
	}
    }
}
