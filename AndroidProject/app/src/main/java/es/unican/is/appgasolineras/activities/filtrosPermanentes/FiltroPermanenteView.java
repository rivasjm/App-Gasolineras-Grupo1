package es.unican.is.appgasolineras.activities.filtrosPermanentes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import es.unican.is.appgasolineras.R;
import es.unican.is.appgasolineras.activities.main.MainPresenter;
import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.common.prefs.Prefs;

public class FiltroPermanenteView extends AppCompatActivity implements IPermanenteContract.view {
    Spinner spnCombustible;
    Spinner spnCCAA;

    IPermanenteContract.presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros_permanentes);

        IPrefs p = Prefs.from(this);

        //MainPresenter mainPresenter = new MainPresenter(this, p);

        spnCombustible = findViewById(R.id.spinner_combustible);
        spnCCAA = findViewById(R.id.spinner_CCAA);



        //Configuracion del spinner de las provincias
        ArrayAdapter<CharSequence> adapterCCAA = ArrayAdapter.createFromResource(this,
                R.array.comunidadesArray, android.R.layout.simple_spinner_item);
        adapterCCAA.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnCCAA.setAdapter(adapterCCAA);
        int comunidadGuardada = p.getInt("idComunidad");
        spnCCAA.setSelection(comunidadGuardada);

        //Configuracion del spinner de los combustibles
        ArrayAdapter<CharSequence> adapterCombustibles = ArrayAdapter.createFromResource(this,
                R.array.combustiblesArray, android.R.layout.simple_spinner_item);
        adapterCombustibles.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnCombustible.setAdapter(adapterCombustibles);
        int combustibleGuardado = p.getInt("tipoGasolina");
        spnCombustible.setSelection(combustibleGuardado);

        getSupportActionBar().setTitle("Filtros permanentes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new FiltroPermanentePresenter(this, p);
        presenter.init();
        this.init();

    }

    @Override
    public void init() {
        Button btnGuardarPermanentes = findViewById(R.id.btnGuardarPermanentes);
        btnGuardarPermanentes.setOnClickListener(view -> {
            presenter.guardaFiltroPermanente(spnCCAA.getSelectedItemPosition(), spnCombustible.getSelectedItemPosition());
            /***HAY QUE METER AQUI EL METODO    doSyncInit()    PARA QUE CARGUE LAS GASOLINERAS DE LA COMUNIDAD AUTONOMA****/
            //doSyncInit();
            finish();
        });

        Button btnResetPermanentes = findViewById(R.id.btnResetearPermanentes);
        btnResetPermanentes.setOnClickListener(view -> {
            presenter.reseteaFiltroPermanente();
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

