package es.unican.is.appgasolineras.activities.main;

import android.location.Location;

import java.util.Comparator;

import es.unican.is.appgasolineras.model.Gasolinera;

public class GasolineraComparator implements Comparator<Gasolinera> {
    Location loc;
    public GasolineraComparator (Location locAct) {
        this.loc = locAct;
    }
    @Override
    public int compare(Gasolinera d1, Gasolinera d2) {
        return 0;
    }

}
