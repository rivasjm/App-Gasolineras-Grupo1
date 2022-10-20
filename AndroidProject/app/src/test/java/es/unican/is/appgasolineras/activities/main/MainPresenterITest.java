package es.unican.is.appgasolineras.activities.main;

import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;


import java.util.LinkedList;

import es.unican.is.appgasolineras.common.prefs.Prefs;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.GasolinerasRepository;
import es.unican.is.appgasolineras.repository.rest.GasolinerasServiceConstants;


@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1})
public class MainPresenterITest {

    @BeforeClass
    public static void setUp() {
        GasolinerasServiceConstants.setStaticURL2();
    }

    @AfterClass
    public static void clean() { GasolinerasServiceConstants.setMinecoURL(); }

    @Mock
    private IMainContract.View mockMainContract; // Definición del objeto Mock
    private MainView sut;

    MainPresenter presenter = null;
    GasolinerasRepository gasolineras;
    Prefs prefs = null;

    @Before
    public void inicializa() {
        MockitoAnnotations.openMocks(this); // Creación de los mocks definidos anteriormente con @Mock

        Context context = ApplicationProvider.getApplicationContext();

        prefs = new Prefs(context);
        gasolineras = new GasolinerasRepository(context);

        presenter = new MainPresenter(mockMainContract, prefs, true);
    }

    @Test
    public void filtraPrecio() {
        //caso1
        assertEquals(156, presenter.filtraPrecio(gasolineras.todasGasolineras(), "2.04").size());
        //caso 2
        assertEquals(0, presenter.filtraPrecio(gasolineras.todasGasolineras(), "-2.04").size());
        //caso2
        assertEquals(0, presenter.filtraPrecio(new LinkedList<Gasolinera>(), "2.04").size());

    }

    @Test
    public void maximoEntreTodas() {
        //caso1 ver maxima
        assertEquals("2.039", presenter.maximoEntreTodas(gasolineras.todasGasolineras()));
        //caso2 lista vacia
        assertEquals("0.00", presenter.maximoEntreTodas(new LinkedList<Gasolinera>()));
    }
}
