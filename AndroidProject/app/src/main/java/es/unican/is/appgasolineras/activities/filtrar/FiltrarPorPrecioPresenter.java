package es.unican.is.appgasolineras.activities.filtrar;

import android.content.Context;

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

    }

    @Override
    public String subePrecio(String actual) {
        if (actual.equals(maxPrecio)){
            return actual;
        }
        double precio = Double.parseDouble(actual.replace(",", "."));
        System.out.println("A:"+precio);
        precio += 0.01;
        System.out.println("D:"+precio);
        if (String.valueOf(precio).length() == 3) {
            return String.valueOf(precio).substring(0,2) + "00";
        }
        return String.valueOf(precio).substring(0,4);

    }

    @Override
    public String bajaPrecio(String actual) {
        if (actual.equals("0.00")) {
            return actual;
        }
        double precio = Double.parseDouble(actual.replace(",", "."));
        System.out.println("A:"+precio);
        precio -= 0.01;
        System.out.println("D:"+precio);
        if (String.valueOf(precio).length() == 3) {
            return String.valueOf(precio).substring(0,2) + "00";
        }
        return String.valueOf(precio).substring(0,4) ;
    }
}
