package es.unican.is.appgasolineras.activities.main;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;

public class MainPresenterTest {

    private static MainPresenter main;
    private static IMainContract.View view;
    private static IGasolinerasRepository mockGasolineras;
    private static IPrefs mockPrefs;
    private List<Gasolinera> listGasolineras;
    private Gasolinera gas;

    @Before
    public void inicializa() {
        mockGasolineras = mock(IGasolinerasRepository.class);
        mockPrefs = mock(IPrefs.class);
        view = mock(IMainContract.View.class);
        main = new MainPresenter(view, mockPrefs, true);
    }

    @Test
    public void filtraTipoTestUnitarias() {

        // Caso de prueba UGIC 1.a
        //Creamos la lista de gasolineras
        listGasolineras = new ArrayList<>();
        List<Gasolinera>listDevueltas = new ArrayList<>();
        gas = new Gasolinera();
        gas.setDieselA("1,97");
        listGasolineras.add(gas);
        listDevueltas.add(gas);
        gas = new Gasolinera();
        gas.setNormal95("1,81");
        gas.setDieselA("");
        listGasolineras.add(gas);
        gas = new Gasolinera();
        gas.setDieselA("2,01");
        listGasolineras.add(gas);
        listDevueltas.add(gas);
        gas = new Gasolinera();
        gas.setNormal95Prem("1,91");
        gas.setDieselA("");
        listGasolineras.add(gas);

        when(mockGasolineras.todasGasolineras())
                .thenReturn(listGasolineras);

        // Caso de prueba UGIC 1.a
        assertEquals(main.filtraTipo(mockGasolineras.todasGasolineras(),
                "dieselA"),listDevueltas);

        // Caso de prueba UGIC 1.c
        listGasolineras = new ArrayList<>();
        listDevueltas = new ArrayList<>();
        gas = new Gasolinera();
        gas.setDieselA("1,97");
        gas.setDieselB("");
        listGasolineras.add(gas);
        gas = new Gasolinera();
        gas.setNormal95("1,81");
        gas.setDieselB("");
        listGasolineras.add(gas);
        gas = new Gasolinera();
        gas.setDieselA("2,01");
        gas.setDieselB("");
        listGasolineras.add(gas);
        gas = new Gasolinera();
        gas.setNormal95Prem("1,91");
        gas.setDieselB("");
        listGasolineras.add(gas);

        when(mockGasolineras.todasGasolineras())
                .thenReturn(listGasolineras);

        assertEquals(main.filtraTipo(mockGasolineras.todasGasolineras(),
                "dieselB"),listDevueltas);
        assertEquals(listDevueltas.size(),main.filtraTipo(mockGasolineras.todasGasolineras(),
                "dieselB").size());

        // Caso de prueba UGIC 1.d
        listGasolineras = new ArrayList<>();
        listDevueltas = new ArrayList<>();
        gas = new Gasolinera();
        gas.setDieselA("1,97");
        listGasolineras.add(gas);
        listDevueltas.add(gas);
        gas = new Gasolinera();
        gas.setNormal95("1,81");
        listGasolineras.add(gas);
        listDevueltas.add(gas);
        gas = new Gasolinera();
        gas.setDieselA("2,01");
        listGasolineras.add(gas);
        listDevueltas.add(gas);
        gas = new Gasolinera();
        gas.setNormal95Prem("1,91");
        listGasolineras.add(gas);
        listDevueltas.add(gas);

        when(mockGasolineras.todasGasolineras())
                .thenReturn(listGasolineras);

        assertEquals(main.filtraTipo(mockGasolineras.todasGasolineras(),
                ""),listDevueltas);
    }
}
