package es.unican.is.appgasolineras.activities.menuPrincipal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import es.unican.is.appgasolineras.R;
import es.unican.is.appgasolineras.activities.info.InfoView;
import es.unican.is.appgasolineras.activities.main.MainView;

public class MenuPrincipalView extends AppCompatActivity implements IMenuPrincipalContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        getSupportActionBar().setTitle("Menú Principal");

        this.init();
    }


    @Override
    public void init() {
        Button botonIrALista = findViewById(R.id.btnAccederLista);
        botonIrALista.setOnClickListener(view ->  {
            this.openMainView();
        });
        Button botonIrAFiltrosPermanentes = findViewById(R.id.btnAccederFiltrosPermanentes);
        botonIrAFiltrosPermanentes.setOnClickListener(view ->  {
            this.openFiltrosPermanentesView();
        });
    }

    @Override
    public void openMainView() {
        Intent intent = new Intent(this, MainView.class);
        startActivity(intent);
    }

    @Override
    public void openFiltrosPermanentesView() {
        // Rellenar
    }
}