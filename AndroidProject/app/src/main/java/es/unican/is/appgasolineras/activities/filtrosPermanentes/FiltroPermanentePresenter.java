package es.unican.is.appgasolineras.activities.filtrosPermanentes;

import es.unican.is.appgasolineras.common.prefs.IPrefs;


public class FiltroPermanentePresenter implements IPermanenteContract.presenter{
    private final IPermanenteContract.view view;
    private IPrefs pref;
    private FiltroPermanenteMapper mapper;

    public FiltroPermanentePresenter(IPermanenteContract.view view, IPrefs pref){
        this.view = view;
        this.pref = pref;
        mapper = new FiltroPermanenteMapper();
    }

    @Override
    public void init() {

    }

    @Override
    public void guardaFiltroPermanente(int idComunidad, int tipoGasolina) {
        pref.putString("tipoGasolina", mapper.getCombustible(tipoGasolina));
        pref.putString("idComunidad", mapper.getCCAAID(idComunidad));
        pref.putString("idComunidadName", mapper.getCCAAName(idComunidad));
    }

    @Override
    public void reseteaFiltroPermanente(){
        pref.putString("tipoGasolina", "");
        pref.putString("idComunidad", "");
        pref.putString("idComunidadName", "");
    }
}