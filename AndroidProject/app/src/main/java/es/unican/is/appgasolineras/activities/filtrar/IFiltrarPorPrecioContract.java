package es.unican.is.appgasolineras.activities.filtrar;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import java.math.BigDecimal;

public interface IFiltrarPorPrecioContract {

    public interface Presenter {
        void init();
        void estableceRango(String max);
        String subePrecio(String actual);
        String bajaPrecio(String actual);

    }

    public interface View {
        boolean onOptionsItemSelected(@NonNull MenuItem item);
        void openMainView();
    }
}
