package ifrs.canoas.ifhelper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

//Selecione a aba de todo e resolva todos eles inclusive esse
//TODO transformar essa tela no idioma Inglês e Português
//TODO adicionar um icone para cada Botão.
//TODO adicionar métodos para todas as funções da atividade principal
//TODO Adicionar remoto e submeter a atividade.(ver tutorial da aula).

//Basic activity com floatButton removido
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /**
     * Evento criado para servir como método de listener.
     *
     * @param v
     */
    public void calculaFaltas(View v) {

        Context contexto = getApplicationContext();
        Intent objIntent = new Intent(contexto, CalculaFaltasActivity.class);

        startActivity(objIntent);
    }

    public void calendarioAcademico(View v){
        Intent objIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com") );
        startActivity(objIntent);
    }

    //TODO add Botão calculadora de notas.

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
