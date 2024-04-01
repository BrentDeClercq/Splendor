package testen;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import domein.Edelsteen;
import domein.Speler;
import exceptions.LoginException;

class EdelsteenTest {

    @Test
    void toInt_zwarteEdelsteen_geeft0() {
	assertEquals(0, Edelsteen.toInt(Edelsteen.ZWART));
    }

    @Test
    void toInt_witteEdelsteen_geeft1() {
	assertEquals(1, Edelsteen.toInt(Edelsteen.WIT));
    }

    @Test
    void toInt_rodeEdelsteen_geeft2() {
	assertEquals(2, Edelsteen.toInt(Edelsteen.ROOD));
    }

    @Test
    void toInt_groeneEdelsteen_geeft3() {
	assertEquals(3, Edelsteen.toInt(Edelsteen.GROEN));
    }

    @Test
    void toInt_blauweEdelsteen_geeft4() {
	assertEquals(4, Edelsteen.toInt(Edelsteen.BLAUW));
    }

    @Test
    void toSoort_int0_geeftZWART() {
	assertEquals(Edelsteen.ZWART, Edelsteen.toSoort(0));
    }

    @Test
    void toSoort_Int1_geeftWIT() {
	assertEquals(Edelsteen.WIT, Edelsteen.toSoort(1));
    }

    @Test
    void toSoort_Int2_geeftROOD() {
	assertEquals(Edelsteen.ROOD, Edelsteen.toSoort(2));
    }

    @Test
    void toSoort_Int3_geeftGROEN() {
	assertEquals(Edelsteen.GROEN, Edelsteen.toSoort(3));
    }

    @Test
    void toSoort_Int4_geeftBLAUW() {
	assertEquals(Edelsteen.BLAUW, Edelsteen.toSoort(4));
    }

}
