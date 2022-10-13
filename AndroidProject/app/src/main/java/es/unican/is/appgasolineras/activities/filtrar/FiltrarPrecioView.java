package es.unican.is.appgasolineras.activities.filtrar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import es.unican.is.appgasolineras.R;
import es.unican.is.appgasolineras.activities.main.MainView;

public class FiltrarPrecioView extends AppCompatActivity implements  IFiltrarPorPrecioContract.View{

    ImageButton btnBajarPrecio;
    ImageButton btnSubirPrecio;
    Button btnResetear;
    Button btnMostrarResultados;
    IFiltrarPorPrecioContract.Presenter presenter;
    TextView tvPrecioLimite;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar_precio_view);
        String max = getIntent().getStringExtra("max");
        presenter = new FiltrarPorPrecioPresenter(this, getApplicationContext(), max);

        getSupportActionBar().setTitle("Filtro Precio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnBajarPrecio = findViewById(R.id.btnBajarPrecio);
        btnSubirPrecio = findViewById(R.id.btnSubirPrecio);
        btnResetear = findViewById(R.id.btnResetear);
        btnMostrarResultados = findViewById(R.id.btnMostrarResultados);
        tvPrecioLimite = findViewById(R.id.tvPrecioLimite);
        tvPrecioLimite.setText(max.substring(0,4));
        btnBajarPrecio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String actual = String.valueOf(tvPrecioLimite.getText());
                tvPrecioLimite.setText(presenter.bajaPrecio(actual));
            }
        });;
        btnSubirPrecio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String actual = String.valueOf(tvPrecioLimite.getText());
                tvPrecioLimite.setText(presenter.subePrecio(actual));
            }
        });
        btnResetear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvPrecioLimite.setText(max);
            }
        });
        btnMostrarResultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.estableceRango(String.valueOf(tvPrecioLimite.getText()));
                openMainView();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openMainView (){
        Intent myIntent = new Intent(this, MainView.class);
        startActivity(myIntent);
    }

}