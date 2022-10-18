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
    private static final String idComunidad = "idComunidad";
    private static final String tipoGasolina = "tipoGasolina";
    private static final String maxPrecioString = "maxPrecio";
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
                if (prefs.getString(idComunidad).equals("")){
                    data = repository.todasGasolineras();
                }else {
                    data = repository.getGasolineras(prefs.getString(idComunidad));
                }
                maxPrecio = maximoEntreTodas(data);
                data = filtraTipo(data, prefs.getString(tipoGasolina));
                data = filtraPrecio(data, prefs.getString(maxPrecioString));
                prefs.putString(maxPrecioString, maxPrecio);
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
        List<Gasolinera> data;
        if (prefs.getString(idComunidad).equals("")){
            data = repository.todasGasolineras();
        }else {
            data = repository.getGasolineras(prefs.getString(idComunidad));
        }

        if (data != null) {
            data = filtraTipo(data, prefs.getString(tipoGasolina));
            maxPrecio = maximoEntreTodas(data);
            data = filtraPrecio(data, prefs.getString(maxPrecioString));
            this.data = data;
            prefs.putString(maxPrecioString, maxPrecio);
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
        prefs.putString(maxPrecioString, maxPrecio);
        doSyncInit();
    }

    @Override
    public List<Gasolinera> filtraTipo(List<Gasolinera> data, String tipoCombustible) {
        List<Gasolinera> listaDevolver = new ArrayList<Gasolinera>();
        if (tipoCombustible.equals("")) {
            return data;
        }
        for (Gasolinera g : data) {
            if (tipoCombustible.equals("dieselA")) {
                if (!g.getDieselA().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals("normal95")) {
                if (!g.getNormal95().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals("normal95E10")) {
                if (!g.getGasolina95E10().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals("normal95E5p")) {
                if (!g.getNormal95Prem().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals("normal98E5")) {
                if (!g.getGasolina98E5().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals("normal98E10")) {
                if (!g.getGasolina98E10().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals("dieselP")) {
                if (!g.getDieselPrem().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals("dieselB")) {
                if (!g.getDieselB().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals("bioEtanol")) {
                if (!g.getBioetanol().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals("bioDiesel")) {
                if (!g.getBiodiesel().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals("glp")) {
                if (!g.getGasLicPet().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals("gasC")) {
                if (!g.getGasNatComp().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals("gasL")) {
                if (!g.getGasNatLic().equals("")) {
                    listaDevolver.add(g);
                }
            } else if (tipoCombustible.equals("h2")) {
                if (!g.getHidrogeno().equals("")) {
                    listaDevolver.add(g);
                }
            }
        }
        return listaDevolver;
    }

    @Override
    public List<Gasolinera> filtraPrecio(List<Gasolinera> data, String maxPrecio) {
        String tipo = prefs.getString(tipoGasolina);
        List<Gasolinera> listaDevolver = new ArrayList<Gasolinera>();
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
        } else if (tipo.equals("normal95")) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getNormal95().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals("normal95E10")) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getGasolina95E10().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals("normal95E5p")) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getNormal95Prem().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals("normal98E5")) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getGasolina98E5().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals("normal98E10")) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getGasolina98E10().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals("dieselA")) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getDieselA().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals("dieselP")) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getDieselPrem().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals("dieselB")) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getDieselB().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals("bioEtanol")) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getBioetanol().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals("bioDiesel")) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getBiodiesel().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals("glp")) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getGasLicPet().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals("gasC")) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getGasNatComp().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals("gasL")) {
            for (Gasolinera g : data) {
                BigDecimal min = new BigDecimal(g.getGasNatLic().replace(',', '.')).setScale(3, RoundingMode.UP);
                if (min.compareTo(actual) <= 0) {
                    listaDevolver.add(g);
                }
            }
        } else if (tipo.equals("h2")) {
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
        String tipo = prefs.getString(tipoGasolina);
        if (data == null) {
            devolver = "";
        } else {
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
            } else if (tipo.equals("normal95")) {
                for (Gasolinera g : data) {
                    if(Double.parseDouble(g.getNormal95().replace(',', '.')) > max) {
                        max = Double.parseDouble(g.getNormal95().replace(',', '.'));
                    }
                }
                devolver = String.valueOf(max);
            } else if (tipo.equals("normal95E10")) {
                for (Gasolinera g : data) {
                    if(Double.parseDouble(g.getGasolina95E10().replace(',', '.')) > max) {
                        max = Double.parseDouble(g.getGasolina95E10().replace(',', '.'));
                    }
                }
                devolver = String.valueOf(max);
            } else if (tipo.equals("normal95E5p")) {
                for (Gasolinera g : data) {
                    if(Double.parseDouble(g.getNormal95Prem().replace(',', '.')) > max) {
                        max = Double.parseDouble(g.getNormal95Prem().replace(',', '.'));
                    }
                }
                devolver = String.valueOf(max);
            } else if (tipo.equals("normal98E5")) {
                for (Gasolinera g : data) {
                    if(Double.parseDouble(g.getGasolina98E5().replace(',', '.')) > max) {
                        max = Double.parseDouble(g.getGasolina98E5().replace(',', '.'));
                    }
                }
                devolver = String.valueOf(max);
            } else if (tipo.equals("normal8E10")) {
                for (Gasolinera g : data) {
                    if(Double.parseDouble(g.getGasolina98E10().replace(',', '.')) > max) {
                        max = Double.parseDouble(g.getGasolina98E10().replace(',', '.'));
                    }
                }
                devolver = String.valueOf(max);
            } else if (tipo.equals("dieselA")) {
                for (Gasolinera g : data) {
                    if(Double.parseDouble(g.getDieselA().replace(',', '.')) > max) {
                        max = Double.parseDouble(g.getDieselA().replace(',', '.'));
                    }
                }
                devolver = String.valueOf(max);
            } else if (tipo.equals("dieselP")) {
                for (Gasolinera g : data) {
                    if(Double.parseDouble(g.getDieselPrem().replace(',', '.')) > max) {
                        max = Double.parseDouble(g.getDieselPrem().replace(',', '.'));
                    }
                }
                devolver = String.valueOf(max);
            } else if (tipo.equals("dieselB")) {
                for (Gasolinera g : data) {
                    if(Double.parseDouble(g.getDieselB().replace(',', '.')) > max) {
                        max = Double.parseDouble(g.getDieselB().replace(',', '.'));
                    }
                }
                devolver = String.valueOf(max);
            } else if (tipo.equals("bioEtanol")) {
                for (Gasolinera g : data) {
                    if(Double.parseDouble(g.getBioetanol().replace(',', '.')) > max) {
                        max = Double.parseDouble(g.getBioetanol().replace(',', '.'));
                    }
                }
                devolver = String.valueOf(max);
            } else if (tipo.equals("bioDiesel")) {
                for (Gasolinera g : data) {
                    if(Double.parseDouble(g.getBiodiesel().replace(',', '.')) > max) {
                        max = Double.parseDouble(g.getBiodiesel().replace(',', '.'));
                    }
                }
                devolver = String.valueOf(max);
            } else if (tipo.equals("glp")) {
                for (Gasolinera g : data) {
                    if(Double.parseDouble(g.getGasLicPet().replace(',', '.')) > max) {
                        max = Double.parseDouble(g.getGasLicPet().replace(',', '.'));
                    }
                }
                devolver = String.valueOf(max);
            } else if (tipo.equals("gasC")) {
                for (Gasolinera g : data) {
                    if(Double.parseDouble(g.getGasNatComp().replace(',', '.')) > max) {
                        max = Double.parseDouble(g.getGasNatComp().replace(',', '.'));
                    }
                }
                devolver = String.valueOf(max);
            } else if (tipo.equals("gasL")) {
                for (Gasolinera g : data) {
                    if(Double.parseDouble(g.getGasNatLic().replace(',', '.')) > max) {
                        max = Double.parseDouble(g.getGasNatLic().replace(',', '.'));
                    }
                }
                devolver = String.valueOf(max);
            } else if (tipo.equals("h2")) {
                for (Gasolinera g : data) {
                    if(Double.parseDouble(g.getHidrogeno().replace(',', '.')) > max) {
                        max = Double.parseDouble(g.getHidrogeno().replace(',', '.'));
                    }
                }
                devolver = String.valueOf(max);
            }
        }
        return devolver;
    }

    public String getMaximoEntreTodas() {
        return maximoEntreTodas(data);
    }
}
