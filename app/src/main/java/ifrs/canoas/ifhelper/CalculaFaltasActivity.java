package ifrs.canoas.ifhelper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Locale;

import ifrs.canoas.model.FaltaCalculator;

public class CalculaFaltasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcula_faltas);
    }

    public void calcula(View v) {
        EditText numCreditosTv = findViewById(R.id.numCreditos);
        EditText faltasTv = findViewById(R.id.diasComFalta);
        FaltaCalculator fc = new FaltaCalculator(Integer.parseInt(numCreditosTv.getText().toString()));
        fc.setTotalFaltas(Integer.parseInt(faltasTv.getText().toString()));

        Log.d("DEBUG", "Frequencia" + fc.calculaFrequencia());

        TextView resultado = findViewById(R.id.resultado);
        resultado.setText(String.format(Locale.getDefault(), "%d%%", fc.calculaFrequencia()));
    }
}
