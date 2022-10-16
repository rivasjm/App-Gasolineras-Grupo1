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


        System.out.println(idComunidad);
        System.out.println(tipoGasolina);

        pref.putString("tipoGasolina", mapper.getCombustible(tipoGasolina));
        pref.putString("idComunidad", mapper.getCCAA(idComunidad));

        System.out.println(pref.getString("idComunidad"));
        System.out.println(pref.getString("tipoGasolina"));


    }

    @Override
    public void reseteaFiltroPermanente(){
       pref.delete("tipoGasolina");
       pref.delete("idComunidad");
    }
}