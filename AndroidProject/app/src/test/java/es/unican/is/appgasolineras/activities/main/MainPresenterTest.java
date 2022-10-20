package es.unican.is.appgasolineras.activities.main;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.model.Gasolinera;

public class MainPresenterTest {
    private static IMainContract.View mockviewMainPresenter;
    private static IPrefs mockPrefs;
    private static MainPresenter main;
    private  ArrayList<Gasolinera> listGasolineras;
    private  ArrayList<Gasolinera> listGasolinerasVacia;

    private static final String TIPOGASOLINA = "tipoGasolina";


    @Before
    public void inicializa() {

        mockPrefs = mock(IPrefs.class);
        mockviewMainPresenter = mock(IMainContract.View.class);
        main = new MainPresenter(mockviewMainPresenter,mockPrefs,true);

        when(mockPrefs.getString(TIPOGASOLINA)).thenReturn("dieselA");

        listGasolineras = new ArrayList<>();
        listGasolinerasVacia = new ArrayList<>();

        Gasolinera g = new Gasolinera();
        Gasolinera g2 = new Gasolinera();

        g.setDieselA("1,99");
        g2.setDieselA("2,02");


        listGasolineras.add(g);
        listGasolineras.add(g2);
    }

    @Test
    public void filtraPrecioTest() {
        assertEquals(listGasolineras,
                main.filtraPrecio(listGasolineras,"2.04"));
        assertEquals(1,
                main.filtraPrecio(listGasolineras,"2.00").size());
        assertEquals(listGasolinerasVacia.size(),
                main.filtraPrecio(listGasolineras,"-2.04").size());
        assertEquals(listGasolinerasVacia.size(),
                main.filtraPrecio(listGasolinerasVacia,"2.04").size());
    }

    @Test
    public void maximoEntreTodasTest() {
        assertEquals("2.02",main.maximoEntreTodas(listGasolineras));
        assertEquals("0.00",main.maximoEntreTodas(listGasolinerasVacia));
    }

}
