package es.unican.is.appgasolineras.activities.filtrosPermanentes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.sql.Array;
import java.util.ArrayList;

import es.unican.is.appgasolineras.R;
import es.unican.is.appgasolineras.activities.detail.IDetailContract;
import es.unican.is.appgasolineras.common.prefs.Prefs;

public class FiltroPermanentePresenter implements IPermanenteContract.presenter{
    private final IPermanenteContract.view view;
    private Prefs p;

    public FiltroPermanentePresenter(IPermanenteContract.view view, Context c){
        this.view = view;
        p = new Prefs(c);
    }

    @Override
    public void init() {

    }

    @Override
    public void guardaFiltro(String tipoGasolina, int idComunidad) {
        p.putString("tipoGasolina", tipoGasolina);
        p.putInt("idComunidad", idComunidad);
    }

    @Override
    public void reseteaFiltroPermanente(){
        p.putString(null);
        p.putInt(null);
    }
}