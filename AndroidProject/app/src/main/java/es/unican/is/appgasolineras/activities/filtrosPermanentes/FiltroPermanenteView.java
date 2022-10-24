package es.unican.is.appgasolineras.activities.filtrosPermanentes;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import es.unican.is.appgasolineras.R;
import es.unican.is.appgasolineras.activities.menuPrincipal.MenuPrincipalView;
import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.common.prefs.Prefs;

public class FiltroPermanenteView extends AppCompatActivity implements IPermanenteContract.View {
    Spinner spnCombustible;
    Spinner spnCCAA;
    IPrefs prefs;

    IPermanenteContract.Presenter presenter;
    FiltroPermanenteMapper mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros_permanentes);

        prefs = Prefs.from(this);
        mapper = new FiltroPermanenteMapper();

        //MainPresenter mainPresenter = new MainPresenter(this, p);

        spnCombustible = findViewById(R.id.spinner_combustible);
        spnCCAA = findViewById(R.id.spinner_CCAA);

        getSupportActionBar().setTitle("Filtros permanentes");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        presenter = new FiltroPermanentePresenter(prefs);
        presenter.init();
        this.init();

    }

    @Override
    public void init() {
        //Configuracion del spinner de las provincias
        ArrayAdapter<CharSequence> adapterCCAA = ArrayAdapter.createFromResource(this,
                R.array.comunidadesArray, android.R.layout.simple_spinner_item);
        adapterCCAA.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnCCAA.setAdapter(adapterCCAA);
        int comunidadGuardada = mapper.getCCAAIndex(prefs.getString("idComunidadName"));
        spnCCAA.setSelection(comunidadGuardada);

        //Configuracion del spinner de los combustibles
        ArrayAdapter<CharSequence> adapterCombustibles = ArrayAdapter.createFromResource(this,
                R.array.combustiblesArray, android.R.layout.simple_spinner_item);
        adapterCombustibles.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spnCombustible.setAdapter(adapterCombustibles);
        int combustibleGuardado = mapper.getCombustibleIndex(prefs.getString("tipoGasolina"));
        spnCombustible.setSelection(combustibleGuardado);

        Button btnGuardarPermanentes = findViewById(R.id.btnGuardarPermanentes);
        btnGuardarPermanentes.setOnClickListener(view -> {
            presenter.guardaFiltroPermanente(spnCCAA.getSelectedItemPosition(), spnCombustible.getSelectedItemPosition());
            openMainView();
        });

        Button btnResetPermanentes = findViewById(R.id.btnResetearPermanentes);
        btnResetPermanentes.setOnClickListener(view -> {
            presenter.reseteaFiltroPermanente();
            this.init();
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            openMainView();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openMainView(){
        Intent myIntent = new Intent(this, MenuPrincipalView.class);
        startActivity(myIntent);
        finish();
    }
}

