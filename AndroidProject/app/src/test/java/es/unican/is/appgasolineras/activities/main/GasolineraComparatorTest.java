package es.unican.is.appgasolineras.activities.main;

import static org.junit.Assert.assertTrue;

import android.location.Location;
import android.os.Build;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import es.unican.is.appgasolineras.model.Gasolinera;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class GasolineraComparatorTest {
    private GasolineraComparator sut;
    private Location location;
    private Gasolinera g1;
    private Gasolinera g2;
    private static final double LATITUD_UC = 43.468732;
    private static final double LONGITUD_UC = -3.805011;
    @Before
    public void inicializa() {
        location = new Location("");
        location.setLatitude(LATITUD_UC);
        location.setLongitude(LONGITUD_UC);
        g1 = new Gasolinera();
        g2 = new Gasolinera();
        sut = new GasolineraComparator(location);
    }

    @Test
    public void compareTest() {
        //UGIC.1a
        g1.setLatitud("43,395944");
        g1.setLongitud("-4,155194");
        g2.setLatitud("43,419778");
        g2.setLongitud("3,858806");
        int dist = sut.compare(g1,g2);
        assertTrue(dist < 0);

        //UGIC.2a
        g1.setLatitud("43,395944");
        g1.setLongitud("-4,155194");
        g2.setLatitud("43,419778");
        g2.setLongitud("");
        assertTrue(sut.compare(g1,g2) > 0);

        //UGIC.3a
        g1.setLatitud("43,395944");
        g1.setLongitud("");
        g2.setLatitud("43,419778");
        g2.setLongitud("3,858806");
        assertTrue(sut.compare(g1,g2) < 0);

        //UGIC.4a
        g1.setLatitud("");
        g1.setLongitud("");
        g2.setLatitud("");
        g2.setLongitud("");
        assertTrue(sut.compare(g1,g2) == 0);

        //UGIC.5a
        g1.setLatitud("");
        g1.setLongitud("-4,155194");
        g2.setLatitud("43,419778");
        g2.setLongitud("3,858806");
        assertTrue(sut.compare(g1,g2) < 0);

        //UGIC.6a
        g1.setLatitud("43,395944");
        g1.setLongitud("-4,155194");
        g2.setLatitud("");
        g2.setLongitud("3,858806");
        assertTrue(sut.compare(g1,g2) > 0);

        //UGIC.7a
        g2.setLatitud("43,395944");
        g2.setLongitud("-4,155194");
        g1.setLatitud("43,419778");
        g1.setLongitud("3,858806");
        assertTrue(sut.compare(g1,g2) > 0);

        //UGIC.8a
        g1.setLatitud("43,395944");
        g1.setLongitud("-4,155194");
        g2.setLatitud("43,395944");
        g2.setLongitud("-4,155194");
        assertTrue(sut.compare(g1,g2) == 0);

        //UGIC.9a
        g1.setLatitud("");
        g1.setLongitud("-4,155194");
        g2.setLatitud("");
        g2.setLongitud("3,858806");
        assertTrue(sut.compare(g1,g2) == 0);

        //UGIC.10a
        g1.setLatitud("43,395944");
        g1.setLongitud("");
        g2.setLatitud("43,419778");
        g2.setLongitud("");
        assertTrue(sut.compare(g1,g2) == 0);



    }

}