package es.unican.is.appgasolineras.activities.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

import es.unican.is.appgasolineras.R;
import es.unican.is.appgasolineras.model.Gasolinera;
import es.unican.is.appgasolineras.repository.db.GasolineraDao;
import es.unican.is.appgasolineras.repository.db.GasolineraDatabase;

public class GasolineraDetailView extends AppCompatActivity implements IDetailContract.View {

    public static final String INTENT_GASOLINERA = "INTENT_GASOLINERA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        GasolineraDatabase db = Room.databaseBuilder(getApplicationContext(),
                GasolineraDatabase.class, "database-name").allowMainThreadQueries().build();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasolinera_detail_view);
        Gasolinera g = getIntent().getExtras().getParcelable(INTENT_GASOLINERA);
        // Link to view elements


        // Get Gas Station from the intent that triggered this activity


        // Set logo


        IDetailContract.Presenter presenter = new GasolineraDetailPresenter(g,this, db);
        presenter.init();

        getSupportActionBar().setTitle("Vista detallada");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Button btnAnhadirGasolineraFavoritas = findViewById(R.id.btnAnhadirGasolineraFavoritas);
        btnAnhadirGasolineraFavoritas.setOnClickListener(view -> {
            presenter.anhadeADb();
        });
        // Set Texts

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void setInfo(String municipio, String rotulo, String horario, String normal95,
                        String dieselA, String media, String direccion) {
        ImageView ivRotulo = findViewById(R.id.ivRotulo);
        int imageID = getResources().getIdentifier(rotulo, "drawable", getPackageName());

        if (imageID == 0 || TextUtils.isDigitsOnly(rotulo)) {
            imageID = getResources()
                    .getIdentifier("generic", "drawable", getPackageName());
        }
        if (imageID != 0) {
            ivRotulo.setImageResource(imageID);
        }
        TextView tvRotulo = findViewById(R.id.tvRotulo);
        TextView tvMunicipio = findViewById(R.id.tvMunicipio);
        TextView tvHorario = findViewById(R.id.tvHorario);
        TextView tvDireccion = findViewById(R.id.tvDireccion);
        TextView tvGasolina = findViewById(R.id.tvGasolina);
        TextView tvDiesel = findViewById(R.id.tvDiesel);
        TextView tvMedia = findViewById(R.id.tvMedia);
        tvRotulo.setText(rotulo.toUpperCase(Locale.ROOT));
        tvMunicipio.setText(municipio);
        tvDireccion.setText("Dirección: " + direccion);
        tvHorario.setText("Horario: "+ horario);
        tvGasolina.setText("Gasolina95: " + normal95);
        tvDiesel.setText("DiéselA: " + dieselA);
        tvMedia.setText("Media: " + media);
    }


}