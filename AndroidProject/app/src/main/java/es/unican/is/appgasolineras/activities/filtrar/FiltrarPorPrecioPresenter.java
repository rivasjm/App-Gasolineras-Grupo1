package es.unican.is.appgasolineras.activities.filtrar;

import android.content.Context;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.common.prefs.Prefs;

public class FiltrarPorPrecioPresenter implements IFiltrarPorPrecioContract.Presenter{
    IPrefs prefs;
    IFiltrarPorPrecioContract.View view;
    String maxPrecio;

    public FiltrarPorPrecioPresenter(IFiltrarPorPrecioContract.View view, Context c, String maxPrecio){
        prefs = new Prefs(c, "MY_APP");
        this.view = view;
        this.maxPrecio = maxPrecio;
    }

    @Override
    public void init() {

    }

    @Override
    public void estableceRango(String max) {
        prefs.putString("RangoPrecio", max);
        view.openMainView();
    }

    @Override
    public String subePrecio(String act) {
        String devolver;
        BigDecimal actual = new BigDecimal(act).setScale(2, RoundingMode.UP);
        BigDecimal maxPrecioDouble = new BigDecimal(maxPrecio.substring(0,4)).setScale(2, RoundingMode.UP);
        if (actual.compareTo(maxPrecioDouble) < 0){
            BigDecimal sum = new BigDecimal(0.01);
            actual = actual.add(sum, MathContext.DECIMAL32);
        }
        devolver = getStringCorrecto(actual);
        return devolver;
    }

    @Override
    public String bajaPrecio(String act) {
        String devolver;
        BigDecimal actual = new BigDecimal(act).setScale(2, RoundingMode.UP);
        BigDecimal cero = new BigDecimal(0.01).setScale(2, RoundingMode.UP);
        if (actual.compareTo(cero) >= 0){
            BigDecimal res = new BigDecimal(0.01000000);
            actual = actual.subtract(res, MathContext.DECIMAL32);
        } else {
            actual = new BigDecimal(0).setScale(2, RoundingMode.UP);;
        }
        devolver = getStringCorrecto(actual);
        return devolver;
    }

    @NonNull
    private String getStringCorrecto(@NonNull BigDecimal actual) {
        String devolver;
        if (actual.toString().length() == 3) {
            devolver = actual.toString().substring(0,3) + "0";
        } else {
            devolver = actual.toString().substring(0,4);
        }
        return devolver;
    }
}
