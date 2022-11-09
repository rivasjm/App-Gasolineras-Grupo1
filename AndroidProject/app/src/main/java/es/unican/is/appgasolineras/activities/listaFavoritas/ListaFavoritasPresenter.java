package es.unican.is.appgasolineras.activities.listaFavoritas;

import java.util.List;

import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;
import es.unican.is.appgasolineras.repository.db.GasolineraDatabase;

public class ListaFavoritasPresenter implements IListaFavoritasContract.Presenter {

    private List<Gasolinera> shownGasolineras;
    private final IListaFavoritasContract.View view;
    private IPrefs prefs;
    private IGasolinerasRepository repository;
    private GasolineraDatabase db;

    public ListaFavoritasPresenter(IListaFavoritasContract.View view, IPrefs prefs, GasolineraDatabase db) {
        this.view = view;
        this.prefs = prefs;
        this.db = db;
    }

    @Override
    public void init() {
        if (repository == null) {
            repository = view.getGasolineraRepository();
        }
        if (repository != null) {
            doSyncInitFavoritas();
        }
    }

    @Override
    public void doSyncInitFavoritas() {
        List<Gasolinera> dataSync;
        dataSync = repository.getGasolineras("06");
        if (dataSync != null) {
            view.showGasolineras(dataSync);
            shownGasolineras = dataSync;
            view.showLoadCorrect(dataSync.size());
        } else {
            shownGasolineras = null;
            view.showLoadErrorServidor();
        }
    }

    @Override
    public void onGasolineraClicked(int index) {
        if (shownGasolineras != null && index < shownGasolineras.size()) {
            Gasolinera gasolinera = shownGasolineras.get(index);
            view.openGasolineraDetails(gasolinera);
        }
    }
}
