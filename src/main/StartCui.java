package main;

import domein.DomeinController;
import ui.SplendorConsoleApplicatie;

public class StartCui {

    public static void main(String[] args) {
	SplendorConsoleApplicatie sca = new SplendorConsoleApplicatie(new DomeinController());
	sca.start();
    }

}
