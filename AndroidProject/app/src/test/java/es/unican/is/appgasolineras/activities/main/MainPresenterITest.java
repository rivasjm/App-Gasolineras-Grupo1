package es.unican.is.appgasolineras.activities.main;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import android.content.Context;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


import java.util.ArrayList;
import java.util.List;

import es.unican.is.appgasolineras.common.prefs.Prefs;
import es.unican.is.appgasolineras.repository.GasolinerasRepository;
import es.unican.is.appgasolineras.repository.rest.GasolinerasServiceConstants;




import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class MainPresenterITest {

    private static MainPresenter main;
    private static IMainContract.View view;
    private static IGasolinerasRepository gasolineras;
    private static IPrefs prefs;
    private static Context con;
    private List<Gasolinera> listGasolineras;

    @Before
    public void inicializa() {
        con = ApplicationProvider.getApplicationContext();
        gasolineras = new GasolinerasRepository(con);
        prefs = new Prefs(con,"KEY_DEFAULT_PREFS");
        view = mock(IMainContract.View.class);
        main = new MainPresenter(view, prefs, true);
    }

    @BeforeClass
    public static void setUp() {
        GasolinerasServiceConstants.setStaticURL2();
    }

    @AfterClass
    public static void clean() { GasolinerasServiceConstants.setMinecoURL(); }

    @Test
    public void filtraPrecioTest() {
        //caso1
        assertEquals(156, main.filtraPrecio(gasolineras.todasGasolineras(), "2.04").size());
        //caso 2
        assertEquals(0, main.filtraPrecio(gasolineras.todasGasolineras(), "-2.04").size());
        //caso2
        assertEquals(0, main.filtraPrecio(new ArrayList<>(), "2.04").size());

    }

    @Test
    public void maximoEntreTodasTest() {
        //caso1 ver maxima
        assertEquals("2.039", main.maximoEntreTodas(gasolineras.todasGasolineras()));
        //caso2 lista vacia
        assertEquals("0.00", main.maximoEntreTodas(new ArrayList<>()));
    }

    @Test
    public void filtraTipoTestInterfaz() {

        // Caso de prueba UGIC 1.a
        //Creamos la lista de gasolineras
        listGasolineras = new ArrayList<>();
        listGasolineras = gasolineras.todasGasolineras();

        // Caso de prueba UGIC 1.a
        assertEquals(156, main.filtraTipo(listGasolineras,
                "dieselA").size());

        // Caso de prueba UGIC 1.a
        //Creamos la lista de gasolineras
        listGasolineras = new ArrayList<>();
        listGasolineras = gasolineras.todasGasolineras();


        // Caso de prueba UGIC 1.a
        assertEquals(0, main.filtraTipo(listGasolineras,
                "bioEtanol").size());

        // Caso de prueba UGIC 1.a
        //Creamos la lista de gasolineras
        listGasolineras = new ArrayList<>();
        listGasolineras = gasolineras.todasGasolineras();

        // Caso de prueba UGIC 1.a
        assertEquals(listGasolineras.size(), main.filtraTipo(listGasolineras,
                "").size());
    }
}
