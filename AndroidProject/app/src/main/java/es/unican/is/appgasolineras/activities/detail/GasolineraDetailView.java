package es.unican.is.appgasolineras.activities.detail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Locale;

import es.unican.is.appgasolineras.R;
import es.unican.is.appgasolineras.model.Gasolinera;

public class GasolineraDetailView extends AppCompatActivity implements IDetailContract.View {

    public static final String INTENT_GASOLINERA = "INTENT_GASOLINERA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gasolinera_detail_view);
        Gasolinera g = getIntent().getExtras().getParcelable(INTENT_GASOLINERA);
        // Link to view elements


        // Get Gas Station from the intent that triggered this activity


        // Set logo


        IDetailContract.Presenter presenter = new GasolineraDetailPresenter(g,this);
        presenter.init();

        getSupportActionBar().setTitle("Vista detallada");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Set Texts

    }

    public void setInfo(String municipio, String rotulo, String horario, String normal95,
                        String dieselA, String media) {
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
        TextView tvGasolina = findViewById(R.id.tvGasolina);
        TextView tvDiesel = findViewById(R.id.tvDiesel);
        TextView tvMedia = findViewById(R.id.tvMedia);
        tvRotulo.setText(rotulo.toUpperCase(Locale.ROOT));
        tvMunicipio.setText(municipio);
        tvHorario.setText("Horario: "+ horario);
        tvGasolina.setText("Gasolina95: " + normal95.substring(0,4) + " €");
        tvDiesel.setText("DiéselA: " + dieselA.substring(0,4) + " €");
        tvMedia.setText("Media: " + media.substring(0,4) + " €");
    }
}