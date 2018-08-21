package ifrs.canoas.ifhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import ifrs.canoas.model.MediaHarmonicaCalculator;

public class CalculaNotasActivity extends AppCompatActivity {
    private MediaHarmonicaCalculator calculator;
    private EditText etNota;
    private EditText etPeso;
    private EditText etLabel;
    private TextView tvNota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcula_notas);

        etNota = findViewById(R.id.etNota);
        etPeso = findViewById(R.id.etPeso);
        etLabel = findViewById(R.id.etLabel);
        tvNota = findViewById(R.id.tvNota);

        calculator = new MediaHarmonicaCalculator(this);
    }

    public void adicionarNota(View v){
        if(etNota.getText().toString().isEmpty()
                || etPeso.getText().toString().isEmpty()
                || etLabel.getText().toString().isEmpty()){
            Toast.makeText(this, getString(R.string.campo_vazio), Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            calculator.addNota(
                    etLabel.getText().toString(),
                    Double.parseDouble(etPeso.getText().toString()),
                    Double.parseDouble(etNota.getText().toString())
            );

            etLabel.setText(null);
            etPeso.setText(null);
            etNota.setText(null);

            Toast.makeText(this, getString(R.string.adicionada), Toast.LENGTH_SHORT).show();
        }catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void calcularMedia(View v){
        try{
            Double media = calculator.calcula();
            tvNota.setText(
                    String.format(
                            Locale.getDefault(),
                            "%s %.2f",
                            getString(R.string.tv_media_prefixo), media
                    )
            );
            calculator.clear();
        }catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
