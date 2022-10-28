package es.unican.is.appgasolineras.activities.main;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import es.unican.is.appgasolineras.common.Filters;
import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;

public class FiltersTest {
    private static IPrefs mockPrefs;
    private ArrayList<Gasolinera> listGasolineras;
    private ArrayList<Gasolinera> listGasolinerasVacia;
    private Gasolinera gas;
    private static IGasolinerasRepository mockGasolineras;

    private static final String TIPOGASOLINA = "tipoGasolina";


    @Before
    public void inicializa() {
        mockGasolineras = mock(IGasolinerasRepository.class);
        mockPrefs = mock(IPrefs.class);

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
                Filters.filtraPrecio(listGasolineras, "2.04", ""));
        assertEquals(1,
                Filters.filtraPrecio(listGasolineras, "2.00", "").size());
        assertEquals(listGasolinerasVacia.size(),
                Filters.filtraPrecio(listGasolineras, "-2.04", "").size());
        assertEquals(listGasolinerasVacia.size(),
                Filters.filtraPrecio(listGasolinerasVacia, "2.04", "").size());
    }

    @Test
    public void maximoEntreTodasTest() {
        assertEquals("2.02", Filters.maximoEntreTodas(listGasolineras, ""));
        assertEquals("0.00", Filters.maximoEntreTodas(listGasolinerasVacia, ""));
    }

    @Test
    public void filtraTipoTestUnitarias() {

        // Caso de prueba UGIC 1.a
        //Creamos la lista de gasolineras
        listGasolineras = new ArrayList<>();
        List<Gasolinera> listDevueltas = new ArrayList<>();
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
        assertEquals(listDevueltas, Filters.filtraTipo(mockGasolineras.todasGasolineras(),
                "dieselA"));

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

        assertEquals(listDevueltas, Filters.filtraTipo(mockGasolineras.todasGasolineras(),
                "dieselB"));
        assertEquals(listDevueltas.size(), Filters.filtraTipo(mockGasolineras.todasGasolineras(),
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

        assertEquals(listDevueltas, Filters.filtraTipo(mockGasolineras.todasGasolineras(),
                ""));
    }
}
