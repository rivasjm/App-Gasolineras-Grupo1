package es.unican.is.appgasolineras.activities.main;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.IGasolinerasRepository;

public class MainPresenter implements IMainContract.Presenter {

    private static final String IDCOMUNIDAD = "idComunidad";
    private static final String TIPOGASOLINA = "tipoGasolina";
    private static final String MAXPRECIOSTRING = "maxPrecio";
    private static final String DIESELA = "dieselA";
    private static final String NORMAL95 = "normal95";
    private static final String NORMAL95E10 = "normal95E10";
    private static final String NORMAL95E5P = "normal95E5p";
    private static final String NORMAL98E5 = "normal98E5";
    private static final String NORMAL98E10 = "normal98E10";
    private static final String DIESELP = "dieselP";
    private static final String DIESELB = "dieselB";
    private static final String BIOETANOL = "bioEtanol";
    private static final String BIODIESEL = "bioDiesel";
    private static final String GLP = "glp";
    private static final String GASC = "gasC";
    private static final String GASL = "gasL";
    private static final String H2 = "h2";

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

    /**
    private void doAsyncInit() {
        repository.requestGasolineras(new Callback<List<Gasolinera>>() {

            //@Override
            public void onSuccess(List<Gasolinera> data) {
                List<Gasolinera> dataAsync;
                if (prefs.getString(IDCOMUNIDAD).equals("")){
                    dataAsync = repository.todasGasolineras();
                }else {
                    dataAsync = repository.getGasolineras(prefs.getString(IDCOMUNIDAD));
                }
                maxPrecio = maximoEntreTodas(dataAsync);
                dataAsync = filtraTipo(dataAsync, prefs.getString(TIPOGASOLINA));
                dataAsync = filtraPrecio(dataAsync, prefs.getString(maxPrecioString));
                prefs.putString(maxPrecioString, maxPrecio);
                view.showGasolineras(dataAsync);
                shownGasolineras = dataAsync;
                view.showLoadCorrect(dataAsync.size());
            }

            //@Override
            public void onFailure() {
                shownGasolineras = null;
                view.showLoadErrorRed();
            }
        });
    }
    */

