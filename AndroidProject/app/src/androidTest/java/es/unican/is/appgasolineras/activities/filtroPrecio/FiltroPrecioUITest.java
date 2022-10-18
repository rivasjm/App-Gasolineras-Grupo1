package es.unican.is.appgasolineras.activities.filtroPrecio;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.anything;

import android.widget.Button;

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

    }


    @Test
    public void mostrarFiltradoPrecioTest() {

        onView(withId(R.id.btnAccederLista)).perform(click());
        onView(withId(R.id.btnFiltroPrecio)).perform(click());
        float precioFiltro = Float.parseFloat("tvPrecioLimite".toString());
        for (int i = 0; i < 10; i++) {
            onView(withId(R.id.btnBajarPrecio)).perform(click());
        }

        //onView(withId(R.id.spinner_combustible)).check(matches(withText()));

        onView(withId(R.id.btnMostrarResultados)).perform(click());
        onView(withId(R.id.lvGasolineras)).perform(click());
        onView(withId(R.id.tvGasolina)).check(matches(withText("tvPrecioLimite")));
    }
}
