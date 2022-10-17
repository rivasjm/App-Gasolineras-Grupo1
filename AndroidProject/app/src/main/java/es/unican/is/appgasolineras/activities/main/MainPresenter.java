package es.unican.is.appgasolineras.activities.main;

import androidx.annotation.NonNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import es.unican.is.appgasolineras.common.Callback;
import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;

public class MainPresenter implements IMainContract.Presenter {

    private final IMainContract.View view;
    private IGasolinerasRepository repository;
    private IPrefs prefs;
    double max = Double.MIN_VALUE;
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
            if (red) {
                doSyncInit();
            } else {
                //Persistir
            }
        }
    }

    private void doAsyncInit() {
        repository.requestGasolineras(new Callback<List<Gasolinera>>() {

            @Override
            public void onSuccess(List<Gasolinera> data) {
                System.out.println (prefs.getString("idComunidad"));
                if (prefs.getString("idComunidad").equals("")){
                    data = repository.todasGasolineras();
                }else {
                    data = repository.getGasolineras(prefs.getString("idComunidad"));
                }
                maxPrecio = maximoEntreTodas(data);
                data = filtraTipo(data, prefs.getString("tipoGasolina"));
                data = filtraPrecio(data, prefs.getString("maxPrecio"));
                prefs.putString("maxPrecio", maxPrecio);
                view.showGasolineras(data);
                shownGasolineras = data;
                view.showLoadCorrect(data.size());
            }

            @Override
            public void onFailure() {
                shownGasolineras = null;
                view.showLoadErrorRed();
            }
        });
    }

    private void doSyncInit() {
        System.out.println (prefs.getString("idComunidad"));
        List<Gasolinera> data;
        if (prefs.getString("idComunidad").equals("")){
            data = repository.todasGasolineras();
        }else {
            data = repository.getGasolineras(prefs.getString("idComunidad"));
        }

        if (data != null) {
            maxPrecio = maximoEntreTodas(data);
            data = filtraTipo(data, prefs.getString("tipoGasolina"));
            data = filtraPrecio(data, prefs.getString("maxPrecio"));
            this.data = data;
            prefs.putString("maxPrecio", maxPrecio);
            view.showGasolineras(data);
            shownGasolineras = data;
            view.showLoadCorrect(data.size());
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
    public void onHomeClicked() { view.openMenuPrincipal(); }

    @Override
    public void onPrecioClicked() {view.openFiltroPrecio();}

    @Override
    public void onResetFiltroPrecioClicked() {
        prefs.putString("maxPrecio", maxPrecio);
        doSyncInit();
    }

    /*@Override
    public List<Gasolinera> filtra(List<Gasolinera> data, String tipoCombustible, String maxPrecio){
        List<Gasolinera> listaDevolver = new ArrayList<Gasolinera>();
        if (maxPrecio.equals("")) {
            return data;
        } else {
            for (Gasolinera g : data) {
            if (tipoCombustible.equals("dieselA")) {
                if(!g.getDieselA().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals("Normal95")) {
                if(!g.getNormal95().equals("")) {
                    listaDevolver.add(g);
                    continue;
                }
            }
                BigDecimal actual = new BigDecimal(maxPrecio).setScale(3, RoundingMode.UP);
                BigDecimal min;
                if (g.getNormal95().equals("") && g.getDieselA().equals("")) {
                    continue;
                } else if (g.getDieselA().equals("")) {
                    min = new BigDecimal(g.getNormal95().replace(',', '.')).setScale(3, RoundingMode.UP);
                } else if (g.getNormal95().equals("")){
                    min = new BigDecimal(g.getDieselA().replace(',', '.')).setScale(3, RoundingMode.UP);
                } else {
                    BigDecimal gas = new BigDecimal(g.getNormal95().replace(',', '.')).setScale(3, RoundingMode.UP);
                    BigDecimal diesel = new BigDecimal(g.getDieselA().replace(',', '.')).setScale(3, RoundingMode.UP);
                    if (gas.compareTo(diesel) >= 0) {
                        min = new BigDecimal(diesel.toString()).setScale(3, RoundingMode.UP);
                    } else {
                        min = new BigDecimal(gas.toString()).setScale(3, RoundingMode.UP);
                    }
                }
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
            return listaDevolver;
        }
    }

     */

    @Override
    public List<Gasolinera> filtraTipo(List<Gasolinera> data, String tipoCombustible) {
        List<Gasolinera> listaDevolver = new ArrayList<Gasolinera>();
        if (tipoCombustible.equals("")) {
            return data;
        } else {
            for (Gasolinera g : data) {
                if (tipoCombustible.equals("dieselA")) {
                    if (!g.getDieselA().equals("")) {
                        listaDevolver.add(g);
                    }
                } else if (tipoCombustible.equals("normal95")) {
                    if (!g.getNormal95().equals("")) {
                        listaDevolver.add(g);
                        continue;
                    }
                }
            }
            return listaDevolver;
        }
    }

    @Override
    public List<Gasolinera> filtraPrecio(List<Gasolinera> data, String maxPrecio){
            List<Gasolinera> listaDevolver = new ArrayList<Gasolinera>();
            if (maxPrecio.equals("")) {
                return data;
            } else {
                for (Gasolinera g : data) {
                    BigDecimal actual = new BigDecimal(maxPrecio).setScale(3, RoundingMode.UP);
                    BigDecimal min;
                    if (g.getNormal95().equals("") && g.getDieselA().equals("")) {
                        continue;
                    } else if (g.getDieselA().equals("")) {
                        min = new BigDecimal(g.getNormal95().replace(',', '.')).setScale(3, RoundingMode.UP);
                    } else if (g.getNormal95().equals("")) {
                        min = new BigDecimal(g.getDieselA().replace(',', '.')).setScale(3, RoundingMode.UP);
                    } else {
                        BigDecimal gas = new BigDecimal(g.getNormal95().replace(',', '.')).setScale(3, RoundingMode.UP);
                        BigDecimal diesel = new BigDecimal(g.getDieselA().replace(',', '.')).setScale(3, RoundingMode.UP);
                        if (gas.compareTo(diesel) >= 0) {
                            min = new BigDecimal(diesel.toString()).setScale(3, RoundingMode.UP);
                        } else {
                            min = new BigDecimal(gas.toString()).setScale(3, RoundingMode.UP);
                        }
                    }
                    if (min.compareTo(actual) <= 0) {
                        listaDevolver.add(g);
                    }
                }
            }
            return listaDevolver;
        }


    @Override
    public String maximoEntreTodas(List<Gasolinera> data){
        String devolver;
        String idCCAA = prefs.getString("idComunidad");

        if (data == null) {
            devolver = "";
        } else {
            for (Gasolinera g : data) {
                if (g.getNormal95() == null || g.getNormal95().equals("")
                        || g.getDieselA() == null || g.getDieselA().equals("")) {
                    break;
                }
                Double maximo = Double.max(Double.parseDouble(g.getNormal95().replace(',','.')),
                        Double.parseDouble(g.getDieselA().replace(',', '.')));
                if (max < maximo) {
                    max = maximo;
                }
            }
            devolver = String.valueOf(max);
        }
        return devolver;
    }

    public String getMaximoEntreTodas() {
        return maximoEntreTodas(data);
    }
}
