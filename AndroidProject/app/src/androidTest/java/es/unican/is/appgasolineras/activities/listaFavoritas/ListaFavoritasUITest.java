package es.unican.is.appgasolineras.activities.listaFavoritas;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSpinnerText;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.not;

import static es.unican.is.appgasolineras.utils.Matchers.hasElements;
import static es.unican.is.appgasolineras.utils.Matchers.sizeElements;

import android.view.View;

import androidx.room.Room;
import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import org.hamcrest.CoreMatchers;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import es.unican.is.appgasolineras.R;
import es.unican.is.appgasolineras.activities.menuPrincipal.MenuPrincipalView;
import es.unican.is.appgasolineras.repository.db.GasolineraDao;
import es.unican.is.appgasolineras.repository.db.GasolineraDao_Impl;
import es.unican.is.appgasolineras.repository.db.GasolineraDatabase;
import es.unican.is.appgasolineras.repository.rest.GasolinerasServiceConstants;

public class ListaFavoritasUITest {

    static View decorView;
    GasolineraDatabase db;
    static GasolineraDao gas;

    @BeforeClass
    public static void setUp() {
        GasolinerasServiceConstants.setStaticURL();
    }

    @AfterClass
    public static void clean() {
        GasolinerasServiceConstants.setMinecoURL();
        gas.deleteAll();
    }

    @Before
    public void inicializa() {
        db = Room.databaseBuilder(getApplicationContext(),
                GasolineraDatabase.class, "database-name").allowMainThreadQueries().build();
        gas = db.gasolineraDao();
        activityRule.getScenario().onActivity(activity -> decorView = activity.getWindow().getDecorView());
    }

    @Rule
    public ActivityScenarioRule<MenuPrincipalView> activityRule =
            new ActivityScenarioRule(MenuPrincipalView.class);

