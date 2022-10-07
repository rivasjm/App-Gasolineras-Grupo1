package es.unican.is.appgasolineras.activities.detail;

import es.unican.is.appgasolineras.activities.main.IMainContract;
import es.unican.is.appgasolineras.model.Gasolinera;

public class GasolineraDetailPresenter implements IDetailContract.Presenter {
    Gasolinera g;
    private final IDetailContract.View view;
    public GasolineraDetailPresenter (Gasolinera g, IDetailContract.View view) {
        this.view = view;
        this.g = g;
    }
    @Override
    public void init() {

        String rotulo = g.getRotulo().toLowerCase();
        view.setInfo(g.getMunicipio(), rotulo, g.getHorario(), g.getNormal95(),
                g.getDieselA(), this.calcula());
    }

    @Override
    public String calcula() {
        double diesel = Double.parseDouble(g.getDieselA().replace(",", "."));
        double gasolina = Double.parseDouble(g.getNormal95().replace(",", "."));
        return String.valueOf ((2*gasolina + diesel)/3).replace(".", ",");
    }
}
