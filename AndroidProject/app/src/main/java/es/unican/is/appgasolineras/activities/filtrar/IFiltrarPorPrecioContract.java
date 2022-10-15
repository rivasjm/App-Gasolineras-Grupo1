package es.unican.is.appgasolineras.activities.filtrar;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import java.math.BigDecimal;

public interface IFiltrarPorPrecioContract {

    public interface Presenter {
        /**
         * Initialization method
         */
        void init();

        /**
         * This method stores in prefs the maximum price selected by the user
         * @param max maximum price to store
         */
        void estableceRango(String max);

        /**
         * This method adds 0.01 to the price selected by the user with some limits,
         * it is called when subir precio is clicked
         * @param actual actual price in the TextView
         * @return the price to print in the TextView
         */
        String subePrecio(String actual);

        /**
         * This method subtracts 0.01 to the price selected by the user with some limits,
         * it is called when bajar precio is clicked
         * @param actual actual price in the TextView
         * @return the price to print in the TextView
         */
        String bajaPrecio(String actual);

    }

    public interface View {
        /**
         * Initialization method
         */
        void init();

        /**
         * The View is requested to open the Main view
         */
        void openMainView();
    }
}
