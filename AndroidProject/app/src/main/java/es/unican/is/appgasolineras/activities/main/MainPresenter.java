package es.unican.is.appgasolineras.activities.main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import es.unican.is.appgasolineras.common.Callback;
import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.common.prefs.Prefs;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;

public class MainPresenter implements IMainContract.Presenter {

    private final IMainContract.View view;
    private IGasolinerasRepository repository;
    private IPrefs prefs;
    double max = Double.MIN_VALUE;

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
        List<Gasolinera> data = repository.getGasolineras();

        data = this.filtra
                (data, prefs.getString("tipoGasolina"),
                        prefs.getInt("IDCCAA"), prefs.getString("maxPrecio"));
        prefs.delete("maxPrecio");

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

        System.out.println(maxPrecio);
        for (int i = 0; i < data.size(); i++) {
            Gasolinera g = data.get(i);
            /*if (g.getIDCCAAInt() != CCAA && CCAA != 0) {
                data.remove(g);
            } else if (tipoCombustible.equals("dieselA")) {
                if(g.getDieselA().equals("")) {
                    data.remove(g);
                    continue;
                }
            } else if (tipoCombustible.equals("Normal95")) {
                if(g.getNormal95().equals("")) {
                    data.remove(g);
                    continue;
                }
            }*/
            /*else */if (!maxPrecio.equals("")) {
                BigDecimal min;
                BigDecimal actual = new BigDecimal(maxPrecio).setScale(3, RoundingMode.UP);
                if (g.getNormal95().equals("") && g.getDieselA().equals("")) {
                   continue;
                } else if (g.getDieselA().equals("")) {
                    min = new BigDecimal(g.getNormal95().replace(',', '.')).setScale(3, RoundingMode.UP);
                } else if (g.getNormal95().equals("")){
                    min = new BigDecimal(g.getDieselA().replace(',', '.')).setScale(3, RoundingMode.UP);

                } else {
                    BigDecimal gas = new BigDecimal(g.getNormal95().replace(',', '.')).setScale(3, RoundingMode.UP);
                    BigDecimal diesel = new BigDecimal(g.getDieselA().replace(',', '.')).setScale(3, RoundingMode.UP);
                    if (gas.compareTo(diesel) > 0) {
                        min = diesel;
                    } else {
                        min = gas;
                    }
                }
                System.out.println(min);
                if (min.compareTo(actual) > 0) {
                    data.remove(g);
                    System.out.println("BORRADO");
                    continue;
                }
            }


        }

        return data;
    }

    public String maximoEntreTodas(){
        List<Gasolinera> data = repository.getGasolineras();
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
        return String.valueOf(max);
    }
}
