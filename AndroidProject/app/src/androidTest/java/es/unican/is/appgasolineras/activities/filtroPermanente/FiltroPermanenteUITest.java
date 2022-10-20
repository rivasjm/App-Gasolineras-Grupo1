package es.unican.is.appgasolineras.activities.filtroPermanente;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static org.hamcrest.CoreMatchers.anything;


import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import es.unican.is.appgasolineras.R;
import es.unican.is.appgasolineras.activities.menuPrincipal.MenuPrincipalView;
import es.unican.is.appgasolineras.repository.rest.GasolinerasServiceConstants;

public class FiltroPermanenteUITest {
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
    public void guardarYResetearFiltrosTest() {
        onView(withId(R.id.btnAccederFiltrosPermanentes)).perform(click());
        onView(withId(R.id.spinner_combustible)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.spinner_combustible)).check(matches(withSpinnerText("Gasolina 95 E5")));
        onView(withId(R.id.spinner_CCAA)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        onView(withId(R.id.spinner_CCAA)).check(matches(withSpinnerText("Cantabria")));
        onView(withId(R.id.btnGuardarPermanentes)).perform(click());
        onView(withId(R.id.btnAccederFiltrosPermanentes)).perform(click());
        onView(withId(R.id.spinner_combustible)).check(matches(withSpinnerText("Gasolina 95 E5")));
        onView(withId(R.id.spinner_CCAA)).check(matches(withSpinnerText("Cantabria")));
        onView(withId(R.id.btnResetearPermanentes)).perform(click());
        onView(withId(R.id.spinner_combustible)).check(matches(withSpinnerText("Todos")));
        onView(withId(R.id.spinner_CCAA)).check(matches(withSpinnerText("Todas")));
    }
}