    @Test
    public void anhadirGasolineraAListaFavoritasTest() {

        // Anhadimos a los filtros Cantabria
        onView(withId(R.id.btnAccederFiltrosPermanentes)).perform(scrollTo(), click());
        // Selecciono en el spinner de la comunidad: Cantabria
        onView(withId(R.id.spinner_CCAA)).perform(scrollTo(), click());
        onData(CoreMatchers.anything()).atPosition(1).perform(scrollTo(), click());
        onView(withId(R.id.spinner_CCAA)).check(matches(withSpinnerText("Cantabria")));
        onView(withId(R.id.btnGuardarPermanentes)).perform(scrollTo(), click());
        /**
         * Caso de prueba 1.a: Exito, Anhadir gasolinera (se prueba anhadiendo
         *     la primera gasolinera mostrada y la ultima
         */
        /**
         * Se anhade la primera y se comprueba
         */
        onView(withId(R.id.btnAccederLista)).perform(scrollTo(), click());
        onView(withId(R.id.lvGasolineras)).check(matches(hasElements()));
        onView(withText("Se han cargado 156 gasolineras")).inRoot(RootMatchers.withDecorView(not(decorView))).check(matches(isDisplayed()));
        //Gasolinera en la primera posicion de la lista
        String direccion1 = "CARRETERA 6316 KM. 10,5";
        DataInteraction gasEnLista1;
        gasEnLista1 = onData(CoreMatchers.anything()).inAdapterView(withId(R.id.lvGasolineras)).atPosition(0);
        gasEnLista1.onChildView(withId(R.id.tvAddress)).check(matches(withText(direccion1)));
        onData(anything()).inAdapterView(withId(R.id.lvGasolineras)).atPosition(0).perform(scrollTo(), click());

        onView(withId(R.id.btnAnhadirGasolineraFavoritas)).perform(scrollTo(), click());
        onView(withText("Se ha añadido la gasolinera correctamente")).inRoot(RootMatchers.withDecorView(not(decorView))).check(matches(isDisplayed()));
        Espresso.pressBack();
        Espresso.pressBack();
        onView(withId(R.id.btnAccederFavoritos)).perform(scrollTo(), click());
        onView(withText("Se han cargado 1 gasolineras")).inRoot(RootMatchers.withDecorView(not(decorView))).check(matches(isDisplayed()));
        onView(withId(R.id.lvGasolineras2)).check(matches(sizeElements(1)));

        DataInteraction gasEnListaFavoritas;
        gasEnListaFavoritas = onData(CoreMatchers.anything()).inAdapterView(withId(R.id.lvGasolineras2)).atPosition(0);
        gasEnListaFavoritas.onChildView(withId(R.id.tvAddress)).check(matches(withText(direccion1)));
        Espresso.pressBack();


        /**
         * Se anhade la ultima y se comprueba
         */
        onView(withId(R.id.btnAccederLista)).perform(scrollTo(), click());
        onView(withId(R.id.lvGasolineras)).check(matches(hasElements()));
        onView(withText("Se han cargado 156 gasolineras")).inRoot(RootMatchers.withDecorView(not(decorView))).check(matches(isDisplayed()));
        //Gasolinera en la ultima posicion de la lista
        String direccion2 = "BARRIO SAN PANTALEÓN, S/N";
        DataInteraction gasEnLista2;
        gasEnLista2 = onData(CoreMatchers.anything()).inAdapterView(withId(R.id.lvGasolineras)).atPosition(155);
        gasEnLista2.onChildView(withId(R.id.tvAddress)).check(matches(withText(direccion2)));
        onData(anything()).inAdapterView(withId(R.id.lvGasolineras)).atPosition(155).perform(scrollTo(), click());

        onView(withId(R.id.btnAnhadirGasolineraFavoritas)).perform(scrollTo(), click());
        onView(withText("Se ha añadido la gasolinera correctamente")).inRoot(RootMatchers.withDecorView(not(decorView))).check(matches(isDisplayed()));
        Espresso.pressBack();
        Espresso.pressBack();
        onView(withId(R.id.btnAccederFavoritos)).perform(scrollTo(), click());
        onView(withText("Se han cargado 2 gasolineras")).inRoot(RootMatchers.withDecorView(not(decorView))).check(matches(isDisplayed()));
        onView(withId(R.id.lvGasolineras2)).check(matches(sizeElements(2)));
        DataInteraction gasEnListaFavoritas1;
        gasEnListaFavoritas1 = onData(CoreMatchers.anything()).inAdapterView(withId(R.id.lvGasolineras2)).atPosition(0);
        gasEnListaFavoritas1.onChildView(withId(R.id.tvAddress)).check(matches(withText(direccion2)));
        DataInteraction gasEnListaFavoritas2;
        gasEnListaFavoritas2 = onData(CoreMatchers.anything()).inAdapterView(withId(R.id.lvGasolineras2)).atPosition(1);
        gasEnListaFavoritas2.onChildView(withId(R.id.tvAddress)).check(matches(withText(direccion1)));
        Espresso.pressBack();

        /**
         * Caso de prueba 1.c: Anhadir gasolinera ya anhadida anteriormente
         *      Se prueba con la segunda gasolinera de la lista.
         *      Se prueba tanto que se ha anhadido a la lista como que no se
         *      ha anhadido dos veces
         */
        onView(withId(R.id.btnAccederLista)).perform(scrollTo(), click());
        onView(withId(R.id.lvGasolineras)).check(matches(hasElements()));
        onView(withText("Se han cargado 156 gasolineras")).inRoot(RootMatchers.withDecorView(not(decorView))).check(matches(isDisplayed()));
        //Gasolinera en la segunda posicion de la lista
        String direccion3 = "CR N-629 79,7";
        DataInteraction gasEnLista3;
        gasEnLista3 = onData(CoreMatchers.anything()).inAdapterView(withId(R.id.lvGasolineras)).atPosition(1);
        gasEnLista3.onChildView(withId(R.id.tvAddress)).check(matches(withText(direccion3)));
        onData(anything()).inAdapterView(withId(R.id.lvGasolineras)).atPosition(1).perform(scrollTo(), click());

        onView(withId(R.id.btnAnhadirGasolineraFavoritas)).perform(scrollTo(), click());
        onView(withText("Se ha añadido la gasolinera correctamente")).inRoot(RootMatchers.withDecorView(not(decorView))).check(matches(isDisplayed()));
        onView(withId(R.id.btnAnhadirGasolineraFavoritas)).perform(scrollTo(), click());
        onView(withText("La gasolinera ya se encuentra añadida a la lista")).inRoot(RootMatchers.withDecorView(not(decorView))).check(matches(isDisplayed()));

        Espresso.pressBack();
        Espresso.pressBack();
        onView(withId(R.id.btnAccederFavoritos)).perform(scrollTo(), click());
        onView(withText("Se han cargado 3 gasolineras")).inRoot(RootMatchers.withDecorView(not(decorView))).check(matches(isDisplayed()));
        onView(withId(R.id.lvGasolineras2)).check(matches(sizeElements(3)));
        gasEnListaFavoritas1 = onData(CoreMatchers.anything()).inAdapterView(withId(R.id.lvGasolineras2)).atPosition(0);
        gasEnListaFavoritas1.onChildView(withId(R.id.tvAddress)).check(matches(withText(direccion3)));
        gasEnListaFavoritas2 = onData(CoreMatchers.anything()).inAdapterView(withId(R.id.lvGasolineras2)).atPosition(1);
        gasEnListaFavoritas2.onChildView(withId(R.id.tvAddress)).check(matches(withText(direccion2)));
        DataInteraction gasEnListaFavoritas3;
        gasEnListaFavoritas3 = onData(CoreMatchers.anything()).inAdapterView(withId(R.id.lvGasolineras2)).atPosition(2);
        gasEnListaFavoritas3.onChildView(withId(R.id.tvAddress)).check(matches(withText(direccion1)));
        Espresso.pressBack();

        // Reseteo los filtros
        onView(withId(R.id.btnAccederFiltrosPermanentes)).perform(scrollTo(), click());
        onView(withId(R.id.btnResetearPermanentes)).perform(scrollTo(), click());
        // Compruebo que los filtros se han reseteado
        onView(withId(R.id.spinner_CCAA)).check(matches(withSpinnerText("Todas")));
    }

}
