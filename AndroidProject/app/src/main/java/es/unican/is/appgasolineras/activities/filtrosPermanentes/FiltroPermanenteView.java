package es.unican.is.appgasolineras.activities.filtrosPermanentes;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import es.unican.is.appgasolineras.R;

public class FiltroPermanenteView extends AppCompatActivity implements IPermanenteContract.view {
    Spinner spnCombustible;
    Spinner spnCCAA;
    Button btnResetPermanentes;
    Button btnGuardarPermanentes;



    IPermanenteContract.presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros_permanentes);
        presenter = new FiltroPermanentePresenter(this, getApplicationContext());

        spnCombustible = findViewById(R.id.spinner_combustible);
        spnCCAA = findViewById(R.id.spinner_CCAA);
        btnGuardarPermanentes = findViewById(R.id.btnGuardarPermanentes);
        btnResetPermanentes = findViewById(R.id.btnResetearPermanentes);

        //Configuracion del spinner de las provincias
        ArrayAdapter<CharSequence> adapterCCAA = ArrayAdapter.createFromResource(this,
                R.array.comunidadesArray, android.R.layout.simple_spinner_item);
        adapterCCAA.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnCCAA.setAdapter(adapterCCAA);

        //Configuracion del spinner de los combustibles
        ArrayAdapter<CharSequence> adapterCombustibles = ArrayAdapter.createFromResource(this,
                R.array.combustiblesArray, android.R.layout.simple_spinner_item);
        adapterCombustibles.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnCombustible.setAdapter(adapterCombustibles);
    }

    /**
     * Listener del boton Guardar. La asignacion de este listener con el boton se realiza en el
     * layout: atributo onclick del boton
     * @param view
     */
    public void guardaPermanentes(View view) {
        FiltroPermanentePresenter f = new FiltroPermanentePresenter((IPermanenteContract.view) view, null);
        f.guardaFiltroPermanente(spnCCAA.getTransitionName(), spnCombustible.getBaseline());
    }

    /**
     * Listener del boton Resetear. La asignacion de este listener con el boton se realiza en el
     * layout: atributo onclick del boton
     * @param view
     */
    public void reseteaPermanentes(View view) {
        FiltroPermanentePresenter f = new FiltroPermanentePresenter((IPermanenteContract.view) view, null);
        f.reseteaFiltroPermanente();
    }



}