    private void doSyncInit() {
        List<Gasolinera> dataSync;
        if (prefs.getString(IDCOMUNIDAD).equals("")){
            dataSync = repository.todasGasolineras();
        }else {
            dataSync = repository.getGasolineras(prefs.getString(IDCOMUNIDAD));
        }

        if (dataSync != null) {
            dataSync = filtraTipo(dataSync, prefs.getString(TIPOGASOLINA));
            maxPrecio = maximoEntreTodas(dataSync);
            dataSync = filtraPrecio(dataSync, prefs.getString(MAXPRECIOSTRING));
            this.data = dataSync;
            prefs.putString(MAXPRECIOSTRING, maxPrecio);
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
    public void onHomeClicked() { view.openMenuPrincipal(); }

    @Override
    public void onPrecioClicked() {view.openFiltroPrecio();}

    @Override
    public void onResetFiltroPrecioClicked() {
        prefs.putString(MAXPRECIOSTRING, maxPrecio);
        doSyncInit();
    }

    @Override
    public List<Gasolinera> filtraTipo(List<Gasolinera> data, String tipoCombustible) {
        List<Gasolinera> listaDevolver = new ArrayList<>();
        if (tipoCombustible.equals("")) {
            return data;
        }
        for (Gasolinera g : data) {
            if (tipoCombustible.equals(DIESELA)) {
                if (!g.getDieselA().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals(NORMAL95)) {
                if (!g.getNormal95().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals(NORMAL95E10)) {
                if (!g.getGasolina95E10().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals(NORMAL95E5P)) {
                if (!g.getNormal95Prem().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals(NORMAL98E5)) {
                if (!g.getGasolina98E5().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals(NORMAL98E10)) {
                if (!g.getGasolina98E10().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals(DIESELP)) {
                if (!g.getDieselPrem().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals(DIESELB)) {
                if (!g.getDieselB().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals(BIOETANOL)) {
                if (!g.getBioetanol().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals(BIODIESEL)) {
                if (!g.getBiodiesel().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals(GLP)) {
                if (!g.getGasLicPet().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals(GASC)) {
                if (!g.getGasNatComp().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals(GASL)) {
                if (!g.getGasNatLic().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals(H2)) {
                if (!g.getHidrogeno().equals("")) {
                    listaDevolver.add(g);
                }
            }
        }
        return listaDevolver;
    }

    @Override
    public List<Gasolinera> filtraPrecio(List<Gasolinera> data, String maxPrecio) {
        String tipo = prefs.getString(TIPOGASOLINA);
        List<Gasolinera> listaDevolver = new ArrayList<>();
        if (maxPrecio.equals("")) {
            return data;
        }
        BigDecimal actual = new BigDecimal(maxPrecio).setScale(3, RoundingMode.UP);
        if (tipo.equals("")) {
            for (Gasolinera g : data) {
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
        } else if (tipo.equals(NORMAL95)) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getNormal95().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals(NORMAL95E10)) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getGasolina95E10().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals(NORMAL95E5P)) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getNormal95Prem().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals(NORMAL98E5)) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getGasolina98E5().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals(NORMAL98E10)) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getGasolina98E10().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals(DIESELA)) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getDieselA().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals(DIESELP)) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getDieselPrem().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals(DIESELB)) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getDieselB().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals(BIOETANOL)) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getBioetanol().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals(BIODIESEL)) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getBiodiesel().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals(GLP)) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getGasLicPet().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals(GASC)) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getGasNatComp().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals(GASL)) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getGasNatLic().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals(H2)) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getHidrogeno().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        }
    return listaDevolver;
    }


    @Override
    public String maximoEntreTodas(List<Gasolinera> data){
        if (data.isEmpty()){
            return "0.00";
        }
        String devolver = "";
        String tipo = prefs.getString(TIPOGASOLINA);
        if (tipo.equals("")) {
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
        } else if (tipo.equals(NORMAL95)) {
            for (Gasolinera g : data) {
                if(Double.parseDouble(g.getNormal95().replace(',', '.')) > max) {
                    max = Double.parseDouble(g.getNormal95().replace(',', '.'));
                }
            }
            devolver = String.valueOf(max);
        } else if (tipo.equals(NORMAL95E10)) {
            for (Gasolinera g : data) {
                if(Double.parseDouble(g.getGasolina95E10().replace(',', '.')) > max) {
                    max = Double.parseDouble(g.getGasolina95E10().replace(',', '.'));
                }
            }
            devolver = String.valueOf(max);
        } else if (tipo.equals(NORMAL95E5P)) {
            for (Gasolinera g : data) {
                if(Double.parseDouble(g.getNormal95Prem().replace(',', '.')) > max) {
                    max = Double.parseDouble(g.getNormal95Prem().replace(',', '.'));
                }
            }
            devolver = String.valueOf(max);
        } else if (tipo.equals(NORMAL98E5)) {
            for (Gasolinera g : data) {
                if(Double.parseDouble(g.getGasolina98E5().replace(',', '.')) > max) {
                    max = Double.parseDouble(g.getGasolina98E5().replace(',', '.'));
                }
            }
            devolver = String.valueOf(max);
        } else if (tipo.equals(NORMAL98E10)) {
            for (Gasolinera g : data) {
                if(Double.parseDouble(g.getGasolina98E10().replace(',', '.')) > max) {
                    max = Double.parseDouble(g.getGasolina98E10().replace(',', '.'));
                }
            }
            devolver = String.valueOf(max);
        } else if (tipo.equals(DIESELA)) {
            for (Gasolinera g : data) {
                if(Double.parseDouble(g.getDieselA().replace(',', '.')) > max) {
                    max = Double.parseDouble(g.getDieselA().replace(',', '.'));
                }
            }
            devolver = String.valueOf(max);
        } else if (tipo.equals(DIESELP)) {
            for (Gasolinera g : data) {
                if(Double.parseDouble(g.getDieselPrem().replace(',', '.')) > max) {
                    max = Double.parseDouble(g.getDieselPrem().replace(',', '.'));
                }
            }
            devolver = String.valueOf(max);
        } else if (tipo.equals(DIESELB)) {
            for (Gasolinera g : data) {
                if(Double.parseDouble(g.getDieselB().replace(',', '.')) > max) {
                    max = Double.parseDouble(g.getDieselB().replace(',', '.'));
                }
            }
            devolver = String.valueOf(max);
        } else if (tipo.equals(BIOETANOL)) {
            for (Gasolinera g : data) {
                if(Double.parseDouble(g.getBioetanol().replace(',', '.')) > max) {
                    max = Double.parseDouble(g.getBioetanol().replace(',', '.'));
                }
            }
            devolver = String.valueOf(max);
        } else if (tipo.equals(BIODIESEL)) {
            for (Gasolinera g : data) {
                if(Double.parseDouble(g.getBiodiesel().replace(',', '.')) > max) {
                    max = Double.parseDouble(g.getBiodiesel().replace(',', '.'));
                }
            }
            devolver = String.valueOf(max);
        } else if (tipo.equals(GLP)) {
            for (Gasolinera g : data) {
                if(Double.parseDouble(g.getGasLicPet().replace(',', '.')) > max) {
                    max = Double.parseDouble(g.getGasLicPet().replace(',', '.'));
                }
            }
            devolver = String.valueOf(max);
        } else if (tipo.equals(GASC)) {
            for (Gasolinera g : data) {
                if(Double.parseDouble(g.getGasNatComp().replace(',', '.')) > max) {
                    max = Double.parseDouble(g.getGasNatComp().replace(',', '.'));
                }
            }
            devolver = String.valueOf(max);
        } else if (tipo.equals(GASL)) {
            for (Gasolinera g : data) {
                if(Double.parseDouble(g.getGasNatLic().replace(',', '.')) > max) {
                    max = Double.parseDouble(g.getGasNatLic().replace(',', '.'));
                }
            }
            devolver = String.valueOf(max);
        } else if (tipo.equals(H2)) {
            for (Gasolinera g : data) {
                if(Double.parseDouble(g.getHidrogeno().replace(',', '.')) > max) {
                    max = Double.parseDouble(g.getHidrogeno().replace(',', '.'));
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
