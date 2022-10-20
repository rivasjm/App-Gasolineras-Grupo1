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

import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.common.prefs.Prefs;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.GasolinerasRepository;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;
import es.unican.is.appgasolineras.repository.rest.GasolinerasServiceConstants;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class MainPresenter_FiltrosPermanentesITest {


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
    public static void clean() {
        GasolinerasServiceConstants.setMinecoURL();
    }

    @Test
    public void filtraTipoTestInterfaz() {

        // Caso de prueba UGIC 1.a
        //Creamos la lista de gasolineras
        listGasolineras = new ArrayList<>();
        listGasolineras = gasolineras.todasGasolineras();

        // Caso de prueba UGIC 1.a
        assertEquals(main.filtraTipo(listGasolineras,
                "dieselA").size(), 156);

        // Caso de prueba UGIC 1.a
        //Creamos la lista de gasolineras
        listGasolineras = new ArrayList<>();
        listGasolineras = gasolineras.todasGasolineras();


        // Caso de prueba UGIC 1.a
        assertEquals(main.filtraTipo(listGasolineras,
                "bioEtanol").size(), 0);

        // Caso de prueba UGIC 1.a
        //Creamos la lista de gasolineras
        listGasolineras = new ArrayList<>();
        listGasolineras = gasolineras.todasGasolineras();

        // Caso de prueba UGIC 1.a
        assertEquals(main.filtraTipo(listGasolineras,
                "").size(), listGasolineras.size());
    }
}