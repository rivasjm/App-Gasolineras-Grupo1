package es.unican.is.appgasolineras.common;

import android.location.Location;

import java.util.List;

import es.unican.is.appgasolineras.activities.main.GasolineraComparator;
import es.unican.is.appgasolineras.model.Gasolinera;

public class Sortings {

    public static List<Gasolinera> ordenaPorUbicacion(List<Gasolinera> data, String latitud, String longitud) {
        Location loc = new Location("");
        loc.setLongitude(Double.parseDouble(longitud.replace(",", ".")));
        loc.setLatitude(Double.parseDouble(latitud.replace(",", ".")));

        GasolineraComparator gasCon = new GasolineraComparator(loc);
        data.sort(gasCon);
        return data;
    }

}
