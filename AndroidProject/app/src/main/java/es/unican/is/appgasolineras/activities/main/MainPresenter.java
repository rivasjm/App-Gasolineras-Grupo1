package es.unican.is.appgasolineras.activities.main;

import static es.unican.is.appgasolineras.common.Filters.filtraMarca;
import static es.unican.is.appgasolineras.common.Filters.filtraPrecio;
import static es.unican.is.appgasolineras.common.Filters.filtraTipo;
import static es.unican.is.appgasolineras.common.Filters.maximoEntreTodas;
import static es.unican.is.appgasolineras.common.Sortings.ordenaPorUbicacion;


import java.util.List;

import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;

public class MainPresenter implements IMainContract.Presenter {

    private static final String IDCOMUNIDAD = "idComunidad";
    private static final String TIPOGASOLINA = "tipoGasolina";
    private static final String MAXPRECIOSTRING = "maxPrecio";
    private static final String MARCA = "marca";
    private static final String UBICACION = "ubicacion";
    private static final String LATITUD = "latitud";
    private static final String LONGITUD = "longitud";


    private final IMainContract.View view;
    private IGasolinerasRepository repository;
    private IPrefs prefs;

    String maxPrecio;
    Boolean red;
    private List<Gasolinera> data;

    private List<Gasolinera> shownGasolineras;

    public MainPresenter(IMainContract.View view, IPrefs prefs, Boolean red) {
        this.view = view;
        this.prefs = prefs;
        this.red = red;
    }

    @Override
    public void init() {
        if (repository == null) {
            repository = view.getGasolineraRepository();
        }
        if (repository != null) {
            if (Boolean.TRUE.equals(red)) {
                doSyncInit();
            } else {
                view.showLoadErrorRed();
                //Persistir
            }
        }
    }

    /**
     * private void doAsyncInit() {
     * repository.requestGasolineras(new Callback<List<Gasolinera>>() {
     * <p>
     * //@Override
     * public void onSuccess(List<Gasolinera> data) {
     * List<Gasolinera> dataAsync;
     * if (prefs.getString(IDCOMUNIDAD).equals("")){
     * dataAsync = repository.todasGasolineras();
     * }else {
     * dataAsync = repository.getGasolineras(prefs.getString(IDCOMUNIDAD));
     * }
     * maxPrecio = maximoEntreTodas(dataAsync);
     * dataAsync = filtraTipo(dataAsync, prefs.getString(TIPOGASOLINA));
     * dataAsync = filtraPrecio(dataAsync, prefs.getString(maxPrecioString));
     * prefs.putString(maxPrecioString, maxPrecio);
     * view.showGasolineras(dataAsync);
     * shownGasolineras = dataAsync;
     * view.showLoadCorrect(dataAsync.size());
     * }
     * <p>
     * //@Override
     * public void onFailure() {
     * shownGasolineras = null;
     * view.showLoadErrorRed();
     * }
     * });
     * }
     */

    private void doSyncInit() {
        List<Gasolinera> dataSync;
        if (prefs.getString(IDCOMUNIDAD).equals("")) {
            dataSync = repository.todasGasolineras();
        } else {
            dataSync = repository.getGasolineras(prefs.getString(IDCOMUNIDAD));
        }
        if (dataSync != null) {
            String tipoCombustible = prefs.getString(TIPOGASOLINA);
            dataSync = filtraTipo(dataSync, tipoCombustible);
            maxPrecio = maximoEntreTodas(dataSync, tipoCombustible);
            dataSync = filtraPrecio(dataSync, prefs.getString(MAXPRECIOSTRING), tipoCombustible);
            dataSync = filtraMarca(dataSync, prefs.getString(MARCA));
            if (prefs.getString(UBICACION).equals("si")) {
                dataSync = ordenaPorUbicacion(dataSync, prefs.getString(LATITUD), prefs.getString(LONGITUD));
            }
            this.data = dataSync;
            prefs.putString(MAXPRECIOSTRING, maxPrecio);
            prefs.putString(MAXPRECIOSTRING, "");
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

    @Override
    public void onInfoClicked() {
        view.openInfoView();
    }

    @Override
    public void onRefreshClicked() {
        init();
    }

    @Override
    public void onHomeClicked() {
        view.openMenuPrincipal();
    }

    @Override
    public void onPrecioClicked() {
        view.openFiltroPrecio();
    }

    @Override
    public void onResetFiltroPrecioClicked() {
        prefs.putString(MAXPRECIOSTRING, maxPrecio);
        prefs.putString(MARCA, "");
        doSyncInit();
    }

    public String getMaximoEntreTodas() {
        return maximoEntreTodas(data, prefs.getString(TIPOGASOLINA));
    }

}
