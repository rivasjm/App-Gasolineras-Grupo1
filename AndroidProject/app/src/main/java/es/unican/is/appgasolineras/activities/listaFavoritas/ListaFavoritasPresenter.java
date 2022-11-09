package es.unican.is.appgasolineras.activities.listaFavoritas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;
import es.unican.is.appgasolineras.repository.db.GasolineraDao;
import es.unican.is.appgasolineras.repository.db.GasolineraDatabase;

public class ListaFavoritasPresenter implements IListaFavoritasContract.Presenter {

    private List<Gasolinera> shownGasolineras;
    private final IListaFavoritasContract.View view;
    private IPrefs prefs;
    private IGasolinerasRepository repository;
    private GasolineraDatabase db;
    private GasolineraDao dao;
    private Map<String, List<String>> mapaMun;
    public ListaFavoritasPresenter(IListaFavoritasContract.View view, IPrefs prefs, GasolineraDatabase db) {
        this.view = view;
        this.prefs = prefs;
        this.db = db;
        dao = db.gasolineraDao();
        mapaMun = new HashMap<>();
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
        dataSync = dao.getAll();

        for (Gasolinera g : dataSync) {
            List<String> listaId = mapaMun.get(g.getIdMun());
            if (listaId == null){
                listaId = new ArrayList<>();
                listaId.add(g.getId());

            } else {
                listaId.add(g.getId());
            }
            mapaMun.put(g.getIdMun(), listaId);
        }
        dataSync.clear();
        for (String idMun : mapaMun.keySet()) {
            List<Gasolinera> listaGas = MunicipioMapper.getGasolinerasMunicipio(idMun, repository);
            Set<String> setId = new HashSet<>(mapaMun.get(idMun));
            for (Gasolinera g: listaGas) {
                if (setId.contains(g.getId())){
                    dataSync.add(g);
                }
            }
        }
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
