package es.unican.is.appgasolineras.activities.main;

import java.util.List;

import es.unican.is.appgasolineras.common.Callback;
import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.common.prefs.Prefs;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;

public class MainPresenter implements IMainContract.Presenter {

    private final IMainContract.View view;
    private IGasolinerasRepository repository;

    private List<Gasolinera> shownGasolineras;

    public MainPresenter(IMainContract.View view) {
        this.view = view;
    }

    @Override
    public void init() {
        if (repository == null) {
            repository = view.getGasolineraRepository();
        }
        if (repository != null) {
            doSyncInit();
        }
    }

    private void doAsyncInit() {
        repository.requestGasolineras(new Callback<List<Gasolinera>>() {
            @Override
            public void onSuccess(List<Gasolinera> data) {

                view.showGasolineras(data);
                shownGasolineras = data;
                view.showLoadCorrect(data.size());
            }

            @Override
            public void onFailure() {
                shownGasolineras = null;
                view.showLoadError();
            }
        });
    }

    private void doSyncInit() {
        List<Gasolinera> data = repository.getGasolineras();

        if (data != null) {
            view.showGasolineras(data);
            shownGasolineras = data;
            view.showLoadCorrect(data.size());

        } else {
            shownGasolineras = null;
            view.showLoadError();
        }
    }

    @Override
    public void onGasolineraClicked(int index) {
        if (shownGasolineras != null && index < shownGasolineras.size()) {
            Gasolinera gasolinera = shownGasolineras.get(index);
            view.openGasolineraDetails(gasolinera);
        }
    }

    @Override
    public void onInfoClicked() {
        view.openInfoView();
    }

    @Override
    public void onRefreshClicked() {
        init();
    }

    @Override
    public void onPrecioClicked() {view.openFiltroPrecio();}

    public List <Gasolinera> filtra(List<Gasolinera> data, String tipoCombustible, int CCAA, String maxPrecio){
        for (Gasolinera g : data) {
            if (g.getIDCCAAInt() != CCAA && CCAA != 0) {
                data.remove(g);
            } else if (tipoCombustible.equals("dieselA")) {
                if(g.getDieselA() == null ||
                        (Double.parseDouble(g.getDieselA().replace(",", ".")) > Double.parseDouble(maxPrecio.replace(",", "."))) && maxPrecio != "") {
                    data.remove(g);
                }
            } else if (tipoCombustible.equals("Normal95")) {
                if(g.getNormal95() == null ||
                        (Double.parseDouble(g.getNormal95().replace(",", ".")) > Double.parseDouble(maxPrecio.replace(",", "."))) && maxPrecio != "") {
                    data.remove(g);
                }
            }
        }
        return data;
    }
}
