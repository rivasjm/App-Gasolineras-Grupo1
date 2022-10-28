package es.unican.is.appgasolineras.activities.filtrosPermanentes;

import es.unican.is.appgasolineras.common.prefs.IPrefs;


public class FiltroPermanentePresenter implements IPermanenteContract.Presenter{
    private IPrefs pref;
    private FiltroPermanenteMapper mapper;

    public FiltroPermanentePresenter(IPrefs pref){
        this.pref = pref;
        mapper = new FiltroPermanenteMapper();
    }

    @Override
    public void init() {
        //No necesita inicializarse nada
    }

    @Override
    public void guardaFiltroPermanente(int idComunidad, int tipoGasolina, boolean ubicacion) {
        pref.putString("tipoGasolina", mapper.getCombustible(tipoGasolina));
        pref.putString("idComunidad", mapper.getCCAAID(idComunidad));
        pref.putString("idComunidadName", mapper.getCCAAName(idComunidad));
        if (ubicacion) {
            pref.putString("ubicacion", "si");
        } else {
            pref.putString("ubicacion", "no");
        }
    }

    @Override
    public void reseteaFiltroPermanente(){
        pref.putString("tipoGasolina", "");
        pref.putString("idComunidad", "");
        pref.putString("idComunidadName", "");
    }
}