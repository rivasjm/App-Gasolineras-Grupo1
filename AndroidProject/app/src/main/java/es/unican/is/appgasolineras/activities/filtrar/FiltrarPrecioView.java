package es.unican.is.appgasolineras.activities.filtrar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import es.unican.is.appgasolineras.R;
import es.unican.is.appgasolineras.activities.main.MainView;
import es.unican.is.appgasolineras.common.prefs.IPrefs;
import es.unican.is.appgasolineras.common.prefs.Prefs;

public class FiltrarPrecioView extends AppCompatActivity implements  IFiltrarPorPrecioContract.View{

    ImageButton btnBajarPrecio;
    ImageButton btnSubirPrecio;
    Button btnResetear;
    Button btnMostrarResultados;
    TextView tvPrecioLimite;

    IFiltrarPorPrecioContract.Presenter presenter;

    String max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtrar_precio_view);
        IPrefs prefs = Prefs.from(this);
        getSupportActionBar().setTitle("Filtro Precio");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        max = getIntent().getStringExtra("max");
        presenter = new FiltrarPorPrecioPresenter(this, prefs, max);
        presenter.init();
        this.init();
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void init() {
        tvPrecioLimite = findViewById(R.id.tvPrecioLimite);
        tvPrecioLimite.setText(max.substring(0,4));

        btnBajarPrecio = findViewById(R.id.btnBajarPrecio);
        btnBajarPrecio.setOnTouchListener(new View.OnTouchListener() {

            private Handler mHandler;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 50);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        break;
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override
                public void run() {
                    String actual = String.valueOf(tvPrecioLimite.getText());
                    tvPrecioLimite.setText(presenter.bajaPrecio(actual));
                    mHandler.postDelayed(this, 50);
                }
            };
        });

        btnSubirPrecio = findViewById(R.id.btnSubirPrecio);
        btnSubirPrecio.setOnTouchListener(new View.OnTouchListener() {

            private Handler mHandler;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 50);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        break;
                }
                return false;
            }

            Runnable mAction = new Runnable() {
                @Override
                public void run() {
                    String actual = String.valueOf(tvPrecioLimite.getText());
                    tvPrecioLimite.setText(presenter.subePrecio(actual));
                    mHandler.postDelayed(this, 50);
                }
            };
        });

        btnResetear = findViewById(R.id.btnResetear);
        btnResetear.setOnClickListener(view -> {
            tvPrecioLimite.setText(max.substring(0,4));
        });

        btnMostrarResultados = findViewById(R.id.btnMostrarResultados);
        btnMostrarResultados.setOnClickListener(view -> {
            presenter.estableceRango(String.valueOf(tvPrecioLimite.getText()));
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void openMainView(){
        Intent myIntent = new Intent(this, MainView.class);
        startActivity(myIntent);
        finish();
    }

}