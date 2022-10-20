package es.unican.is.appgasolineras.activities.filtroPrecio;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static es.unican.is.appgasolineras.utils.Matchers.hasElements;


import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import es.unican.is.appgasolineras.R;
import es.unican.is.appgasolineras.activities.menuPrincipal.MenuPrincipalView;
import es.unican.is.appgasolineras.repository.rest.GasolinerasServiceConstants;

public class FiltroPrecioUITest {
    @BeforeClass
    public static void setUp() {
        GasolinerasServiceConstants.setStaticURL2();
    }

    @AfterClass
    public static void clean() {
        GasolinerasServiceConstants.setMinecoURL();
    }

    @Rule
    public ActivityScenarioRule<MenuPrincipalView> activityRule =
            new ActivityScenarioRule(MenuPrincipalView.class);

    @Test
    public void reseteaFiltroPrecioTest() {

        onView(withId(R.id.btnAccederLista)).perform(click());
        onView(withId(R.id.btnFiltroPrecio)).perform(click());
        for (int i = 0; i < 10; i++) {

            onView(withId(R.id.btnBajarPrecio)).perform(click());
        }
        onView(withId(R.id.btnResetear)).perform(click());
        onView(withId(R.id.tvPrecioLimite)).check(matches(withText("2.02")));

    }


    @Test
    public void modificaFiltroPrecioTest() {
        onView(withId(R.id.btnAccederLista)).perform(click());
        onView(withId(R.id.btnFiltroPrecio)).perform(click());

        //primero intento subir el precio para comprobar que no sobrepasa el limite superior
        for (int i = 0; i < 3; i++) {

            onView(withId(R.id.btnSubirPrecio)).perform(click());
        }
        onView(withId(R.id.tvPrecioLimite)).check(matches(withText("2.02")));


        //resto 10 decimales al precio
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onView(withId(R.id.btnBajarPrecio)).perform(click());

            //await().atMost(2, 100.0).until(onView(withId(R.id.btnBajarPrecio)).perform(click()));
        }
        onView(withId(R.id.tvPrecioLimite)).check(matches(withText("1.92")));

        //le sumo 3 decimales al precio
        for (int i = 0; i < 3; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            onView(withId(R.id.btnSubirPrecio)).perform(click());
        }
        onView(withId(R.id.tvPrecioLimite)).check(matches(withText("1.95")));

        for (int i = 0; i < 210; i++) {

            onView(withId(R.id.btnBajarPrecio)).perform(click());
        }
        onView(withId(R.id.tvPrecioLimite)).check(matches(withText("0.00")));
    }

    @Test
    public void muestraResultadosFiltroTest() {

        onView(withId(R.id.btnAccederLista)).perform(click());
        onView(withId(R.id.btnFiltroPrecio)).perform(click());

        onView(withId(R.id.btnMostrarResultados)).perform(click());
        onView(withId(R.id.lvGasolineras)).check(matches(hasElements()));
    }
}