package es.unican.is.appgasolineras.activities.filtrosPermanentes;

import es.unican.is.appgasolineras.common.prefs.IPrefs;


public class FiltroPermanentePresenter implements IPermanenteContract.presenter{
    private final IPermanenteContract.view view;
    private IPrefs pref;

    public FiltroPermanentePresenter(IPermanenteContract.view view, IPrefs pref){
        this.view = view;
        this.pref = pref;
    }

    @Override
    public void init() {

    }

    @Override
    public void guardaFiltroPermanente(int idComunidad, int tipoGasolina) {
        System.out.println(pref.getInt("idComunidad"));
        System.out.println(pref.getInt("tipoGasolina"));

        System.out.println(idComunidad);
        System.out.println(tipoGasolina);

        pref.putInt("tipoGasolina", tipoGasolina);
        pref.putInt("idComunidad", idComunidad);

        System.out.println(pref.getInt("idComunidad"));
        System.out.println(pref.getInt("tipoGasolina"));


    }

    @Override
    public void reseteaFiltroPermanente(){
       // p.delete("tipoGasolina");
       // p.delete("idComunidad");
    }
}