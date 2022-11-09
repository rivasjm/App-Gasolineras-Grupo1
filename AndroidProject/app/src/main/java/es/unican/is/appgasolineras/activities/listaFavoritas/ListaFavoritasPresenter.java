package es.unican.is.appgasolineras.activities.listaFavoritas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;
import es.unican.is.appgasolineras.repository.db.GasolineraDao;
import es.unican.is.appgasolineras.repository.db.GasolineraDatabase;

public class ListaFavoritasPresenter implements IListaFavoritasContract.Presenter {

    private List<Gasolinera> shownGasolineras;
    private final IListaFavoritasContract.View view;
    private IGasolinerasRepository repository;
    private final GasolineraDao dao;
    private final Boolean red;


    public ListaFavoritasPresenter(IListaFavoritasContract.View view, GasolineraDatabase db, Boolean red) {
        this.view = view;
        this.red = red;
        dao = db.gasolineraDao();
    }

    @Override
    public void init() {
        if (repository == null) {
            repository = view.getGasolineraRepository();
        }
        if (repository != null) {
            if (Boolean.TRUE.equals(red)) {
                doSyncInitFavoritas();
            } else {
                view.showLoadErrorRed();
            }
        }
    }

    @Override
    public void doSyncInitFavoritas() {
        Boolean repositorio = false;
        List<Gasolinera> dataSync;
        dataSync = dao.getAll();
        if (!dataSync.isEmpty()) {
            Map<String, List<String>> mapaMun = new HashMap<>();
            for (Gasolinera g : dataSync) {
                List<String> listaId = mapaMun.get(g.getIdMun());
                if (listaId == null) {
                    listaId = new ArrayList<>();
                }
                listaId.add(g.getId());
                mapaMun.put(g.getIdMun(), listaId);
            }
            dataSync.clear();
            for (String idMun : mapaMun.keySet()) {
                List<Gasolinera> listaGasolinerasMunicipio = repository.gasolinerasMunicipio(idMun);
                if (listaGasolinerasMunicipio == null) {
                    repositorio = true;
                } else {
                    Set<String> setId = new HashSet<>(mapaMun.get(idMun));
                    for (Gasolinera g : listaGasolinerasMunicipio) {
                        if (setId.contains(g.getId())) {
                            dataSync.add(g);
                        }
                    }
                }
            }
            if (repositorio) {
                shownGasolineras = null;
                view.showLoadErrorServidor();
            } else {
                view.showGasolineras(dataSync);
                shownGasolineras = dataSync;
                view.showLoadCorrect(dataSync.size());
            }
        } else {
            shownGasolineras = null;
            view.showLoadErrorDAOVacia();
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
