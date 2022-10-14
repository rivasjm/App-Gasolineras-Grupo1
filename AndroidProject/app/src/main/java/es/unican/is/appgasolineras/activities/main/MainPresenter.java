package es.unican.is.appgasolineras.activities.main;

import java.util.List;

import es.unican.is.appgasolineras.common.Callback;
import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;

public class MainPresenter implements IMainContract.Presenter {

    private final IMainContract.View view;
    private IGasolinerasRepository repository;
    private IPrefs prefs;

    private List<Gasolinera> shownGasolineras;

    public MainPresenter(IMainContract.View view, IPrefs prefs) {
        this.view = view;
        this.prefs = prefs;
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
        String idCCAA = "00";

        int aux1 = prefs.getInt("idComunidad");
        int aux2;

        //el 0 es "Todas", el 1 es Cantabria y el 7 vuelve a ser Cantabria ordenado alfabeticamente
        if (aux1 == 1 || aux1 == 7) {
            idCCAA = "06";
        } else if (aux1 > 1){
            aux2 = (aux1 - 1);
            idCCAA = String.valueOf(aux2);
        }

        //aqui hago arriba que el getInt sea de la CCAA que debe  ser
        List<Gasolinera> data = repository.getGasolineras(idCCAA);

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
}
