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
    public int compare(Gasolinera g1, Gasolinera g2) {
        Location locG1 = new Location("");
        locG1.setLatitude(Double.parseDouble(g1.getLatitud().replace(",",".")));
        locG1.setLongitude(Double.parseDouble(g1.getLongitud().replace(",",".")));
        Location locG2 = new Location("");
        locG2.setLatitude(Double.parseDouble(g2.getLatitud().replace(",",".")));
        locG2.setLongitude(Double.parseDouble(g2.getLongitud().replace(",",".")));
        double distG1, distG2;
        distG1 = loc.distanceTo(locG1);
        distG2 = loc.distanceTo(locG2);
        return (int) distG1 - (int) distG2;
    }

}
